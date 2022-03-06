package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ActualizaMensajeDTO;
import com.example.demo.dto.AmigoDTO;
import com.example.demo.dto.ComentarioDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.VotoDTO;
import com.example.demo.error.ApiError;
import com.example.demo.error.ComentarioException;
import com.example.demo.error.ForbiddenVotosException;
import com.example.demo.error.JuegoNotFoundException;
import com.example.demo.error.MensajeException;
import com.example.demo.error.UsuarioNotFoundException;
import com.example.demo.error.VotarAntesException;
import com.example.demo.error.VotoException;
import com.example.demo.error.VotoNotFoundException;
import com.example.demo.model.Amistad;
import com.example.demo.model.Comentario;
import com.example.demo.model.Juego;
import com.example.demo.model.Usuario;
import com.example.demo.model.Votacion;
import com.example.demo.services.JuegoService;
import com.example.demo.services.UsuarioService;

/**
 * Controlado para el resto de la lógica de negocio
 * @author Nacho
 *
 */
@RestController
public class MainController {
	
	@Autowired
	private UsuarioService servicioUser; 
	
	@Autowired
	private JuegoService servicioGame; 
	
	/**
	 * Método para recuperar las votaciones de un usuario para usarlo en el front. Además mediante un atributo del DTO
	 * se controla que sólo los followers pueden ver los votos de un usuario
	 * @param stalker
	 * @return
	 */
	@PostMapping("/votacion")
	public List<Votacion> getVotesByUser(@RequestBody(required = false) AmigoDTO stalker) {
		Usuario resultado = servicioUser.getByMail(stalker.getCorreoTarget());
		if (resultado == null) {
			throw new UsuarioNotFoundException(stalker.getCorreoTarget());
		} 
		else if (stalker.getCorreoAskToFollow() != null){
			if (servicioUser.verVotos(stalker.getCorreoAskToFollow(), stalker.getCorreoTarget())==null) {
				throw new ForbiddenVotosException(stalker.getCorreoTarget());
			}
			else {
				return servicioUser.verVotos(stalker.getCorreoAskToFollow(), stalker.getCorreoTarget());
			}
		}
		else if (resultado.getVotos()==null) {
			List<Votacion> votos= new ArrayList<Votacion>(); 
			resultado.setVotos(votos);
			return resultado.getVotos();
		}
		else {
			return resultado.getVotos();
		}
	}
	
	/**
	 * Método para recuperar el usuario del token
	 * @return
	 */
	@GetMapping("/usuario")
	public Usuario getUser() { 
		String correo= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario user = servicioUser.getByMail(correo);
	    return user;		
	}
	
