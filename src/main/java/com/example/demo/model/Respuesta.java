package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Respuesta {
	

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long referencia; 
	
	private String text;

	@ManyToOne
	@JsonIgnore
	private Question question;

	private Integer orden;

	public Respuesta(String text, Question question, Integer orden) {
		super();
		this.text = text;
		this.question = question;
		this.orden = orden;
	}

	public Respuesta() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public long getReferencia() {
		return referencia;
	}
	
	
	

}
