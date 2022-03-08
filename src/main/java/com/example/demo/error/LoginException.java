package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginException extends RuntimeException{

	private static final long serialVersionUID = -6734027569391630482L;

	public LoginException() {
		super("La contraseña o el correo proporcionado no son correctos");
	}


}
