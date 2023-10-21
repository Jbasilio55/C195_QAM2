package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

/**
 * The Appointment class represents an appointment model with various fields.
 *
 * This class provides constructors and getter/setter methods for accessing and manipulating
 * appointment information.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class Appointment {
    //fields
    private int appointmentId, CustomerId, userId, contactId;
    private String title, description, location, type, createdBy, contact;
    private LocalDateTime start, end, lastUpdatedBy;

    //This is just for my query
    private SimpleStringProperty month;
    private SimpleIntegerProperty count;

    /**
     * Default constructor for the `Appointment` class.
     */
    public Appointment() {
    }

    /**
     * Constructor for creating an `Appointment` with specified properties.
     *
     * @param appointmentId   The unique identifier for the appointment.
     * @param customerId      The ID of the associated customer.
     * @param userId          The ID of the user responsible for the appointment.
     * @param contactId       The ID of the contact person for the appointment.
     * @param title           The title of the appointment.
     * @param description     The description of the appointment.
     * @param location        The location of the appointment.
     * @param type            The type of the appointment.
     * @param createdBy       The user who created the appointment.
     * @param lastUpdatedBy   The user who last updated the appointment.
     * @param start           The start date and time of the appointment.
     * @param end             The end date and time of the appointment.
     */
    public Appointment(int appointmentId, int customerId, int userId, int contactId, String title, String description,
                       String location, String type, String createdBy, LocalDateTime lastUpdatedBy, LocalDateTime start,
                       LocalDateTime end) {
        this.appointmentId = appointmentId;
        this.CustomerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor for creating an `Appointment` with specified properties, including a contact name.
     *
     * @param appointmentId   The unique identifier for the appointment.
     * @param customerId      The ID of the associated customer.
     * @param userId          The ID of the user responsible for the appointment.
     * @param contactId       The ID of the contact person for the appointment.
     * @param title           The title of the appointment.
     * @param description     The description of the appointment.
     * @param location        The location of the appointment.
     * @param type            The type of the appointment.
     * @param createdBy       The user who created the appointment.
     * @param lastUpdatedBy   The user who last updated the appointment.
     * @param start           The start date and time of the appointment.
     * @param end             The end date and time of the appointment.
     * @param contact         The name of the contact person for the appointment.
     */
    public Appointment(int appointmentId, int customerId, int userId, int contactId, String title, String description,
                       String location, String type, String createdBy, LocalDateTime lastUpdatedBy, LocalDateTime start,
                       LocalDateTime end, String contact) {
        this.appointmentId = appointmentId;
        this.CustomerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.start = start;
        this.end = end;
        this.contact = contact;
    }

    /**
     * Constructor for creating an `Appointment` based on query data.
     *
     * @param appointmentId   The unique identifier for the appointment.
     * @param title           The title of the appointment.
     * @param type            The type of the appointment.
     * @param description     The description of the appointment.
     * @param start           The start date and time of the appointment.
     * @param end             The end date and time of the appointment.
     * @param customerId      The ID of the associated customer.
     */
    public Appointment(int appointmentId, String title, String type, String description,
                       LocalDateTime start, LocalDateTime end, int customerId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.CustomerId = customerId;
    }

    /**
     * Constructor for creating an `Appointment` based on query data with month, type, and count.
     *
     * @param Month   The month of the appointment.
     * @param Type    The type of the appointment.
     * @param Count   The count of appointments with the specified type in the month.
     */
    public Appointment(String Month, String Type, int Count){
        type = Type;
        month = new SimpleStringProperty(Month);
        count = new SimpleIntegerProperty(Count);
    }

    /**
     * @return The appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }
    /**
     * @return The customer ID.
     */
    public int getCustomerId() {
        return CustomerId;
    }
    /**
     * @param customerId The customer ID to set.
     */
    public void setCustomerId(int customerId) { this.CustomerId = customerId;}
    /**
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) { this.userId = userId; }
    /**
     * @return The contact ID.
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * @param contactId The contact ID to set.
     */
    public void setContactId(int contactId) { this.contactId = contactId; }
    /**
     * @return The title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return The location.
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @return The type.
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return The createdBy (localDateTime).
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * @return The lastUpdatedBy.
     */
    public LocalDateTime getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * @param lastUpdatedBy The lastUpdatedBy to set.
     */
    public void setLastUpdatedBy(LocalDateTime lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * @return The start (localDateTime).
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * @param start The start (localDateTime) to set.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * @return The end (localDateTime).
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * @param end The end (localDateTime) to set.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /**
     * @return The contact.
     */
    public String getContact(){return contact;}
    /**
     * @return The month.
     */
    public String getMonth() {
        return this.month.get();
    }
    /**
     * @return The count.
     */
    public int getCount() {
        return count.get();
    }
}
