package by.itacademy.servlet;

import by.itacademy.database.entity.User;
import by.itacademy.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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
        List<User> userList = userService.findAll().stream().collect(Collectors.toList());
        req.setAttribute("userList", userList);

        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/user-list.jsp")
                .forward(req, resp);
    }

}

