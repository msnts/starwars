package com.msantos.starwars.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
		
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(UnprocessableEntityException.class)
    public void springHandleUnprocessableEntity(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
	
	@ExceptionHandler(BadRequestException.class)
    public void springHandleBadRequest(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
