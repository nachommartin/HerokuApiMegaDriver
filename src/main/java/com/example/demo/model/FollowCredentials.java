package com.example.demo.model;

import java.io.Serializable;

public class FollowCredentials implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario user;
	private boolean esAmigo;
	
	
	public FollowCredentials(Usuario user, boolean esAmigo) {
		super();
		this.user = user;
		this.esAmigo = esAmigo;
	}


	public Usuario getUser() {
		return user;
	}


	public void setUser(Usuario user) {
		this.user = user;
	}


	public boolean isEsAmigo() {
		return esAmigo;
	}


	public void setEsAmigo(boolean esAmigo) {
		this.esAmigo = esAmigo;
	}
	
	


}
