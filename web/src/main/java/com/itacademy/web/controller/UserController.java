package com.itacademy.web.controller;

import com.itacademy.database.entity.User;
import com.itacademy.service.UserService;
import com.itacademy.web.util.UtilText;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/" + UtilText.USER_LIST)
    public String getAllProducts(Model model) {
        log.info("findAllUser controller<-service<-dao");
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return UtilText.USER_LIST;
    }
}