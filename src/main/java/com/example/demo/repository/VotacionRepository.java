package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Votacion;


public interface VotacionRepository extends JpaRepository<Votacion,Long>{
	
	List<Votacion> findAllByUsuarioCorreoOrderByFechaDesc(String correo);


}
