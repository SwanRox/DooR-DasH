package game.engine.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import game.engine.Game;
import game.engine.Role;

public class PickSideController {
    @FXML
    public void onScarerSelected(ActionEvent event) {
        startGameWithRole(Role.SCARER, event);
    }

    @FXML
    public void onLaugherSelected(ActionEvent event) {
        startGameWithRole(Role.LAUGHER, event);
    }

    private void startGameWithRole(Role selectedRole, ActionEvent event) {
        try {
            // 1. Initialize the backend game engine with the chosen role
            Game newGame = new Game(selectedRole);

            // 2. Load the Game Board FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent boardRoot = loader.load();

            // 3. CRITICAL STEP: Get the GameController and pass the engine to it
            GameController gameController = loader.getController();
            gameController.setGameEngine(newGame);

            // 4. Swap the scene to the game board
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(boardRoot));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle IOException from FXMLLoader or Game constructor
        }
    }
}
