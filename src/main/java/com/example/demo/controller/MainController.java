package com.example.demo.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;

import com.example.demo.dto.ActualizaMensajeDTO;
import com.example.demo.dto.AmigoDTO;
import com.example.demo.dto.ComentarioDTO;
import com.example.demo.dto.ListadoDTO;
import com.example.demo.dto.MailDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.VotoDTO;
import com.example.demo.error.ApiError;
import com.example.demo.error.ComentarioException;
import com.example.demo.error.ForbiddenVotosException;
import com.example.demo.error.JuegoNotFoundException;
import com.example.demo.error.ListadoDontExistException;
import com.example.demo.error.MensajeException;
import com.example.demo.error.NotUpdateException;
import com.example.demo.error.UsuarioNotFoundException;
import com.example.demo.error.VotarAntesException;
import com.example.demo.error.VotoException;
import com.example.demo.error.VotoNotFoundException;
import com.example.demo.model.Amistad;
import com.example.demo.model.Comentario;
import com.example.demo.model.FollowCredentials;
import com.example.demo.model.Juego;
import com.example.demo.model.Listado;
import com.example.demo.model.Usuario;
import com.example.demo.model.Votacion;
import com.example.demo.services.EmailSender;
import com.example.demo.services.JuegoService;
import com.example.demo.services.LoadFiles;
import com.example.demo.services.UsuarioService;

/**
 * Controlador para el resto de la lógica de negocio
 * @author Nacho
 *
 */
@RestController
public class MainController {
	
	@Autowired
	private UsuarioService servicioUser; 
	
	@Autowired
	private JuegoService servicioGame; 
	
	@Autowired
	private PasswordEncoder encriptador; 
	
	@Autowired
	private EmailSender sender;
	
	@Autowired
	private LoadFiles loader;
	
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
	@GetMapping("/token")
	public Usuario getUser(@RequestParam(required = false) String nick) { 
		Usuario user= new Usuario();
		if (nick!=null) {
			user = servicioUser.getByNick(nick);
		}
		else {
		String correo= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 user = servicioUser.getByMail(correo);
		}
	    return user;		
	}
	
	/**
	 * Método para la edición y la actualización del usuario
	 * @param user
	 * @return
	 */
	@PutMapping("/usuario")
	public Usuario setUser(@RequestBody(required = true) UsuarioDTO user) { 
		Usuario aux = servicioUser.getByMail(user.getCorreoSource());
		if (aux == null) {
			throw new UsuarioNotFoundException(user.getCorreoSource());
		} 
		else if(user.getCiudad()==null && user.getNick()==null) {
			servicioUser.updatePass(aux, encriptador.encode(user.getPassword()));
		}
		else if(user.getPassword()==null && user.getNick()==null) {
			servicioUser.updateCiudad(aux, user.getCiudad());
		}
		else if(user.getPassword()==null && user.getCiudad()==null) {
			servicioUser.updateNick(aux, user.getNick());
		}
		else if(user.getNick()==null) {
			servicioUser.updateCiudadPass(aux, user.getCiudad(), encriptador.encode(user.getPassword()));
		}
		else if (user.getCiudad()==null) {
			servicioUser.updateNickPass(aux, user.getNick(), encriptador.encode(user.getPassword()));
		}
		else if (user.getPassword()==null) {
			servicioUser.updateCiudadNick(aux, user.getCiudad(), user.getNick());
		}
		else {
			servicioUser.updateAll(aux, user.getCiudad(), encriptador.encode(user.getPassword()),user.getNick());
		}
	    return aux;		
	}
	
	@GetMapping("/amistad")
	@ResponseBody
	public List<FollowCredentials> getUsersByNick(@RequestParam(required = true) String nick, @RequestParam (required=true) String correoTarget) { 
			return this.servicioUser.getByPartialNick(nick, correoTarget);
	}
	
	@GetMapping("usuario/")
	public List<Usuario> getUsers(){
		return this.servicioUser.mostrarUsuarios();
	}
	
	@GetMapping("/usuario/amistad")
	@ResponseBody
	public List<FollowCredentials> getFollowers(@RequestParam (required=true) String correoSource) { 
			return this.servicioUser.getFollowers(correoSource);
	}
	
