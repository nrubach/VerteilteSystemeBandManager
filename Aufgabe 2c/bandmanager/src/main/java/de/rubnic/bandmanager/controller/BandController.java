package de.rubnic.bandmanager.controller;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.rubnic.bandmanager.assembler.BandRessourceAssemblerHelper;
import de.rubnic.bandmanager.assembler.MusicianRessourceAssemblerHelper;
import de.rubnic.bandmanager.entities.Band;
import de.rubnic.bandmanager.model.BandModel;
import de.rubnic.bandmanager.model.MusicianModel;
import de.rubnic.bandmanager.repositories.BandRepository;
import de.rubnic.bandmanager.repositories.MusicianRepository;
import de.rubnic.bandmanager.util.EntityNotFoundException;

/**
 * 
 * Controller class for /bands
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/api/bands")
public class BandController {
	
	private final BandRepository bandRepo; //Repository where all bands are saved
	private final BandRessourceAssemblerHelper bandAssembler; //Component Assembler Helper
	private final MusicianRessourceAssemblerHelper musicianAssembler; //Helper class to assemble response
	
	//Constructor will automatically be executed and filled with correct parameters, because they are registered as @Bean (in LoadDatabase.java) and @Component
	public BandController(BandRepository bandRepo, BandRessourceAssemblerHelper bandAssembler, MusicianRepository musicianRepo, MusicianRessourceAssemblerHelper musicianAssembler) {
		this.bandRepo = bandRepo;
		this.bandAssembler = bandAssembler;
		this.musicianAssembler = musicianAssembler;
	}

	/*
	 * API path /bands
	 * Returns list of all bands in bandRepo repository
	 */
	@GetMapping
	public CollectionModel<BandModel> getAllBands() {
		return bandAssembler.toCollectionModel(bandRepo.findAll());
	}
	
	/*
	 * API path /bands
	 * POST Request to create new band
	 */
	@PostMapping
	ResponseEntity<?> createBand(@RequestBody Band band) throws URISyntaxException{
		BandModel entityModel = bandAssembler.toModel(bandRepo.save(band));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	/*
	 * API path /bands/id
	 * GET Request to display a specific band
	 */
	@GetMapping("/{id}")
	public BandModel getBand(@PathVariable Long id) {
		return bandRepo.findById(id).map(bandAssembler::toModel).orElseThrow(() -> new EntityNotFoundException("Band", id));
	}
	
	/*
	 * API path /bands/id/musicians
	 * Get all musicians belonging to a specific band
	 */
	@GetMapping("/{id}/musicians")
	public CollectionModel<MusicianModel> getMusiciansOfBand(@PathVariable Long id) {
		Optional<Band> band = bandRepo.findById(id);
		if(band.isPresent()) {
			return musicianAssembler.toCollectionModel(band.get().getMusicians());
		} else {
			throw new EntityNotFoundException("Band", id);
		}
	}
	
	/*
	 * API path /bands/id
	 * PUT Request to edit / replace a specific band
	 */
	@PutMapping("/{id}")
	ResponseEntity<?> replaceBand(@RequestBody Band newBand, @PathVariable Long id) throws URISyntaxException {
		Band updatedBand =  bandRepo.findById(id)
		      .map(band -> {
		    	  band.setName(newBand.getName());
		    	  band.setCity(newBand.getCity());
		    	  band.setGenre(newBand.getGenre());
		    	  band.setMusicians(newBand.getMusicians());
		        return bandRepo.save(band);
		      })
		      .orElseGet(() -> {
		        newBand.setId(id);
		        return bandRepo.save(newBand);
		      });
		
		BandModel entityModel = bandAssembler.toModel(updatedBand);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
}
