package com.example.demo.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Modelo para la acción de que un usuario vote un juego
 * @author Nacho
 *
 */
@Entity
public class Votacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long codigo; 
	
	@ManyToOne
    @JoinColumn(name = "juego_id")
	private Juego juego;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private String votante; 
	
	private int numVotosVotante;
	
	private int voto;
	
	@Column(name= "reseña")
	private String review;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
		
	

	public Votacion(Juego juego, Usuario usuario, int voto, String review) {
		super();
		this.juego = juego;
		this.usuario = usuario;
		this.voto = voto;
		this.review = review;
		this.fecha = new Date();
	}
	
	public Votacion(Juego juego, Usuario usuario, int voto) {
		super();
		this.juego = juego;
		this.usuario = usuario;
		this.voto = voto;
		this.fecha = new Date();
	}
	
	public Votacion() {
		super();
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

	public String getVotante() {
		return usuario.getNick();
	}

	public void setVotante(String votante) {
		this.votante = votante;
	}

	public int getNumVotosVotante() {
		return usuario.getNumVotos();
	}

	public void setNumVotosVotante(int numVotosVotante) {
		this.numVotosVotante = numVotosVotante;
	}

	@Override
	public int hashCode() {
		return Objects.hash(juego, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Votacion other = (Votacion) obj;
		return Objects.equals(juego, other.juego) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Votacion [juego=" + juego + ", usuario=" + usuario + ", voto=" + voto + ", review=" + review
				+ ", fecha=" + fecha + "]";
	} 
	
	
	

}
