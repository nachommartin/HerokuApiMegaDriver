package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.LoginException;
import com.example.demo.error.UsuarioNotFoundException;
import com.example.demo.model.LoginCredentials;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JWTUtil;
import com.example.demo.services.UsuarioService;

/**
 * Controlador para la autenticación y registro de usuarios 
 * @author Nacho
 *
 */
@RestController
public class AuthController {
	
	@Autowired private UsuarioRepository userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UsuarioService servicioUser; 
    
    /**
     * Método que devuelve un usuario si éste existe en el repositorio para controlar desde el
     * front que no se pueda registrar más de un usuario con el mismo correo
     * @param correo
     * @return
     */
	@GetMapping("/register")
	@ResponseBody
	public JSONObject getUser(@RequestParam(required = false) String correo, @RequestParam(required = false) String nick) { 
		if (correo!=null) {
			Usuario resultado = servicioUser.getByMail(correo);
			if (resultado == null) {
			throw new UsuarioNotFoundException(correo);
			}
			else {
			String cadenaParseo= "{\"correo\":\""+ correo+"\"}";  
			JSONObject json= new JSONObject(cadenaParseo);
		    return json;	
			}
		}
		else {
			Usuario aux = servicioUser.getByNick(nick);
			if (aux == null) {
				throw new UsuarioNotFoundException(correo);
			}
			else {
				String cadenaParseo= "{\"nick\":\""+ nick+"\"}";  
				JSONObject json= new JSONObject(cadenaParseo);
				return json;
			}
		}
	}
	
    /**
     * Método para registrar un usuario.
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody Usuario user){
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getCorreo());
        return Collections.singletonMap("access_token", token);
    }
	
    /**
     * Método para autenticar un usuario
     * @param body
     * @return
     */
	 @PostMapping("/login")
	    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
	        try {
	            UsernamePasswordAuthenticationToken authInputToken =
	                    new UsernamePasswordAuthenticationToken(body.getCorreo(), body.getPassword());

	            authManager.authenticate(authInputToken);

	            String token = jwtUtil.generateToken(body.getCorreo());

	            return Collections.singletonMap("access_token", token);
	        }catch (AuthenticationException authExc){
	            throw new LoginException();
	        }
	    }
	 
	 /**
	  * Gestor de la excepción de un login incorrecto
	  * @param ex
	  * @return
	  */
		@ExceptionHandler(LoginException.class)
		public ResponseEntity<ApiError> handleBadLogin(LoginException ex) {
			ApiError apiError = new ApiError();
			apiError.setEstado(HttpStatus.UNAUTHORIZED);
			apiError.setFecha(LocalDateTime.now());
			apiError.setMensaje(ex.getMessage());
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
		}
	 


}
