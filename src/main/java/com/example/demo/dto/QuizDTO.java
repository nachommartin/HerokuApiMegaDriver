package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.model.Respuesta;

public class QuizDTO implements Serializable {
	
	private static final long serialVersionUID = 7218677703634423163L;
	
	private String name;
	private String enunciadoPregunta;
	private int ordenPregunta;
	private String textoRespuesta;
	private int ordenRespuesta;
	private Respuesta respuestaCorrecta;
	
	
	public QuizDTO() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEnunciadoPregunta() {
		return enunciadoPregunta;
	}


	public void setEnunciadoPregunta(String enunciadoPregunta) {
		this.enunciadoPregunta = enunciadoPregunta;
	}


	public int getOrdenPregunta() {
		return ordenPregunta;
	}


	public void setOrdenPregunta(int ordenPregunta) {
		this.ordenPregunta = ordenPregunta;
	}


	public String getTextoRespuesta() {
		return textoRespuesta;
	}


	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}


	public int getOrdenRespuesta() {
		return ordenRespuesta;
	}


	public void setOrdenRespuesta(int ordenRespuesta) {
		this.ordenRespuesta = ordenRespuesta;
	}


	public Respuesta getRespuestaCorrecta() {
		return respuestaCorrecta;
	}


	public void setRespuestaCorrecta(Respuesta respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	} 
	
	
	
	
	


}
