package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long ref; 
	
	private String texto;

	@JsonIgnore
	@ManyToOne     
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	private Integer orden;

	@JsonIgnore
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Respuesta> respuestas;

	@OneToOne
	private Respuesta respuestaCorrect;
	
	private Boolean isValid = false;

	public Question(String texto, Quiz quiz, Integer orden) {
		super();
		this.texto = texto;
		this.quiz= quiz;
		this.orden = orden;
		this.respuestas= new ArrayList<Respuesta>();
	}

	public Question() {
		super();
		this.respuestas= new ArrayList<Respuesta>();
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuesta(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public Respuesta getRespuestaCorrect() {
		return respuestaCorrect;
	}

	public void setRespuestaCorrect(Respuesta respuestaCorrect) {
		this.respuestaCorrect = respuestaCorrect;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public long getRef() {
		return ref;
	}
	
	
	


}
