package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Respuesta;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.RespuestaRepository;


@Service
public class RespuestaService {
	
	@Autowired RespuestaRepository repositorio; 
	@Autowired QuestionRepository repoQuestion; 
	
	public Respuesta getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	public List<Respuesta> getByQuestion(long ref) {
		return repositorio.findAllByQuestionRef(ref);
	}
	
	public Respuesta addRespuesta(String texto, Question question, int orden) {
		Respuesta aux = new Respuesta(texto,question,orden);
		question.getRespuestas().add(aux);
		question.setNumRespuesta(question.getRespuestas().size());
		repoQuestion.save(question);
		return aux;
	}
	
	
	public void removeRespuesta(long refP, long ref) {
		Question preg= repoQuestion.findById(refP).orElse(null);
		Respuesta aux = this.getByRef(ref);
			if(preg.getRespuestaCorrect().equals(aux))
				preg.setRespuestaCorrect(null);
			if(preg.getRespuestas().contains(aux))
				preg.getRespuestas().remove(aux);
			repoQuestion.save(preg);
		repositorio.delete(aux);
	}
	
	public Respuesta editRespuesta(long ref, String texto) {
		Respuesta aux = this.getByRef(ref);
			aux.setText(texto);
			repositorio.save(aux); 
		return aux;
	}

}
