package de.rubnic.bandmanager.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Musician representation model
 * Is generated to display correct json response
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "musicians")
@Relation(collectionRelation = "musicians")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicianModel extends RepresentationModel<MusicianModel>{
	private long id;
	private String name;
	private String instrument;
	private BandModel band;
}
