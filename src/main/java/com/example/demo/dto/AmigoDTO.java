package com.example.demo.dto;

import java.io.Serializable;

/**
 * DTO utilizado para el seguimiento de un usuario y para que un follower vea los votos
 * del usuario al que sigue
 * @author Nacho
 *
 */
public class AmigoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5037276998537268932L;
	private String correoAskToFollow;
	private String correoTarget;
	
	
	public AmigoDTO() {
		super();
	}


	public String getCorreoAskToFollow() {
		return correoAskToFollow;
	}


	public void setCorreoAskToFollow(String correoAskToFollow) {
		this.correoAskToFollow = correoAskToFollow;
	}


	public String getCorreoTarget() {
		return correoTarget;
	}


	public void setCorreoTarget(String correoTarget) {
		this.correoTarget = correoTarget;
	} 
	
	
	
	
	
	

}
