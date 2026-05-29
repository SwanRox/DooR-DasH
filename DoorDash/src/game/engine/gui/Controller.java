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
	    
	    Scene mainMenu = new Scene(root, 1920, 1080);
	    primaryStage.setScene(mainMenu);
	    primaryStage.setTitle("Door Dash");
	    primaryStage.setMaximized(true);
	    primaryStage.show();
	    sounds.playMenuMusic();
	}
	

	
	public static void main (String[]args){
		System.setProperty("prism.allowhidpi", "false");
		System.setProperty("glass.win.uiScale", "1.0");
		System.setProperty("sun.java2d.uiScale.enabled", "false");
		launch(args);
	}
}
