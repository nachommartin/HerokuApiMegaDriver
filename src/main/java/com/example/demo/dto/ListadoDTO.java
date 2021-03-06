package com.example.demo.dto;

import java.io.Serializable;

public class ListadoDTO implements Serializable{
	
	private static final long serialVersionUID = -7471214343776999170L;
	
	private String correo;
	private boolean publico;
	private String nombre;
	private long refJuego;
	
	
	public ListadoDTO(String correo, boolean publico, String nombre, long refJuego) {
		super();
		this.correo = correo;
		this.publico = publico;
		this.nombre = nombre;
		this.refJuego = refJuego;

	}
	

	public ListadoDTO() {
		super();
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public boolean isPublico() {
		return publico;
	}


	public void setPublico(boolean publico) {
		this.publico = publico;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public long getRefJuego() {
		return refJuego;
	}




	public void setRefJuego(long refJuego) {
		this.refJuego = refJuego;
	} 
	
	


}
