package controller;

import DAO.AppointmentDBAccess;
import DAO.ContactDBAccess;
import DAO.CustomerDBAccess;
import DAO.UserDBAccess;
import dialogBox.DialogBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * This class is responsible for editing of appointments.
 *
 * It implements the Initializable interface to initialize the JavaFX components.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class EditAppointmentController implements Initializable {
    //fields
    public static Appointment appointment = null;
    String action = null;

    @FXML
    private TextArea appDescriptionTxt;

    @FXML
    private Label appHeaderLbl;

    @FXML
    private TextField appIdTxt;

    @FXML
    private TextField appLocationTxt;

    @FXML
    private TextField appTitleTxt;

    @FXML
    private TextField appTypeTxt;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<LocalTime> endCombo;

    @FXML
    private ComboBox<LocalTime> startCombo;

    @FXML
    private ComboBox<User> userCombo;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;
    /**
     * Adjusts the screen based on the action type (add or update).
     *
     * @param type The action type, "add" or "update."
     */
    public void adjustScreen(String type){
        if(type == "update"){
            appHeaderLbl.setText("Edit Appointment");
        }
    }
    /**
     * Modifies the appointment to be edited or updated.
     *
     * @param selectedAppointment The appointment to modify.
     */
    public static void modifyAppointment(Appointment selectedAppointment){
        appointment = selectedAppointment;
    }
    /**
     * Handles the action when the cancel button is clicked.
     *
     * @param event The ActionEvent associated with the cancel button.
     * @throws IOException If an I/O error occurs during the window change.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "/view/MainScreen.fxml");
    }
    /**
     * Handles the action when the save button is clicked.
     *
     * @param event The ActionEvent associated with the save button.
     * @throws SQLException If a database error occurs.
     * @throws IOException If an I/O error occurs during the window change.
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        //all appointments
        ObservableList<Appointment> allAppointmentsList = AppointmentDBAccess.getAllAppointments();

        //get all fields inputs
        String appTitle = appTitleTxt.getText();
        String appDescription = appDescriptionTxt.getText();
        String appLocation = appLocationTxt.getText();
        String appType = appTypeTxt.getText();
        LocalTime startTime = startCombo.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime endTime = endCombo.getSelectionModel().getSelectedItem();
        LocalDate endDate = endDatePicker.getValue();
        Customer customer = customerCombo.getSelectionModel().getSelectedItem();
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        User user = userCombo.getSelectionModel().getSelectedItem();

        //display error is any field is empty
        if(appTitle == "" || appDescription == "" || appLocation == "" || appType == "" || startDate == null ||
                startTime == null || endDate == null || endTime == null || customer == null || contact == null || user == null ){
            DialogBox.errorDialog("Input Error", "Text Field/Selection Error",
                    "Please make sure that all fields are filled out.");
            return;
        }

        //display error if start and end (date/time) is not inputted correctly
        if(startDate.isAfter(endDate) || endDate.isBefore(startDate) || startTime.isAfter(endTime) ||
                endTime.isBefore(startTime) || startTime.equals(endTime)){
            DialogBox.errorDialog("Input Error", "Date/Time Error",
                    "Please make sure the Date and Time is filled out correctly.");
            return;
        }

        //display error if date/time is not within business hours
        if(startTime.isBefore(LocalTime.of(8, 00)) || endTime.isAfter(LocalTime.of(22, 00))
                || !(startDate.equals(endDate))){
            DialogBox.errorDialog("Input Error", "Date/Time Error",
                    "Reminder: Business Hours are from 08:00 AM - 10:00 PM (EST)");
            return;
        }

        //get customer id, contact is, and user id from combo selection
        int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
        int userId = userCombo.getSelectionModel().getSelectedItem().getUserId();

        //format date and time
        String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
        String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

        ////format date and time (without manual conversion)
        String sUTC = (startDate + " " + startTime + ":00");
        String eUTC = (endDate + " " + endTime + ":00");

        LocalDateTime sTime = Timestamp.valueOf(sUTC).toLocalDateTime();
        LocalDateTime eTime = Timestamp.valueOf(eUTC).toLocalDateTime();

        //add or update appointment based on action
        if (action.equals("add")) {

            for(Appointment a : allAppointmentsList){
                if(a.getCustomerId() == customerId){
                    if(a.getStart().isAfter(sTime) && a.getStart().isBefore(eTime) ||
                            a.getEnd().isBefore(eTime) && a.getEnd().isAfter(sTime) ||
                            a.getStart().equals(sTime) && a.getEnd().equals(eTime)){
                        DialogBox.errorDialog("Appointment Error", "Appointment Overlap",
                                "A customer can not have overlapping appointments, please check the selected time");
                        return;
                    }
                }
            }
            // Insert the appointment with UTC start and end times
            AppointmentDBAccess.insertAppointment(appTitle, appDescription, appLocation, appType, sUTC, eUTC,
                    customerId, userId, contactId);

        } else if (action.equals("update")) {
            // Update the appointment with UTC start and end times
            int id = Integer.parseInt(appIdTxt.getText());

            for(Appointment a : allAppointmentsList){
                if(a.getCustomerId() == customerId){
                    if(a.getStart().isAfter(sTime) && a.getStart().isBefore(eTime) ||
                            a.getEnd().isBefore(eTime) && a.getEnd().isAfter(sTime) ||
                            a.getStart().equals(sTime) && a.getEnd().equals(eTime)){
                        DialogBox.errorDialog("Appointment Error", "Appointment Overlap",
                                "A customer can not have overlapping appointments, please check the selected time");
                        return;
                    }
                }
            }
            AppointmentDBAccess.updateAppointment(id, appTitle, appDescription, appLocation, appType, sUTC, eUTC,
                    customerId, userId, contactId);
        }
        changeWindow(event, "/view/MainScreen.fxml");
    }
    /**
     * Converts a given date and time to UTC format.
     *
     * @param dateTime The date and time to convert to UTC format.
     * @return The UTC-formatted date and time as a string.
     */
    public static String convertTimeDateUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(dateTime);
        LocalDateTime localDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = localDT.atZone(ZoneId.systemDefault());
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime utcLocalDT = utcDT.toLocalDateTime();
        return utcLocalDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Changes the current window to a new window specified by the path.
     *
     * @param event The ActionEvent associated with the window change.
     * @param path The path to the new window.
     * @throws IOException If an I/O error occurs during the window change.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the edit appointment controller - for either (add or update).
     * for add - all combo boxes are filled is data
     * for edit - all fields are filed with selected data.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialized");
        String type = MainScreenController.adjustScreenAppointment();
        if(type == null){
            type = ViewAppointmentController.adjustScreenCustomer();
        }
        adjustScreen(type);
        action = type;

        try {
            //create observables for allCustomers, allUsers, allContacts
            ObservableList<Customer> allCustomers = CustomerDBAccess.getAllCustomers();
            ObservableList<User> allUsers = UserDBAccess.getAllUsers();
            ObservableList<Contact> allContacts = ContactDBAccess.getAllContacts();

            //when editing, if appointment is chosen populate fields
            if(appointment != null){
                //set all value of fields
                appIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
                appTitleTxt.setText(appointment.getTitle());
                appDescriptionTxt.setText(appointment.getDescription());
                appLocationTxt.setText(appointment.getLocation());
                appTypeTxt.setText(appointment.getType());

                //eastern Time zone
                ZoneId estZone = ZoneId.of("America/New_York");

//                // Convert the appointment times from system default to Eastern Time
//                LocalDateTime easternStartDateTime = appointment.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(estZone).toLocalDateTime();
//                LocalDateTime easternEndDateTime = appointment.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(estZone).toLocalDateTime();

                // Convert the appointment times from system default to Eastern Time
                LocalDateTime startDateTime = appointment.getStart().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endDateTime = appointment.getEnd().atZone(ZoneId.systemDefault()).toLocalDateTime();

                // Set the values in your UI components
                startDatePicker.setValue(startDateTime.toLocalDate());
                startCombo.setValue(startDateTime.toLocalTime());
                endDatePicker.setValue(endDateTime.toLocalDate());
                endCombo.setValue(endDateTime.toLocalTime());

                //set values for combo boxes
                for (Customer customer : allCustomers) {
                    if(customer.getCustomerId() == appointment.getCustomerId()){
                        customerCombo.setValue(customer);
                    }
                }
                for (Contact contact : allContacts) {
                    if(contact.getContactId() == appointment.getContactId()){
                        contactCombo.setValue(contact);
                    }
                }
                for (User user : allUsers) {
                    if(user.getUserId() == appointment.getUserId()){
                        userCombo.setValue(user);
                    }
                }
            }
            //populate combo boxes (customers, users, contact)
            customerCombo.setItems(allCustomers);
            userCombo.setItems(allUsers);
            contactCombo.setItems(allContacts);

            //populate start and ed time using while loop
            LocalTime start = LocalTime.of(8,0);
            LocalTime end = LocalTime.of(22,0);

            while(start.isBefore(end.plusSeconds(1))){
                startCombo.getItems().add(start);
                endCombo.getItems().add(start);
                start = start.plusMinutes(15);
            }

            //Combo prompts when adding user
            customerCombo.setPromptText("Choose a Customer");
            userCombo.setPromptText("Choose a User");
            contactCombo.setPromptText("Choose a Customer");
            startCombo.setPromptText("Choose a Start Time");
            endCombo.setPromptText("Choose an End Time");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
