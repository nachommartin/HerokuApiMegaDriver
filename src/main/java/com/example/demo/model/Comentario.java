package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Modelo de comentario que tiene un usuario emisor y un usuario receptor
 * @author Nacho
 *
 */
@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long codigoComentario;
	
	private String texto;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuarioReceptor;
	
	 @ManyToOne()
	 @JoinColumn(name = "emisor_id")
	 private Usuario usuarioEmisor;
	
	

	public Comentario(String texto, Usuario usuarioSource, Usuario usuarioTarget) {
		super();
		this.texto = texto;
		this.usuarioEmisor = usuarioSource;
		this.usuarioReceptor = usuarioTarget;
		this.fecha = new Date();

	}
	
	public Comentario() {
		super();
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(Usuario usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}

	public Usuario getUsuarioEmisor() {
		return usuarioEmisor;
	}

	public void setUsuarioEmisor(Usuario usuarioEmisor) {
		this.usuarioEmisor = usuarioEmisor;
	}

	public String getFecha() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaTexto = formatter.format(this.fecha);
		return fechaTexto;
		}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCodigoComentario() {
		return codigoComentario;
	}

	public void setCodigoComentario(Long codigoComentario) {
		this.codigoComentario = codigoComentario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoComentario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		return codigoComentario == other.codigoComentario;
	}
	
	
	
	
	
	

}
