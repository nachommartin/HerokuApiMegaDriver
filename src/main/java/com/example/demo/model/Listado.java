package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Listado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long referencia; 
	
	private boolean publico;
	
	private String nombre; 
	
	@JsonIgnore
	@OneToMany(orphanRemoval=false)
	private List<Juego> juegos;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private int numJuego;

	public Listado(boolean publico, String nombre, Usuario usuario) {
		super();
		this.publico = publico;
		this.nombre = nombre;
		this.juegos = new ArrayList<Juego>();
		this.usuario = usuario;

	}

	public Listado() {
		super();
		this.juegos = new ArrayList<Juego>();

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

	public List<Juego> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<Juego> juegos) {
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
