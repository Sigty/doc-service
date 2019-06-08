package com.itacademy.servlet;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Part;
import com.itacademy.service.PartService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("findAllUser servlet-service<-dao");
        Integer page = 1;
        Integer recordsPerPage = 10;
        FilterPartBasicDto filter = getFilter(req, page, recordsPerPage);
        Integer noOfPages = getNoOfPages(req, page, recordsPerPage, filter);
        List<Part> allPartList = partService.getAll();
        getDataPart(req, allPartList);
        if ((req.getParameter("page") != null)) {
            filter.setPage(Integer.parseInt(req.getParameter("page")));
        }
        if ((req.getParameter("recordsPerPage") != null)) {
            filter.setRecordsPerPage(Integer.parseInt(req.getParameter("recordsPerPage")));
        }
        List<ViewPartBasicDto> partList = partService.findListPart(filter);
        req.setAttribute("filter", filter);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("partList", partList);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/part-list.jsp")
                .forward(req, resp);
    }

    private FilterPartBasicDto getFilter(HttpServletRequest req, Integer page, Integer recordsPerPage) {
        String partNumber = req.getParameter("partNumber");
        String type = req.getParameter("type");
        String sort = req.getParameter("sort");
        String manufacturer = req.getParameter("manufacturer");
        FilterPartBasicDto filter = FilterPartBasicDto.builder()
                .page(page)
                .recordsPerPage(recordsPerPage)
                .partNumber(partNumber)
                .type(type)
                .sort(sort)
                .manufacturer(manufacturer)
                .build();
        req.setAttribute("partNumber", partNumber);
        req.setAttribute("type", type);
        req.setAttribute("sort", sort);
        req.setAttribute("manufacturer", manufacturer);
        return filter;
    }

    private void getDataPart(HttpServletRequest req, List<Part> allPartList) {
        Set<String> sortList = new HashSet<>();
        allPartList.forEach(part -> sortList.add(part.getSort()));
        Set<String> typeList = new HashSet<>();
        allPartList.forEach(part -> typeList.add(part.getType()));
        Set<String> manufacturerList = new HashSet<>();
        allPartList.forEach(part -> manufacturerList.add(part.getManufacturer().getName()));
        System.out.println(manufacturerList);
        req.setAttribute("sortList", sortList);
        req.setAttribute("typeList", typeList);
        req.setAttribute("manufacturerList", manufacturerList);
    }

    private Integer getNoOfPages(HttpServletRequest req, Integer page, Integer recordsPerPage,
                                 FilterPartBasicDto filter) {
        if (req.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
        }
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        Integer noOfRecords = partService.findListAllPart(filter).size();
        Integer noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("noOfRecords", noOfRecords);
        return noOfPages;
    }
}

