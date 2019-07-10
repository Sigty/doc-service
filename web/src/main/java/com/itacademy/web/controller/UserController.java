package com.itacademy.web.controller;

import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.database.exception.DaoException;
import com.itacademy.database.util.PredicateUtil;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.InvalidAccessException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.RoleService;
import com.itacademy.service.service.UserService;
import com.itacademy.web.util.UtilPage;
import com.itacademy.web.util.UtilPath;
import com.itacademy.web.util.UtilRole;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/" + UtilPath.USER_LIST)
    public String getAllUser(Model model,
                             @RequestParam(value = "pageList", defaultValue = "1") Integer pageList,
                             @RequestParam("sizeList") Optional<Integer> sizeList) {
        log.info("getAllUser controller<-service<-dao");
        int selListSize = sizeList.orElse(UtilPage.INITIAL_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pageList - 1, selListSize);
        Page<RegistrationUserDto> userList = userService.findAllUser(pageable);
        int totalPagesList = userList.getTotalPages();
        if (totalPagesList > 0) {
            List<Integer> pageTotalList = IntStream.rangeClosed(1, totalPagesList)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTotalList", pageTotalList);
        }
        List<String> allRoleUser = roleService.findAllRoleName();
        model.addAttribute("allRoleUser", allRoleUser);
        model.addAttribute("pageSizes", UtilPage.PAGE_SIZES);
        model.addAttribute("selListSize", selListSize);
        model.addAttribute("userList", userList);
        return UtilPath.USER_LIST;
    }

    @PostMapping("/" + UtilPath.USER_CHANGE_DETAIL)
    public String changeDetailUser(Model model, RegistrationUserDto userCurrent) throws NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userCurrent.setLogin(authentication.getName());
        String editFirstName = userCurrent.getFirstName();
        String editLastName = userCurrent.getLastName();
        if (PredicateUtil.predicateNoNullNoBlank(editFirstName)
                && PredicateUtil.predicateNoNullNoBlank(editLastName)) {
            try {
                userService.changeDetailUser(userCurrent);
            } catch (DaoException | EntityNotFoundException | DataIntegrityViolationException ex) {
                model.addAttribute("error", ex.getMessage());
                return UtilPath.REDIRECT + UtilPath.CABINET;
            }
            return UtilPath.REDIRECT + UtilPath.CABINET;
        } else if (userCurrent == null) {
            throw new NullPointerException("Obj.: " + userCurrent);
        }
        return UtilPath.REDIRECT + UtilPath.CABINET;
    }

    @PostMapping("/" + UtilPath.USER_CHANGE_ROLE + "/{id}")
    public String changeRoleUser(Model model, String role, @PathVariable("id") Integer id) throws InvalidAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(UtilRole.ADMIN));
        if (hasAdminRole) {
            try {
                userService.changeRoleUser(id, role);
            } catch (DaoException | EntityNotFoundException | DataIntegrityViolationException ex) {
                model.addAttribute("error", role);
            }
            return UtilPath.REDIRECT + UtilPath.USER_LIST;
        }
        throw new InvalidAccessException("Not access");
    }

    @GetMapping("/" + UtilPath.SINGIN)
    public String getRegistrationPage(Model model) {
        model.addAttribute("registrationUserDto", new RegistrationUserDto());
        model.addAttribute("error", "");
        return UtilPath.SINGIN;
    }

    @PostMapping("/" + UtilPath.SINGIN)
    public String registerNewUser(RegistrationUserDto registrationUser) {
        userService.saveUser(registrationUser);
        return UtilPath.REDIRECT + UtilPath.LOGIN;
    }

    @ExceptionHandler(ResponseException.class)
    public String errorNotUnique(Model model, ResponseException ex) {
        model.addAttribute("registrationUserDto", new RegistrationUserDto());
        model.addAttribute("error", ex.getMessage());
        return "singin";
    }
}