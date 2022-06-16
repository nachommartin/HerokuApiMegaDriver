package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends RuntimeException  {
	
	private static final long serialVersionUID = -6734027569391630482L;

	
	public QuizNotFoundException(Long ref) {
		super("No existe el quiz con la referencia: " + ref);
	}

}
