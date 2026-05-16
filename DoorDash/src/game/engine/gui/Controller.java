package game.engine.gui;

import game.engine.Game;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;

public class Controller extends Application{

	private View view;

	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("mainmenu.fxml"));
		view = new View();
		
		Scene mainMenu = new Scene(root,1280,800);
		primaryStage.setScene(mainMenu);
		primaryStage.setTitle("Door Dash");
		primaryStage.show();
	}
	public static void main (String[]args){
		launch(args);}
	


}
