package StudentManagement.init;

import StudentManagement.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                + "student_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "first_name VARCHAR(50), "
                + "last_name VARCHAR(50), "
                + "dob DATE, "
                + "contact_number VARCHAR(20), "
                + "email VARCHAR(100), "
                + "address VARCHAR(255))";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'students' has been created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while creating the table.");
        }
    }
}
