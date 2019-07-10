package com.itacademy.service.service;

import com.itacademy.database.converter.UserToRegistrationUserDto;
import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.UserSpecialty;
import com.itacademy.database.exception.DaoException;
import com.itacademy.database.repository.RoleRepository;
import com.itacademy.database.repository.UserRepository;
import com.itacademy.database.repository.UserSpecialtyRepository;
import com.itacademy.database.util.PredicateUtil;
import static com.itacademy.database.util.PredicateUtil.predicateNoEqNoNull;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.util.ExceptionText;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@CacheConfig(cacheNames = "user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(readOnly = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserSpecialtyRepository userSpecialtyRepository;
    private final UserToRegistrationUserDto toRegistrationUserDto;

    @Cacheable
    public Page<RegistrationUserDto> findAllUser(Pageable pageable) {
        Page<User> allUser = userRepository.findAllByOrderById(pageable);
        return new PageImpl<>(allUser
                .stream()
                .map(toRegistrationUserDto::convert)
                .collect(Collectors.toList()), pageable, (int) allUser.getTotalElements());
    }

    @Cacheable
    public RegistrationUserDto findUserByLogin(String login) throws EntityNotFoundException {
        return Optional.of(login)
                .map(userRepository::findByLoginOrderByLogin)
                .map(toRegistrationUserDto::convert)
                .orElseThrow(() -> new EntityNotFoundException(login + "not found"));
    }

    @Transactional
    @CachePut
    public void saveUser(RegistrationUserDto userReg) throws ResponseException {
        UserSpecialty userSpecialty = UserSpecialty.builder()
                .firstName(userReg.getFirstName())
                .lastName(userReg.getLastName())
                .email(userReg.getEmail())
                .build();
        String encodePassword = new BCryptPasswordEncoder().encode(userReg.getPassword());
        User user = User.builder()
                .login(userReg.getLogin().toLowerCase())
                .password(encodePassword)
                .userSpecialty(userSpecialty)
                .role(roleRepository.findRoleByRole("user"))
                .build();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException(ExceptionText.exceptionTextUtil((ex.getCause().getCause().getMessage())));
        }
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void changeRoleUser(Integer id, String roleUser) throws DaoException, EntityNotFoundException {
        User defaultUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID =" + id));
        if (predicateNoEqNoNull(roleUser, defaultUser.getRole().getRole())) {
            Role roleId = roleRepository.findByRole(roleUser)
                    .orElseThrow(() -> new EntityNotFoundException("role =" + roleUser));
            userRepository.updateRoleUser(id, roleId);
        }
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void changeDetailUser(RegistrationUserDto editUser) {
        RegistrationUserDto defaultUser = findUserByLogin(editUser.getLogin());
        Integer specialtyId = defaultUser.getId();
        String defaultFirstName = defaultUser.getFirstName();
        String defaultLastName = defaultUser.getLastName();
        if (PredicateUtil.predicateNoEqNoNullNoBlank(editUser.getFirstName(), defaultFirstName)
                && PredicateUtil.predicateNoEqNoNullNoBlank(editUser.getLastName(), defaultLastName)) {
            userSpecialtyRepository.updateSpecialtyUser(specialtyId, editUser.getFirstName(), editUser.getLastName());
        } else if (PredicateUtil.predicateNoEqNoNullNoBlank(editUser.getFirstName(), defaultFirstName)) {
            userSpecialtyRepository.updateFirstNameUser(specialtyId, editUser.getFirstName());
        } else if (PredicateUtil.predicateNoEqNoNullNoBlank(editUser.getLastName(), defaultLastName)) {
            userSpecialtyRepository.updateLastNameUser(specialtyId, editUser.getLastName());
        }
    }
}