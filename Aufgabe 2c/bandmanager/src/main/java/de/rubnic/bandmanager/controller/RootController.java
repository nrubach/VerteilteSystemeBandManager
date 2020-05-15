package de.rubnic.bandmanager.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * Root Controller as API entry point
 *
 */
@CrossOrigin
@RestController
public class RootController {
	/*
	 * API path /api
	 * Responds paths to musicians and bands
	 */	
	@GetMapping("/api")
    public RepresentationModel<?> root() {
        RepresentationModel<?> rootResource = new RepresentationModel<>();
        rootResource.add(
            linkTo(methodOn(BandController.class).getAllBands()).withRel("bands"),
            linkTo(methodOn(MusicianController.class).getAllMusicians()).withRel("musicians"),
            linkTo(methodOn(RootController.class).root()).withSelfRel());
        return rootResource;
    }
}
