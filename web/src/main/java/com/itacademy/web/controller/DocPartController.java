package com.itacademy.web.controller;

import com.itacademy.database.dto.DocPartMidDto;
import com.itacademy.database.dto.PartBigDto;
import com.itacademy.service.exception.DeleteException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.DocPartService;
import com.itacademy.service.service.PartService;
import com.itacademy.web.util.UtilPage;
import com.itacademy.web.util.UtilPath;
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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class DocPartController {

    private final DocPartService docPartService;
    private final PartService partService;

    @GetMapping("/" + UtilPath.DOCPART_DETAIL + "/{id}")
    public String getEditDocumentPage(Model model, @PathVariable("id") Integer id,
                                      @RequestParam(value = "pageDoc", defaultValue = "1") String pageDoc,
                                      @RequestParam("sizeDoc") Optional<Integer> sizeDoc) {

        log.info("getDocPart byId controller<-service<-dao");
        int selDocSize = sizeDoc.orElse(UtilPage.INITIAL_PAGE_SIZE);
        Pageable pageable = PageRequest.of(Integer.parseInt(pageDoc) - 1, selDocSize);
        Page<DocPartMidDto> docPartByDocument = docPartService.findDocPartByDocumentId(id, pageable);
        int totalPagesDoc = docPartByDocument.getTotalPages();
        if (totalPagesDoc > 0) {
            List<Integer> pageTotalDoc = IntStream.rangeClosed(1, totalPagesDoc)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTotalDoc", pageTotalDoc);
        }
        Page<PartBigDto> allPart = partService.findAllByOrderByDateDesc(Pageable.unpaged());
        System.out.println(id);
        model.addAttribute("id", id);
        model.addAttribute("allPart", allPart);
        model.addAttribute("pageDoc", pageDoc);
        model.addAttribute("pageSizes", UtilPage.PAGE_SIZES);
        model.addAttribute("selDocSize", selDocSize);
        model.addAttribute("docPartByDocument", docPartByDocument);
        return UtilPath.DOCPART_DETAIL;
    }

    @PostMapping("/" + UtilPath.DOCPART_DELETE + "/{id}")
    public String deleteDocPart(Model model, @PathVariable("id") Integer partId, Integer docId,
                                @RequestHeader(value = "Referer",
                                        defaultValue = UtilPath.REDIRECT + UtilPath.CABINET, required = false) String urlResult) {

        try {
            docPartService.deleteDocPart(docId, partId);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorDoc", ex.getMessage());
            throw new DeleteException(docId.toString() + partId.toString());
        }
        if (UtilPath.checkUrl(urlResult, UtilPath.DOCPART_DETAIL, UtilPath.CABINET)) {
            model.addAttribute("url", UtilPath.pathUrl(urlResult));
            return UtilPath.REDIRECT + UtilPath.pathUrl(urlResult);
        } else {
            return UtilPath.REDIRECT + UtilPath.DOCPART_DETAIL;
        }
    }

    @PostMapping("/" + UtilPath.DOCPART_EDIT)
    public String editDocumentPage(Model model, Integer docId, Integer partId, Integer quantityParts) {

        try {
            docPartService.updateQuantityParts(docId, partId, quantityParts);
        } catch (UnexpectedRollbackException | ResponseException ex) {
            model.addAttribute("error", ex.getMessage());
            return UtilPath.REDIRECT + UtilPath.DOCPART_DETAIL + "/" + docId;
        }
        return UtilPath.REDIRECT + UtilPath.DOCPART_DETAIL + "/" + docId;
    }

    @PostMapping("/" + UtilPath.DOCPART_ADD + "/{id}")
    public String addPartPage(Model model, @PathVariable("id") Integer docId,
                              Integer part,
                              Integer quantityAdd) {
        try {
            docPartService.saveDocPartById(docId, part, quantityAdd);
        } catch (UnexpectedRollbackException | ResponseException ex) {
            model.addAttribute("error", part);
            return UtilPath.DOCPART_DETAIL + "/" + docId;
        }

        return UtilPath.REDIRECT + UtilPath.DOCPART_DETAIL + "/" + docId;
    }
}
