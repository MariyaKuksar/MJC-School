package com.epam.esm.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class HandlerException {
    private static final Logger logger = LogManager.getLogger();
    private final MessageSource messageSource;

    @Autowired
    public HandlerException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(exception.getMessageKey(),
                new String[]{exception.getIncorrectParameter()}, locale);
        logger.error(exception);
        return new ResponseMessage(errorMessage, HttpStatus.NOT_FOUND.value() + exception.getErrorCode());
    }
}

