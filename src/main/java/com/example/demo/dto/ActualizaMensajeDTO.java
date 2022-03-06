package com.example.demo.dto;

import java.io.Serializable;

/**
 * DTO usado para la edición de un comentario enviado
 * @author Nacho
 *
 */
public class ActualizaMensajeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9176634427698884047L;
	private String texto;
	private String receptor; 
	
	
	public ActualizaMensajeDTO() {
		super();
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
