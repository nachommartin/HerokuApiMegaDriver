package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Juego;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Respuesta;
import com.example.demo.model.Usuario;
import com.example.demo.repository.JuegoRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.UsuarioRepository;

@SpringBootApplication
public class DespliegueRetrogamerApplication extends SpringBootServletInitializer  {
	
	@Autowired
	private PasswordEncoder encriptador; 
	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
				return builder.sources(DespliegueRetrogamerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DespliegueRetrogamerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initData(JuegoRepository repositorio) {
		return (args) ->{
			repositorio.saveAll(
					Arrays.asList(new Juego("Alex Kidd in the Enchanted Castle", "Mega Drive", "1990", "SEGA", "Plataformas"),
							new Juego("Altered Beast", "Mega Drive", "1990", "SEGA", "Beat'em up"),
							new Juego("Dynamite Duke", "Mega Drive", "1991", "Seibu Kaihatsu", "Shooter"),
							new Juego("Forgotten World", "Mega Drive", "1990", "Capcom", "Shoot'em up"),
							new Juego("Ghouls'n Ghosts", "Mega Drive", "1990", "Capcom", "Plataformas"),
							new Juego("Golden Axe", "Mega Drive", "1990", "SEGA", "Beat'em up"),
							new Juego("Last Battle", "Mega Drive", "1990", "SEGA", "Beat'em up"),
							new Juego("Mystic Defender", "Mega Drive", "1990", "SEGA", "RPG"),
							new Juego("Phantasy Star II", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego("Rambo III", "Mega Drive", "1990", "SEGA", "Shoot'em up"),
							new Juego("Space Harrier II", "Mega Drive", "1990", "SEGA", "Shoot'em up"),
							new Juego("Super Thunder Blade", "Mega Drive", "1990", "SEGA", "Simulación bélica"),
							new Juego("Thunder Force II", "Mega Drive", "1990", "Technosoft", "Shoot'em up"),
							new Juego("Super League", "Mega Drive", "1990", "SEGA", "Simulación deportiva"),
							new Juego("Truxton", "Mega Drive", "1990", "Toaplan", "Shoot'em up"),
							new Juego("World Cup Italia '90", "Mega Drive", "1990", "SEGA", "Simulación deportiva"),
							new Juego("Zoom!", "Mega Drive", "1990", "Discovery", "Puzzle"),
							new Juego("After Burner II", "Mega Drive", "1991", "SEGA", "Simulación bélica"),
							new Juego("Arrow Flash", "Mega Drive", "1991", "Renovation", "Shoot'em up"),
							new Juego("Batman", "Mega Drive", "1992", "Sunsoft", "Plataformas"),
							new Juego("Battle Squadron", "Mega Drive", "1991", "Electronic Arts", "Shoot'em up"),
							new Juego("Budokan: The Martial Spirit", "Mega Drive", "1990", "Electronic Arts", "Lucha"),
							new Juego("Burning Force", "Mega Drive", "1991", "Namco", "Shoot'em up"),
							new Juego("Castle of Illusion starring Mickey Mouse", "Mega Drive", "1990", "SEGA", "Plataformas"),
							new Juego("Cyberball", "Mega Drive", "1990", "SEGA", "Simulación deportiva"), 
							new Juego("DJ Boy", "Mega Drive", "1990", "Kaneko", "Beat'em up"),
							new Juego ("ESWAT: City Under Siege", "Mega Drive", "1990", "SEGA", "Shoot'em up"),
							new Juego ("Fire Shark", "Mega Drive", "1991", "Toaplan", "Shoot'em up"),
							new Juego ("Ghostbusters", "Mega Drive", "1990", "SEGA", "Shoot'em up"),
							new Juego ("Hard Drivin'", "Mega Drive", "1991", "Tengen", "Simulación de carreras"),
							new Juego ("Hellfire", "Mega Drive", "1991", "Toaplan", "Shoot'em up"),
							new Juego ("Herzog Zwei", "Mega Drive", "1990", "Toaplan", "Estrategia"),
							new Juego ("Ishido: The Way of Stones", "Mega Drive", "1990", "Accolade", "Puzzle"),
							new Juego ("James 'Buster' Douglas Knock Out Boxing", "Mega Drive", "1990", "Taito", "Lucha"),
							new Juego ("Joe Montana Football", "Mega Drive", "1991", "SEGA", "Simulación deportiva"),
							new Juego ("John Madden American Football", "Mega Drive", "1990", "Electronic Arts", "Simulación deportiva"),
							new Juego ("Lakers versus Celtics and the NBA Playoffs", "Mega Drive", "1991", "Electronic Arts", "Simulación deportiva"),
							new Juego ("Michael Jackson's Moonwalker", "Mega Drive", "1990", "SEGA", "Plataformas"),
							new Juego ("Super Real Basketball", "Mega Drive", "1991", "SEGA", "Simulación deportiva"),
							new Juego ("Phelios", "Mega Drive", "1991", "Namco", "Shoot'em up"),
							new Juego ("Populous", "Mega Drive", "1990", "Electronic Arts", "Estrategia"),
							new Juego ("Strider", "Mega Drive", "1991", "Capcom", "Plataformas"),
							new Juego ("Super Hang-On", "Mega Drive", "1990", "SEGA", "Simulación de carreras"),
							new Juego ("Super Monaco GP", "Mega Drive", "1991", "SEGA", "Simulación de carreras"),
							new Juego ("Sword of Sodan", "Mega Drive", "1991", "Electronic Arts", "Beat'em up"),
							new Juego ("Sword of Vermilion", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego ("Thunder Force III", "Mega Drive", "1992", "Technosoft", "Shoot'em up"),
							new Juego ("Zany Golf", "Mega Drive", "1990", "Electronic Arts", "Simulación deportiva"),
							new Juego ("Wonder Boy III: Monster Lair", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Xenon 2: Megablast", "Mega Drive", "1992", "Virgin", "Shoot'em up"),
							new Juego ("688 Attack Sub", "Mega Drive", "1991", "SEGA", "Simulación bélica"),
							new Juego ("M-1 Abrams Battle Tank", "Mega Drive", "1991", "SEGA", "Simulación bélica"),
							new Juego ("Alien Storm", "Mega Drive", "1991", "SEGA", "Beat'em up"),
							new Juego ("Spider-Man vs. The Kingpin", "Mega Drive", "1991", "SEGA", "Plataformas"),							
							new Juego ("Art Alive!", "Mega Drive", "1992", "SEGA", "Educativo"),
							new Juego ("Back to the Future Part III", "Mega Drive", "1992", "Image Works", "Acción"),
							new Juego ("Battletoads", "Mega Drive", "1993", "SEGA", "Beat'em up"),
							new Juego ("Blockout", "Mega Drive", "1991", "Electronic Arts", "Puzzle"),
							new Juego ("Bonanza Bros", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Buck Rogers: Countdown to Doomsday", "Mega Drive", "1992", "Electronic Arts", "RPG"),
							new Juego ("Bulls vs. Lakers and the NBA Playoffs", "Mega Drive", "1992", "Electronic Arts", "Simulación deportiva"),
							new Juego ("California Games", "Mega Drive", "1991", "SEGA", "Simulación deportiva"),
							new Juego ("Centurion: Defender of Rome", "Mega Drive", "1991", "Electronic Arts", "Estrategia"),
							new Juego ("Columns", "Mega Drive", "1991", "SEGA", "Puzzle"),
							new Juego ("Crack Down", "Mega Drive", "1991", "SEGA", "Acción"),
							new Juego ("Decap Attack", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Dick Tracy", "Mega Drive", "1991", "SEGA", "Shoot'em up"),
							new Juego ("F-22 Interceptor: Advanced Tactical Fighter", "Mega Drive", "1991", "Electronic Arts", "Simulación bélica"),
							new Juego ("The Faery Tale Adventure", "Mega Drive", "1991", "Electronic Arts", "RPG"),
							new Juego ("Fantasia", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Fatal Labyrinth", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego ("Flicky", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Gain Ground", "Mega Drive", "1991", "SEGA", "Shoot'em up"),
							new Juego ("G-LOC: Air Battle", "Mega Drive", "1993", "SEGA", "Simulación bélica"),
							new Juego("Golden Axe II", "Mega Drive", "1991", "SEGA", "Beat'em up"),
							new Juego("The Immortal", "Mega Drive", "1991", "Electronic Arts", "RPG"),
							new Juego("James Pond: Underwater Agent", "Mega Drive", "1991", "Electronic Arts", "Plataformas"),
							new Juego("James Pond II: Codename: RoboCod", "Mega Drive", "1991", "Electronic Arts", "Plataformas"),
							new Juego("Jewel Master", "Mega Drive", "1991", "SEGA", "Beat'em up"),
							new Juego("John Madden Football '92", "Mega Drive", "1991", "Electronic Arts", "Simulación deportiva"),
							new Juego("Fatal Rewind", "Mega Drive", "1991", "Electronic Arts", "Shoot'em up"),
							new Juego("King's Bounty", "Mega Drive", "1991", "Electronic Arts", "RPG"),
							new Juego("Klax", "Mega Drive", "1992", "Tengen", "Puzzle"),
							new Juego("M-1 Abrams Battle Tank", "Mega Drive", "1991", "Electronic Arts", "Simulación bélica"),
							new Juego("Marble Madness", "Mega Drive", "1991", "Electronic Arts", "Puzzle"),
							new Juego("Marvel Land", "Mega Drive", "1992", "Namco", "Plataformas"),
							new Juego("Chiki Chiki Boys", "Mega Drive", "1993", "Capcom", "Plataformas"),
							new Juego("Mercs", "Mega Drive", "1991", "Capcom", "Shoot'em up"),
							new Juego("Might and Magic: Gates to Another World", "Mega Drive", "1991", "Electronic Arts", "RPG"),
							new Juego("Mike Ditka Power Football", "Mega Drive", "1991", "Accolade", "Simulación deportiva"),
							new Juego("Ms. Pac-Man", "Mega Drive", "1995", "Namco", "Acción"),
							new Juego ("Out Run", "Mega Drive", "1991", "SEGA", "Simulación de carreras"),
							new Juego("Pac-Mania", "Mega Drive", "1991", "Namco", "Acción"),
							new Juego("Paper Boy", "Mega Drive", "1992", "Tengen", "Acción"),
							new Juego("PGA Tour Golf", "Mega Drive", "1991", "Electronic Arts", "Simulación deportiva"),
							new Juego("Pit-Fighter", "Mega Drive", "1992", "Tengen", "Lucha"),
							new Juego("Quackshot Starring Donald Duck", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego("The Revenge of Shinobi", "Mega Drive", "1990", "SEGA", "Plataformas"),
							new Juego("Rings of Power", "Mega Drive", "1992", "Electronic Arts", "RPG"),
							new Juego("Road Rash", "Mega Drive", "1992", "Electronic Arts", "Simulación de carreras"),
							new Juego("Rolling Thunder 2", "Mega Drive", "1993", "Namco", "Shoot'em up"),
							new Juego("Shadow Dancer: The Secret of Shinobi", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego("Shadow of the Beast", "Mega Drive", "1991", "Electronic Arts", "Beat'em up"),
							new Juego("Shining in the Darkness", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego("Super Smash T.V.", "Mega Drive", "1992", "Flying Edge", "Acción"),
							new Juego("Sonic the Hedgehog", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego("Speedball 2", "Mega Drive", "1992", "Virgin Games", "Simulación deportiva"),
							new Juego("Starflight", "Mega Drive", "1992", "Electronic Arts", "Simulación bélica"),
							new Juego("Streets of Rage", "Mega Drive", "1991", "SEGA", "Beat'em up"),
							new Juego("ToeJam & Earl", "Mega Drive", "1991", "SEGA", "Acción"),
							new Juego("Toki: Going Ape Spit", "Mega Drive", "1992", "SEGA", "Plataformas"),
							new Juego("Turrican", "Mega Drive", "1991", "Accolade", "Shoot'em up"),
							new Juego("Two Crude Dudes", "Mega Drive", "1992", "SEGA", "Beat'em up"),
							new Juego("Gynoug", "Mega Drive", "1992", "SEGA", "Shoot'em up"),
							new Juego("Wonder Boy in Monster World", "Mega Drive", "1992", "SEGA", "Plataformas"),
							new Juego("Wrestle War", "Mega Drive", "1991", "SEGA", "Lucha"),
							new Juego("Zero Wing", "Mega Drive", "1992", "SEGA", "Shoot'em up")

							)
					);
			
			
		
		};

	
	}
	
	@Bean
	CommandLineRunner initAdmin(UsuarioRepository repositorio) {
		return (args) ->{
			Usuario aux= new Usuario("nacho@email.com", "nach85", encriptador.encode("1Arrakis"));
			aux.setRol("ADMIN");
			repositorio.save(aux);
	};
	}
	
	
	@Bean
	CommandLineRunner initQuiz(QuizRepository repositorio) {
		return (args) ->{
			Quiz quiz= new Quiz("Nivel 1");
			Question preg1 = new Question("¿En qué año salió Sonic The Hedgehog?", quiz, 1);
			Respuesta r1= new Respuesta("1990", preg1, 1);
			Respuesta r2= new Respuesta("1989", preg1, 2);
			Respuesta r3= new Respuesta("1991", preg1, 3);
			Respuesta r4= new Respuesta("1992", preg1, 4);
			Question preg2 = new Question("¿Qué desarrolladora llevó a Sparkster a la Mega Drive?", quiz, 2);
			Respuesta a1= new Respuesta("Capcom", preg2, 1);
			Respuesta a2= new Respuesta("SEGA", preg2, 2);
			Respuesta a3= new Respuesta("Electronic Arts", preg2, 3);
			Respuesta a4= new Respuesta("Konami", preg2, 4);
			Question preg3 = new Question("¿Qué tres juegos conformaban el pack Mega Games I?", quiz, 3);
			Respuesta resp1= new Respuesta("Super Hang-On, World Cup Italia '90 y Columns", preg3, 1);
			Respuesta resp2= new Respuesta("Super Monaco GP, World Cup Italia '90 y Columns", preg3, 2);
			Respuesta resp3= new Respuesta("The Revenge of Shinobi, Streets of Rage y Golden Axe", preg3, 3);
			Respuesta resp4= new Respuesta("Columns, Golden Axe y Sega Soccer", preg3, 4);
			preg3.setRespuestaCorrect(resp1);
			Question preg4 = new Question("¿Cómo se llama la Mega Drive en Norteamérica?", quiz, 4);
			Respuesta ans1= new Respuesta("Igual que en el resto del mundo, Mega Drive", preg4, 1);
			Respuesta ans2= new Respuesta("Super SEGA", preg4, 2);
			Respuesta ans3= new Respuesta("Mega Drive 32X", preg4, 3);
			Respuesta ans4= new Respuesta("Genesis", preg4, 4);
			preg1.getRespuestas().add(r1);
			preg1.getRespuestas().add(r2);
			preg1.getRespuestas().add(r3);
			preg1.getRespuestas().add(r4);
			preg1.setRespuestaCorrect(r3);
			preg2.getRespuestas().add(a1);
			preg2.getRespuestas().add(a2);
			preg2.getRespuestas().add(a3);
			preg2.getRespuestas().add(a4);
			preg2.setRespuestaCorrect(a4);
			preg3.getRespuestas().add(resp1);
			preg3.getRespuestas().add(resp2);
			preg3.getRespuestas().add(resp3);
			preg3.getRespuestas().add(resp4);
			preg3.setRespuestaCorrect(resp1);
			preg4.getRespuestas().add(ans1);
			preg4.getRespuestas().add(ans2);
			preg4.getRespuestas().add(ans3);
			preg4.getRespuestas().add(ans4);
			preg4.setRespuestaCorrect(ans4);
			quiz.getPreguntas().add(preg1);
			quiz.getPreguntas().add(preg2);
			quiz.getPreguntas().add(preg3);
			quiz.getPreguntas().add(preg4);
			quiz.setNumPreguntas(quiz.getPreguntas().size());
			repositorio.save(quiz);
	
	};
	}

}
