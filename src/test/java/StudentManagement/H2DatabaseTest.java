package StudentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseTest {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:h2:tcp://localhost/~/test"; // 如果是内存模式，则使用 jdbc:h2:mem:test
        String username = "sa";  // 默认用户名
        String password = "";    // 默认密码为空

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Successfully connected to H2 database!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }


}
