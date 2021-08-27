package com.epam.esm.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

/**
 * Class represents controller which handle all generated exceptions.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_ERROR_CODE = "00";
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles ResourceNotFoundException
     *
     * @param exception the exception
     * @param locale the locale of HTTP request
     * @return the response message entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(exception.getMessageKey(),
                new String[]{exception.getIncorrectParameter()}, locale);
        logger.error(HttpStatus.NOT_FOUND, exception);
        return new ResponseMessage(errorMessage, HttpStatus.NOT_FOUND.value() + exception.getErrorCode());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseMessage handleResourceAlreadyExistsException (ResourceAlreadyExistsException exception, Locale locale){
        String errorMessage = messageSource.getMessage(exception.getMessageKey(),
                new String[]{exception.getIncorrectParameter()}, locale);
        logger.error(HttpStatus.CONFLICT, exception);
        return new ResponseMessage(errorMessage, HttpStatus.CONFLICT.value() + exception.getErrorCode());
    }
    /**
     * Handles IncorrectParamValueException
     *
     * @param exception the exception
     * @param locale the locale of HTTP request
     * @return the response message entity
     */
    @ExceptionHandler(IncorrectParamValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleIncorrectParamValueException(IncorrectParamValueException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(exception.getMessageKey(),
                new String[]{exception.getIncorrectParameter()}, locale);
        logger.error(HttpStatus.BAD_REQUEST, exception);
        return new ResponseMessage(errorMessage, HttpStatus.BAD_REQUEST.value() + exception.getErrorCode());
    }

    /**
     * Handles HttpMessageNotReadableException, MethodArgumentTypeMismatchException and
     * BindException
     *
     * @param exception the exception
     * @param locale the locale of HTTP request
     * @return the response message entity
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            BindException.class, HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleInvalidParamFormatException(Exception exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MessageKey.INVALID_PARAM_FORMAT, new String[]{}, locale);
        logger.error(HttpStatus.BAD_REQUEST, exception);
        return new ResponseMessage(errorMessage, HttpStatus.BAD_REQUEST.value() + DEFAULT_ERROR_CODE);
    }

    /**
     * Handles all others Exceptions
     *
     * @param exception the exception
     * @param locale the locale of HTTP request
     * @return the response message entity
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleException(Exception exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MessageKey.INTERNAL_SERVER_ERROR, new String[]{}, locale);
        logger.error(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        return new ResponseMessage(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value() + DEFAULT_ERROR_CODE);
    }
}


