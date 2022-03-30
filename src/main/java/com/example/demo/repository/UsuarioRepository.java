package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Usuario;

/**
 * Repositorio de usuarios
 * @author Nacho
 *
 */
public interface UsuarioRepository  extends JpaRepository<Usuario,String>{
	
	@Query("select u from Usuario u where u.nick like ?1")
	Usuario getByNick(String nick); 
	

}
