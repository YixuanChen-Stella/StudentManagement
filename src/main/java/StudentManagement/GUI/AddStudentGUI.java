package StudentManagement.GUI;

import StudentManagement.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class AddStudentGUI extends JFrame {

    @Autowired
    private RestTemplate restTemplate;  // Inject RestTemplate to send HTTP requests

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField contactField;
    private JTextField emailField;
    private JTextField addressField;

    private JLabel errorLabel;

    // Constructor
    public AddStudentGUI() {
        setTitle("Add Student");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));  // Changed to 9 to accommodate errorLabel

        // Create form fields
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

        // Add "Add Student" button
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        panel.add(addButton);

        // Add "Exit" button
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
        if (firstName.isEmpty()) {
            errorLabel.setText("First Name is required.");
        } else {
            errorLabel.setText("");
        }
    }

    private void validateLastName() {
        String lastName = lastNameField.getText();
        if (lastName.isEmpty()) {
            errorLabel.setText("Last Name is required.");
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
            errorLabel.setText("Contact is required.");
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

    private void addStudent() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dobString = dobField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        // Input validation
        if (firstName.isEmpty() || lastName.isEmpty() || dobString.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid email address.");
            return;
        }

        LocalDate dob = null;
        try {
            dob = LocalDate.parse(dobString);  // Convert string to LocalDate
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        // Create Student instance
        Student student = new Student(firstName, lastName, dob, contact, email, address);

        // Send POST request to backend to add the student
        try {
            String url = "http://localhost:8080/students"; // Backend API address
            restTemplate.postForEntity(url, student, Student.class); // Send POST request
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to add student: " + e.getMessage());
        }
    }

    private void exit() {
        // Close the current window
        this.dispose();
    }
}
