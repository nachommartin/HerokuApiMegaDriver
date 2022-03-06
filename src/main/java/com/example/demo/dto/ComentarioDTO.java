package com.example.demo.dto;

import java.io.Serializable;

/**
 * DTO para el envío de un comentario de un usuario a otro, así como para su posterior borrado
 * @author Nacho
 *
 */
public class ComentarioDTO implements Serializable{

	
	private static final long serialVersionUID = -7471214343776999170L;
	
	private String correoEmisor;
	private String receptor;
	private String texto;
	
	
	
	
	public ComentarioDTO() {
		super();
	}

	public String getCorreoEmisor() {
		return correoEmisor;
	}
	
	public void setCorreoEmisor(String correoEmisor) {
		this.correoEmisor = correoEmisor;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	
	

	
	
	

}
