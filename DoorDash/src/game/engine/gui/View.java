package game.engine.gui;


import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

public class View {
	VBox menu = new VBox(60);
	StackPane root = new StackPane(menu);
	
	
	public View(){
		menu.setAlignment(Pos.CENTER);
		Rectangle logoPlaceholder = new Rectangle(20,20,200,150);
		Button startButton = new Button("Start");
		Button instructionsButton = new Button("Instructions");
		Button quitButton = new Button("Quit");
		menu.getChildren().add(logoPlaceholder);
		menu.getChildren().add(startButton);
		menu.getChildren().add(instructionsButton);
		menu.getChildren().add(quitButton);
		logoPlaceholder.setLayoutY(300);
	}

}
