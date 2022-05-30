package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Juego;
import com.example.demo.model.Usuario;
import com.example.demo.model.Votacion;
import com.example.demo.repository.JuegoRepository;
import com.example.demo.repository.VotacionRepository;

/**
 * Lógica de negocio que repercute en el juego
 * @author Nacho
 *
 */
@Service
public class JuegoService {
	
	@Autowired
	private JuegoRepository repositorio;
	
	@Autowired 
	private VotacionRepository votos;
	
	
	
	
	/**
	 * Método para recuperar un juego por su referencia (PK/ID)
	 * @param ref
	 * @return
	 */
	public Juego getByRef(long ref) {
		return repositorio.findById(ref).orElse(null);
	}
	
	/**
	 * Método para recuperar juegos filtrando por su categoría
	 * @param categoria
	 * @return
	 */
	public List<Juego> getByCategoria(String categoria) {
		return repositorio.getByCategoria(categoria);
	}
	
	/**
	 * Método para recuperar juegos mediante una cadena de texto que coincida con parte de su título
	 * @param titulo
	 * @return
	 */
	public List<Juego> getByTitulo(String titulo) {
		return repositorio.getByTitulo("%"+titulo+"%");
	}
	
	/**
	 * Método para recuperar juegos filtrando por su desarrollador
	 * @param desarrollador
	 * @return
	 */
	public List<Juego> getByDesarrollador(String desarrollador) {
		return repositorio.getByDesarrollador(desarrollador);
	}
	
	/**
	 * Método para recuperar juegos filtrando por su año
	 * @param year
	 * @return
	 */
	public List<Juego> getByYear(String year) {
		return repositorio.getByYear(year);
	}
	
	public List<Juego> getByNumVotos() {
		return repositorio.findByOrderByNumVotosDesc();

	}
	

	public List<Juego> getByMedia() {
		return repositorio.findByOrderByVotacionMediaDesc();

	}
	
	
	/**
	 * Método para recuperar un listado con todos los juegos
	 * @return
	 */
	public List<Juego> mostrarJuegos() {
		return repositorio.findAll();
	}
	
	// Los tres métodos de a continuación son comodines en caso de modificar la base de datos cuando la aplicación ya esté en fase de producción 
			
	/**
	 * Método para añadir juegos
	 * @param game
	 */
	public void addJuego(Juego game) {
		repositorio.save(game); 
	}
	
	/**
	 * Método para editar un juego
	 * @param game
	 * @return
	 */
	public void update(Juego aux, String titulo, String year, String desarrollador, String categoria) {
		if(categoria!=null) {
			aux.setCategoria(categoria);
		}
		if(desarrollador!=null) {
			aux.setDesarrollador(desarrollador);
		}
		if(year!=null) {
			aux.setYear(year);
		}
		if(titulo!=null) {
			aux.setTitulo(titulo);
		}
		repositorio.save(aux); 		
		}
		
	
	
	/**
	 * Método para borrar un juego
	 * @param game
	 * @return
	 */
	public void removeJuego(long ref) {
		Juego aux = this.getByRef(ref);
		repositorio.delete(aux); 
	}
	
	/**
	 * Método para votar un juego
	 * @param vt
	 * @return
	 */
	public Votacion addVotos(Votacion vt) {	
			if (vt.getJuego().getVotos().isEmpty()) {
				vt.getJuego().getVotos().add(vt);
				vt.getJuego().VotacionMedia();
				vt.getJuego().setNumVotos(vt.getJuego().getVotos().size());
				repositorio.save(vt.getJuego());
			}
			else {
				if(vt.getJuego().getVotos().contains(vt)) {
				int OldVt = vt.getJuego().getVotos().indexOf(vt);
				vt.getJuego().getVotos().get(OldVt).setVoto(vt.getVoto());
				vt.getJuego().VotacionMedia();
				vt.getJuego().setNumVotos(vt.getJuego().getVotos().size());
				repositorio.save(vt.getJuego());
				}
				else {
					vt.getJuego().getVotos().add(vt);
					vt.getJuego().VotacionMedia();
					vt.getJuego().setNumVotos(vt.getJuego().getVotos().size());
					repositorio.save(vt.getJuego());
				}
		}
		return vt;
	}
	
	/**
	 * Método para saber si un usuario ha votado un juego
	 * @param ref
	 * @param user
	 * @return
	 */
	public Votacion findByGameUser (long ref, Usuario user) {
		Juego aux=  this.getByRef(ref);
		Votacion vt = new Votacion (aux, user, 2); 
		if(aux.getVotos().contains(vt)) {
			int vtAEditar = aux.getVotos().indexOf(vt);
			return  aux.getVotos().get(vtAEditar);
		}
		else {
			return null; 
			}

		}
	
	/**
	 * Método para añadir una reseña
	 * @param vt
	 * @param review
	 */
	public void addReview(Votacion vt, String review) {
		Juego aux= vt.getJuego(); 
		Usuario user= vt.getUsuario(); 
		if(aux.getVotos().contains(vt)) {
			int OldVt = aux.getVotos().indexOf(vt);
			int OldVtUser = user.getVotos().indexOf(vt);
			aux.getVotos().get(OldVt).setReview(review);
			user.getVotos().get(OldVtUser).setReview(review);
			repositorio.save(aux);
			}
		
	}
	
	/**
	 * Método para recuperar las reseñas de un juego
	 * @param ref
	 * @return
	 */
	public List<Votacion> getReviews(long ref){
		Juego aux= this.getByRef(ref);
		List<Votacion> votos= new ArrayList<Votacion>();
		for(int i = 0;i<aux.getVotos().size();i++) {
			if(aux.getVotos().get(i).getReview()!=null) {
				votos.add(aux.getVotos().get(i));
			}
		}
		return votos; 
	}
	
	/**
	 * Método para borrar un voto
	 * @param vt
	 */
	public void deleteVoto(Votacion vt) {
		Juego aux= vt.getJuego(); 
		Usuario user= vt.getUsuario(); 
		if(aux.getVotos().contains(vt)) {
			int OldVt = aux.getVotos().indexOf(vt);
			int OldVtUser = user.getVotos().indexOf(vt);
			aux.getVotos().remove(OldVt);
			user.getVotos().remove(OldVtUser);
			repositorio.save(aux);
		
		}
	}
	
	public Votacion getVotoByRef(Long ref) {
		return votos.findById(ref).orElse(null);
	}
	
	public void editarReview(Votacion voto, String review) {
		voto.setReview(review);
		votos.save(voto);
		}

	
	public void borrarReview(Votacion voto) {
		voto.setReview(null);
		votos.save(voto);
		}
	
	
}
	
	
