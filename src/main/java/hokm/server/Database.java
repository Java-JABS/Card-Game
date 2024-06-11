package hokm.server;

import java.sql.*;

import static javax.swing.UIManager.getString;

public class Database {
    private Connection connection;

    Database(String filename) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
    }

    void initialize() {
    }

    String getToken(String username) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT token FROM Users WHEN username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? resultSet.getString(1) : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
