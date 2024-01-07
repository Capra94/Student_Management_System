package ch.PascalFritschi.StudentManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.context.annotation.ComponentScan;

@RestController
@SpringBootApplication(scanBasePackages = "ch.PascalFritschi.StudentManagementSystem")
@ComponentScan(basePackages = "ch.PascalFritschi.StudentManagementSystem")
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to my Student Management System!";
 
    }
}