package de.rubnic.bandmanager.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * Band Entity
 * Band object which is stored in BandRepository
 *
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "bands")
@ToString(exclude = "musicians")
public class Band {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String genre;
	private String city;
	
	//One to many relation
	//One Band --> Many Musicians
	@OneToMany(mappedBy = "band", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Musician> musicians;
	
	public Band(String name, String genre, String city) {
		this.name = name;
		this.genre = genre;
		this.city = city;
	}
}
