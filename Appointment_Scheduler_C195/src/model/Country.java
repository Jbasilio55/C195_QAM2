package model;

/**
 * The Country class represents a country model with its fields.
 * It is used to store and retrieve information about countries.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class Country {
    //fields
    private int countryId;
    private String country;
    /**
     * Default constructor for the Country class.
     */
    public Country() {
    }
    /**
     * Constructor for the Country class.
     *
     * @param countryId The unique identifier for the country.
     * @param country   The name of the country.
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
    /**
     * @return The unique identifier of the country.
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }
    /**
     * @return The name of the country as a string.
     */
    @Override
    public String toString() {
        return country;
    }
}
