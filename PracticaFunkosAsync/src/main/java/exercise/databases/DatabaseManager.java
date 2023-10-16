package exercise.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseManager {

    private static final String PROPERTIES_FILE = "database.properties";

    public static void main(String[] args) {
        try {
            Properties properties = loadDatabaseProperties();
            String jdbcUrl = properties.getProperty("jdbcUrl");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Realiza las operaciones de inserción en la base de datos
            insertData(connection, "Id", "nombre", "modelo", Double.parseDouble("precio"), "fechaLanzamiento");

            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void insertData(Connection connection, String id,  String nombre, String modelo, double precio, String fechaLanzamiento) throws SQLException {
        String insertSQL = "INSERT INTO FUNKOS (id, nombre, modelo, precio, fechaLanzamiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, modelo);
            preparedStatement.setString(4, String.valueOf(precio));
            preparedStatement.setString(5, fechaLanzamiento);
            preparedStatement.executeUpdate();
        }
    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
        }
        return properties;
    }
}
