package DAO;

import utilities.JDBC;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
/**
 * This class provides access to the appointment data in the database.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class AppointmentDBAccess {
    /**
     * Retrieves a list of all appointments from the database.
     *
     * @return An ObservableList of Appointment objects representing all appointments.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(id, customerId, userId, contactId, title, description,
                    location, type, createdBy, createDate, start, end);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /**
     * Retrieves a list of all appointments with associated contacts from the database.
     *
     * @return An ObservableList of Appointment objects representing all appointments with contacts.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> getAllAppointmentsWithContacts() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * \n" +
                "FROM appointments\n" +
                "JOIN contacts\n" +
                "\tON appointments.Contact_ID = contacts.Contact_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contact = rs.getString("Contact_Name");
            Appointment appointment = new Appointment(id, customerId, userId, contactId, title, description,
                    location, type, createdBy, createDate, start, end, contact);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /**
     * Inserts a new appointment into the database.
     *
     * @param title      The title of the appointment.
     * @param description The description of the appointment.
     * @param location   The location of the appointment.
     * @param type       The type of the appointment.
     * @param start      The start time of the appointment.
     * @param end        The end time of the appointment.
     * @param customerId The ID of the customer associated with the appointment.
     * @param userId     The ID of the user creating the appointment.
     * @param contactId  The ID of the contact associated with the appointment.
     * @return The number of rows affected by the insertion (1 if successful).
     * @throws SQLException if a database error occurs.
     */
    public static int insertAppointment(String title, String description, String location, String type,
                                        String start, String end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, LoginController.UserName());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(10, LoginController.UserName());
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected);
        return rowsAffected;
    }
    /**
     * Updates an existing appointment in the database.
     *
     * @param id         The ID of the appointment to be updated.
     * @param title      The updated title of the appointment.
     * @param description The updated description of the appointment.
     * @param location   The updated location of the appointment.
     * @param type       The updated type of the appointment.
     * @param start      The updated start time of the appointment.
     * @param end        The updated end time of the appointment.
     * @param customerId The updated ID of the customer associated with the appointment.
     * @param userId     The updated ID of the user updating the appointment.
     * @param contactId  The updated ID of the contact associated with the appointment.
     * @return The number of rows affected by the update (1 if successful).
     * @throws SQLException if a database error occurs.
     */
    public static int updateAppointment(int id, String title, String description, String location, String type,
                                        String start, String end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, LoginController.UserName());
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * Deletes an appointment from the database.
     *
     * @param id The ID of the appointment to be deleted.
     * @return The number of rows affected by the deletion (1 if successful).
     * @throws SQLException if a database error occurs.
     */
    public static int deleteAppointment(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**
     * Retrieves a list of appointment types and their counts for a specific month.
     *
     * @param month The month for which to retrieve appointment type statistics.
     * @param Month The name of the month (e.g., "January") for display purposes.
     * @return An ObservableList of Appointment objects representing type statistics.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> filterByMonthType(int month, String Month) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT Type, COUNT(TYPE) FROM appointments WHERE MONTH(Start) =? GROUP BY Type";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, month + 1);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String type = rs.getString("Type");
            int count = rs.getInt("COUNT(Type)");
            Appointment appointment = new Appointment(Month, type, count);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /**
     * Retrieves a list of appointments associated with a specific contact.
     *
     * @param contactId The ID of the contact for which to retrieve appointments.
     * @return An ObservableList of Appointment objects representing contact appointments.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> contactAppointments(int contactId) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT *\n" +
                "FROM appointments\n" +
                "JOIN contacts c\n" +
                "\tON appointments.Contact_ID = c.Contact_ID\n" +
                "WHERE c.Contact_ID = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            Appointment appointment = new Appointment(id, title, type, description,start, end, customerId);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /**
     * Retrieves a list of appointments for the current month.
     *
     * @return An ObservableList of Appointment objects representing appointments for the current month.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments\n" +
                "JOIN contacts\n" +
                "\tON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE MONTH(Start) = MONTH(NOW()) AND YEAR(Start) = YEAR(NOW());";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment appointment = new Appointment(id, customerId, userId, contactId, title, description,
                    location, type, createdBy, createDate, start, end, contactName);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /**
     * Retrieves a list of appointments for the current week.
     *
     * @return An ObservableList of Appointment objects representing appointments for the current week.
     * @throws SQLException if a database error occurs.
     */
    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments\n" +
                "JOIN contacts\n" +
                "\tON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE YEARWEEK(Start, 1) = YEARWEEK(NOW(), 1);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment appointment = new Appointment(id, customerId, userId, contactId, title, description,
                    location, type, createdBy, createDate, start, end, contactName);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
}
