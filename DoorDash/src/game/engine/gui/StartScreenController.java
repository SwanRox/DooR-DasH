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

public class StartScreenController {

    @FXML
    public void onExitButton(ActionEvent event){
        Platform.exit();
    }

    @FXML
    public void onPlayButtonClicked(ActionEvent event) {
        try {
            System.out.println("Play Button was clicked!");
            Parent boardRoot = FXMLLoader.load(getClass().getResource("pickside.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();
            
            Scene boardScene = new Scene(boardRoot);
            stage.setScene(boardScene);
            
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);
            
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add this method to handle the Instructions button click
    @FXML
    public void onInstructionsButtonClicked(ActionEvent event) {
        System.out.println("Instructions Button was clicked!");
        InstructionsController.openInstructions();
    }
}