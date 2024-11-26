package StudentManagement.controller;

import StudentManagement.entity.Student;
import StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Component
public class StudentManager {

    @Autowired
    private StudentService studentService;

    // Add a new student
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        System.out.println("Adding student...");
        Student createdStudent = studentService.addStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED); // 201 Created
    }

    // Update student information
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) throws Throwable {
        // Get student information
        Student existingStudent = studentService.getStudentById(id);

        // If student does not exist, return 404 Not Found
        if (existingStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Only update fields that are not null
        if (student.getFirstName() != null) {
            existingStudent.setFirstName(student.getFirstName());
        }
        if (student.getLastName() != null) {
            existingStudent.setLastName(student.getLastName());
        }
        if (student.getDateOfBirth() != null) {
            existingStudent.setDateOfBirth(student.getDateOfBirth());
        }
        if (student.getContactNumber() != null) {
            existingStudent.setContactNumber(student.getContactNumber());
        }
        if (student.getEmail() != null) {
            existingStudent.setEmail(student.getEmail());
        }
        if (student.getAddress() != null) {
            existingStudent.setAddress(student.getAddress());
        }

        // Call service layer to update student information
        Student updatedStudent = studentService.updateStudent(existingStudent);

        // Return the updated student information
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK); // 200 OK and updated student data
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    // Retrieve all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK); // 200 OK
    }

    // Retrieve a specific student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        try {
            Student student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