	/**
	 * Método para que usuario siga (follow) a otro usuario
	 * @param amistad
	 * @return
	 */
	  @PostMapping("/amistad")
		public Amistad follow(@RequestBody AmigoDTO amistad) {
			Usuario userFollowed = servicioUser.getByMail(amistad.getCorreoTarget());
			Usuario userFollower = servicioUser.getByMail(amistad.getCorreoAskToFollow());
			if (userFollowed == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoTarget());
			} 
			else if (userFollower == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoAskToFollow());
			} 
			Amistad ami = servicioUser.followUser(amistad.getCorreoTarget(), amistad.getCorreoAskToFollow());
			return ami;
		}
	  
	  /**
	   * Método para que un usuario deje de seguir (unfollow) a otro usuario
	   * @param amistad
	   * @return
	   */
	  @DeleteMapping("/amistad")
		public String unfollow(@RequestBody AmigoDTO amistad) {
			Usuario userFollowed = servicioUser.getByMail(amistad.getCorreoTarget());
			Usuario userFollower = servicioUser.getByMail(amistad.getCorreoAskToFollow());
			if (userFollowed == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoTarget());
			} 
			else if (userFollower == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoAskToFollow());
			} 
			servicioUser.unfollowUser(amistad.getCorreoTarget(), amistad.getCorreoAskToFollow());
			return "Has dejado de seguir a " +amistad.getCorreoTarget();
		}
	    
	
	/**
	 * Método para recuperar un listado de juego según parárametros concretos
	 * @param year
	 * @param titulo
	 * @param desarrollador
	 * @param categoria
	 * @return
	 */
	@GetMapping("/juego")
	@ResponseBody
	public List<Juego> getGames(@RequestParam(required = false) String year, @RequestParam(required = false) String titulo,
			@RequestParam(required = false) String desarrollador, @RequestParam(required = false) String categoria) { 
		if (year!= null) {
			return this.servicioGame.getByYear(year);
	}
		else if (categoria !=null) {
			return this.servicioGame.getByCategoria(categoria);
		}
		else if(desarrollador !=null) {
			return this.servicioGame.getByDesarrollador(desarrollador);
		}
		else if (titulo !=null) {
			return this.servicioGame.getByTitulo(titulo);
		}
		else {
			return this.servicioGame.mostrarJuegos();
		}
		
	}
	
	/**
	 * Método para recuperar un juego concreto 
	 * @param ref
	 * @return
	 */
	@GetMapping("/juego/{ref}")
	public Juego findByRef(@PathVariable long ref) {
		Juego resultado = servicioGame.getByRef(ref);
		if (resultado == null) {
			throw new JuegoNotFoundException(ref);
		} else {
			return resultado;
		}
	}
	
	
	/**
	 * Método para ver las votaciones que tiene un juego
	 * @param ref
	 * @return
	 */
	@GetMapping("/juego/{ref}/votacion")
	public List<Votacion> getVotesByGame(@PathVariable long ref) {
		Juego resultado = servicioGame.getByRef(ref);
		if (resultado == null) {
			throw new JuegoNotFoundException(ref);
		} 
		else if (resultado.getVotos()==null) {
			List<Votacion> votos= new ArrayList<Votacion>(); 
			resultado.setVotos(votos);
			return resultado.getVotos();
		}
		else {
			return resultado.getVotos();
		}
	}
	
	/**
	 * Método para ver las reseñas que tiene un juego
	 * @param ref
	 * @return
	 */
	@GetMapping("/juego/{ref}/votacion/review")
	public List<Votacion> getReviewsByGame(@PathVariable long ref) {
		Juego resultado = servicioGame.getByRef(ref);
		if (resultado == null) {
			throw new JuegoNotFoundException(ref);
		} 
		else if (resultado.getVotos()==null) {
			List<Votacion> votos= new ArrayList<Votacion>(); 
			resultado.setVotos(votos);
			return resultado.getVotos();
		}
		else {
			return servicioGame.getReviews(ref);
		}
	}
	
	/**
	 * Método para votar un juego
	 * @param ref
	 * @param voto
	 * @return
	 */
    @PostMapping("/juego/{ref}/votacion")
	public Votacion add(@PathVariable long ref, @RequestBody VotoDTO voto) {
		Juego resultado = servicioGame.getByRef(ref);
		Usuario user = servicioUser.getByMail(voto.getCorreo());
		if (resultado == null) {
			throw new JuegoNotFoundException(ref);
		} 
		else if(voto.getVoto() <0 || voto.getVoto() >10) {
			throw new VotoException();
			
		}
		else if (user == null) {
			throw new UsuarioNotFoundException(voto.getCorreo());
		} 
		Votacion vt = new Votacion(resultado, user, voto.getVoto());
		servicioGame.addVotos(vt);
		return vt;
	}
    
    /**
     * Método para actualizar un voto de un juego añadiendo una reseña
     * @param ref
     * @param review
     * @return
     */
    @PutMapping("/juego/{ref}/votacion")
   	public Votacion addReview(@PathVariable long ref, @RequestBody ReviewDTO review) {
   		Juego resultado = servicioGame.getByRef(ref);
   		Usuario user = servicioUser.getByMail(review.getCorreo());
   		if (resultado == null) {
   			throw new JuegoNotFoundException(ref);
   		} 
   		else if (user == null) {
   			throw new UsuarioNotFoundException(review.getCorreo());
   		} 
   		else if(review.getReview().length()<2) {
   			throw new MensajeException(review.getReview());
   		}
   		Votacion vt = servicioGame.findByGameUser(ref, user); 
   		try{
   			servicioGame.addReview(vt, review.getReview());
   		}
   		catch (NullPointerException ex){
   			throw new VotarAntesException();
   		}
   		return vt;
   	}
    
    /**
     * Método para borrar el voto de un juego
     * @param ref
     * @param review
     * @return
     */
    @DeleteMapping("/juego/{ref}/votacion")
   	public String borrarVoto(@PathVariable long ref, @RequestBody ReviewDTO review) {
   		Juego resultado = servicioGame.getByRef(ref);
   		Usuario user = servicioUser.getByMail(review.getCorreo());
   		if (resultado == null) {
   			throw new JuegoNotFoundException(ref);
   		} 
   		else if (user == null) {
   			throw new UsuarioNotFoundException(review.getCorreo());
   		}
   		Votacion vt = servicioGame.findByGameUser(ref, user); 
   		try{
   			servicioGame.deleteVoto(vt);
   		}
   		catch (NullPointerException ex){
   			throw new VotoNotFoundException();
   		}
    	return "El voto ha sido borrado";
    }
    
    /**
     * Método para recuperar los comentarios que ha recibido un usuario
     * @param message
     * @return
     */
    @GetMapping("/comentario")
    public List<Comentario> getComentarios(@RequestBody ComentarioDTO message){
		Usuario userReceptor = servicioUser.getByMail(message.getReceptor());
		if (userReceptor == null) {
			throw new UsuarioNotFoundException(message.getReceptor());
		}
		
		return userReceptor.getComentarios();

    	
    }

    /**
     * Método para enviar un comentario a un usuario
     * @param message
     * @return
     */
    @PostMapping("/comentario")
	public Comentario sendMessage(@RequestBody ComentarioDTO message) {
		Usuario userReceptor = servicioUser.getByMail(message.getReceptor());
		Usuario userEmisor = servicioUser.getByMail(message.getCorreoEmisor());
		if (message.getTexto().length()<2) {
			throw new MensajeException(message.getTexto());
		}
		else if (userReceptor == null) {
			throw new UsuarioNotFoundException(message.getReceptor());
		} 
		else if (userEmisor == null) {
			throw new UsuarioNotFoundException(message.getCorreoEmisor());
		} 
		String mensaje= "Comentario de "+userEmisor.getNick()+" para ti:\n"+message.getTexto()+"\n"+"Enviado el ";
		Comentario comi = servicioUser.sendComentario(mensaje, message.getCorreoEmisor(), message.getReceptor());
		return comi;
	}
    
    /**
     * Método para editar el comentario enviado a un usuario
     * @param ref
     * @param message
     * @return
     */
    @PutMapping("/comentario/{ref}")
	public Comentario updatedMessage(@PathVariable long ref, @RequestBody ActualizaMensajeDTO message) {
		Usuario userReceptor = servicioUser.getByMail(message.getReceptor());
		if (message.getTexto().length()<2) {
			throw new MensajeException(message.getTexto());
		}
		else if (userReceptor == null) {
			throw new UsuarioNotFoundException(message.getReceptor());
		} 
		Comentario aux = servicioUser.updateComentario(message.getReceptor(), message.getTexto(), ref);
		if (aux==null) {
			throw new ComentarioException();
		}
		else {
			return aux; 
		}
    	
    }
    
    /**
     * Método para borrar un comentario enviado a un usuario
     * @param ref
     * @param message
     * @return
     */
    @DeleteMapping("/comentario/{ref}")
  	public String deleteMessage(@PathVariable long ref, @RequestBody ComentarioDTO message) {
  		Usuario userReceptor = servicioUser.getByMail(message.getReceptor());
  		if (userReceptor == null) {
  			throw new UsuarioNotFoundException(message.getReceptor());
  		} 
  		Comentario aux = servicioUser.deleteComentario(message.getReceptor(), ref);
  		if (aux==null) {
  			throw new ComentarioException();
  		}
  		else {
  			return "El comentario ha sido borrado"; 
  		}
      	
      }
	
    /**
     * Gestor de la excepción de no existencia de un usuario
     * @param ex
     * @return
     */
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<ApiError> handleUsuarioNoEncontrado(UsuarioNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	/**
	 * Gestor de la excepción de no existencia de un juego
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(JuegoNotFoundException.class)
	public ResponseEntity<ApiError> handleJuegoNoEncontrado(JuegoNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	/**
	 * Gestor de la excepción de no existencia de un voto
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(VotoNotFoundException.class)
	public ResponseEntity<ApiError> handleVotoNoEncontrado(VotoNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	/**
	 * Gestor de la excepción de que un usuario no puede ver los votos de otro usuario
	 * a no ser que sea un follower
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ForbiddenVotosException.class)
	public ResponseEntity<ApiError> handleForbiddenVotos(ForbiddenVotosException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.FORBIDDEN);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	}
	
	/**
	 * Gestor de la excepción de que tanto un comentario como una reseña debe tener como mínimo dos
	 * caracteres
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MensajeException.class)
	public ResponseEntity<ApiError> handleBadMessage(MensajeException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.FORBIDDEN);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	}
	
	/**
	 * Gestor de la excepción de que para reseñar un juego antes debes de votarlo
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(VotarAntesException.class)
	public ResponseEntity<ApiError> handleBadMessage(VotarAntesException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.BAD_REQUEST);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	/**
	 * Gestor de la excepción para que los votos no puedan ser ni menor de 1 ni mayor de 10
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(VotoException.class)
	public ResponseEntity<ApiError> handleForbiddenVotos(VotoException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.BAD_REQUEST);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	/**
	 * Gestor de la no existencia de un comentario
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ComentarioException.class)
	public ResponseEntity<ApiError> handleComentarioError(ComentarioException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

}
