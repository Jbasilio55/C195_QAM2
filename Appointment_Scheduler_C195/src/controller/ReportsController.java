package controller;

import DAO.AppointmentDBAccess;
import DAO.ContactDBAccess;
import DAO.FirstLevelDivisionDBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
/**
 * The ReportsController class is responsible for displaying reports
 * related to contacts, appointments, and divisions.
 *
 * It implements the Initializable interface for JavaFX initialization.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class ReportsController implements Initializable {
    //fields
    @FXML
    private ComboBox<Contact> ContactCombo;

    @FXML
    private TableColumn<?, ?> EndCol;

    @FXML
    private TableColumn<?, ?> StartCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> appsMonthCol;

    @FXML
    private TableColumn<Appointment, String> appsTypeCol;

    @FXML
    private TableColumn<?, ?> appDescriptionCol;

    @FXML
    private TableView<Appointment> contactTableView;


    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> divisionCol;

    @FXML
    private TableView<FirstLevelDivision> divisionTableView;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TableColumn<Appointment, Integer> totalAppsCol;

    @FXML
    private TableColumn<?, ?> totalCustomersCol;

    @FXML
    private TableColumn<?, ?> appIDCol;

    @FXML
    private TableColumn<?, ?> appTitleCol;

    @FXML
    private TableColumn<?, ?> appTypeCol;

    /**
     * A list of month names for populating the monthCombo ComboBox.
     */
    private final List<String> monthList = Arrays.asList(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    );

    /**
     * Cancel report and redirects to the main screen.
     *
     * @param event The ActionEvent triggered by the user's interaction.
     * @throws IOException If there is an issue with loading the new screen.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "/view/MainScreen.fxml");
    }
    /**
     * Changes the current window to the one specified by the given path.
     *
     * @param event The ActionEvent that triggered the window change.
     * @param path  The path to the FXML file of the new window.
     * @throws IOException If there is an issue with loading the new screen.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the JavaFX controller, sets up ComboBoxes and fetches data for tables.
     * Lambda Expression #2 - used to simplify the event handlers for combo boxes
     *
     * @param url            The location used to resolve relative paths for root object initialization.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableMonths = FXCollections.observableArrayList(monthList);
        monthCombo.setItems(observableMonths);
        monthCombo.setPromptText("Choose a Month");

        // Simplified event handlers with lambda expressions
        ContactCombo.setOnAction(event -> handleContactClicked());
        monthCombo.setOnAction(event -> handleMonthClick());

        try {
            ObservableList<FirstLevelDivision> FLDQueryList = FirstLevelDivisionDBAccess.getTotalCustomersPerDivision();
            ObservableList<Contact> contactList = ContactDBAccess.getAllContacts();
            ContactCombo.setItems(contactList);
            ContactCombo.setPromptText("Choose a contact");
            divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            totalCustomersCol.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));
            divisionTableView.setItems(FLDQueryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lambda expression used to handle Contact data - get values from database then set Table View.
     *
     */
    private void handleContactClicked() {
        try {
            int contactID = ContactCombo.getValue().getContactId();
            ObservableList<Appointment> contactAppointment = AppointmentDBAccess.contactAppointments(contactID);
            appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            EndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactTableView.setItems(contactAppointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Lambda expression used to handle appointment month/type data - get values from database then set Table View.
     *
     */
    private void handleMonthClick() {
        try {
            String selectedMonth = monthCombo.getValue();
            int monthIndex = monthList.indexOf(selectedMonth);
            ObservableList<Appointment> months = AppointmentDBAccess.filterByMonthType(monthIndex, selectedMonth);
            appsMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            appsTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            totalAppsCol.setCellValueFactory(new PropertyValueFactory<>("count"));
            appointmentTableView.setItems(months);
            appointmentTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
