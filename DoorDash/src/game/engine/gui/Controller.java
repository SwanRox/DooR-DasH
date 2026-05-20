package game.engine.gui;

import java.io.File;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;

public class Controller extends Application{

	private View view;
	public static SoundController sounds = new SoundController();

	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
		view = new View();
		
		Scene mainMenu = new Scene(root,1280,800);
		primaryStage.setScene(mainMenu);
		primaryStage.setTitle("Door Dash");
		primaryStage.show();
		primaryStage.setMaximized(true);
		sounds.playMenuMusic();
		
	}
	

	
	public static void main (String[]args){
		launch(args);
	}
}
