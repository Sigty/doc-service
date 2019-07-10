package com.itacademy.web.controller;

import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.service.exception.DeleteException;
import com.itacademy.service.exception.InvalidAccessException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.DocPartService;
import com.itacademy.service.service.DocumentService;
import com.itacademy.web.util.UtilPage;
import com.itacademy.web.util.UtilPath;
import com.itacademy.web.util.UtilRole;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class DocumentController {

    private final DocumentService documentService;
    private final DocPartService docPartService;

    @GetMapping("/" + UtilPath.DOCUM_LIST)
    public String showDocumentPage(Model model,
                                   @RequestParam(value = "pageDoc", defaultValue = "1") String pageDoc,
                                   @RequestParam("sizeDoc") Optional<Integer> sizeDoc) {

        log.info("getDocument controller<-service<-dao");
        int selDocSize = sizeDoc.orElse(UtilPage.INITIAL_PAGE_SIZE);
        Pageable pageableD = PageRequest.of(Integer.parseInt(pageDoc) - 1, selDocSize);
        Page<DocumentMidDto> documentHomeList = documentService.findAllDocByOrderByDateDesc(pageableD);
        int totalPagesDoc = documentHomeList.getTotalPages();
        if (totalPagesDoc > 0) {
            List<Integer> pageTotalDoc = IntStream.rangeClosed(1, totalPagesDoc)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTotalDoc", pageTotalDoc);
        }
        model.addAttribute("documentHomeList", documentHomeList);
        model.addAttribute("pageSizes", UtilPage.PAGE_SIZES);
        model.addAttribute("selDocSize", selDocSize);
        return UtilPath.DOCUM_LIST;
    }

    @GetMapping("/" + UtilPath.DOCUM_CREATE)
    public String getCreateDocumentPage(Model model) {
        model.addAttribute("documentNew", new DocumentMidDto());
        model.addAttribute("docType", docPartService.docTypeSet());
        model.addAttribute("error", "");
        return UtilPath.DOCUM_CREATE;
    }

    @PostMapping("/" + UtilPath.DOCUM_CREATE)
    public String createDocument(Model model, DocumentMidDto documentNew) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = authentication.getName();
        try {
            documentService.saveDocument(documentNew, loginUser);
        } catch (ResponseException ex) {
            model.addAttribute("documentNew", documentNew);
            model.addAttribute("docType", docPartService.docTypeSet());
            model.addAttribute("error", documentNew.getNumber());
            return UtilPath.DOCUM_CREATE;
        }
        model.addAttribute("documentNew", documentNew);
        return UtilPath.REDIRECT + UtilPath.DOCUM_LIST;
    }

    @GetMapping("/" + UtilPath.DOCUM_EDIT + "/{id}")
    public String getEditDocumentPage(Model model, @PathVariable("id") Integer id) {
        DocumentMidDto documentNew = documentService.findDocumentById(id);
        model.addAttribute("id", id);
        model.addAttribute("docType", docPartService.docTypeSet());
        model.addAttribute("documentNew", documentNew);
        return UtilPath.DOCUM_EDIT;
    }

    @PostMapping("/" + UtilPath.DOCUM_EDIT + "/{id}")
    public String editDocumentPage(Model model, @PathVariable("id") Integer id, DocumentMidDto documentNew) {
        try {
            documentService.editDocumentById(id, documentNew);
        } catch (UnexpectedRollbackException | ResponseException ex) {
            model.addAttribute("documentNew", documentNew);
            model.addAttribute("docType", docPartService.docTypeSet());
            model.addAttribute("error", documentNew.getNumber());
            return UtilPath.DOCUM_EDIT;
        }
        model.addAttribute("documentNew", documentNew);
        return UtilPath.REDIRECT + UtilPath.DOCUM_LIST;
    }

    @PostMapping("/" + UtilPath.DOCUM_DELETE + "/{id}")
    public String deleteDocument(Model model, @PathVariable("id") Integer id,
                                 @RequestHeader(value = "Referer",
                                         defaultValue = UtilPath.REDIRECT + UtilPath.CABINET, required = false) String urlResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(UtilRole.ADMIN));
        if (hasAdminRole) {
            try {
                documentService.deleteDocumentById(id);
            } catch (DataIntegrityViolationException ex) {
                model.addAttribute("errorDoc", ex.getMessage());
                throw new DeleteException(id.toString());
            }
        } else {
            throw new InvalidAccessException("Not access");
        }

        if (UtilPath.checkUrl(urlResult, UtilPath.CABINET, UtilPath.DOCUM_LIST)) {
            model.addAttribute("url", UtilPath.pathUrl(urlResult));
            return UtilPath.REDIRECT + UtilPath.pathUrl(urlResult);
        } else {
            return UtilPath.REDIRECT + UtilPath.DOCUM_LIST;
        }
    }

    @ExceptionHandler(DeleteException.class)
    public String errorDelete(RedirectAttributes redirectAttrs, DeleteException ex) {
        redirectAttrs.addFlashAttribute("errorDoc", ex.getMessage());
        return UtilPath.REDIRECT + UtilPath.CABINET;
    }
}
