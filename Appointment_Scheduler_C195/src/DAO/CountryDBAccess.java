package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * A class provides methods to access and retrieve information about countries from a database.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class CountryDBAccess {
    /**
     * Retrieves a list of all countries from the database.
     *
     * @return An ObservableList containing all countries.
     * @throws SQLException If a database error occurs during the retrieval.
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();
        // SQL query to select all countries
        String sql = "SELECT * FROM client_schedule.countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Country country = new Country(countryId, countryName);
            countriesList.add(country);
        }
        return countriesList;
    }
    /**
     * Retrieves a specific country by its ID from the database.
     *
     * @param id The ID of the country to retrieve.
     * @return An ObservableList containing the specified country.
     * @throws SQLException If a database error occurs during the retrieval.
     */
    public static ObservableList<Country> getSingleCountry(int id) throws SQLException {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();
        // SQL query to select a specific country by its ID
        String sql = String.format("SELECT * FROM client_schedule.countries WHERE Country_ID = %d", id);
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Country country = new Country(countryId, countryName);
            countriesList.add(country);
        }
        return countriesList;
    }
}
