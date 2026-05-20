package game.engine.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class InstructionsController {

   
    public static void openInstructions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(InstructionsController.class.getResource("/game/engine/gui/Instructions.fxml"));
            Parent root = fxmlLoader.load();
            
            Stage instructionsStage = new Stage();
            instructionsStage.setTitle("How to Play");
            
            // APPLICATION_MODAL prevents clicking outside the pop-up
            instructionsStage.initModality(Modality.APPLICATION_MODAL); 
            instructionsStage.setScene(new Scene(root));
            
            // Disables resizing to keep the text formatting intact
            instructionsStage.setResizable(false); 
            instructionsStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Instructions.fxml. Check the file path.");
        }
    }

    
    @FXML
    public void handleCloseButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}