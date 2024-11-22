package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    public static String getPropertyString(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);

            String hostname = properties.getProperty("hostname");
            String port = properties.getProperty("port");
            String dbName = properties.getProperty("dbname");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            return "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + 
                   "?user=" + username + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
