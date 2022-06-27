package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

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


	@Override
	public int hashCode() {
		return Objects.hash(user);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FollowCredentials other = (FollowCredentials) obj;
		return Objects.equals(user, other.user);
	}
	
	


}
