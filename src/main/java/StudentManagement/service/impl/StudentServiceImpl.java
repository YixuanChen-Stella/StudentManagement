package StudentManagement.service.impl;

import StudentManagement.entity.Student;
import StudentManagement.repository.StudentRepository;
import StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        // Save the student to the repository and return the saved student object
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        // Check if the student exists before updating
        if (!studentRepository.existsById(student.getStudentId())) {
            throw new RuntimeException("Student not found with ID: " + student.getStudentId());
        }
        // Save the updated student and return the updated object
        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(int studentId) {
        // Check if the student exists before deleting
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        // Delete the student by their ID
        studentRepository.deleteById(studentId);
        return true; // Return true to indicate successful deletion
    }

    @Override
    public List<Student> getAllStudents() {
        // Retrieve and return all students from the repository
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(int studentId) throws Throwable {
        // Retrieve a student by their ID, throw an exception if not found
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }
}
