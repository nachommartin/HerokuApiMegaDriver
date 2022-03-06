package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JuegoNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -6734027569391630482L;
	
	public JuegoNotFoundException(long ref) {
		super("No se puede encontrar el juego con la siguiente referencia: " + ref);
	}
	
	public JuegoNotFoundException() {
		super("No se ha encontrado el juego en nuestra base de datos. Asegúrese de escribir correctamente el título");
	}

}
