package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.model.Usuario;
import com.example.demo.services.UsuarioService;
import java.util.Collections;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioService servicioUsuario; 


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario userRes = servicioUsuario.getByMail(email);
        if(userRes==null)
            throw new UsernameNotFoundException("No existe el usuario con el siguiente correo: " + email);
        return new org.springframework.security.core.userdetails.User(
                email,
                userRes.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}