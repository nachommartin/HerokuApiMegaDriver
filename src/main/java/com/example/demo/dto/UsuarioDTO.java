package com.example.demo.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972569443531641912L;
	
	private String correoSource;
	private String nick;
	private String password;
	private String ciudad;
	
	
	public UsuarioDTO() {
		super();
	}


	public String getCorreoSource() {
		return correoSource;
	}


	public void setCorreoSource(String correoSource) {
		this.correoSource = correoSource;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	} 
	
	
	

	
	
	
	
}
