package model;

import java.time.LocalDateTime;

/**
 * The User class represents a user model with its fields.
 * the getters and setter are used to retrieve and set data.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class User {
    //fields
    private int userId;
    private String userName, password, createdBy, lastUpdatedBy;
    private LocalDateTime createDate, lastUpdate;
    /**
     * Default constructor for the User class.
     */
    public User() {
    }
    /**
     * Parameterized constructor for the User class.
     *
     * @param userId         The unique identifier for the user.
     * @param userName       The username of the user.
     * @param password       The password of the user.
     * @param createdBy      The name of the user who created this user record.
     * @param lastUpdatedBy  The name of the user who last updated this user record.
     * @param createDate     The date and time when the user record was created.
     * @param lastUpdate     The date and time when the user record was last updated.
     */
    public User(int userId, String userName, String password, String createdBy, String lastUpdatedBy, LocalDateTime createDate,
                LocalDateTime lastUpdate) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return The user's unique identifier.
     */
    public int getUserId() { return userId; }
    /**
     * @return The username of the user.
     */
    public String getUserName() { return userName; }
    /**
     * @param userName The new username to set for the user.
     */
    public void setUserName(String userName) { this.userName = userName; }
    /**
     * @return The password of the user.
     */
    public String getPassword() { return password; }
    /**
     * @param password The new password to set for the user.
     */
    public void setPassword(String password) { this.password = password; }
    /**
     * @return The name of the user who created this user record.
     */
    public String getCreatedBy() { return createdBy; }
    /**
     * @param createdBy The new name to set as the creator of the user record.
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    /**
     * @return The name of the user who last updated this user record.
     */
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    /**
     * @param lastUpdatedBy The new name to set as the last updater of the user record.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    /**
     * @return The date and time when the user record was created.
     */
    public LocalDateTime getCreateDate() { return createDate; }
    /**
     * @param createDate The new date and time to set as the creation date of the user record.
     */
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    /**
     * @return The date and time when the user record was last updated.
     */
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    /**
     * @param lastUpdate The new date and time to set as the last update date of the user record.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
    /**
     * @return The username of the user as the string representation.
     */
    @Override
    public String toString() {
        return userName;
    }
}
