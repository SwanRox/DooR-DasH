package game.engine.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import game.engine.gui.Controller;

public class StartScreenController {

    @FXML
    public void onExitButton(ActionEvent event){
        Platform.exit();
    };
    public void onPlayButtonClicked(ActionEvent event) {
        try {
            // 1. Load the Board Screen FXML
        	System.out.println("Button was clicked!");
            Parent boardRoot = FXMLLoader.load(getClass().getResource("game.fxml")); // Replace with your actual filename

            // 2. Get the current Stage (Window) from the button that was clicked
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Create a new Scene with the Board root and set it on the Stage
            Scene boardScene = new Scene(boardRoot);
            stage.setScene(boardScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error dialog)
        }
    }
}