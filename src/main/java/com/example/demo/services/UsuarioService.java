package com.example.demo.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AmigoDTO;
import com.example.demo.model.Amistad;
import com.example.demo.model.Comentario;
import com.example.demo.model.FollowCredentials;
import com.example.demo.model.Juego;
import com.example.demo.model.Listado;
import com.example.demo.model.Rango;
import com.example.demo.model.Usuario;
import com.example.demo.model.Votacion;
import com.example.demo.repository.ComentarioRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.VotacionRepository;

/** 
 * Lógica de negocio que repercute en el usuario
 * @author Nacho
 *
 */
@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired ComentarioRepository comentarios; 
	
	@Autowired VotacionRepository votos; 
	

	/**
	 * Método para encontrar a un usuario por su correo electrónico (su PK/ID)
	 * @param correo
	 * @return
	 */
	public Usuario getByMail(String correo) {
		return repositorio.findById(correo).orElse(null);
	}
	/**
	 * Método para encontrar a un usuario por su nick (clave secundaria)
	 * @param nick
	 * @return
	 */
	public Usuario getByNick(String nick) {
		return repositorio.getByNick(nick);
	}
	
	public void deleteUser(String nick) {
		Usuario aux = this.getByNick(nick);
		repositorio.delete(aux);
	}
	
	/**
	 * Método para recuperar todos los usuarios
	 * @return
	 */
	public List<Usuario> mostrarUsuarios() {
		LocalDateTime fecha=  LocalDateTime.now();
		for(Usuario user:repositorio.findAll()) {
			if (user.getFechaBaneo()!=null && user.getFechaBaneo().isAfter(fecha)){
				user.setBaneado(false);
				user.setFechaBaneo(null);
			}
			
		}
		return repositorio.findAll();
	}
	
	/**
	 * Método para mostrar la cantidad de usuarios
	 * @return
	 */
	public int numUsuarios() {
		return repositorio.findAll().size();
	}
	
	/**
	 * Método para recuperar usuarios mediante una cadena de texto que coincida con parte de su nick
	 * @param nick
	 * @return
	 */
	public Set<FollowCredentials> getByPartialNick(String nick, String correoTarget) {
		List<Usuario> users= repositorio.getUsersByNick("%"+nick+"%");
		Usuario userFollower= this.getByMail(correoTarget);
		users.remove(userFollower);
		boolean aux=false; 
		Set<FollowCredentials> lista= new HashSet<FollowCredentials>();
		for(int i = 0;i<users.size();i++) {
			for(int j = 0;j<userFollower.getLosQueSigo().size();j++) {
				if (userFollower.getLosQueSigo().get(j).getUsuarioSource().equals(users.get(i))&&
					userFollower.getLosQueSigo().get(j).getFollower().equals(userFollower)) {
					aux=true;
					FollowCredentials fc = new FollowCredentials(users.get(i), aux);
					lista.add(fc);
				}
				else {
					aux=false;
				}
				
			}
			FollowCredentials fc = new FollowCredentials(users.get(i), aux);
			if (aux==false) {
			lista.add(fc);
			}
	
		}
		return lista;
	}
	
	/**
	 * Método para devolver los followers
	 * @param nick
	 * @param correoTarget
	 * @return
	 */
	public Set<FollowCredentials> getFollowers(String correoSource) {
		Usuario userFollowed= this.getByMail(correoSource);
		boolean aux=false; 
		Set<FollowCredentials> lista= new HashSet();
		for(int i = 0;i<userFollowed.getFollowers().size();i++) {
			for(int j = 0;j<userFollowed.getLosQueSigo().size();j++) {
				if (userFollowed.getLosQueSigo().get(j).getUsuarioSource().equals(userFollowed.getFollowers().get(i).getFollower())&&
					userFollowed.getLosQueSigo().get(j).getFollower().equals(userFollowed)) {
					aux=true;
					FollowCredentials fc = new FollowCredentials(userFollowed.getFollowers().get(i).getFollower(), aux);
					lista.add(fc);
				}
				else {
					aux=false;
				}
				
			}
			FollowCredentials fc = new FollowCredentials(userFollowed.getFollowers().get(i).getFollower(), aux);
			if (aux==false) {
			lista.add(fc);
			}
	
		}
		return lista;
	}
	

	
	/**
	 * Método para seguir a un usuario
	 * @param correoSource
	 * @param correoTarget
	 * @return
	 */
	public Amistad followUser(String correoSource, String correoTarget) {
		Usuario userFollowed= this.getByMail(correoTarget);
		Usuario userFollower= this.getByMail(correoSource);
		Amistad ami= new Amistad(userFollower, userFollowed);
		userFollowed.getFollowers().add(ami);
		repositorio.save(userFollowed); 
		return ami; 		
	}
	
	
	/**
	 * Método para dejar de seguir a un usuario
	 * @param correoSource
	 * @param correoTarget
	 * @return
	 */
	public Amistad unfollowUser(String correoSource, String correoTarget) {
		Usuario userFollower= this.getByMail(correoTarget);
		Usuario userFollowed= this.getByMail(correoSource);
		Amistad ami= new Amistad(userFollowed, userFollower);
		for(int i = 0;i<userFollowed.getFollowers().size();i++) {
			if (userFollowed.getFollowers().get(i).getUsuarioSource().equals(userFollowed)&&
					userFollowed.getFollowers().get(i).getFollower().equals(userFollower)) {
				Amistad aux= userFollowed.getFollowers().get(i);
				userFollowed.getFollowers().remove(aux);
				repositorio.save(userFollowed); 
			}
		}
		return ami; 		
	}
	
	

	
	
	
	/**
	 * Método para ver los votos de un usuario
	 * @param correoSource
	 * @param correoTarget
	 * @return
	 */
	public List<Votacion>verVotos(String correoSource, String correoTarget) {
		Usuario userFollowed= this.getByMail(correoTarget);
		Usuario userStalker= this.getByMail(correoSource);
		Amistad aux= new Amistad();		
		for(int i = 0;i<userFollowed.getFollowers().size();i++) {
			if (userFollowed.getFollowers().get(i).getUsuarioSource().equals(userFollowed)&&
					userFollowed.getFollowers().get(i).getFollower().equals(userStalker)) {
				 aux= userFollowed.getFollowers().get(i);
			}
		}
		if (userFollowed.getFollowers().contains(aux)){
			return userFollowed.getVotos();
		}
		else {
			return null;
			
		}	

		
	}
	
	/**
	 * Método para enviar un comentario a un usuario
	 * @param comentario
	 * @param correoSource
	 * @param correoTarget
	 * @return
	 */
	public Comentario sendComentario(String comentario, String correoSource, String correoTarget) {
		Usuario userReceptor= this.getByMail(correoTarget);
		Usuario userEmisor= this.getByMail(correoSource);
		Comentario aux= new Comentario(comentario, userEmisor, userReceptor); 
		userReceptor.getComentarios().add(aux); 
		repositorio.save(userReceptor); 
		return aux; 		
	}
	
	/**
	 * Método para editar un comentario ya enviado
	 * @param correoTarget
	 * @param comentario
	 * @param ref
	 * @return
	 */
	public Comentario updateComentario(String correoTarget, String comentario, Long ref) {
		Usuario userReceptor= this.getByMail(correoTarget);
		Comentario aux= new Comentario();
		aux.setCodigoComentario(ref); 
		userReceptor.getComentarios();
		int buscador= userReceptor.getComentarios().indexOf(aux);
		if (buscador == -1) {
			return null; 
		}
		else {
			Comentario aRescatar = userReceptor.getComentarios().get(buscador);
			Date fecha= new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
			String fechaTexto = formatter.format(fecha);
			comentario= "Comentario de "+aRescatar.getUsuarioEmisor().getNick()+" para ti:\n"+comentario+"\n"+"Enviado el ";
			userReceptor.getComentarios().get(buscador).setTexto(comentario+fechaTexto);
			userReceptor.getComentarios().get(buscador).setFecha(fecha);
			repositorio.save(userReceptor); 
		}
		return userReceptor.getComentarios().get(buscador); 
	}
	
	/**
	 * Método para borrar un comentario
	 * @param correoTarget
	 * @param ref
	 * @return
	 */
	public Comentario deleteComentario(String correoTarget, Long ref) {
		Usuario userReceptor= this.getByMail(correoTarget);
		Comentario aux= comentarios.findById(ref).orElse(null);
		if (aux==null) {
			return null;
		}
		int buscador= userReceptor.getComentarios().indexOf(aux);
		if (buscador == -1) {
			return null; 
		}
		else {
			aux = userReceptor.getComentarios().get(buscador);
			userReceptor.getComentarios().remove(buscador);
			repositorio.save(userReceptor); 
		}
		return aux; 
	}
	
	public Listado crearListado(Listado lt) {	
			lt.getUsuario().getMisListas().add(lt);
			repositorio.save(lt.getUsuario());
	return lt;
	}
	
	public Listado buscarListado(long ref, Usuario user) {
		Listado aux=null;
		for(int i = 0;i<user.getMisListas().size();i++) {
			if (user.getMisListas().get(i).getReferencia()==ref) {
				 aux= user.getMisListas().get(i);
			}
		}
		return aux; 
	}
	
	public Listado actualizarListado(long ref, Usuario user, Juego game) {
		Listado aux=buscarListado(ref,user);
		int pos = user.getMisListas().indexOf(aux);
		if(user.getMisListas().get(pos).getJuegos().contains(game)){
			user.getMisListas().get(pos).getJuegos().remove(game);
		}
		else {
		user.getMisListas().get(pos).getJuegos().add(game);
		}
		repositorio.save(user);
		return aux;	
	}
	
	public Listado borrarListado(long ref, Usuario user) {
		Listado aux=buscarListado(ref,user);
		int pos = user.getMisListas().indexOf(aux);
		user.getMisListas().remove(pos);
		repositorio.save(user);
		return aux;	
	}
	
	
	
	public void updateAll(Usuario user, String ciudad, String pass, String nick) {
		if (ciudad !=null) {
			user.setCiudad(ciudad);		

		}
		if (pass!=null) {
			user.setPassword(pass);	
		}
		if(nick!=null) {
			user.setNick(nick);		
		}
		repositorio.save(user); 

		
	}
	
	public void banearUsuario(Usuario user, String status) {
		if(status!=null) {
			if(status.equals("true")) {
				user.setBaneado(true);
				LocalDateTime fecha=  LocalDateTime.now();
				user.setFechaBaneo(fecha.plusDays(7));
			}else if(status.equals("false")) {
				user.setBaneado(false);
				user.setFechaBaneo(null);
			}

		}
		repositorio.save(user); 
	}
	
	public void sumarPuntos(Usuario user, int puntos) {
		user.setPuntos(user.getPuntos()+puntos);
		if(user.getPuntos()>200 && user.getPuntos()<500) {
			user.setRango(Rango.GAMER);
		}
		if(user.getPuntos()>500 && user.getPuntos()<1000) {
			user.setRango(Rango.EXPERT);
		}
		if(user.getPuntos()>1000) {
			user.setRango(Rango.MASTER_OF_THE_SYSTEM);
		}
		repositorio.save(user);
	}
	
	public void cargaAvatar(Usuario user, byte[] imagen) {
		user.setAvatar(imagen);
		repositorio.save(user);
	}
	
	
	public Comentario getComentario(Long ref) {
		return comentarios.findById(ref).orElse(null);
	}
	
	public List<Votacion> getVotosUser(String correo) {
		return votos.findAllByUsuarioCorreoOrderByFechaDesc(correo);	
				
	}
	
	public List<Votacion> getVotosConReviews(String correo) {
		List<Votacion> votaciones= votos.findAllByUsuarioCorreoOrderByFechaDesc(correo);	
		List<Votacion> conReviews= new ArrayList<Votacion>();
		for(int i = 0;i<votaciones.size();i++) {
			if (votaciones.get(i).getReview()!=null) {
				conReviews.add(votaciones.get(i));
			}
		}
		return conReviews;		
	}

}
