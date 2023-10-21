package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * A class that provides database access methods for managing customer data.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class CustomerDBAccess {
    /**
     * Retrieves a list of all customers from the database.
     *
     * @return An ObservableList of Customer objects representing all customers in the database.
     * @throws SQLException if a database access error occurs.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        String sql = "SELECT \n" +
                "\tCustomer_ID,\n" +
                "    first_level_divisions.Division_ID,\n" +
                "\tcountries.Country_ID,\n" +
                "    Customer_Name,\n" +
                "    Address,\n" +
                "    Postal_Code,\n" +
                "    Division,\n" +
                "    Phone,\n" +
                "    Country,\n" +
                "    customers.Create_Date,\n" +
                "    customers.Created_By,\n" +
                "    customers.Last_Update,\n" +
                "    customers.Last_Updated_By\n" +
                "FROM customers\n" +
                "JOIN first_level_divisions\n" +
                "\tON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "JOIN countries\n" +
                "\tON first_level_divisions.Country_ID = countries.Country_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Customer_ID");
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            String city = rs.getString("Division");
            String country = rs.getString("Country");

            Customer customer = new Customer(id, divisionId, countryId, name, address, postalCode, city, phone,
                    country, createdBy, lastUpdatedBy, createDate, lastUpdate);
            customersList.add(customer);
        }
        return customersList;
    }
    /**
     * Inserts a new customer into the database.
     *
     * @param name           The name of the customer.
     * @param address        The address of the customer.
     * @param postalCode     The postal code of the customer.
     * @param phone          The phone number of the customer.
     * @param createdBy      The user who created the customer.
     * @param lastUpdatedBy  The user who last updated the customer.
     * @param divisionId     The division (region) ID associated with the customer.
     * @return The number of rows affected by the insert operation (usually 1).
     * @throws SQLException if a database access error occurs.
     */
    public static int insertCustomer(String name, String address, String postalCode, String phone, String createdBy,
                                      String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * Updates an existing customer's information in the database.
     *
     * @param id             The ID of the customer to update.
     * @param name           The updated name of the customer.
     * @param address        The updated address of the customer.
     * @param postalCode     The updated postal code of the customer.
     * @param phone          The updated phone number of the customer.
     * @param lastUpdatedBy  The user who last updated the customer.
     * @param divisionId     The updated division (region) ID associated with the customer.
     * @return The number of rows affected by the update operation (usually 1).
     * @throws SQLException if a database access error occurs.
     */
    public static int updateCustomer(int id, String name, String address, String postalCode, String phone,
                                     String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * Deletes a customer from the database by their ID.
     *
     * @param id The ID of the customer to delete.
     * @return The number of rows affected by the delete operation (usually 1).
     * @throws SQLException if a database access error occurs.
     */
    public static int deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
