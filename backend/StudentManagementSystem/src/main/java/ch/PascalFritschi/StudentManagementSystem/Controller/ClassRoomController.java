package ch.PascalFritschi.StudentManagementSystem.Controller;

import ch.PascalFritschi.StudentManagementSystem.model.ClassRoom;
import ch.PascalFritschi.StudentManagementSystem.model.Student;
import ch.PascalFritschi.StudentManagementSystem.model.StudentClassroom;
import ch.PascalFritschi.StudentManagementSystem.Repository.ClassRoomRepository;
import ch.PascalFritschi.StudentManagementSystem.Repository.StudentClassroomRepository;
import ch.PascalFritschi.StudentManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


/**
 * The `ClassRoomController` class is a REST controller responsible for handling HTTP requests related to classes.
 * It provides endpoints for retrieving all classes, creating a new class, deleting a class, and retrieving students in a class.
 */

@RestController
@RequestMapping("/api/classroom")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassRoomController {

    private final ClassRoomRepository classRoomRepository;
    

    @Autowired
    public ClassRoomController(ClassRoomRepository classRoomRepository,
                               StudentRepository studentRepository,
                               StudentClassroomRepository studentClassroomRepository) {
    	
        this.classRoomRepository = classRoomRepository;
       
    }

    
    /**
     * Retrieves all classes.
     *
     * @return A ResponseEntity containing a list of all classes if successful, or an OK status with an empty list if no classes are found.
     */
    
    @GetMapping("/getClassrooms")
    public ResponseEntity<List<ClassRoom>> getAllClassRooms() {
        List<ClassRoom> classRooms = classRoomRepository.findAll();
        return ResponseEntity.ok(classRooms);
   
    }
    
    
    /**
     * Creates a new class.
     *
     * @param classRoom The class object to be created.
     * @return A ResponseEntity containing the created class if successful, or a BAD_REQUEST status with an error message if the class name is null or empty.
     * 		Also checks if a classroom with the same name already exists in the list and returns an error if that is the case. 
     */

    @PostMapping("/createClassroom")
    public ResponseEntity<?> createClassRoom(@RequestBody ClassRoom classRoom) {
        // Makes sure 'name' is set before saving to the database
        if (classRoom.getName() == null || classRoom.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Classroom name cannot be null or empty");
        }

        // Check if a classroom with the same name already exists
        Optional<ClassRoom> existingClassroom = classRoomRepository.findByName(classRoom.getName());
        if (existingClassroom.isPresent()) {
            // Classroom with the same name already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Classroom with the same name already exists");
        }

        // Save the new classroom
        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);
        return ResponseEntity.ok(savedClassRoom);
    }

    
    
    /**
     * Deletes a class by its ID.
     *
     * @param classroom_id The ID of the class to be deleted.
     * @return A ResponseEntity with a no-content status if the class is deleted, or a NOT_FOUND status if the class is not found.
     */

    @DeleteMapping("/{classroom_id}")
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Long classroom_id) {
        classRoomRepository.deleteById(classroom_id);
        return ResponseEntity.noContent().build();
        
    }

    
    /**
     * Retrieves students in a class by class ID.
     *
     * @param classroom_id The ID of the class for which to retrieve students.
     * @return A ResponseEntity containing a list of students in the class if successful, or a NOT_FOUND status if the class is not found.
     */
    
    @GetMapping("/{classroom_id}/students")
    public ResponseEntity<List<Student>> getStudentsByClassRoomId(@PathVariable Long classroom_id) {
        Optional<ClassRoom> optionalClassRoom = classRoomRepository.findById(classroom_id);
        if (optionalClassRoom.isPresent()) {
            ClassRoom classRoom = optionalClassRoom.get();
            List<StudentClassroom> studentClassrooms = classRoom.getStudentClassrooms();
            List<Student> students = new ArrayList<>();
            for (StudentClassroom studentClassroom : studentClassrooms) {
                students.add(studentClassroom.getStudent());
            }
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  
}

