package ch.PascalFritschi.StudentManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


 /**
 * The `StudentClassroom` class represents the association between a student and a classroom.
 * It is mapped as an entity in the database and contains information about the assignment of students to classrooms.
 */

@Entity
@Table(name = "student_classrooms")
public class StudentClassroom {

	 /**
     * The unique identification number for the assignment of students to classrooms.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The student associated with this assignment.
     */
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /**
     * The classroom associated with this assignment.
     */
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;
    

    public StudentClassroom() {
        
    }

    /**
     * Constructor for the `StudentClassroom` class, accepting classroom and student as parameters.
     *
     * @param classRoom The classroom to which the student is assigned.
     * @param student   The student assigned to the classroom.
     */
    
    public StudentClassroom(ClassRoom classRoom, Student student) {
        this.classRoom = classRoom;
        this.student = student;
    }

    public Long getId() {
        return id;
    }
    

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
}