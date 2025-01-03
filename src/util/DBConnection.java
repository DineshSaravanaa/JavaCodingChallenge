package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String connectionString = PropertyUtil.getPropertyString("db.properties");
                connection = DriverManager.getConnection(connectionString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
