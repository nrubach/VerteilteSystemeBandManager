package de.rubnic.bandmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.rubnic.bandmanager.entities.Band;

/**
 * 
 * BandRepository stores bands
 *
 */
public interface BandRepository extends JpaRepository<Band, Long> {

}
