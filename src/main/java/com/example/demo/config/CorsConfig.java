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
	
	//private String url= "https://nachommartin.github.io";
	private String url= "http://localhost:4200";

	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/login/**")
				.allowedOrigins(url)
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/register/**")
				.allowedOrigins(url)
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/juego/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","POST", "PUT", "Content-Type","X-Requested-With","accept","Origin",
						"Authorization","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/votacion/**")
				.allowedOrigins(url)
				.allowedHeaders("POST", "DELETE","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/usuario/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","PUT","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/amistad/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","PUT","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/token/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","PUT","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/comentario/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","POST","Content-Type","X-Requested-With",
						"accept","Authorization","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/enviar/**")
				.allowedOrigins(url)
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/carga/**")
				.allowedOrigins(url)
				.allowedHeaders("POST", "Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/listado/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","POST","PUT","Content-Type","X-Requested-With","accept","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
				registry.addMapping("/quiz/**")
				.allowedOrigins(url)
				.allowedHeaders("GET","PUT","Content-Type","X-Requested-With","accept","Authorization","Origin",
						"Access-Control-Request-Method","Access-Control-Request-Headers")
				.allowedMethods("PUT", "DELETE","OPTIONS", "GET", "POST", "HEAD")
				.exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials");
		};
	};
	
	
	}
	
}
