package com.itacademy.web.servlet;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.service.ManufacturerService;
import com.itacademy.service.PartService;
import com.itacademy.web.config.WebConfig;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j
@WebServlet("/part-list")
public class PartListServlet extends HttpServlet {

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
    private static PartService partService = context.getBean("partService", PartService.class);
    private static ManufacturerService manufacturerService = context.getBean("manufacturerService", ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("findAllUser servlet-service<-dao");
        Integer page = 1;
        Integer recordsPerPage = 10;
        FilterPartBasicDto filter = getFilter(req, page, recordsPerPage);
        Integer noOfPages = getNoOfPages(req, page, recordsPerPage, filter);
        getDataPart(req);
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

    private void getDataPart(HttpServletRequest req) {
        List<String> sortList = partService.sortList();
        List<String> typeList = partService.typeList();
        List<String> manufacturerList = manufacturerService.manufacturersName();
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
        Long noOfRecords = partService.findCountPart(filter);
        Integer noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("noOfRecords", noOfRecords);
        return noOfPages;
    }
}