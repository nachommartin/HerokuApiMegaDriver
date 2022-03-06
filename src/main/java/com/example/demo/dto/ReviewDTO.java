package com.example.demo.dto;

import java.io.Serializable;

/**
 * DTO para la creación de una reseña
 * @author Nacho
 *
 */
public class ReviewDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7218677703634423163L;
	
	private String correo;
	private String review;
	
	
	public ReviewDTO() {
		super();
	}




	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	} 
	
	
	

}
