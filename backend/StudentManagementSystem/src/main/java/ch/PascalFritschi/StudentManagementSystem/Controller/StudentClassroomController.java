package ch.PascalFritschi.StudentManagementSystem.Controller;

import ch.PascalFritschi.StudentManagementSystem.model.ClassRoom;
import ch.PascalFritschi.StudentManagementSystem.model.Student;
import ch.PascalFritschi.StudentManagementSystem.model.StudentClassroom;
import ch.PascalFritschi.StudentManagementSystem.Repository.ClassRoomRepository;
import ch.PascalFritschi.StudentManagementSystem.Repository.StudentClassroomRepository;
import ch.PascalFritschi.StudentManagementSystem.Repository.StudentRepository;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * The `StudentClassroomController` class is a REST controller responsible for handling HTTP requests related to student-classroom relationships.
 * It provides endpoints for retrieving all student-classroom relationships in a class, adding a student to a class, and removing a student from a class.
 */

@RestController
@RequestMapping("/api/studentclassroom")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentClassroomController {

	private final StudentClassroomRepository studentClassroomRepository;
	private final StudentRepository studentRepository;
	private final ClassRoomRepository classRoomRepository;

	@Autowired
	public StudentClassroomController(StudentClassroomRepository studentClassroomRepository,
			StudentRepository studentRepository, ClassRoomRepository classRoomRepository) {
		this.studentClassroomRepository = studentClassroomRepository;
		this.studentRepository = studentRepository;
		this.classRoomRepository = classRoomRepository;
	}

	
	/**
     * Retrieves all student-classroom relationships in a class by class ID.
     *
     * @param classroom_id The ID of the class for which to retrieve student-classroom relationships.
     * @return A ResponseEntity containing a list of student-classroom relationships if successful, or an OK status with an empty list if no relationships are found.
     */
	
	@GetMapping("/getAll/{classroom_id}")
	public ResponseEntity<List<StudentClassroom>> getAllStudentClassrooms(@PathVariable Long classroom_id) {
	    List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassRoomId(classroom_id);

	    if (studentClassrooms.isEmpty()) {
	        return ResponseEntity.ok(Collections.emptyList());
	    } else {
	        return ResponseEntity.ok(studentClassrooms);
	    }
	    
	    
	}

	
	
	/**
	 * Adds a student to a classroom.
	 *
	 * @param student_id       The ID of the student to be added.
	 * @param classroom_id     The ID of the classroom to which the student will be added.
	 * @param studentClassroom The information about the student's association with the classroom.
	 * @return ResponseEntity with the added StudentClassroom if successful; otherwise, a ResponseEntity with an error message.
	 * @throws AttributeNotFoundException If either the student or the classroom is not found based on the provided IDs.
	 */
	
	@PostMapping("/add/{student_id}/{classroom_id}")
	public ResponseEntity<?> addStudentToClassRoom(
	        @PathVariable Long student_id,
	        @PathVariable Long classroom_id,
	        @RequestBody StudentClassroom studentClassroom) throws AttributeNotFoundException {

	    // Check if the association already exists
	    if (studentClassroomRepository.existsByStudentIdAndClassRoomId(student_id, classroom_id)) {
	        return ResponseEntity.badRequest().body("Student already in the classroom");
	    }

	    // Retrieve students and classRooms from the repositories
	    Optional<Student> studentOptional = Optional.ofNullable(studentRepository.findById(student_id));
	    if (studentOptional.isPresent()) {
	        Student student = studentOptional.get();

	        Optional<ClassRoom> classRoomOptional = classRoomRepository.findById(classroom_id);
	        if (classRoomOptional.isPresent()) {
	            ClassRoom classRoom = classRoomOptional.get();

	            studentClassroom.setStudent(student);
	            studentClassroom.setClassRoom(classRoom);

	            StudentClassroom savedStudentClassroom = studentClassroomRepository.save(studentClassroom);
	            return ResponseEntity.ok(savedStudentClassroom);
	        } else {
	            throw new AttributeNotFoundException("ClassRoom not found with ID: " + classroom_id);
	        }
	    } else {
	        throw new AttributeNotFoundException("Student not found with ID: " + student_id);
	    }
	}

	
	
	/**
     * Removes a student from a class.
     *
     * @param student_id    The ID of the student to be removed.
     * @param classroom_id  The ID of the class from which the student will be removed.
     * @return A ResponseEntity with a no-content status if the student is removed, or a NOT_FOUND status if the student is not found,
     * or an INTERNAL_SERVER_ERROR status if an error occurs.
     */

	@DeleteMapping("/remove/{student_id}/{classroom_id}")
	public ResponseEntity<Void> removeStudentFromClassRoom(
			@PathVariable Long student_id,
			@PathVariable Long classroom_id) {
		// Find the StudentClassroom by studentId and classRoomId
		java.util.Optional<StudentClassroom> optionalStudentClassroom = studentClassroomRepository
				.findByStudentIdAndClassRoomId(student_id, classroom_id);

		if (optionalStudentClassroom.isPresent()) {
			// If the StudentClassroom exists, delete it
			studentClassroomRepository.delete(optionalStudentClassroom.get());
			return ResponseEntity.noContent().build();
		} else {
			// If not found, return a not found response
			return ResponseEntity.notFound().build();
		}
	}
}
