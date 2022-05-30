package com.example.demo.dto;

import java.io.Serializable;

public class PuntosDTO implements Serializable {
	
	private static final long serialVersionUID = 7218677703634423163L;
	
	private Integer puntos;
	
	

	public PuntosDTO() {
		super();
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	
	

}
