package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Respuesta;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizRepository;

@Service
public class QuestionService {
	
	@Autowired QuestionRepository repositorio; 
	@Autowired QuizRepository repoQuiz; 
	
	public Question getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	public List<Question> getByQuiz(long ref) {
		return repositorio.findAllByQuizRefOrderByOrdenAsc(ref);
	}
	
	public void removeQuestion(long refQuiz, long ref) {
		Quiz quiz= repoQuiz.findById(refQuiz).orElse(null);
		Question aux = this.getByRef(ref);
			if(quiz.getPreguntas().contains(aux))
			quiz.getPreguntas().remove(aux);
		repoQuiz.save(quiz);
		repositorio.delete(aux);
	}
	
	public Question addQuestion(String texto, Quiz quiz, int orden) {
		Question aux = new Question(texto,quiz,orden);
		quiz.getPreguntas().add(aux);
		quiz.setNumPreguntas(quiz.getPreguntas().size());
		repoQuiz.save(quiz);
		return aux;
	}
	
	public Question editQuestion(long ref, String texto, Respuesta respuesta) {
		Question aux = this.getByRef(ref);
		if(texto!=null) {
			aux.setTexto(texto);
		}
		else if(respuesta!=null) {
			aux.setRespuestaCorrect(respuesta);
		}
		repositorio.save(aux); 
		return aux;
	}

}
