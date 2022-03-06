package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo para el usuario
 * @author Nacho
 *
 */
@Entity
public class Usuario {
	
	@Id
	private String correo;
	
	private String nick;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Votacion> votos;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuarioReceptor", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Comentario> comentarios;
	
	@JsonIgnore
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Amistad> amigos;
	
	public Usuario(String correo, String nick, String password) {
		super();
		this.correo = correo;
		this.nick = nick;
		this.password = password;
		this.votos= new ArrayList<Votacion>();
		this.comentarios= new ArrayList<Comentario>();
		this.amigos= new ArrayList<Amistad>();



	}
	
	public Usuario() {
		super();
		this.votos= new ArrayList<Votacion>();
		this.comentarios= new ArrayList<Comentario>();
		this.amigos= new ArrayList<Amistad>();
	}
	
	

	public String getCorreo() {
		return correo;
	}
	
	public List<Votacion> getVotos() {
		return votos;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public List<Amistad> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Amistad> amigos) {
		this.amigos = amigos;
	}
		
	

	public void setVotos(List<Votacion> votos) {
		this.votos = votos;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo, votos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(correo, other.correo) && Objects.equals(votos, other.votos);
	}

	@Override
	public String toString() {
		return "Usuario " + correo;
	}

	
	
	
	
	
	

}
