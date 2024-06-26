package hokm.server;

import java.sql.*;

public class Database {
    private final Connection connection;

    Database(String filename) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
    }

    void initialize() {

        try {
            Statement statement = connection.createStatement();
            statement.execute("create table users (id INT PRIMARY KEY, username varchar(255) UNIQUE NOT NULL,token varchar(64) NOT NULL)");
        } catch (SQLException ignored) {
        }
    }

    String getUsername(String token) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users WHERE token = ?")) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? resultSet.getString(1) : null;

        } catch (SQLException e) {
            return null;
        }
    }

    boolean createUser(String username, String token) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, token) VALUES (?, ?);")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, token);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            if(e.getErrorCode()!=19)
                e.printStackTrace();
        }
        return false;
    }
}
