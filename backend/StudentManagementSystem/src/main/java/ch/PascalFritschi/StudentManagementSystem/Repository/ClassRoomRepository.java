package ch.PascalFritschi.StudentManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.PascalFritschi.StudentManagementSystem.model.ClassRoom;

import java.util.Optional;


/**
 * The `ClassRoomRepository` interface provides methods to interact with the database for managing class entities.
 */

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	
	
	/**
     * Retrieves a class by its ID.
     *
     * @param id The ID of the class to retrieve.
     * @return An Optional containing the class if found, or an empty Optional if not found.
     */
	
    Optional<ClassRoom> findById(Long id);

    /**
     * 
     * @param name
     * @return An Optional containing the class if found, or an empty Optional if not found.
     */
	Optional<ClassRoom> findByName(String name);
}