	@GetMapping("usuario/amistad/seguidos")
	public List<Amistad> getSeguidos(@RequestParam (required=true) String correoSource){
		Usuario userFollower = servicioUser.getByMail(correoSource);
		if (userFollower == null) {
			throw new UsuarioNotFoundException(correoSource);
		} 

		return userFollower.getLosQueSigo();
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
		public ResponseEntity<String>  unfollow(@RequestBody AmigoDTO amistad) {
			Usuario userFollowed = servicioUser.getByMail(amistad.getCorreoTarget());
			Usuario userFollower = servicioUser.getByMail(amistad.getCorreoAskToFollow());
			if (userFollowed == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoTarget());
			} 
			else if (userFollower == null) {
				throw new UsuarioNotFoundException(amistad.getCorreoAskToFollow());
			} 
			servicioUser.unfollowUser(amistad.getCorreoTarget(), amistad.getCorreoAskToFollow());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	    
	
	/**
	 * Método para recuperar un listado de juego según parámetros concretos
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
	
	@PostMapping("/juego")
	public Juego guardarJuego(@RequestBody(required= true)Juego juego) {
		if (juego.getYear() == null && juego.getDesarrollador()==null && juego.getTitulo()==null
				&& juego.getCategoria()==null) {
			throw new NotUpdateException();
		} 
		servicioGame.addJuego(juego);
		return juego; 
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
	
	@PutMapping("/juego/{ref}")
	public Juego updateJuego(@PathVariable long ref, @RequestBody(required = true) Juego juego) { 
		Juego aux = servicioGame.getByRef(ref);
		if (aux == null) {
			throw new JuegoNotFoundException(juego.getReferencia());
		} 
		if (juego.getYear() == null && juego.getDesarrollador()==null && juego.getTitulo()==null
				&& juego.getCategoria()==null) {
			throw new NotUpdateException();
		} 
		servicioGame.update(aux, juego.getTitulo(),  juego.getYear(), juego.getDesarrollador(), juego.getCategoria());
	    return aux;		
	}
	
	
	@DeleteMapping("/juego/{ref}")
	public ResponseEntity<Object> borrarJuego(@PathVariable long ref){
   		Juego resultado = servicioGame.getByRef(ref);
   		if (resultado == null) {
   			throw new JuegoNotFoundException(ref);
   		}
   		servicioGame.removeJuego(ref);
        
		ApiError respuesta = new ApiError();
        respuesta.setEstado(HttpStatus.CREATED);
        respuesta.setFecha(LocalDateTime.now());
        respuesta.setMensaje("Juego borrado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

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
	@GetMapping("/juego/{ref}/review")
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
   	public ResponseEntity<String> borrarVoto(@PathVariable long ref, @RequestBody ReviewDTO review) {
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
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Método para recuperar los comentarios que ha recibido un usuario
     * @param message
     * @return
     */
    @GetMapping("/comentario")
    public List<Comentario> getComentarios(@RequestParam(required = true) String nick){
		Usuario userReceptor = servicioUser.getByNick(nick);
		if (userReceptor == null) {
			throw new UsuarioNotFoundException(nick);
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
  	public ResponseEntity<String> deleteMessage(@PathVariable long ref, @RequestBody ComentarioDTO message) {
  		Usuario userReceptor = servicioUser.getByMail(message.getReceptor());
  		if (userReceptor == null) {
  			throw new UsuarioNotFoundException(message.getReceptor());
  		} 
  		Comentario aux = servicioUser.deleteComentario(message.getReceptor(), ref);
  		if (aux==null) {
  			throw new ComentarioException();
  		}
  		else {
  			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
  		}
      	
      }
    
    @PostMapping("/listado")
	public Listado crearListado(@RequestBody ListadoDTO listado) {
		Usuario user = servicioUser.getByMail(listado.getCorreo());
		if (user == null) {
			throw new UsuarioNotFoundException(listado.getCorreo());
		} 
		Listado lt = new Listado(listado.isPublico(), user);
		servicioUser.crearListado(lt);
		return lt;
	}
    
    @PutMapping("/listado/{ref}")
	public Listado actualizarListado(@PathVariable long ref, @RequestBody ListadoDTO listado) {
		Usuario user = servicioUser.getByMail(listado.getCorreo());
   		Juego auxGame = servicioGame.getByRef(listado.getRefJuego());
   		if (auxGame == null) {
   			throw new JuegoNotFoundException(ref);
   		} 
		if (user == null) {
			throw new UsuarioNotFoundException(listado.getCorreo());
		} 
		if (servicioUser.buscarListado(ref, user)==null) {
			throw new ListadoDontExistException();
		}
   		Listado lt= servicioUser.actualizarListado(ref, user, auxGame);
		System.out.println(lt.getJuegos().size());


		return lt;
	}
    
    
    @GetMapping("/usuario/{nick}/listado")
   	public List<Listado> obtenerListados(@PathVariable String nick) {
   		Usuario user = servicioUser.getByNick(nick);
   		if (user == null) {
   			throw new UsuarioNotFoundException(nick);
   		} 
   		return user.getMisListas();
   	}
    
    
    @GetMapping("/usuario/{nick}/listado/{ref}")
   	public List<Juego> obtenerJuegosListado(@PathVariable String nick, @PathVariable long ref) {
   		Usuario user = servicioUser.getByNick(nick);
   		Listado lista =servicioUser.buscarListado(ref, user);
   		if (user == null) {
   			throw new UsuarioNotFoundException(nick);
   		} 
   		if (servicioUser.buscarListado(ref, user)==null) {
			throw new ListadoDontExistException();
		}
   		return lista.getJuegos();
   	}
    
    @PostMapping("/enviar")
    public void sendEmail(@RequestBody MailDTO datos) throws MessagingException {
    	datos.setTo("ignmmartin@gmail.com");
    	
		sender.send(datos.getTo(), datos.getSubject(), datos.getText());
	}  
    
    
    @PostMapping("/carga")
    public ResponseEntity<String> loadImages(@RequestBody MultipartFile file, @RequestParam Long ref) {
		Juego game = servicioGame.getByRef(ref);
		if (game == null) {
			throw new JuegoNotFoundException(ref);
		} 
    	try {
        	byte[] imagen=this.loader.save(file);
        		game.setImagen(imagen);
        		servicioGame.addJuego(game);
        		return new ResponseEntity<>(HttpStatus.OK);
        	}catch(Exception ex) {
        		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
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
		apiError.setEstado(HttpStatus.BAD_REQUEST);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
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
	
	@ExceptionHandler(NotUpdateException.class)
	public ResponseEntity<ApiError> handleJuegoNoActualizado(NotUpdateException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

}
