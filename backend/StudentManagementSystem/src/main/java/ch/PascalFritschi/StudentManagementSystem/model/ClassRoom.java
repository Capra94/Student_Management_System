package ch.PascalFritschi.StudentManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The `ClassRoom` class represents a classroom in the system and is mapped to the "ClassRooms" table in the database.
 */

@Entity
@Table(name = "ClassRooms")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message="Please enter a classroom name")
    @Column(name = "name")
    private String name;

    /**
     * The list of student-classroom associations associated with the classroom.
     */
    
    @JsonManagedReference
    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClassroom> studentClassrooms = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentClassroom> getStudentClassrooms() {
        return studentClassrooms;
    }
    
    

    public void setStudentClassrooms(List<StudentClassroom> studentClassrooms) {
        this.studentClassrooms = studentClassrooms;
    }

    /**
     * Adds a student-classroom association to the classroom.
     *
     * @param studentClassroom The student-classroom association to be added.
     */
    
    public void addStudentClassroom(StudentClassroom studentClassroom) {
        studentClassrooms.add(studentClassroom);
        studentClassroom.setClassRoom(this);
    }
    
    /**
     * Removes a student-classroom association from the classroom.
     *
     * @param studentClassroom The student-classroom association to be removed.
     */

    public void removeStudentClassroom(StudentClassroom studentClassroom) {
        studentClassrooms.remove(studentClassroom);
        studentClassroom.setClassRoom(null);
    }
}

