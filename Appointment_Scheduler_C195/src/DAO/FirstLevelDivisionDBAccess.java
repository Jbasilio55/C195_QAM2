package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class provides methods for accessing and retrieving data related to first-level divisions.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class FirstLevelDivisionDBAccess {
    /**
     * Retrieves a list of all first-level divisions.
     *
     * @return An ObservableList of FirstLevelDivision objects representing all first-level divisions.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivision() throws SQLException{
        ObservableList<FirstLevelDivision> firstLDList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");
            String division = rs.getString("Division");

            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionId, countryId, division);
            firstLDList.add(firstLevelDivision);
        }
        return firstLDList;
    }
    /**
     * Retrieves a list of first-level divisions associated with a specific country.
     *
     * @param countryID The ID of the country to filter divisions by.
     * @return An ObservableList of FirstLevelDivision objects associated with the specified country.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<FirstLevelDivision> getFirstLevelDivision_withCountryId(int countryID) throws SQLException{
        ObservableList<FirstLevelDivision> firstLDList = FXCollections.observableArrayList();
        String sql = String.format("SELECT * FROM first_level_divisions WHERE Country_ID = %d", countryID);
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");
            String division = rs.getString("Division");

            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionId, countryId, division);
            firstLDList.add(firstLevelDivision);
        }
        return firstLDList;
    }
    /**
     * Retrieves a list of first-level divisions with a given name.
     *
     * @param divisionName The name of the division to search for.
     * @return An ObservableList of FirstLevelDivision objects with the specified name.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<FirstLevelDivision> getFirstLevelDivision_withName(String divisionName) throws SQLException{

        ObservableList<FirstLevelDivision> firstLDList = FXCollections.observableArrayList();
        String sql = String.format("SELECT * FROM first_level_divisions WHERE Division = %s", divisionName);
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");
            String division = rs.getString("Division");

            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionId, countryId, division);
            firstLDList.add(firstLevelDivision);
        }
        return firstLDList;
    }
    /**
     * Retrieves the total number of customers per first-level division.
     *
     * @return An ObservableList of FirstLevelDivision objects, each containing the division name and the total number of
     * customers in that division.
     *
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<FirstLevelDivision> getTotalCustomersPerDivision() throws SQLException{
        ObservableList<FirstLevelDivision> firstLDList = FXCollections.observableArrayList();
        String sql = "SELECT Division, COUNT(Customer_ID)\n" +
                "FROM first_level_divisions\n" +
                "JOIN customers\n" +
                "\tON customers.Customer_ID = first_level_divisions.Division_ID\n" +
                "GROUP BY Division;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String division = rs.getString("Division");
            int totalCustomer = rs.getInt("COUNT(Customer_ID)");

            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(division, totalCustomer);
            firstLDList.add(firstLevelDivision);
        }
        return firstLDList;
    }
}
