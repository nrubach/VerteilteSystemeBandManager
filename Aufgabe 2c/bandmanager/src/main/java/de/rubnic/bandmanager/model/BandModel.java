package de.rubnic.bandmanager.model;

import java.util.List;
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
 * Band representation model
 * Is generated to display correct json response
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "bands")
@Relation(collectionRelation = "bands")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BandModel extends RepresentationModel<BandModel>{
	private long id;
	private String name;
	private String genre;
	private String city;
	private List<MusicianModel> musicians;
}
