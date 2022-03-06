package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de acceso externo a nuestra API mediante Cors
 * @author Nacho
 *
 */
@Configuration
public class CorsConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/login/**")
				.allowedOrigins("https://nachommartin.github.io/")
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/register/**")
				.allowedOrigins("https://nachommartin.github.io/")
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/juego/**")
				.allowedOrigins("https://nachommartin.github.io/")
				.allowedHeaders("GET","POST", "PUT", "Content-Type","X-Requested-With","accept","Origin",
						"Authorization","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/votacion/**")
				.allowedOrigins("https://nachommartin.github.io/")
				.allowedHeaders("POST","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/usuario/**")
				.allowedOrigins("https://nachommartin.github.io/")
				.allowedHeaders("GET","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
		};
	};
	
	
	}
	
}
