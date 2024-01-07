package ch.PascalFritschi.StudentManagementSystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Student` class represents a student in the system and is mapped to the "Students" table in the database.
 */

@Entity
@Table(name = "Students")
public class Student {

	/**
     * The unique identifier for the student.
     */
	
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    
    @NotEmpty(message="Name cannot be empty")
    @Column(name = "name")
    private String name;
    
    @Email(message="Not a valid Email (e.x. abc@online.com")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Pattern(regexp = "\\d{10}", message = "Invalid phone number (use only 10 digits)")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "grade")
    private String grade;

    /**
     * The list of classrooms associated with the student.
     */
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClassroom> studentClassrooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }
    
    /**
     * Returns a string representation of the student, including relevant information.
     *
     * @return A string representation of the student.
     */
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phoneNumber + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    

	
}
