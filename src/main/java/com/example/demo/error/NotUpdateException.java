package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotUpdateException extends RuntimeException {

private static final long serialVersionUID = -6734027569391630482L;
	
	public NotUpdateException() {
		super("Rellena un campo al menos");
	}

}
