package com.example.demo.model;

import java.io.Serializable;

/**
 * POJO para el Login 
 * @author Nacho
 *
 */
public class LoginCredentials implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String correo;
    private String password;
    
    
        
	public LoginCredentials(String correo, String password) {
		super();
		this.correo = correo;
		this.password = password;
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    

}
