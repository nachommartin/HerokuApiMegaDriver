package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Quiz {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long ref; 
	
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private Set<Question> preguntas;

	private int numPreguntas;
	
	public Quiz(String name) {
		super();
		this.name = name;
		this.preguntas= new HashSet<Question>();

	}


	public Quiz() {
		super();
		this.preguntas= new HashSet<Question>();


	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Question> getPreguntas() {
		return preguntas;
	}


	public void setPreguntas(Set<Question> preguntas) {
		this.preguntas = preguntas;
	}


	public long getRef() {
		return ref;
	}
	
	
	
	
	public int getNumPreguntas() {
		return numPreguntas;
	}


	public void setNumPreguntas(int numPreguntas) {
		this.numPreguntas = numPreguntas;
	}


	@Override
	public String toString() {
		return "Quiz [ref=" + ref + ", name=" + name + ", preguntas=" + preguntas + "]";
	}
	

	
	

}
