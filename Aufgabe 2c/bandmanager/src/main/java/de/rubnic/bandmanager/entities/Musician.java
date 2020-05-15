package de.rubnic.bandmanager.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * Musician Entity
 * Musician object which is stored in MusicianRepository
 *
 */

@Entity //JPA makes object ready for storage
@Data //Generates Getters and Setters - Lombok
@NoArgsConstructor //Generates empty constructor - Lombok
@Table(name = "musicians")
@ToString(exclude = "band")
public class Musician {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String instrument;
	
	//Many to one relation to band
	//Many Musicians -> One Band
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="band_id")
	private Band band;
	
	public Musician(String name, String instrument, Band band) {
		this.name = name;
		this.instrument = instrument;
		this.band = band;
	}
	
	public Musician(String name, String instrument) {
		this.name = name;
		this.instrument = instrument;
	}
	
}
