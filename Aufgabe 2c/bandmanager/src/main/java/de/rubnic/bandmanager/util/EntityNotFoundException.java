package de.rubnic.bandmanager.util;

/**
 * 
 * Custom Exception class to be thrown when non existant ID is requested
 *
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entityType, Long id) {
		super("Could not find "+ entityType +" with id " + id);
	}
}
