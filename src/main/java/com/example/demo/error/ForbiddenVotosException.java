package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenVotosException extends RuntimeException {
	
	private static final long serialVersionUID = -6734027569391630482L;

	
	public ForbiddenVotosException(String user) {
		super("No tienes acceso a ver los votos del usuario: " + user);
	}


}
