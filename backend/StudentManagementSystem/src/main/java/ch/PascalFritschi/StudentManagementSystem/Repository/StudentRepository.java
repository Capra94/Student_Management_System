package ch.PascalFritschi.StudentManagementSystem.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.PascalFritschi.StudentManagementSystem.model.Student;


/**
 * The `StudentRepository` interface provides methods to interact with the database for managing students.
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{


	/**
     * Retrieves all students from the database.
     *
     * @return A list of all students.
     */
	
    List<Student> findAll();
    
    /**
     * Retrieves a student by their ID.
     *
     * @param student_id The ID of the student.
     * @return The student with the specified ID.
     */
    
    Student findById(Long student_id);
    
    
    /**
     * Retrieves a student by their name.
     *
     * @param studentName The name of the student.
     * @return An Optional containing the student if found, or an empty Optional if not found.
     */
    
    Optional<Student> findByName(String studentName);
	
}
