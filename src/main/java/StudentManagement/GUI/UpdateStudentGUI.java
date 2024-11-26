package StudentManagement.GUI;

import StudentManagement.controller.StudentManager;
import StudentManagement.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class UpdateStudentGUI extends JFrame {

    private StudentManager studentManager;

    private JTextField studentIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField contactField;
    private JTextField emailField;
    private JTextField addressField;

    private JLabel errorLabel;

    // Constructor injecting StudentManager
    public UpdateStudentGUI(StudentManager studentManager) {
        this.studentManager = studentManager;

        setTitle("Update Student");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2)); // Added extra row for error message

        // Creating form fields
        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        panel.add(studentIdField);

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField();
        panel.add(dobField);

        panel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        panel.add(contactField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        // Add Update Student button
        JButton updateButton = new JButton("Update Student");
        updateButton.addActionListener(e -> {
            try {
                updateStudent();
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(updateButton);

        // Add Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exit());
        panel.add(exitButton);

        // Add error label for real-time validation feedback
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        add(panel);

        // Real-time validation on fields
        addRealTimeValidation();
    }

    private void addRealTimeValidation() {
        firstNameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateFirstName();
            }
            public void removeUpdate(DocumentEvent e) {
                validateFirstName();
            }
            public void changedUpdate(DocumentEvent e) {
                validateFirstName();
            }
        });

        lastNameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateLastName();
            }
            public void removeUpdate(DocumentEvent e) {
                validateLastName();
            }
            public void changedUpdate(DocumentEvent e) {
                validateLastName();
            }
        });

        dobField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateDOB();
            }
            public void removeUpdate(DocumentEvent e) {
                validateDOB();
            }
            public void changedUpdate(DocumentEvent e) {
                validateDOB();
            }
        });

        contactField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateContact();
            }
            public void removeUpdate(DocumentEvent e) {
                validateContact();
            }
            public void changedUpdate(DocumentEvent e) {
                validateContact();
            }
        });

        emailField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }
            public void changedUpdate(DocumentEvent e) {
                validateEmail();
            }
        });

        addressField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateAddress();
            }
            public void removeUpdate(DocumentEvent e) {
                validateAddress();
            }
            public void changedUpdate(DocumentEvent e) {
                validateAddress();
            }
        });
    }

    private void validateFirstName() {
        String firstName = firstNameField.getText();
        if (firstName.isEmpty() || firstName.length() < 2) {
            errorLabel.setText("First Name must be at least 2 characters.");
        } else {
            errorLabel.setText("");
        }
    }

    private void validateLastName() {
        String lastName = lastNameField.getText();
        if (lastName.isEmpty() || lastName.length() < 2) {
            errorLabel.setText("Last Name must be at least 2 characters.");
        } else {
            errorLabel.setText("");
        }
    }

    private void validateDOB() {
        String dobString = dobField.getText();
        if (!dobString.isEmpty()) {
            try {
                LocalDate.parse(dobString);  // Validate date format
                errorLabel.setText("");
            } catch (DateTimeParseException ex) {
                errorLabel.setText("Please use YYYY-MM-DD.");
            }
        }
    }

    private void validateContact() {
        String contact = contactField.getText();
        if (contact.isEmpty()) {
            errorLabel.setText("Contact number is required.");
        } else {
            errorLabel.setText("");
        }
    }

    private void validateEmail() {
        String email = emailField.getText();
        if (email.isEmpty() || !email.contains("@")) {
            errorLabel.setText("Please enter a valid email.");
        } else {
            errorLabel.setText("");
        }
    }

    private void validateAddress() {
        String address = addressField.getText();
        if (address.isEmpty()) {
            errorLabel.setText("Address is required.");
        } else {
            errorLabel.setText("");
        }
    }

    private void updateStudent() throws Throwable {
        String studentIdString = studentIdField.getText();
        if (studentIdString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID is required.");
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(studentIdString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Student ID must be a valid number.");
            return;
        }

        // Use studentManager to fetch student, returns a ResponseEntity
        ResponseEntity<Student> response = studentManager.getStudentById(studentId);

        if (response.getStatusCode() != HttpStatus.OK) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        Student student = response.getBody();
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        // Update student information
        student.setFirstName(firstNameField.getText());
        student.setLastName(lastNameField.getText());
        String dobString = dobField.getText();
        if (!dobString.isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(dobString);  // Convert string to LocalDate
                student.setDateOfBirth(dob);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
                return;
            }
        }

        student.setContactNumber(contactField.getText());
        student.setEmail(emailField.getText());
        student.setAddress(addressField.getText());

        // Call StudentManager to update the student
        ResponseEntity<Student> updateResponse = studentManager.updateStudent(student.getStudentId(), student);

        if (updateResponse.getStatusCode() == HttpStatus.OK) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update student.");
        }
    }

    // Event handler for the Exit button
    private void exit() {
        // Close the current window and return to the previous screen
        this.dispose();
    }
}
