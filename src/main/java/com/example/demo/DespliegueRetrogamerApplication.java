package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Juego;
import com.example.demo.repository.JuegoRepository;

@SpringBootApplication
public class DespliegueRetrogamerApplication extends SpringBootServletInitializer  {
	

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
							new Juego("Altered Beast", "Mega Drive", "1990", "SEGA", "Beat'em Up"),
							new Juego("Dynamite Duke", "Mega Drive", "1991", "Seibu Kaihatsu", "Shooter"),
							new Juego("Forgotten World", "Mega Drive", "1990", "Capcom", "Shoot'em Up"),
							new Juego("Ghouls'n Ghosts", "Mega Drive", "1990", "Capcom", "Plataformas"),
							new Juego("Golden Axe", "Mega Drive", "1990", "SEGA", "Beat'em Up"),
							new Juego("Last Battle", "Mega Drive", "1990", "SEGA", "Beat'em Up"),
							new Juego("Mystic Defender", "Mega Drive", "1990", "SEGA", "RPG"),
							new Juego("Phantasy Star II", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego("Rambo III", "Mega Drive", "1990", "SEGA", "Shoot'em Up"),
							new Juego("Space Harrier II", "Mega Drive", "1990", "SEGA", "Shoot'em Up"),
							new Juego("Super Thunder Blade", "Mega Drive", "1990", "SEGA", "Simulación aérea"),
							new Juego("Thunder Force II", "Mega Drive", "1990", "Technosoft", "Shoot'em Up"),
							new Juego("Super League", "Mega Drive", "1990", "SEGA", "Simulación deportiva"),
							new Juego("Truxton", "Mega Drive", "1990", "Toaplan", "Shoot'em Up"),
							new Juego("World Cup Italia '90", "Mega Drive", "1990", "SEGA", "Simulación deportiva"),
							new Juego("Zoom!", "Mega Drive", "1990", "Discovery", "Puzzle"),
							new Juego("After Burner II", "Mega Drive", "1991", "SEGA", "Simulación aérea"),
							new Juego("Arrow Flash", "Mega Drive", "1991", "Renovation", "Shoot'em Up"),
							new Juego("Batman", "Mega Drive", "1992", "Sunsoft", "Plataformas"),
							new Juego("Battle Squadron", "Mega Drive", "1991", "Electronic Arts", "Shoot'em Up"),
							new Juego("Budokan: The Martial Spirit", "Mega Drive", "1990", "Electronic Arts", "Lucha"),
							new Juego("Burning Force", "Mega Drive", "1991", "Namco", "Shoot'em Up"),
							new Juego("Castle of Illusion starring Mickey Mouse", "Mega Drive", "1990", "SEGA", "Plataformas"),
							new Juego("Cyberball", "Mega Drive", "1990", "SEGA", "Simulación deportiva"), 
							new Juego("DJ Boy", "Mega Drive", "1990", "Kaneko", "Beat'em Up"),
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
							new Juego ("Sword of Sodan", "Mega Drive", "1991", "Electronic Arts", "Beat'em Up"),
							new Juego ("Sword of Vermilion", "Mega Drive", "1991", "SEGA", "RPG"),
							new Juego ("Thunder Force III", "Mega Drive", "1992", "Technosoft", "Shoot'em Up"),
							new Juego ("Zany Golf", "Mega Drive", "1990", "Electronic Arts", "Simulación deportiva"),
							new Juego ("Wonder Boy III: Monster Lair", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Xenon 2: Megablast", "Mega Drive", "1992", "Virgin", "Shoot'em Up"),
							new Juego ("688 Attack Sub", "Mega Drive", "1991", "SEGA", "Simulación bélica"),
							new Juego ("M-1 Abrams Battle Tank", "Mega Drive", "1991", "SEGA", "Simulación bélica"),
							new Juego ("Alien Storm", "Mega Drive", "1991", "SEGA", "Beat'em Up"),
							new Juego ("Spider-Man vs. The Kingpin", "Mega Drive", "1991", "SEGA", "Plataformas"),							
							new Juego ("Art Alive!", "Mega Drive", "1992", "SEGA", "Educativo"),
							new Juego ("Back to the Future Part III", "Mega Drive", "1992", "Image Works", "Acción"),
							new Juego ("Battletoads", "Mega Drive", "1993", "SEGA", "Beat'em Up"),
							new Juego ("Blockout", "Mega Drive", "1991", "Electronic Arts", "Puzzle"),
							new Juego ("Bonanza Bros", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Buck Rogers: Countdown to Doomsday", "Mega Drive", "1992", "Electronic Arts", "RPG"),
							new Juego ("Bulls vs. Lakers and the NBA Playoffs", "Mega Drive", "1992", "Electronic Arts", "Simulación deportiva"),
							new Juego ("California Games", "Mega Drive", "1991", "SEGA", "Simulación deportiva"),
							new Juego ("Centurion: Defender of Rome", "Mega Drive", "1991", "Electronic Arts", "Estrategia"),
							new Juego ("Columns", "Mega Drive", "1991", "SEGA", "Puzzle"),
							new Juego ("Crack Down", "Mega Drive", "1991", "SEGA", "Acción"),
							new Juego ("Decap Attack", "Mega Drive", "1991", "SEGA", "Plataformas"),
							new Juego ("Dick Tracy", "Mega Drive", "1991", "SEGA", "Shoot'em Up")

							)
					);
			
			
		
	};

}
	

}
