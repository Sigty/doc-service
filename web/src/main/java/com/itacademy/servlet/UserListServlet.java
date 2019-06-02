package com.itacademy.servlet;

import com.itacademy.database.entity.User;
import com.itacademy.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("findAllUser servlet-service<-dao");
        List<User> userList = userService.findAll();
        req.setAttribute("userList", userList);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/user-list.jsp")
                .forward(req, resp);
    }

}

