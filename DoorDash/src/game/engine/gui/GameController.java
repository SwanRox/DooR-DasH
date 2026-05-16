package game.engine.gui;

import game.engine.Game;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameController {

	private Game gameEngine;
    public void setGameEngine(Game gameEngine) {
        this.gameEngine = gameEngine;
        updateBoardGraphics(); 
    }
    
    private void updateBoardGraphics() {

    }

   
    @FXML
    public void onQuestionMarkClicked(ActionEvent event) {
        System.out.println("Help button clicked in the game!");
        InstructionsController.openInstructions();
    }
    
}