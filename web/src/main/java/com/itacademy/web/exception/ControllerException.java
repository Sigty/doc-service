package com.itacademy.web.exception;

import com.itacademy.database.exception.DaoException;
import com.itacademy.service.exception.InvalidAccessException;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(NullPointerException.class)
    public String catchNullPointerException(Model model, NullPointerException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DaoException.class)
    public String catchDaoException(Model model, DaoException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String catchNoSuchElementException(Model model, NoSuchElementException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoResultException.class)
    public String catchNoResultException(Model model, NoResultException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(PersistenceException.class)
    public String catchPersistenceException(Model model, PersistenceException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidAccessException.class)
    public String catchInvalidAccessException(Model model, InvalidAccessException ex) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
