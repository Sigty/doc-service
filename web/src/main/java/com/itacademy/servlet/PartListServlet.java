package com.itacademy.servlet;

import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Part;
import com.itacademy.service.PartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;

@Log4j
@WebServlet("/part-list")
public class PartListServlet extends HttpServlet {

    private PartService partService = PartService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("findAllUser servlet-service<-dao");
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Part> allPartList = partService.getAll();
        String partNumber = req.getParameter("partNumber");
        String sort = req.getParameter("sort");
        String manufacturer = req.getParameter("manufacturer");
        List<ViewPartBasicDto> partList = partService.findListPart(page, recordsPerPage,
                partNumber, sort, manufacturer);
        int noOfRecords = allPartList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("allPartList", allPartList);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("partList", partList);
        req.setAttribute("partNumber", partNumber);
        req.setAttribute("sort", sort);
        req.setAttribute("manufacturer", manufacturer);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/part-list.jsp")
                .forward(req, resp);
    }
}

