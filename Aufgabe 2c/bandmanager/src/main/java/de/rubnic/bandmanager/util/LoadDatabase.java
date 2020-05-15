package de.rubnic.bandmanager.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.rubnic.bandmanager.entities.Band;
import de.rubnic.bandmanager.entities.Musician;
import de.rubnic.bandmanager.repositories.BandRepository;
import de.rubnic.bandmanager.repositories.MusicianRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Helper class to initiate Musician and Band Repository
 * Both are afterwards registered as beans and can be used in other classes
 *
 */

@Configuration
@Slf4j
public class LoadDatabase {
	
	@Bean
	CommandLineRunner initRepos(MusicianRepository musicianRepo, BandRepository bandRepo) {
		return args ->  {
			Band sooster = new Band("Sooster", "Pop", "Reutlingen");
			Band fallOutBoy = new Band("Fall Out Boy", "Rock", "Chicago");
			log.info("Preloading " + bandRepo.save(sooster));
			log.info("Preloading " + bandRepo.save(fallOutBoy));
			log.info("Preloading " + musicianRepo.save(new Musician("Nico", "Violin", sooster)));
			log.info("Preloading " + musicianRepo.save(new Musician("Vero", "Violin", sooster)));
			log.info("Preloading " + musicianRepo.save(new Musician("Yannick", "Clarinet", sooster)));
			log.info("Preloading " + musicianRepo.save(new Musician("Morgane", "Vocal", sooster)));
			log.info("Preloading " + musicianRepo.save(new Musician("Patrick", "Guitar", fallOutBoy)));
			log.info("Preloading " + musicianRepo.save(new Musician("Joe", "Guitar", fallOutBoy)));
			log.info("Preloading " + musicianRepo.save(new Musician("Pete", "Bass", fallOutBoy)));
			log.info("Preloading " + musicianRepo.save(new Musician("Andy", "Drums", fallOutBoy)));
		};
	}
	
//	@Bean
//	CommandLineRunner initBandRepo(BandRepository bandRepo) {
//		return args -> {
//			log.info("Preloading " + bandRepo.save(new Band("Sooster", "Pop", "Reutlingen")));
//		};
//	}
}
