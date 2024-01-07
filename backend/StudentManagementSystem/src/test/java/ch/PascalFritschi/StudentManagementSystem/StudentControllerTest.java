package ch.PascalFritschi.StudentManagementSystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import ch.PascalFritschi.StudentManagementSystem.Controller.StudentController;
import ch.PascalFritschi.StudentManagementSystem.Repository.StudentRepository;
import ch.PascalFritschi.StudentManagementSystem.model.Student;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

   
    /**
     * Unit test for the injection of StudentController, ensuring that it is not null.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void whenStudentControllerInjected_thenNotNull() throws Exception {
        assertThat(studentController).isNotNull();
    }

    
    /**
     * Unit test for handling a POST request to add a new student, validating the correct response.
     *
     * Mocks the data by creating a mock student and setting up the repository to return it.
     * Performs a POST request to add a new student and expects a successful (201 Created) response.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void whenPostRequestToAddStudent_thenCorrectResponse() throws Exception {
        // Mock data
        Student mockStudent = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

        // Performs POST request to add a new student
        mockMvc.perform(MockMvcRequestBuilders.post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"age\":25}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    
    /**
     * Unit test for handling a GET request to retrieve all students, validating the correct response.
     *
     * Mocks the data by creating an empty list of students and setting up the repository to return it.
     * Performs a GET request to retrieve all students and expects a successful (200 OK) response
     * with content type as JSON.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void whenGetAllStudents_thenCorrectResponse() throws Exception {
        // Mock data
        List<Student> mockStudents = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(mockStudents);

        // Performs GET request to retrieve all students
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    

    /**
     * Unit test for handling a DELETE request to delete a non-existing student, validating the not found response.
     *
     * Mocks the data by assuming a non-existing student ID and setting up the repository to return an empty Optional.
     * Performs a DELETE request to delete a non-existing student by ID and expects a not found (404) response
     * with the message "Student not found".
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void whenDeleteNonExistingStudent_thenNotFoundResponse() throws Exception {
        // Mock data
        int nonExistingStudentId = 999; // Assuming this ID does not exist in the mocked data
        when(studentRepository.findById(eq(nonExistingStudentId))).thenReturn(Optional.empty());

        // Performs DELETE request to delete a non-existing student by ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/delete")
                .param("studentId", String.valueOf(nonExistingStudentId)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Student not found"));
    }

    
    /**
     * Unit test for handling a PUT request to update an existing student, validating the correct response.
     *
     * Mocks the data by creating a mock student and setting up the repository to return it.
     * Performs a PUT request to update an existing student and expects a successful (200 OK) response
     * with content type as JSON.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void whenUpdateStudent_thenCorrectResponse() throws Exception {
        // Mock data
        Student mockStudent = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

        // Performs PUT request to update an existing student
        mockMvc.perform(MockMvcRequestBuilders.put("/api/student/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"age\":30}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}


