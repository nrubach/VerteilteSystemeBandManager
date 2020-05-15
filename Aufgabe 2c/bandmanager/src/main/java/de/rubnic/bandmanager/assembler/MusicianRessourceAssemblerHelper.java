package de.rubnic.bandmanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import de.rubnic.bandmanager.controller.BandController;
import de.rubnic.bandmanager.controller.MusicianController;
import de.rubnic.bandmanager.controller.RootController;
import de.rubnic.bandmanager.entities.Band;
import de.rubnic.bandmanager.entities.Musician;
import de.rubnic.bandmanager.model.BandModel;
import de.rubnic.bandmanager.model.MusicianModel;

/**
 * 
 * Class to assemble a Representation Model of the Musician Object
 *
 */

@Component //Loads class at start of application
public class MusicianRessourceAssemblerHelper extends RepresentationModelAssemblerSupport<Musician, MusicianModel>{
	public MusicianRessourceAssemblerHelper() {
		super(MusicianController.class, MusicianModel.class);
	}

	/*
	 * Convert single Musician to MusicianModel
	 */
	@Override
	public MusicianModel toModel(Musician musician) {
		MusicianModel model = instantiateModel(musician);
		model.add(linkTo(methodOn(MusicianController.class).getMusician(musician.getId())).withSelfRel()); //Link to musician itself
		model.add(linkTo(methodOn(MusicianController.class).getAllMusicians()).withRel("musicians")); //Link to all musicians
		model.add(linkTo(methodOn(RootController.class).root()).withRel("api")); //Link to API root
		//Set musician model variables
		model.setId(musician.getId());
		model.setName(musician.getName());
		model.setInstrument(musician.getInstrument());
		//Set band if existant
		Band band = musician.getBand();
		if(band == null) {
			model.setBand(null);
		} else {
			model.setBand(BandModel.builder()
						.id(band.getId())
						.name(band.getName())
						.city(band.getCity())
						.genre(band.getGenre())
						.build()
						.add(linkTo(methodOn(BandController.class).getBand(band.getId())).withSelfRel()) //Link to band
						.add(linkTo(methodOn(BandController.class).getAllBands()).withRel("bands"))); //Link to all bands
		}
		return model;
	}
	
	/*
	 * Convert multiple musicians to MusicianModel collection
	 */
	@Override
    public CollectionModel<MusicianModel> toCollectionModel(Iterable<? extends Musician> entities) {
        CollectionModel<MusicianModel> musicianModels = super.toCollectionModel(entities);
        musicianModels.add(linkTo(methodOn(MusicianController.class).getAllMusicians()).withSelfRel()); //Link to all musicians
        musicianModels.add(linkTo(methodOn(RootController.class).root()).withRel("api")); //Link to API root
        return musicianModels;
    }
}
