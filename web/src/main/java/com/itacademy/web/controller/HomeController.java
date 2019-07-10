package com.itacademy.web.controller;

import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.dto.PartBigDto;
import com.itacademy.service.service.DocumentService;
import com.itacademy.service.service.PartService;
import com.itacademy.web.util.UtilPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class HomeController {

    private final PartService partService;
    private final DocumentService documentService;

    @GetMapping("/" + UtilPath.HOME)
    public String showHomePage(Model model) {
        log.info("getHomePart controller<-service<-dao");
        Pageable pageable = PageRequest.of(0, 10);
        Page<PartBigDto> partHomeList = partService.findAllByOrderByDateDesc(pageable);
        Pageable pageableD = PageRequest.of(0, 10);
        Page<DocumentMidDto> documentHomeList = documentService.findAllDocByOrderByDateDesc(pageableD);
        System.out.println(documentHomeList);
        model.addAttribute("partHomeList", partHomeList);
        model.addAttribute("documentHomeList", documentHomeList);
        return UtilPath.HOME;
    }
}