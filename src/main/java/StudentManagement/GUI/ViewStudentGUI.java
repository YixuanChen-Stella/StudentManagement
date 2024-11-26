package StudentManagement.GUI;

import StudentManagement.controller.StudentManager;
import StudentManagement.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class ViewStudentGUI extends JFrame {

    private StudentManager studentManager;

    private JTextField studentIdField;
    private JButton viewButton;
    private JButton viewAllButton; // Button for viewing all student information
    private JButton exitButton; // Button for exiting and returning to the previous screen
    private JTable table;

    // Constructor that accepts StudentManager
    public ViewStudentGUI(StudentManager studentManager) {
        this.studentManager = studentManager;

        setTitle("View Students");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "First Name", "Last Name", "Date of Birth", "Contact", "Email", "Address"};
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Enter Student ID:"));
        studentIdField = new JTextField(10);
        controlPanel.add(studentIdField);

        viewButton = new JButton("View Student");
        controlPanel.add(viewButton);

        // Add "View All Students" button
        viewAllButton = new JButton("View All Students");
        controlPanel.add(viewAllButton);

        // Add "Exit" button (to return to previous screen)
        exitButton = new JButton("Exit");
        controlPanel.add(exitButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);

        // View Student button click event
        viewButton.addActionListener(e -> {
            String studentIdText = studentIdField.getText().trim();
            if (studentIdText.isEmpty()) {
                List<Student> students = getAllStudents(); // Get all students
                displayStudentData(students);
            } else {
                try {
                    int studentId = Integer.parseInt(studentIdText);
                    Student student = studentManager.getStudentById(studentId).getBody();
                    if (student != null) {
                        displayStudentData(List.of(student));
                    } else {
                        JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // View All Students button click event
        viewAllButton.addActionListener(e -> {
            List<Student> students = getAllStudents(); // Get all students
            displayStudentData(students);
        });

        // Exit button click event to return to the previous screen
        exitButton.addActionListener(e -> exit());
    }

    // Get all students
    private List<Student> getAllStudents() {
        ResponseEntity<List<Student>> response = studentManager.getAllStudents();
        return response.getBody(); // Get the Body part of the ResponseEntity
    }

    // Method to display student data
    private void displayStudentData(List<Student> students) {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data available.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[][] data = new String[students.size()][7];
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                data[i][0] = String.valueOf(student.getStudentId());
                data[i][1] = student.getFirstName();
                data[i][2] = student.getLastName();
                // Convert LocalDate to string format
                data[i][3] = student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "N/A";
                data[i][4] = student.getContactNumber();
                data[i][5] = student.getEmail();
                data[i][6] = student.getAddress();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "First Name", "Last Name", "Date of Birth", "Contact", "Email", "Address"}));
        }
    }

    // Exit method to close the current window and return to the previous screen
    private void exit() {
        this.setVisible(false);  // Hide current window instead of disposing it
    }
}
