package controller;

import DAO.AppointmentDBAccess;
import DAO.CustomerDBAccess;
import dialogBox.DialogBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * The MainScreenController class is responsible for controlling the main application screen.
 * It handles various user actions and interactions, such as adding, updating, deleting, and viewing appointments and customers.
 * Additionally, it provides functionality for switching between different views and populating data tables.
 *
 * This class implements the Initializable interface to support JavaFX initialization.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class MainScreenController implements Initializable {
    //Fields
    private static String customerScreenAction = null;
    private static String appointmentScreenAction = null;

    @FXML
    private TableColumn<?, ?> AppContact;

    @FXML
    private TableColumn<?, ?> AppCustomerID;

    @FXML
    private TableColumn<?, ?> AppDescription;

    @FXML
    private TableColumn<?, ?> AppEnd;

    @FXML
    private TableColumn<?, ?> AppID;

    @FXML
    private TableColumn<?, ?> AppLocation;

    @FXML
    private TableColumn<?, ?> AppStart;

    @FXML
    private TableColumn<?, ?> AppTitle;

    @FXML
    private TableColumn<?, ?> AppType;

    @FXML
    private TableColumn<?, ?> AppUserID;

    @FXML
    private TableView<Appointment> AppointmentsTableView;

    @FXML
    private RadioButton allAppRadio;

    @FXML
    private ToggleGroup appointmentRadio;

    @FXML
    private TableColumn<?, ?> customerAddress;

    @FXML
    private TableColumn<?, ?> customerID;

    @FXML
    private TableColumn<?, ?> customerName;

    @FXML
    private TableColumn<?, ?> customerPhone;

    @FXML
    private TableColumn<?, ?> customerPostal;

    @FXML
    private TableColumn<?, ?> customerState;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton weekRadio;
    /**
     * returns which action to take (add/update) for customer.
     *
     */
    public static String adjustScreenCustomer(){
        return customerScreenAction;
    }
    /**
     * returns which action to take (add/update) for appointment.
     *
     */
    public static String adjustScreenAppointment(){
        return appointmentScreenAction;
    }
    /**
     * Add app - changes window to edit appointment controller (add).
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionAddApp(ActionEvent event) throws IOException {
        Appointment selectedAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
        appointmentScreenAction = "add";
        changeWindow(event, "/view/EditAppointment.fxml");
    }
    /**
     * Add customer - changes window to edit customer controller (add).
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        customerScreenAction = "add";
        changeWindow(event, "/view/EditCustomer.fxml");
    }
    /**
     * Deletes an appointment from database and updates the table view.
     * Confirm deleted appointment and prompt with the deleted appointment id and type
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionDeleteApp(ActionEvent event) throws SQLException {
        //get selected appointment
        Appointment selectedAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
        //if no selection is made, display error message
        if(selectedAppointment == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
            return;
        }else{
            //delete app and update app table view
            boolean delete = DialogBox.confirmDialog("Delete", "Delete Appointment", "Are you sure you wish to delete appointment");

            if(delete == true){
                //get selected Id and type
                int id = selectedAppointment.getAppointmentId();
                String type = selectedAppointment.getType();
                //delete app from database
                AppointmentDBAccess.deleteAppointment(id);
                //refresh tableview
                ObservableList<Appointment> refreshAppointments = AppointmentDBAccess.getAllAppointments();
                AppointmentsTableView.setItems(refreshAppointments);
                //show deleted app id and type
                DialogBox.errorDialog("Delete", "Delete Appointment", String.format("Appointment Id: %d \nAppointment type: %s",id,type));
            }else{
                ObservableList<Appointment> refreshAppointments = AppointmentDBAccess.getAllAppointments();
                AppointmentsTableView.setItems(refreshAppointments);
            }
        }
    }
    /**
     * Deletes a customer from database and updates the table view.
     * Also deletes all appointments related to customer.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        //get all appointments
        ObservableList<Appointment> appList = AppointmentDBAccess.getAllAppointments();
        //get selected customer
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        //if no selection is made, display error message
        if(selectedCustomer == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
            return;
        }
        //get customer id
        int id = selectedCustomer.getCustomerId();

        //check if the user wants to continue or cancel, the deletion of customer
        boolean option = DialogBox.confirmDialog("Delete", "Delete Customer",
                "Warning! Deleting a customer, will also delete the selected customer's appointments.");

        //if user chooses to continue
        if(option == true){
            //search all app list
            for(Appointment a: appList){
                //if apps customer id matcher selected customer id
                if(a.getCustomerId() == id){
                    //delete all related appointments
                    AppointmentDBAccess.deleteAppointment(a.getAppointmentId());
                }
            }
            //then delete customer
            CustomerDBAccess.deleteCustomer(id);
        }else{
            //if user cancels, then just return
            return;
        }
        ObservableList<Customer> refreshCustomers = CustomerDBAccess.getAllCustomers();
        customerTableView.setItems(refreshCustomers);
        AppointmentsTableView.refresh();
    }
    /**
     * logs out and returns back to login screen.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        changeWindow(event, "/view/Login.fxml");
    }
    /**
     * change windows to reports.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        changeWindow(event, "/view/ReportsMenu.fxml");
    }
    /**
     * Update the selected Appointment.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionUpdateApp(ActionEvent event) throws IOException {
        //advice next screen this is an update
        appointmentScreenAction = "update";
        //get all appointments
        Appointment selectedAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
        //if no selection is made, display and error message
        if(selectedAppointment == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
            return;
        }
        EditAppointmentController.modifyAppointment(selectedAppointment);
        changeWindow(event, "/view/EditAppointment.fxml");
    }
    /**
     * Update the selected Customer.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        //get all customers
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        //advice next screen this is an update
        customerScreenAction = "update";
        //if no selection is made, display error message.
        if(selectedCustomer == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
            return;
        }
        //send data to next screen
        EditCustomerController.modifyCustomer(selectedCustomer);
        changeWindow(event, "/view/EditCustomer.fxml");
    }
    /**
     * View the selected Appointment.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionViewApp(ActionEvent event) throws IOException {
        //get selected appointment
        Appointment selectedAppointment = AppointmentsTableView.getSelectionModel().getSelectedItem();
        //if no selection is made and the button is clicked, display and error message.
        if(selectedAppointment == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
            return;
        }
        //send data from selected customer to View Appointment Controller
        ViewAppointmentController.saveAppointment(selectedAppointment);
        changeWindow(event, "/view/ViewAppointment.fxml");
    }

    /**
     * View the selected customer.
     *
     * @param event The ActionEvent associated with the button click.
     * @throws IOException if there is an issue loading the new window.
     */
    @FXML
    void onActionViewCustomer(ActionEvent event) throws IOException {
        //get selected customer
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        //if no selection is made and the button is clicked, display and error message.
        if(selectedCustomer == null){
            DialogBox.errorDialog("Error", "Selection Error", "Please make sure you have made a selection.");
        }
        //send data from selected customer to View Customer Controller
        ViewCustomerController.saveCustomer(selectedCustomer);
        changeWindow(event, "/view/ViewCustomer.fxml");
    }

    /**
     * Change the current window to a new one.
     * @param event The ActionEvent associated with the button click.
     * @param path The path to the FXML file for the new window.
     * @throws IOException if there is an issue loading the new window.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the radio button click action to filter and display week/month/ and all appointments.
     * @param event The ActionEvent associated with the radio button click.
     * @throws SQLException if there is an issue with the database access.
     */
    @FXML
    void onActionRadioClicked(ActionEvent event) throws SQLException {
        ObservableList<Appointment> allAppointmentsList = AppointmentDBAccess.getAllAppointmentsWithContacts();
        ObservableList<Appointment> monthAppointmentsList = AppointmentDBAccess.getMonthlyAppointments();
        ObservableList<Appointment> weekAppointmentsList = AppointmentDBAccess.getWeeklyAppointments();

        if(weekRadio.isSelected()){
            AppointmentsTableView.setItems(weekAppointmentsList);
        }else if(monthRadio.isSelected()){
            AppointmentsTableView.setItems(monthAppointmentsList);
        }else{
            AppointmentsTableView.setItems(allAppointmentsList);
        }
    }
    /**
     * Initialize the main screen components and load data from the database.
     * Populates both Appointment and Customer Table.
     * Also converts the Start and End time from database (UTC) to local Time.
     *
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle containing localizable resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointment> allAppointmentsList = AppointmentDBAccess.getAllAppointmentsWithContacts();

            //populate Appointment Table View
            AppID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            AppCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
            AppUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
            AppContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            AppTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            AppDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            AppLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            AppType.setCellValueFactory(new PropertyValueFactory<>("type"));
            AppStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            AppEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

            AppointmentsTableView.setItems(allAppointmentsList);

            //Populate Customer Table View
            ObservableList<Customer> allCustomersList = CustomerDBAccess.getAllCustomers();
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerState.setCellValueFactory(new PropertyValueFactory<>("division"));

            customerTableView.setItems(allCustomersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
