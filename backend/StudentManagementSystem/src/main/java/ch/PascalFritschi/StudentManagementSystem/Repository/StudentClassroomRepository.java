package ch.PascalFritschi.StudentManagementSystem.Repository;

import ch.PascalFritschi.StudentManagementSystem.model.StudentClassroom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The `StudentClassroomRepository` interface provides methods to interact with the database for managing student-classroom relationships.
 */

@Repository
public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Long> {
    
	
	/**
     * Retrieves a student-classroom relationship by student ID and class ID.
     *
     * @param student_id   The ID of the student.
     * @param classroom_id The ID of the classroom.
     * @return An Optional containing the student-classroom relationship if found, or an empty Optional if not found.
     */
	
	Optional<StudentClassroom> findByStudentIdAndClassRoomId(Long student_id, Long classroom_id);

	
	/**
     * Retrieves a list of student-classroom relationships by class ID.
     *
     * @param classroom_id The ID of the classroom.
     * @return A list of student-classroom relationships associated with the specified class ID.
     */
	
	List<StudentClassroom> findByClassRoomId(Long classroom_id);
	
	
	/**
	 * 
	 * @param studentId
	 * @param classRoomId
	 * @return returns a boolean value indicating whether a record with the specified studentId and classRoomId exists in the database.
	 */
	
	boolean existsByStudentIdAndClassRoomId(Long studentId, Long classRoomId);

}