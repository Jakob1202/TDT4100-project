package blackjack;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/** Blackjack application to start the application */
public class BlackjackApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack.fxml"));
        Parent root = loader.load();
        BlackjackController controller = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                controller.handleClose();
                if(controller.getRoundCount() == 0) {
                    controller.showAlert("The table was not saved because there has not been played any rounds", AlertType.INFORMATION);
                } else {
                    controller.showAlert("The table succeded to be saved", AlertType.INFORMATION);
                }
            } catch (IOException e) {
                controller.showAlert("The table failed to be saved", AlertType.ERROR);
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}