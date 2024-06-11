package hokm.server;

import java.sql.*;

import static javax.swing.UIManager.getString;

public class Database {
    private Connection connection;

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

    String getToken(String username) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT token FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? resultSet.getString(1) : null;

        } catch (SQLException e) {
            return null;
        }
    }
    void createUser(String username ,String token){
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, token) VALUES (?, ?);")) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,token);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
