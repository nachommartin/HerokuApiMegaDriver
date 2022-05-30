package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired QuestionRepository repositorio; 
	
	public Question getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	public List<Question> getByQuiz(long ref) {
		return repositorio.findAllByQuizRef(ref);
	}

}
