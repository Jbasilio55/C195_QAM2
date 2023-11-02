package DAO;

import utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The UserDBAccess class provides methods for accessing user data in a database.
 * It includes methods for retrieving user records, including all users and a single user by ID,
 * as well as a method for checking user credentials.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class UserDBAccess{
    /**
     * Retrieves a list of all users from the database.
     *
     * @return An ObservableList of User objects representing all users.
     * @throws SQLException If a database access error occurs.
     */
    public static ObservableList<User> getAllUsers() throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            User user = new User(userID, userName, userPassword, createdBy, lastUpdatedBy, createDate, lastUpdate);
            userList.add(user);
        }
        return userList;
    }
    /**
     * Retrieves a list of a single user with the specified ID from the database.
     *
     * @param id The ID of the user to retrieve.
     * @return An ObservableList containing a User object representing the user with the given ID.
     * @throws SQLException If a database access error occurs.
     */
    public static ObservableList<User> getSingleUsers(int id) throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = String.format("SELECT * FROM users WHERE User_ID = %d", id);
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            User user = new User(userID, userName, userPassword, createdBy, lastUpdatedBy, createDate, lastUpdate);
            userList.add(user);
        }
        return userList;
    }
    /**
     * Checks if a user with the given username and password exists in the database.
     *
     * @param username The username of the user to check.
     * @param password The password of the user to check.
     * @return The User_ID of the user if the credentials are valid, or -1 if not found.
     */
    public static int checkUser(String username, String password) {
        try{
            String sql = "SELECT * FROM users WHERE user_name= '"+ username +"' AND password= '"+password+"'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password)){
                System.out.println("User_ID: "+rs.getInt("User_ID"));
                return rs.getInt("User_ID");
            };
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return -1;
    }
}
