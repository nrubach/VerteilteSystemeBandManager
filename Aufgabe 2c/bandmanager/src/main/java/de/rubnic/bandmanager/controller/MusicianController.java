package de.rubnic.bandmanager.controller;

import java.net.URISyntaxException;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.rubnic.bandmanager.assembler.MusicianRessourceAssemblerHelper;
import de.rubnic.bandmanager.entities.Musician;
import de.rubnic.bandmanager.model.MusicianModel;
import de.rubnic.bandmanager.repositories.MusicianRepository;
import de.rubnic.bandmanager.util.EntityNotFoundException;

/**
 * 
 * Musician Controller class
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/api/musicians")
public class MusicianController {
	
	private final MusicianRepository musicianRepo; //Repo with all musicians
	private final MusicianRessourceAssemblerHelper musicianAssembler; //Helper class to assemble response
	
	//Constructor will automatically be executed and filled with correct parameters, because they are registered as @Bean (in LoadDatabase.java) and @Component
	public MusicianController(MusicianRepository musicianRepo, MusicianRessourceAssemblerHelper musicianAssembler) {
		this.musicianRepo = musicianRepo;
		this.musicianAssembler = musicianAssembler;
	}
	
	/*
	 * API path /musicians
	 * Returns list of all musicians in musicianRepo repository
	 */
	@GetMapping
	public CollectionModel<MusicianModel> getAllMusicians() {
		return musicianAssembler.toCollectionModel(musicianRepo.findAll());
	}
	
	/*
	 * API path /musicians
	 * POST Request to create new musician
	 */
	@PostMapping
	ResponseEntity<?> createMusician(@RequestBody Musician musician) throws URISyntaxException{
		/*if(musicianModel.getBand_id() != 0) {
			Band band = bandRepo.getOne(musicianModel.getBand_id());
			musician = new Musician(musicianModel.getName(), musicianModel.getInstrument(), band);
		} else {
			musician = new Musician(musicianModel.getName(), musicianModel.getInstrument());
		}*/
		
		musicianRepo.save(musician);
		MusicianModel entityModel = musicianAssembler.toModel(musicianRepo.getOne(musician.getId()));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	/*
	 * API path /musicians/id
	 * GET Request to display a specific musician
	 */
	@GetMapping("/{id}")
	public MusicianModel getMusician(@PathVariable Long id) {
		return musicianRepo.findById(id).map(musicianAssembler::toModel).orElseThrow(() -> new EntityNotFoundException("Musician", id));
	}
	
	/*
	 * API path /musicians/id
	 * PUT Request to edit / replace a specific musician
	 */
	@PutMapping("/{id}")
	ResponseEntity<?> replaceMusician(@RequestBody Musician newMusician, @PathVariable Long id) throws URISyntaxException {
		Musician updatedMusician =  musicianRepo.findById(id)
		      .map(musician -> {
		    	  musician.setName(newMusician.getName());
		    	  musician.setInstrument(newMusician.getInstrument());
		    	  return musicianRepo.save(musician);
		      })
		      .orElseGet(() -> {
		    	return musicianRepo.save(new Musician(newMusician.getName(), newMusician.getInstrument()));
		      });
		
		MusicianModel entityModel = musicianAssembler.toModel(updatedMusician);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
}
