package dialogBox;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
/**
 * The DialogBox class provides utility methods for displaying different types of dialog boxes
 * using JavaFX Alert dialogs, such as confirmation dialogs, error, and prompt dialogs.
 *
 * @author Jorge Basilio
 * @version  10/19/23
 */
public class DialogBox {
    /**
     * Displays a confirmation dialog with the specified title, header, and content.
     *
     * @param title   The title of the confirmation dialog.
     * @param header  The header text to be displayed in the dialog.
     * @param content The main content text of the dialog.
     * @return true if the user clicks the 'OK' button, false otherwise.
     */
    public static boolean confirmDialog(String title, String header, String content){
        // Creating a new Alert of type Confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Setting the title, header text, and content text of the Alert dialog
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        // Displaying the Alert dialog and waiting for the user to respond
        Optional<ButtonType> result = alert.showAndWait();
        // Checking if the user clicked the 'OK' button
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Displays an error/Prompts dialog with the specified title, header, and content.
     *
     * @param title   The title of the error dialog.
     * @param header  The header text to be displayed in the dialog.
     * @param content The main content text of the dialog.
     */
    public static void errorDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // Setting the title, header text, and content text of the Alert dialog
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        // Displaying the error dialog and waiting for the user to acknowledge it
        alert.showAndWait();
    }
}
