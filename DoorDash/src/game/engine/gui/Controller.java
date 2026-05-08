package game.engine.gui;

import game.engine.Game;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Controller extends Application{

	private View view;

	public void start(Stage primaryStage) throws Exception {

		view = new View();

		Scene mainMenu = new Scene(view.root,1000,800);
		primaryStage.setScene(mainMenu);
		primaryStage.setTitle("Test Name");
		primaryStage.show();
	}
	public static void main (String[]args){
		launch(args);}

}
