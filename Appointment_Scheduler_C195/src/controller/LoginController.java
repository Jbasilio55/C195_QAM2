package controller;

import DAO.AppointmentDBAccess;
import DAO.UserDBAccess;
import dialogBox.DialogBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import model.Appointment;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The LoginController class handles user login and authentication in the application.
 * It implements the Initializable interface for JavaFX initialization.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class LoginController implements Initializable {
    //fields
    private static int userId;
    private static String userName;
    public static boolean loginSuccessful = false;

    Stage stage;

    @FXML
    private Button ExitBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label timeZoneLbl;

    @FXML
    private Label timezoneLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameTxt;

    /**
     * @return The User ID.
     */
    public static int UserId(){
        return userId;
    }
    /**
     * @return The username.
     */
    public static String UserName(){
        return userName;
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Handles the action when the "Login" button is clicked. It performs user authentication and redirects to the main screen on successful login.
     *
     * @param event The ActionEvent generated by the button click.
     * @throws IOException If an IO exception occurs during the login process.
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException{
        //get username and password text
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if(username.equals(null) || password.equals(null) || username == "" || password == ""){
            // Display an error message in the user's default language
            String errorMessage = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                    .getString("nullError");
            String errorTitle = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                    .getString("errorTitle");
            String errorHeader = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                    .getString("errorHeader");

            DialogBox.errorDialog(errorTitle, errorHeader, errorMessage);
            writeToFile(loginSuccessful);
            return;
        }
        //get user Id from database from user
        int userID = UserDBAccess.checkUser(username, password);

        //set global variables
        userName = usernameTxt.getText();
        userId = userID;

        try{
            //If user not found
            if(userID < 0){
                // Display an error message in the user's default language
                String errorMessage = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                        .getString("loginError");
                String errorTitle = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                        .getString("errorTitle");
                String errorHeader = ResourceBundle.getBundle("languages/Login", Locale.getDefault())
                        .getString("errorHeader");

                DialogBox.errorDialog(errorTitle, errorHeader, errorMessage);
                writeToFile(loginSuccessful);
            }else{
                // Successful login or missing username/password
                loginSuccessful = true;
                changeWindow(event, "/view/MainScreen.fxml");
                appointmentNotice();
                writeToFile(loginSuccessful);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Writes to file if login was successful or not and if login was successful document user and time.
     *
     * @param loginAttempt if login attempt was successful or not.
     * @throws IOException If an IO exception occurs during the writing of file.
     */
    public void writeToFile(boolean loginAttempt) throws IOException {
        //create new login activity text file
        File f = new File("login_activity.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(); // Get the current time once

        //append if user login was successful (with username) or not and local date time.
        if(loginAttempt == true){
            writer.write(String.format("\nLogin Attempt Successful |Username: %s on %s (LocalDateTime)",UserName(),now.format(dateTimeFormatter)));
        }else{
            writer.write(String.format("\nLogin Attempt Unsuccessful | %s (LocalDateTime)",now.format(dateTimeFormatter)));
        }

        //close writer
        writer.close();
    }

    /**
     * Displays a notification for upcoming appointments, if any.
     *
     * @throws SQLException If a SQL exception occurs during the appointment retrieval.
     */
    public void appointmentNotice() throws SQLException {
        //get all appointments from DB
        ObservableList<Appointment> allAppointmentsList = AppointmentDBAccess.getAllAppointments();
        //date formatter & local date time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(); // Get the current time once

        boolean dialogShown = false;

        //go through appointment list and check start time
        for(Appointment a: allAppointmentsList){

            //check if there are any upcoming appointments in the next 15 mins, if so display info.
            if(now.isBefore(a.getStart()) && now.plusMinutes(15).isAfter(a.getStart())){
                DialogBox.errorDialog("Notice", "Appointment Notice",
                        String.format("You have an upcoming appointment.\n \n" +
                                "Appointment ID: %d \n" +
                                "Appointment Time: %s", a.getAppointmentId(), dateTimeFormatter.format(a.getStart())));
                dialogShown = true;
            }
        }
        // if not appointment is upcoming - tell user there is no upcoming appointments
        if(dialogShown == false){
            DialogBox.errorDialog("Notice", "Appointment Notice","There are no upcoming appointments.");
        }
    }

    /**
     * Changes the current window to a new window specified by the given path.
     *
     * @param event The ActionEvent that triggered the window change.
     * @param path  The path to the new window's FXML file.
     * @throws IOException If an IO exception occurs during the window change.
     */
    public void changeWindow(ActionEvent event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the LoginController, also checks resource bundle (language).
     * depending on language - sets the label and texts to systems language
     * also sets the time zone to system default (current user computers timezone).
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources for the localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gets resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("languages/Login", Locale.getDefault());
        //set timezone to users computer setting time zone
        timezoneLbl.setText(String.valueOf(ZoneId.systemDefault()));

        //set text depending on language
        loginLbl.setText(rb.getString("login"));
        usernameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        timeZoneLbl.setText(rb.getString("timezone"));
        loginBtn.setText(rb.getString("login"));
        ExitBtn.setText(rb.getString("exit"));
    }
}