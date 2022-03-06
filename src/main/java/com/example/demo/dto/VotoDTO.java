package com.example.demo.dto;

import java.io.Serializable;

/**
 * DTO para el voto de un juego
 * @author Nacho
 *
 */
public class VotoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6783565581511893723L;
	
	
	private int voto;
	private String correo;
	
	
	public VotoDTO() {
		super();
	}


	public int getVoto() {
		return voto;
	}


	public void setVoto(int voto) {
		this.voto = voto;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
	
	
	
	

}
