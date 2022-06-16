package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {
	
	List<Respuesta> findAllByQuestionRef(Long ref);


}
