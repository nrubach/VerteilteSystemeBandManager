package de.rubnic.bandmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.rubnic.bandmanager.entities.Musician;

/**
 * 
 * MusicianRepository stores musicians
 *
 */
public interface MusicianRepository extends JpaRepository<Musician, Long> {

}
