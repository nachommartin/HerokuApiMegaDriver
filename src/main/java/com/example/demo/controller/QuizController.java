package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PuntosDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.error.ApiError;
import com.example.demo.error.BadRequestException;
import com.example.demo.error.QuestionNotFoundException;
import com.example.demo.error.QuizNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Respuesta;
import com.example.demo.model.Usuario;
import com.example.demo.services.QuestionService;
import com.example.demo.services.QuizService;
import com.example.demo.services.RespuestaService;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired QuizService servicioQuiz; 
	@Autowired QuestionService servicioQuestion;
	@Autowired UsuarioService servicioUsuario; 
	@Autowired RespuestaService servicioRespuesta; 

	
	@GetMapping("/{referencia}")
	public Quiz getQuiz(@PathVariable Long referencia) {
		 return servicioQuiz.getByRef(referencia);
	}
	
	@GetMapping
	public List<Quiz> getAllQuiz(){
		return servicioQuiz.getAllQuiz();
	}
	
	@PostMapping
	public Quiz saveQuiz(@RequestBody QuizDTO quiz) {
		 return servicioQuiz.createQuiz(quiz.getName());
	}
	
	@PutMapping("/{referencia}")
	public Quiz updateQuiz(@PathVariable Long referencia, @RequestBody QuizDTO quiz) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		 return servicioQuiz.updateQuiz(aux, quiz.getName());
	}
	
	@DeleteMapping("/{referencia}")
	public void deleteQuiz(@PathVariable Long referencia) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 if (aux==null) {
				throw new QuizNotFoundException(referencia);
			 }
		 servicioQuiz.removeQuiz(referencia);

	}
	
	@GetMapping("/{referencia}/preguntas")
	public List<Question> getQuestions(@PathVariable Long referencia) {
		 return servicioQuestion.getByQuiz(referencia);
	}
	
	@GetMapping("/{referencia}/preguntas/{refQuestion}")
	public Question getQuestion(@PathVariable Long referencia, @PathVariable Long refQuestion) {
		 return servicioQuestion.getByRef(refQuestion);

	}
	
	@PostMapping("/{referencia}/preguntas")
	public Question addQuestion(@PathVariable Long referencia, @RequestBody QuizDTO quiz) {
		 Quiz auxQuiz= servicioQuiz.getByRef(referencia);
		if (auxQuiz==null) {
			throw new QuizNotFoundException(referencia);
		 }
		return servicioQuestion.addQuestion(quiz.getEnunciadoPregunta(), auxQuiz, quiz.getOrdenPregunta());
	}
	
	
	@PutMapping("/{referencia}/preguntas/{refQuestion}")
	public void editQuestion(@PathVariable Long referencia, @PathVariable Long refQuestion, @RequestBody QuizDTO quiz) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 Question q= servicioQuestion.getByRef(refQuestion);
		if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		if (q==null) {
			throw new QuestionNotFoundException(refQuestion);
		 }	
		  servicioQuestion.editQuestion(refQuestion, quiz.getEnunciadoPregunta(),quiz.getRespuestaCorrecta());

	}
	
	
	@DeleteMapping("/{referencia}/preguntas/{refQuestion}")
	public void deleteQuestion(@PathVariable Long referencia, @PathVariable Long refQuestion) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 Question q= servicioQuestion.getByRef(refQuestion);
		if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		if (q==null) {
			throw new QuestionNotFoundException(refQuestion);
		 }	
		  servicioQuestion.removeQuestion(referencia, refQuestion);
		  servicioQuiz.ordenadoPreguntas(referencia);

	}
	
	
	@GetMapping("/{referencia}/preguntas/{refQuestion}/respuestas")
	public List<Respuesta> getRespuestas(@PathVariable Long referencia, @PathVariable Long refQuestion) {
		 Question aux= servicioQuestion.getByRef(refQuestion);
		 return aux.getRespuestas();

	}
	
	@PostMapping("/{referencia}/preguntas/{refQuestion}/respuestas")
	public Respuesta postRespuesta(@PathVariable Long referencia, @PathVariable Long refQuestion, @RequestBody QuizDTO quiz) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 Question q= servicioQuestion.getByRef(refQuestion);
		if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		if (q==null) {
			throw new QuestionNotFoundException(refQuestion);
		 }			 
		return servicioRespuesta.addRespuesta(quiz.getTextoRespuesta(), q, quiz.getOrdenRespuesta());
	}
	
	@PutMapping("/{referencia}/preguntas/{refQuestion}/respuestas/{refRespuesta}")
	public void editRespuesta(@PathVariable Long referencia, @PathVariable Long refQuestion, @PathVariable Long refRespuesta,
			@RequestBody QuizDTO quiz) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 Question q= servicioQuestion.getByRef(refQuestion);
		if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		if (q==null || !aux.getPreguntas().contains(q)) {
			throw new QuestionNotFoundException(refQuestion);
		 }	
		if (quiz.getTextoRespuesta()==null) {
			throw new BadRequestException();
		 }	
		  servicioRespuesta.editRespuesta(refRespuesta, quiz.getTextoRespuesta());

	}
	
	@DeleteMapping("/{referencia}/preguntas/{refQuestion}/respuestas/{refRespuesta}")
	public void deleteRespuesta(@PathVariable Long referencia, @PathVariable Long refQuestion, @PathVariable Long refRespuesta) {
		 Quiz aux= servicioQuiz.getByRef(referencia);
		 Question q= servicioQuestion.getByRef(refQuestion);
		if (aux==null) {
			throw new QuizNotFoundException(referencia);
		 }
		if (q==null || !aux.getPreguntas().contains(q)) {
			throw new QuestionNotFoundException(refQuestion);
		 }	
		  servicioRespuesta.removeRespuesta(refQuestion, refRespuesta);

	}
	
	@PutMapping("/puntos")
	public int sumarPuntos(@RequestBody PuntosDTO puntos) {
		String correo= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario user = servicioUsuario.getByMail(correo);	
		servicioUsuario.sumarPuntos(user,puntos.getPuntos());
		return puntos.getPuntos();


	}
	
	@ExceptionHandler(QuizNotFoundException.class)
	public ResponseEntity<ApiError> handleQuizNoExiste(QuizNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(QuestionNotFoundException.class)
	public ResponseEntity<ApiError> handleQuestionNoExiste(QuestionNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.BAD_REQUEST);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}


}
