package game.engine.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameController {

   
    @FXML
    public void onQuestionMarkClicked(ActionEvent event) {
        System.out.println("Help button clicked in the game!");
        InstructionsController.openInstructions();
    }
    
}