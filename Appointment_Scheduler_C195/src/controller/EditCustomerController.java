package controller;

import DAO.CountryDBAccess;
import DAO.CustomerDBAccess;
import DAO.FirstLevelDivisionDBAccess;
import DAO.UserDBAccess;
import dialogBox.DialogBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The EditCustomerController class implements the Initializable interface and
 * is responsible for controlling the user interface for editing customer information.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class EditCustomerController implements Initializable {
    //fields
    String action = null;
    public static Customer customer = null;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<Country> countrySelect;

    @FXML
    private Label headerLabel;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private ComboBox<FirstLevelDivision> stateSelect;
    /**
     * Handles the event when a user clicks on the country selection.
     *
     * @param event The event triggered by the user's action.
     */
    @FXML
    void onCountryClicked(MouseEvent event) {
        //clear selection of province when country combo box is clicked
        stateSelect.getSelectionModel().clearSelection();
        stateSelect.setValue(null);
    }
    /**
     * Handles the event when a user clicks on the state/province selection.
     *
     * @param event The event triggered by the user's action.
     * @throws SQLException Thrown when a SQL-related error occurs.
     */
    @FXML
    void onStateClicked(MouseEvent event) throws SQLException {
        if(countrySelect.getItems().isEmpty()){
            //ignore
        }
        //set province based on country
        int countryId = countrySelect.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<FirstLevelDivision> divisions = FirstLevelDivisionDBAccess.getFirstLevelDivision_withCountryId(countryId);
        stateSelect.setItems(divisions);
    }
    /**
     * Modifies the customer to be edited.
     *
     * @param selectedCustomer The customer to be edited.
     */
    public static void modifyCustomer(Customer selectedCustomer){
        customer = selectedCustomer;
    }

    /**
     * Changes the current window to the specified path.
     *
     * @param event The event triggered by the user's action.
     * @param path  The path to the FXML file for the new window.
     * @throws IOException Thrown when an I/O-related error occurs.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Adjusts the screen based on the type of action (update or add).
     *
     * @param type The type of action (update or add).
     */
    public void adjustScreen(String type){
        if(type == "update"){
            //change header label
            headerLabel.setText("Edit Customer");
        }
    }
    /**
     * Handles the event when a user clicks on the "Save" button.
     *
     * @param event The event triggered by the user's action.
     * @throws SQLException Thrown when a SQL-related error occurs.
     * @throws IOException  Thrown when an I/O-related error occurs.
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        //get all users
        ObservableList<User> users = UserDBAccess.getAllUsers();
        //get selections
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String createdBy = LoginController.UserName();
        String lastUpdatedBy = LoginController.UserName();
        FirstLevelDivision division = stateSelect.getSelectionModel().getSelectedItem();
        Country country = countrySelect.getSelectionModel().getSelectedItem();

        //if any selection is null, display error box
        if(name == "" || address == "" || postalCode == "" || phone == "" || division == null || country == null){
            DialogBox.errorDialog("Error", "Input Error", "Please make sure all fields are filled out.");
            return;
        }

        //get division id
        Integer divisionId = stateSelect.getSelectionModel().getSelectedItem().getDivisionId();

        //add or update based on action
        if(action == "add"){
            CustomerDBAccess.insertCustomer(name, address, postalCode, phone, createdBy, lastUpdatedBy, divisionId);
        }else if(action == "update"){
            int setId = Integer.parseInt(idTxt.getText());
            CustomerDBAccess.updateCustomer(setId, name, address, postalCode, phone, lastUpdatedBy, divisionId);
        }
        changeWindow(event, "/view/MainScreen.fxml");
    }

    /**
     * Handles the event when a user clicks on the "Cancel" button.
     *
     * @param event The event triggered by the user's action.
     * @throws IOException Thrown when an I/O-related error occurs.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        changeWindow(event, "/view/MainScreen.fxml");
        customer = null;
    }

    /**
     * Initializes the controller and sets the action type.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if not specified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialized");
        //set type of action
        String type = MainScreenController.adjustScreenCustomer();
        if(type == null){
            type = ViewCustomerController.adjustScreenCustomer();
        }
        adjustScreen(type);
        action = type;


        try {
            //get all countries and all FL divisions
            ObservableList<Country> allCountries = CountryDBAccess.getAllCountries();
            ObservableList<FirstLevelDivision> allFLDivisions = FirstLevelDivisionDBAccess.getAllFirstLevelDivision();

            //if global customer is not null they set data <- update
            if(customer != null){
                idTxt.setText(String.valueOf(customer.getCustomerId()));
                nameTxt.setText(customer.getName());
                addressTxt.setText(customer.getAddress());
                phoneTxt.setText(customer.getPhone());
                countrySelect.setItems(allCountries);
                for (Country c : allCountries) {
                    if(c.getCountryId() == customer.getCountryId()){
                        countrySelect.setValue(c);
                    }
                }
                for(FirstLevelDivision fld: allFLDivisions){
                    if(fld.getDivisionId() == customer.getDivisionId()){
                        stateSelect.setValue(fld);
                    }
                }
                postalTxt.setText(customer.getPostalCode());
            }else{
                //populate countries combo box and set prompts
                countrySelect.setItems(allCountries);
                countrySelect.setPromptText("Select a Country");
                stateSelect.setPromptText("Select a State/Province");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
