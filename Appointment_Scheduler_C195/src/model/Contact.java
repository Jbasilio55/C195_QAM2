package model;

/**
 * The Contact class represents a basic contact model,
 * Used to retrieve name and email address.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class Contact {
    //fields
    private int contactId;
    private String name, email;
    /**
     * Default constructor for creating an empty Contact object.
     */
    public Contact() {
    }
    /**
     * Constructs a Contact object with the specified attributes.
     *
     * @param contactId The unique identifier for the contact.
     * @param name The name of the contact.
     * @param email The email address of the contact.
     */
    public Contact(int contactId, String name, String email) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
    }
    /**
     * Gets the unique identifier for the contact.
     *
     * @return The contact's unique identifier.
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the contact.
     *
     * @param name The new name to set for the contact.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the email address of the contact.
     *
     * @return The email address of the contact.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email address of the contact.
     *
     * @param email The new email address to set for the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns a string representation of the contact, which is the contact's name.
     *
     * @return The name of the contact as a string.
     */
    @Override
    public String toString() {
        return name;
    }
}
