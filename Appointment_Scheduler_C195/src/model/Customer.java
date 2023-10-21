package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 * The Customer class represents a customer model with its various fields
 * Used to retrieve customer information using its getters and setters
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class Customer {
    //fields
    private int customerId, divisionId, countryId;
    private String name, address, postalCode, division, phone, country, createdBy, lastUpdatedBy;
    private LocalDateTime createDate;
    private Timestamp lastUpdate;
    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
    }
    /**
     * Parameterized constructor for the Customer class
     *
     * @param customerId      The unique identifier for the customer.
     * @param divisionId      The identifier for the customer's division.
     * @param countryId       The identifier for the customer's country.
     * @param name            The name of the customer.
     * @param address         The customer's address.
     * @param postalCode      The postal code of the customer's location.
     * @param division        The division to which the customer belongs.
     * @param phone           The customer's contact phone number.
     * @param country         The customer's country of residence.
     * @param createdBy       The user who created the customer entry.
     * @param lastUpdatedBy   The user who last updated the customer entry.
     * @param createDate      The date and time of customer creation.
     * @param lastUpdate      The timestamp of the last update to the customer's information.
     */
    public Customer(int customerId, int divisionId, int countryId, String name, String address, String postalCode,
                    String division, String phone, String country, String createdBy, String lastUpdatedBy,
                    LocalDateTime createDate, Timestamp lastUpdate) {
        this.customerId = customerId;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.phone = phone;
        this.country = country;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }
    /**
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * @param customerId The customer ID to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * @return The division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * @param divisionId The division ID to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**
     * @return The country ID.
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * @param countryId The country ID to set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /**
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return The address of the customer.
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @return The postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode The postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return The division of the customer.
     */
    public String getDivision() {
        return division;
    }
    /**
     * @param division The division name to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone The phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return The name of the country of the customer.
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country The country name to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return The name of creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy The name of creator to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * @return The the name of the user who updated the info last.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * @param lastUpdatedBy The name lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * @return The date it was created.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate The date of creation to set.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * @return The localDateTime of last update.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /**
     * @param lastUpdate The lastUpdate to set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * Overrides the `toString` method to provide a string representation of the customer, which is the customer's name.
     *
     * @return The name of the customer as a string.
     */
    @Override
    public String toString() {
        return name;
    }
}
