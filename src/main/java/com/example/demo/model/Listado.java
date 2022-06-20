package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Listado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long referencia; 
	
	private boolean publico;
	
	private String nombre; 
	
	@JsonIgnore
	@ManyToMany
	private Set<Juego> juegos;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private int numJuego;

	public Listado(boolean publico, String nombre, Usuario usuario) {
		super();
		this.publico = publico;
		this.nombre = nombre;
		this.juegos = new HashSet<Juego>();
		this.usuario = usuario;

	}

	public Listado() {
		super();
		this.juegos = new HashSet<Juego>();

	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public Set<Juego> getJuegos() {
		return juegos;
	}

	public void setJuegos(Set<Juego> juegos) {
		this.juegos = juegos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getReferencia() {
		return referencia;
	}

	public int getNumJuego() {
		return this.getJuegos().size();
	}

	public void setNumJuego(int numJuego) {
		this.numJuego = numJuego;
	}
	
	
	
	
	

}
