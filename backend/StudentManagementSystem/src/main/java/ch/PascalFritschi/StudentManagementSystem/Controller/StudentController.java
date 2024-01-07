package ch.PascalFritschi.StudentManagementSystem.Controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.PascalFritschi.StudentManagementSystem.Repository.StudentRepository;
import ch.PascalFritschi.StudentManagementSystem.model.Student;


/**
 * The `StudentController` class is a REST controller responsible for handling HTTP requests related to student entities.
 * It provides endpoints for retrieving, adding, updating, and deleting students.
 */

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    
    /**
     * Retrieves all students from the database.
     *
     * @return A ResponseEntity containing a list of students if successful, or an INTERNAL_SERVER_ERROR status if an error occurs.
     */
    
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok().body(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Adds a new student to the database.
     *
     * @param student The student object to be added.
     * @return A ResponseEntity containing the added student if successful, or an INTERNAL_SERVER_ERROR status if an error occurs.
     */

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Finds a student by ID.
     *
     * @param student_id The ID of the student to be retrieved.
     * @return A ResponseEntity containing the found student if successful, or a NOT_FOUND status if the student is not found,
     * or an INTERNAL_SERVER_ERROR status if an error occurs.
     */
    
    @GetMapping("/search")
    public ResponseEntity<Student> findStudent(@RequestParam("studentId") int student_id) {
        try {
        	Optional<Student> optionalStudent = studentRepository.findById(student_id);

            return optionalStudent.map(student -> ResponseEntity.ok().body(student))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Updates an existing student in the database.
     *
     * @param student The student object with updated information.
     * @return A ResponseEntity containing the updated student if successful, or an INTERNAL_SERVER_ERROR status if an error occurs.
     */

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        try {
            Student updatedStudent = studentRepository.save(student);
            return ResponseEntity.ok().body(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Deletes a student by ID.
     *
     * @param student_id The ID of the student to be deleted.
     * @return A ResponseEntity with a success message if the student is deleted, a NOT_FOUND status if the student is not found,
     * or an INTERNAL_SERVER_ERROR status if an error occurs.
     */

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam("studentId") int student_id) {
        try {
        	Optional<Student> optionalStudent = studentRepository.findById(student_id);
            if (optionalStudent.isPresent()) {
                studentRepository.deleteById(student_id);
                return ResponseEntity.ok().body("Student deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
