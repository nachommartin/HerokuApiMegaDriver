package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException {
	
private static final long serialVersionUID = -6734027569391630482L;

	
	public QuestionNotFoundException(Long ref) {
		super("No existe la pregunta con la referencia: " + ref);
	}

}
