package de.rubnic.bandmanager.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Responds custom EntityNotFoundExeption to request when thrown
 *
 */
@ControllerAdvice
public class EntityNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(EntityNotFoundException ex) {
		return ex.getMessage();
	}
}
