package com.itacademy.service.service;

import com.itacademy.database.converter.PartToPartBigDto;
import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.PartBigDto;
import com.itacademy.database.dto.PartMidDto;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Part;
import com.itacademy.database.exception.DaoException;
import com.itacademy.database.repository.ManufacturerRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.database.repository.UserRepository;
import com.itacademy.database.util.PredicateUtil;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.util.ExceptionText;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(readOnly = true)
public class PartService {

    private final PartRepository partRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final UserRepository userRepository;
    private final PartToPartBigDto partMidDto;

    @Cacheable("parts")
    public List<PartMidDto> findListPart(FilterPartBasicDto filter) {
        return partRepository.filterListPart(filter);
    }

    @Cacheable(value = "parts", key = "{#root.methodName ,#root.args[0]}")
    public Long findCountPart(FilterPartBasicDto filter) {
        return partRepository.findCountPart(filter);
    }

    @Cacheable(value = "sortTypes", key = "#root.methodName")
    public List<String> sortList() {
        return partRepository.findAllSort();
    }

    @Cacheable(value = "sortTypes", key = "#root.methodName")
    public List<String> typeList() {
        return partRepository.findAllType();
    }

    @Cacheable(value = "parts")
    public Page<PartBigDto> findAllByOrderByDateDesc(Pageable pageable) {
        Page<Part> partPage = partRepository.findAllByOrderByCreatePartDateDesc(pageable);
        return new PageImpl<>(partPage
                .stream()
                .map(partMidDto::convert)
                .collect(Collectors.toList()), pageable, (int) partPage.getTotalElements());
    }

    @Cacheable(value = "parts")
    public Page<PartBigDto> findAllByOrderByDateDesc(String login, Pageable pageable) {
        Page<Part> partPage = partRepository.findAllByPartUser_LoginOrderByCreatePartDateDesc(login, pageable);
        return new PageImpl<>(partPage
                .stream()
                .map(partMidDto::convert)
                .collect(Collectors.toList()), pageable, (int) partPage.getTotalElements());
    }

    @Transactional
    @CacheEvict(value = "parts", allEntries = true)
    public void deletePartById(Integer id) {
        partRepository.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "parts", allEntries = true)
    public void deletePartByIdAndLogin(Integer id, String login) {
        partRepository.deleteByIdAndPartUser_Login(id, login);
    }

    @Transactional
    @CachePut("parts")
    public void savePart(CreateOrEditPartDto createPart) throws ResponseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = authentication.getName();
        Manufacturer manufacturer;
        try {
            manufacturer = manufacturerRepository.findByName(createPart.getManufacturer().toLowerCase().trim())
                    .orElseThrow(DaoException::new);
        } catch (DaoException ex) {
            manufacturer = manufacturerRepository.save(new Manufacturer(createPart.getManufacturer().toLowerCase().trim()));
        }
        Part newPart = Part.builder()
                .partNumber(createPart.getPartNumber())
                .description(createPart.getDescription().toLowerCase().trim())
                .type(createPart.getType().toLowerCase().trim())
                .sort(createPart.getSort().toLowerCase().trim())
                .manufacturer(manufacturer)
                .createPartDate(OffsetDateTime.now(ZoneId.systemDefault()))
                .partUser(userRepository.findByLoginOrderByLogin(loginUser))
                .build();
        try {
            partRepository.save(newPart);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException(ExceptionText.exceptionTextUtil((ex.getCause().getCause().getMessage())));
        }
    }

    @Cacheable(value = "parts")
    public CreateOrEditPartDto findPartById(Integer id) {
        return partRepository.findPartById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID =" + id));
    }

    @Transactional
    @CacheEvict(value = "parts", allEntries = true)
    public void editPartById(Integer id, CreateOrEditPartDto createOrEditPartDto) throws ResponseException {
        CreateOrEditPartDto partDefault = partRepository.findPartById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID =" + id));

        String partNumber = createOrEditPartDto.getPartNumber().trim();
        String sort = createOrEditPartDto.getSort().toLowerCase().trim();
        String type = createOrEditPartDto.getType().toLowerCase().trim();
        String description = createOrEditPartDto.getDescription().trim();
        String manufacturer = createOrEditPartDto.getManufacturer().trim();

        String defaultPartNumber = partDefault.getPartNumber();
        String defaultSort = partDefault.getSort().trim();
        String defaultType = partDefault.getType().trim();
        String defaultDescription = partDefault.getDescription().trim();
        String defaultManufacturer = partDefault.getManufacturer().trim();

        if (PredicateUtil.predicateNoEqNoNullNoBlank(partNumber, defaultPartNumber)) {
            try {
                partRepository.updatePartNumberPart(id, partNumber);
            } catch (UnexpectedRollbackException | DataIntegrityViolationException ex) {
                new ResponseException(partNumber);
            }
        }
        if (PredicateUtil.predicateNoEqNoNullNoBlank(sort, defaultSort)) {
            partRepository.updateSortPart(id, sort);
        }
        if (PredicateUtil.predicateNoEqNoNullNoBlank(type, defaultType)) {
            partRepository.updateTypePart(id, type);
        }
        if (PredicateUtil.predicateNoEqNoNullNoBlank(description, defaultDescription)) {
            partRepository.updateDescriptionPart(id, description);
        }
        if (PredicateUtil.predicateNoEqNoNullNoBlank(manufacturer, defaultManufacturer)) {
            Manufacturer manufacturerFind;
            try {
                manufacturerFind = manufacturerRepository.findByName(manufacturer.toLowerCase().trim())
                        .orElseThrow(() -> new EntityNotFoundException(manufacturer));
            } catch (EntityNotFoundException ex) {
                manufacturerFind = manufacturerRepository.save(new Manufacturer(createOrEditPartDto.getManufacturer().toLowerCase().trim()));
            }
            partRepository.updateManufactureIdPart(id, manufacturerFind);
        }
    }
}