package com.example.demo.error;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VotoException extends RuntimeException{

	private static final long serialVersionUID = -6734027569391630482L;
	
	public VotoException() {
		super("El voto debe ser como mínimo 1");
	
	}
	
}
