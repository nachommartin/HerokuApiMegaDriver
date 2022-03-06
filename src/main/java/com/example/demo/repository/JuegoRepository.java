package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Juego;

/**
 * Repositorio de juegos con querys personalizadas para el filtrado
 * @author Nacho
 *
 */
public interface JuegoRepository extends JpaRepository<Juego,Long> {
	
	@Query("select j from Juego j where j.categoria like ?1")
	List<Juego> getByCategoria(String categoria); 
	
	@Query("select j from Juego j where j.titulo like ?1")
	List<Juego> getByTitulo(String titulo); 
	
	@Query("select j from Juego j where j.desarrollador like ?1")
	List<Juego> getByDesarrollador(String desarrollador); 
	
	@Query("select j from Juego j where j.year = ?1")
	List<Juego> getByYear(String year); 
	
	@Query("select j from Juego j where j.titulo like ?1")
	Juego getByTituloExacto(String titulo); 

}
