package com.itacademy.web.controller;

import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.dto.PartBigDto;
import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.service.service.DocumentService;
import com.itacademy.service.service.PartService;
import com.itacademy.service.service.UserService;
import com.itacademy.web.util.UtilPage;
import com.itacademy.web.util.UtilPath;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class CabinetController {

    private final PartService partService;
    private final DocumentService documentService;
    private final UserService userService;

    @GetMapping("/" + UtilPath.CABINET)
    public String showCabinetPage(Model model,
                               @RequestParam(value = "pagePart", defaultValue = "1") Integer pagePart,
                               @RequestParam("sizePart") Optional<Integer> sizePart,
                               @RequestParam(value = "pageDoc", defaultValue = "1") Integer pageDoc,
                               @RequestParam("sizeDoc") Optional<Integer> sizeDoc) {

        log.info("getHomePart controller<-service<-dao");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = authentication.getName();
        RegistrationUserDto userCurrent = userService.findUserByLogin(loginUser);
        int selPartSize = sizePart.orElse(UtilPage.INITIAL_PAGE_SIZE);
        Pageable pageable = PageRequest.of(pagePart - 1, selPartSize);
        Page<PartBigDto> partHomeList = partService.findAllByOrderByDateDesc(loginUser, pageable);
        int totalPagesPart = partHomeList.getTotalPages();
        if (totalPagesPart > 0) {
            List<Integer> pageTotalPart = IntStream.rangeClosed(1, totalPagesPart)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTotalPart", pageTotalPart);
        }
        int selDocSize = sizeDoc.orElse(UtilPage.INITIAL_PAGE_SIZE);
        Pageable pageableD = PageRequest.of(pageDoc - 1, selDocSize);
        Page<DocumentMidDto> documentHomeList = documentService.findAllDocByLoginUser(loginUser, pageableD);
        int totalPagesDoc = documentHomeList.getTotalPages();
        if (totalPagesDoc > 0) {
            List<Integer> pageTotalDoc = IntStream.rangeClosed(1, totalPagesDoc)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTotalDoc", pageTotalDoc);
        }
        model.addAttribute("pagePart", pagePart);
        model.addAttribute("pageDoc", pageDoc);
        model.addAttribute("userCurrent", userCurrent);
        model.addAttribute("partHomeList", partHomeList);
        model.addAttribute("documentHomeList", documentHomeList);
        model.addAttribute("pageSizes", UtilPage.PAGE_SIZES);
        model.addAttribute("selPartSize", selPartSize);
        model.addAttribute("selDocSize", selDocSize);
        return UtilPath.CABINET;
    }
}