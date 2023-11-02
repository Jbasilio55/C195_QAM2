import utilities.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The Main class serves as the entry point for the Appointment Scheduler application. It extends the JavaFX Application class
 * to create and display the application's user interface.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class Main extends Application {
    /**
     * The start method is called when the JavaFX application is launched. It initializes the main application window and
     * loads the Login.fxml file to display the login screen.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws Exception If an exception occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 500, 450));
        stage.show();
    }
    /**
     * The main method is the entry point for the Java application. It opens a JDBC database connection, launches the JavaFX
     * application, and then closes the database connection when the application exits.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
