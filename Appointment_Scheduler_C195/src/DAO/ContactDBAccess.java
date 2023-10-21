package DAO;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * A class provides methods to access and retrieve information about contacts from a database.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class ContactDBAccess {
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();
        // SQL query to select all contacts
        String sql = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(contactId, contactName, email);
            contactsList.add(contact);
        }
        return contactsList;
    }
}
