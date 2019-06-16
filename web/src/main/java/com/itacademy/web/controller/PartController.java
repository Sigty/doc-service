package com.itacademy.web.controller;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.service.ManufacturerService;
import com.itacademy.service.PartService;
import com.itacademy.web.util.UtilText;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j
@Controller
public class PartController {

    @Autowired
    private PartService partService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/" + UtilText.PART_LIST)
    public String getPartList(Model model, HttpServletRequest req) {
        log.info("PartList controller<-service<-dao");
        Integer page = 1;
        Integer recordsPerPage = 10;
        FilterPartBasicDto filter = getFilter(model, page, recordsPerPage, req);
        Integer noOfPages = getNoOfPages(model, page, recordsPerPage, filter, req);
        getDataPart(model);
        if ((req.getParameter("page") != null)) {
            filter.setPage(Integer.parseInt(req.getParameter("page")));
        }
        if ((req.getParameter("recordsPerPage") != null)) {
            filter.setRecordsPerPage(Integer.parseInt(req.getParameter("recordsPerPage")));
        }
        List<ViewPartBasicDto> partList = partService.findListPart(filter);
        model.addAttribute("filter", filter);
        model.addAttribute("noOfPages", noOfPages);
        model.addAttribute("partList", partList);
        return UtilText.PART_LIST;
    }

    private FilterPartBasicDto getFilter(Model model, Integer page, Integer recordsPerPage, HttpServletRequest req) {
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
        model.addAttribute("partNumber", partNumber);
        model.addAttribute("type", type);
        model.addAttribute("sort", sort);
        model.addAttribute("manufacturer", manufacturer);
        return filter;
    }

    private Integer getNoOfPages(Model model, Integer page, Integer recordsPerPage, FilterPartBasicDto filter,
                                 HttpServletRequest req) {
        if (req.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
        }
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
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

}