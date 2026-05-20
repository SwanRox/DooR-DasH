package game.engine.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import game.engine.Game;
import game.engine.Role;

public class PickSideController {
	
	@FXML
	private ImageView scarericon;
	@FXML
	private ImageView laughericon;
	@FXML
	private ImageView picksidebackground;
	private Image scarerbackground = new Image(getClass().getResourceAsStream("picksidescarer.png"));
	private Image laugherbackground = new Image(getClass().getResourceAsStream("picksidelaugher.png"));
	private Image defaultbackground = new Image(getClass().getResourceAsStream("pickside.png"));
	
	@FXML
    public void onScarerSelected(ActionEvent event) {
        startGameWithRole(Role.SCARER, event);
    }

    @FXML
    public void onLaugherSelected(ActionEvent event) {
        startGameWithRole(Role.LAUGHER, event);
    }
    @FXML
    public void onScarerEntered(Event event) {
        scarericon.setOpacity(1);
        picksidebackground.setImage(scarerbackground);
    }
    @FXML
    public void onScarerExited(Event event) {
    	scarericon.setOpacity(0.2);
    	picksidebackground.setImage(defaultbackground);
    }
    @FXML
    public void onLaugherEntered(Event event) {
    	laughericon.setOpacity(1);
    	picksidebackground.setImage(laugherbackground);
    }
    @FXML
    public void onLaugherExited(Event event) {
    	laughericon.setOpacity(0.2);
    	picksidebackground.setImage(defaultbackground);
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
            Controller.sounds.playGameMusic();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle IOException from FXMLLoader or Game constructor
        }
    }
}
