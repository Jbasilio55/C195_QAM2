package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * This class, ViewCustomerController, is responsible for managing the customer details view
 * and handling user interactions within the view. It implements the Initializable interface
 * to initialize the view when it's loaded.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class ViewCustomerController implements Initializable {
    //Fields
    public static Customer chosenCustomer = null;
    private static String screenAction = null;

    @FXML
    private Label headerLabel;

    @FXML
    private Label setAddressLbl;

    @FXML
    private Label setCountryLbl;

    @FXML
    private Label setIdLbl;

    @FXML
    private Label setNameLbl;

    @FXML
    private Label setPhoneLbl;

    @FXML
    private Label setPostalLbl;

    @FXML
    private Label setStateLbl;

    /**
     * Handles the "Cancel" action in the view, returning to the main screen.
     *
     * @param event The ActionEvent triggering the action.
     * @throws IOException if an I/O error occurs during the action.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        screenAction = null;
        changeWindow(event, "/view/MainScreen.fxml");
    }

    /**
     * Retrieves the current screen action.
     *
     * @return The current screen action (e.g., "update" or "create").
     */
    public static String adjustScreenCustomer(){
        return screenAction;
    }

    /**
     * Handles the "Edit" action in the view, allowing the user to modify customer details.
     *
     * @param event The ActionEvent triggering the action.
     * @throws IOException if an I/O error occurs during the action.
     */
    @FXML
    void onActionEdit(ActionEvent event) throws IOException {
        screenAction = "update";
        EditCustomerController.modifyCustomer(chosenCustomer);
        changeWindow(event, "/view/EditCustomer.fxml");
    }
    /**
     * Saves the selected customer to global customer.
     * This is so that the selected customer can be passed to edit customer controller.
     *
     * @param selectedCustomer The customer to be saved.
     */
    public static void saveCustomer(Customer selectedCustomer){
        chosenCustomer = selectedCustomer;
    }

    /**
     * Changes the window to a different view based on the provided path.
     *
     * @param event The ActionEvent triggering the action.
     * @param path  The path to the FXML file for the new view.
     * @throws IOException if an I/O error occurs during the action.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the customer view from chosen customer's details.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resource bundle that contains localized resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIdLbl.setText(String.valueOf(chosenCustomer.getCustomerId()));
        setNameLbl.setText(chosenCustomer.getName());
        setAddressLbl.setText(chosenCustomer.getAddress());
        setPhoneLbl.setText(chosenCustomer.getPhone());
        setCountryLbl.setText(chosenCustomer.getCountry());
        setStateLbl.setText(chosenCustomer.getDivision());
        setPostalLbl.setText(chosenCustomer.getPostalCode());
    }
}
