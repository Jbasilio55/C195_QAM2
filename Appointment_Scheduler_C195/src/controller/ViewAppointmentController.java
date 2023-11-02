package controller;

import DAO.ContactDBAccess;
import DAO.CustomerDBAccess;
import DAO.UserDBAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * The ViewAppointmentController class is responsible for displaying details of a
 * selected appointment and providing options to edit or cancel the appointment.
 *
 * It implements the Initializable interface for JavaFX initialization.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class ViewAppointmentController implements Initializable {
    //fields
    public static Appointment selectedAppointment = null;
    public static String action = null;

    @FXML
    private Label appHeaderLbl;

    @FXML
    private Label contactLbl;

    @FXML
    private Label customerLbl;

    @FXML
    private Label descriptionLbl;

    @FXML
    private Label endDateLbl;

    @FXML
    private Label endTimeLbl;

    @FXML
    private Label idLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label startDateLbl;

    @FXML
    private Label startTimeLbl;

    @FXML
    private Label titleLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private Label userLbl;

    public static void saveAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }

    public static String adjustScreenCustomer(){
        return action;
    }
    /**
     * Handles the action when the cancel button is clicked.
     *
     * @param event The ActionEvent triggered by the cancel button click.
     * @throws IOException If an I/O exception occurs during window change.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        action = null;
        changeWindow(event, "/view/MainScreen.fxml");
    }
    /**
     * Handles the action when the edit button is clicked.
     * Sets the action to "update" and navigates to the EditAppointment screen.
     *
     * @param event The ActionEvent triggered by the edit button click.
     * @throws IOException If an I/O exception occurs during window change.
     */
    @FXML
    void onActionEdit(ActionEvent event) throws IOException {
        action = "update";
        EditAppointmentController.modifyAppointment(selectedAppointment);
        changeWindow(event, "/view/EditAppointment.fxml");
    }
    /**
     * Changes the window to the specified path.
     *
     * @param event The ActionEvent that triggers the window change.
     * @param path  The path of the new window to display.
     * @throws IOException If an I/O exception occurs during window change.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the view with details of the selected appointment.
     * Lambda expression #1 helps shorten my code, which helps filter the items and perform
     * what ever action need to be completed for 3 different list (customers, contacts, users)
     *
     * @param url            The location used to resolve relative paths for resources.
     * @param resourceBundle The resource bundle to localize the root object, or null.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //set data
            idLbl.setText(String.valueOf(selectedAppointment.getAppointmentId()));
            titleLbl.setText(selectedAppointment.getTitle());
            typeLbl.setText(selectedAppointment.getType());
            locationLbl.setText(selectedAppointment.getLocation());
            descriptionLbl.setText(selectedAppointment.getDescription());

            // Get all customers, users, and contacts
            ObservableList<Customer> allCustomers = CustomerDBAccess.getAllCustomers();
            ObservableList<User> allUsers = UserDBAccess.getAllUsers();
            ObservableList<Contact> allContacts = ContactDBAccess.getAllContacts();

            //***Lambda #1 x3 -  (much more easy to read the code)***
            // Set info for customer
            allCustomers.stream()
                    .filter(c -> c.getCustomerId() == selectedAppointment.getCustomerId())
                    .forEach(c -> customerLbl.setText(c.getName()));

            // Set info for contact
            allContacts.stream()
                    .filter(contact -> contact.getContactId() == selectedAppointment.getContactId())
                    .forEach(contact -> contactLbl.setText(contact.getName()));

            // Set info for user
            allUsers.stream()
                    .filter(u -> u.getUserId() == selectedAppointment.getUserId())
                    .forEach(u -> userLbl.setText(u.getUserName()));

            //get (EST) time zone
            ZoneId easternTimeZone = ZoneId.of("America/New_York");
            //date and time formatter
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            //change local date/time to (EST)
            ZonedDateTime eStartLDT = selectedAppointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(easternTimeZone);
            ZonedDateTime eEndLDT = selectedAppointment.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(easternTimeZone);
            //set start date and time
            startDateLbl.setText(eStartLDT.format(dateFormatter));
            startTimeLbl.setText(eStartLDT.format(timeFormatter));
            //set end date and time
            endDateLbl.setText(eEndLDT.format(dateFormatter));
            endTimeLbl.setText(eEndLDT.format(timeFormatter));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
