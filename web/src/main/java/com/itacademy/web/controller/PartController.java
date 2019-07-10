package com.itacademy.web.controller;

import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.PartMidDto;
import com.itacademy.service.exception.DeleteException;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.ManufacturerService;
import com.itacademy.service.service.PartService;
import com.itacademy.web.util.UtilPath;
import com.itacademy.web.util.UtilRole;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PartController {

    private final PartService partService;
    private final ManufacturerService manufacturerService;

    @GetMapping("/" + UtilPath.PART_LIST)
    public String getPartList(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "recordsPerPage", required = false, defaultValue = "10") Integer recordsPerPage,
                              @RequestParam(value = "partNumber", required = false) String partNumber,
                              @RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "sort", required = false) String sort,
                              @RequestParam(value = "manufacturer", required = false) String manufacturer
    ) {
        log.info("PartList controller<-service<-dao");
        FilterPartBasicDto filter = getFilter(model, page, recordsPerPage, partNumber, type, sort, manufacturer);
        Integer noOfPages = getNoOfPages(model, page, recordsPerPage, filter);
        getDataPart(model);
        List<PartMidDto> partList = partService.findListPart(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("noOfPages", noOfPages);
        model.addAttribute("partList", partList);
        return UtilPath.PART_LIST;
    }

    private FilterPartBasicDto getFilter(Model model,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("recordsPerPage") Integer recordsPerPage,
                                         @RequestParam(value = "partNumber", defaultValue = "") String partNumber,
                                         @RequestParam(value = "type", defaultValue = "") String type,
                                         @RequestParam(value = "sort", defaultValue = "") String sort,
                                         @RequestParam(value = "manufacturer", defaultValue = "") String manufacturer) {
        FilterPartBasicDto filter = FilterPartBasicDto.builder()
                .page(page)
                .recordsPerPage(recordsPerPage)
                .partNumber(partNumber)
                .type(type)
                .sort(sort)
                .manufacturer(manufacturer)
                .build();
        model.addAttribute("partNumber", partNumber);
        model.addAttribute("type", type);
        model.addAttribute("sort", sort);
        model.addAttribute("manufacturer", manufacturer);
        return filter;
    }

    private Integer getNoOfPages(Model model, @RequestParam("page") Integer page,
                                 @RequestParam("recordsPerPage") Integer recordsPerPage,
                                 FilterPartBasicDto filter) {

        Long noOfRecords = partService.findCountPart(filter);
        Integer noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, noOfPages).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("recordsPerPage", recordsPerPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("noOfRecords", noOfRecords);
        return noOfPages;
    }

    private void getDataPart(Model model) {
        List<String> sortList = partService.sortList();
        List<String> typeList = partService.typeList();
        List<String> manufacturerList = manufacturerService.manufacturersName();
        model.addAttribute("sortList", sortList);
        model.addAttribute("typeList", typeList);
        model.addAttribute("manufacturerList", manufacturerList);
    }

    @PostMapping("/part-delete/{id}")
    public String deletePart(Model model, @PathVariable("id") Integer id,
                             @RequestHeader(value = "Referer",
                                     defaultValue = UtilPath.REDIRECT + UtilPath.CABINET, required = false) String urlResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = authentication.getName();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(UtilRole.ADMIN));
        if (hasAdminRole) {
            try {
                partService.deletePartById(id);
            } catch (DataIntegrityViolationException ex) {
                model.addAttribute("errorPart", ex.getMessage());
                throw new DeleteException(id.toString());
            }
        } else {
            try {
                partService.deletePartByIdAndLogin(id, loginUser);
            } catch (DataIntegrityViolationException ex) {
                model.addAttribute("errorPart", ex.getMessage());
                throw new DeleteException(id.toString());
            }
        }
        model.addAttribute("url", UtilPath.pathUrl(urlResult));
        return UtilPath.REDIRECT + UtilPath.pathUrl(urlResult);
    }

    @GetMapping("/" + UtilPath.PART_CREATE)
    public String getCreatePartPage(Model model) {
        model.addAttribute("createOrEditPartDto", new CreateOrEditPartDto());
        model.addAttribute("error", "");
        return UtilPath.PART_CREATE;
    }

    @PostMapping("/" + UtilPath.PART_CREATE)
    public String createPart(Model model, CreateOrEditPartDto createOrEditPartDto) {
        try {
            partService.savePart(createOrEditPartDto);
        } catch (ResponseException ex) {
            model.addAttribute("error", createOrEditPartDto.getPartNumber());
            return UtilPath.PART_CREATE;
        }
        model.addAttribute("createOrEditPartDto", createOrEditPartDto);
        return "redirect:/home";
    }

    @GetMapping("/" + UtilPath.PART_EDIT + "/{id}")
    public String getEditPartPage(Model model, @PathVariable("id") Integer id) {
        CreateOrEditPartDto createOrEditPartDto = partService.findPartById(id);
        model.addAttribute("id", id);
        model.addAttribute("createOrEditPartDto", createOrEditPartDto);
        return UtilPath.PART_EDIT;
    }

    @PostMapping("/" + UtilPath.PART_EDIT + "/{id}")
    public String editPart(Model model, @PathVariable("id") Integer id, CreateOrEditPartDto createOrEditPartDto) {
        try {
            partService.editPartById(id, createOrEditPartDto);
        } catch (UnexpectedRollbackException | ResponseException ex){
            model.addAttribute("error", createOrEditPartDto.getPartNumber());
            return UtilPath.PART_EDIT;
        }
        return UtilPath.REDIRECT + UtilPath.CABINET;
    }

    @ExceptionHandler(DeleteException.class)
    public String errorDelete(RedirectAttributes redirectAttrs, DeleteException ex) {
        redirectAttrs.addFlashAttribute("errorPart", ex.getMessage());
        return UtilPath.REDIRECT + UtilPath.CABINET;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String errorFindId(RedirectAttributes redirectAttrs, EntityNotFoundException ex) {
        redirectAttrs.addFlashAttribute("error", ex.getMessage());
        return UtilPath.REDIRECT + UtilPath.CABINET;
    }
}