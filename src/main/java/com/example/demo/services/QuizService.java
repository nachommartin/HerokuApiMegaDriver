package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;

@Service
public class QuizService {
	
	@Autowired QuizRepository repositorio;
	
	public Quiz getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	

}
