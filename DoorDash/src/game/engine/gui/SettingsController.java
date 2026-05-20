package game.engine.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

   @FXML
   private Slider volumeslider;
   
   @FXML
   private Button setvolumebutton;
   
   @FXML
   public void onSetVolumeButton(ActionEvent event) {
	   Controller.sounds.adjustVolume(volumeslider.getValue());
   }
   
   public static void openSettings() {
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(InstructionsController.class.getResource("/game/engine/gui/settings.fxml"));
            Parent root = fxmlLoader.load();
            
            Stage SettingsStage = new Stage();
            SettingsStage.setTitle("Settings");
            
            // APPLICATION_MODAL prevents clicking outside the pop-up
            SettingsStage.initModality(Modality.APPLICATION_MODAL); 
            SettingsStage.setScene(new Scene(root));
            
            // Disables resizing to keep the text formatting intact
            SettingsStage.setResizable(false); 
            SettingsStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading settings.fxml. Check the file path.");
        }
    }

    
    @FXML
    public void handleCloseButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}