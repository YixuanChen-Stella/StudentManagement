package StudentManagement.service;

import StudentManagement.entity.Student;

import java.util.List;

public interface StudentService {
    // Add a new student
    Student addStudent(Student student);

    // Update student information
    Student updateStudent(Student student);

    // Delete a student by ID
    boolean deleteStudent(int studentId);

    // Retrieve all students
    List<Student> getAllStudents();

    // Retrieve a student by their ID
    Student getStudentById(int studentId) throws Throwable; // Can throw Throwable to handle exceptions during database operations
}
