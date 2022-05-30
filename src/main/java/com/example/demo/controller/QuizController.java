package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PuntosDTO;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Respuesta;
import com.example.demo.model.Usuario;
import com.example.demo.services.QuestionService;
import com.example.demo.services.QuizService;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired QuizService servicioQuiz; 
	@Autowired QuestionService servicioQuestion;
	@Autowired UsuarioService servicioUsuario; 
	
	@GetMapping("/{referencia}")
	public Quiz getQuiz(@PathVariable Long referencia) {
		 return servicioQuiz.getByRef(referencia);
	}
	
	@GetMapping("/{referencia}/preguntas")
	public List<Question> getQuestions(@PathVariable Long referencia) {
		 return servicioQuestion.getByQuiz(referencia);
	}
	
	@GetMapping("/{referencia}/preguntas({refQuestion}")
	public Question getQuestion(@PathVariable Long referencia, @PathVariable Long refQuestion) {
		 return servicioQuestion.getByRef(referencia);

	}
	
	@GetMapping("/{referencia}/preguntas/{refQuestion}/respuestas")
	public List<Respuesta> getRespuestas(@PathVariable Long referencia, @PathVariable Long refQuestion) {
		 Question aux= servicioQuestion.getByRef(refQuestion);
		 return aux.getRespuestas();


	}
	@PutMapping("/puntos")
	public int sumarPuntos(@RequestBody PuntosDTO puntos) {
		String correo= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario user = servicioUsuario.getByMail(correo);	
		servicioUsuario.sumarPuntos(user,puntos.getPuntos());
		return puntos.getPuntos();


	}

}
