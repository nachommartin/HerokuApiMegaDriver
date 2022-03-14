package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MensajeException extends RuntimeException{
	
	private static final long serialVersionUID = -6734027569391630482L;
	
	public MensajeException(String mssg) {
		super("El mensaje: (" + mssg+") tiene menos de dos carácteres");
	}

}
