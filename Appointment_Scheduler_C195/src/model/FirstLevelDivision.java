package model;
/**
 * The FirstLevelDivision class represents a first-level division within a country.
 * First-level divisions are typically administrative regions within a country.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 *
 */
public class FirstLevelDivision {
    //Fields
    private int divisionId, countryId;
    private String division;
    private int totalCustomers;

    /**
     * Default constructor for the FirstLevelDivision class.
     */
    public FirstLevelDivision() {
    }
    /**
     * Parameterized constructor for the FirstLevelDivision class.
     *
     * @param divisionId The unique identifier for the division.
     * @param countryId  The unique identifier for the country to which the division belongs.
     * @param division   The name of the division.
     */
    public FirstLevelDivision(int divisionId, int countryId, String division) {
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.division = division;
    }
    /**
     * Parameterized constructor for the FirstLevelDivision class.
     *
     * @param division        The name of the division.
     * @param totalCustomers  The total number of customers associated with this division.
     */
    public FirstLevelDivision(String division, int totalCustomers){
        this.division = division;
        this.totalCustomers = totalCustomers;
    }
    /**
     * @return The division's unique identifier.
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }
    /**
     * @return The total number of customers.
     */
    public int getTotalCustomers(){
        return totalCustomers;
    }
    /**
     * @return A string representation of the division.
     */
    @Override
    public String toString() {
        return division;
    }
}
