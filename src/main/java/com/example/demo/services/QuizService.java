package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;

@Service
public class QuizService {
	
	@Autowired QuizRepository repositorio;
	
	public Quiz getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	public List<Quiz> getAllQuiz(){
		return repositorio.findAll();
	}
	
	public Quiz createQuiz(String name) {
		Quiz aux= new Quiz(name);
		repositorio.save(aux);
		return aux; 
	}
	
	public Quiz updateQuiz(Quiz quiz, String name) {
		quiz.setName(name);
		repositorio.save(quiz);
		return 	quiz; 
	}
	
	public void removeQuiz(long ref) {
		Quiz aux = this.getByRef(ref);
		repositorio.delete(aux); 
	}
	
	public void ordenadoPreguntas(long ref) {
		Quiz aux = this.getByRef(ref);
		List<Question> list = new ArrayList<Question>(aux.getPreguntas());
		for(int i = 0;i<list.size();i++) {
			list.get(i).setOrden(i+1);
		}
		aux.setNumPreguntas(aux.getPreguntas().size());
		aux.getPreguntas().clear();
		for(int i = 0;i<list.size();i++) {
			aux.getPreguntas().add(list.get(i));
		}
		
		repositorio.save(aux);
	}
	
	
	
	

}
