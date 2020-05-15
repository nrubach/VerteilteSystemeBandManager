package de.rubnic.bandmanager.assembler;

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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * Class to assemble a Representation Model of the Band Object
 *
 */

@Component //Loads class at start of application
public class BandRessourceAssemblerHelper extends RepresentationModelAssemblerSupport<Band, BandModel>{

	public BandRessourceAssemblerHelper() {
		super(BandController.class, BandModel.class);
	}

	/*
	 * Convert single Band object to Band model
	 */
	@Override
	public BandModel toModel(Band band) {
		BandModel model = instantiateModel(band);
		model.add(linkTo(methodOn(BandController.class).getBand(band.getId())).withSelfRel()); //HATEOAS Link to band itself
		model.add(linkTo(methodOn(BandController.class).getMusiciansOfBand(band.getId())).withRel("bandmusicians"));
		model.add(linkTo(methodOn(BandController.class).getAllBands()).withRel("bands")); //HATEOAS Link to all bands
		model.add(linkTo(methodOn(RootController.class).root()).withRel("api")); //HATEOAS Link to api root
		//Fill model with values of band
		model.setId(band.getId());
		model.setCity(band.getCity());
		model.setGenre(band.getGenre());
		model.setName(band.getName());
		//Add musicians + musician reference to model
		List<Musician> musicians = band.getMusicians();
		if(musicians == null) {
			return model;
		}
		if(band.getMusicians().isEmpty()) {
			model.setMusicians(new ArrayList<>());
		} else {
			model.setMusicians(band.getMusicians().stream()
					.map(musician -> MusicianModel.builder()
							.id(musician.getId())
							.name(musician.getName())
							.instrument(musician.getInstrument())
							.build()
							.add(linkTo(methodOn(MusicianController.class).getMusician(musician.getId())).withSelfRel()) //Link to specific musician
							.add(linkTo(methodOn(MusicianController.class).getAllMusicians()).withRel("musicians"))) // Link to all musicians
					.collect(Collectors.toList()));
		}
		return model;
	}
	
	/*
	 * Convert multiple Band objects to BandModel collection
	 */
	@Override
    public CollectionModel<BandModel> toCollectionModel(Iterable<? extends Band> entities) {
        CollectionModel<BandModel> bandModels = super.toCollectionModel(entities);
        bandModels.add(linkTo(methodOn(BandController.class).getAllBands()).withSelfRel()); //Link to all bands
        bandModels.add(linkTo(methodOn(RootController.class).root()).withRel("api")); //Link to API root
        return bandModels;
    }

}
