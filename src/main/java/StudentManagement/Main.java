package StudentManagement;

import StudentManagement.GUI.StudentManagementGUI;
import StudentManagement.init.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class, args);
        DatabaseInitializer.createTable();

        javax.swing.SwingUtilities.invokeLater(() -> {
            StudentManagementGUI studentManagementGUI = context.getBean(StudentManagementGUI.class);
            studentManagementGUI.setVisible(true);
        });

    }
}
