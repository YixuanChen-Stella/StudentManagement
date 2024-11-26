package StudentManagement.GUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Primary
@Component
public class StudentManagementGUI extends JFrame {

    private final AddStudentGUI addStudentGUI;
    private final DeleteStudentGUI deleteStudentGUI;
    private final UpdateStudentGUI updateStudentGUI;
    private final ViewStudentGUI viewStudentGUI;

    // Constructor with dependency injection
    @Autowired
    public StudentManagementGUI(AddStudentGUI addStudentGUI, DeleteStudentGUI deleteStudentGUI,
                                UpdateStudentGUI updateStudentGUI, ViewStudentGUI viewStudentGUI) {
        this.addStudentGUI = addStudentGUI;
        this.deleteStudentGUI = deleteStudentGUI;
        this.updateStudentGUI = updateStudentGUI;
        this.viewStudentGUI = viewStudentGUI;

        setTitle("Student Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();  // Initialize GUI components
    }

    // Method to initialize components and layout
    public void init() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));  // Create a panel to hold buttons
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton viewButton = new JButton("View");

        // Action listeners for each button
        addButton.addActionListener(e -> addStudentGUI.setVisible(true));  // Show add student UI
        deleteButton.addActionListener(e -> deleteStudentGUI.setVisible(true));  // Show delete student UI
        updateButton.addActionListener(e -> updateStudentGUI.setVisible(true));  // Show update student UI
        viewButton.addActionListener(e -> viewStudentGUI.setVisible(true));  // Show view student UI

        // Add buttons to button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.NORTH);  // Add button panel to the top of the frame

        JTextArea mainDisplayArea = new JTextArea("Welcome to Student Management System!");  // Display welcome message
        mainDisplayArea.setEditable(false);  // Make the text area non-editable
        add(new JScrollPane(mainDisplayArea), BorderLayout.CENTER);  // Add the text area in the center of the frame
    }
}
