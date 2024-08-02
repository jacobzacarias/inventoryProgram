package zacarias.desktopSchedule.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.helper.JDBC;
import zacarias.desktopSchedule.model.Customers;
import zacarias.desktopSchedule.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Provides query methods for managing users.
 */
public class UsersDao {
    /**
     * Gets users.
     *
     * @return users
     * @throws SQLException the sql exception
     */
    public static ObservableList<Users> getUsers() throws SQLException {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        String query = "SELECT * from users";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                int userId = resultSet.getInt("User_ID");
                String username = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Users user = new Users(userId,username,password);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    /**
     * Validate credentials users.
     *
     * @param username the username
     * @param password the password
     * @return users
     * @throws SQLException the sql exception
     */
    public static Users validateCredentials(String username, String password) throws SQLException {
        String sqlQuery = "SELECT User_ID FROM users WHERE User_Name = ? AND Password = ?";
        Connection connection = JDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            int userId = resultSet.getInt("User_ID");
            Users user = new Users(userId, username, password);
            return user;
        }
        return null;
    }

    /**
     * Retrieves a user by ID. The lambda expression filters users in order to find the ID for that particular user.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The user with that specific ID.
     */
    public static Users getUserById(int userId){
        Users user = null;
        try {
            ObservableList<Users> list = getUsers().stream()
                    .filter(users -> users.getUserId() == userId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            user = list.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user; //Returns user regardless, should be the user ID
    }
}