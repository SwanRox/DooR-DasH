package game.engine.gui;

import game.engine.Game;
import game.engine.cells.CardCell;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.cells.MonsterCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane; // Make sure to import this!
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class GameController {

    // 1. Link the GridPane from your FXML to the controller
    @FXML
    private GridPane boardGrid; 

    private Game gameEngine;

    public void setGameEngine(Game gameEngine) {
        this.gameEngine = gameEngine;
        updateBoardGraphics(); 
    }

    private void updateBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        
        // Optional: If you call this method every turn, you might want to clear 
        // the grid first so images don't stack on top of each other forever.
        // boardGrid.getChildren().clear(); 
        
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                
                String imageName = null;
                Cell currentCell = cellArray[i][j];

                if (currentCell instanceof DoorCell) {
                	if(((DoorCell) currentCell).isActivated())
                	{
                		imageName = "dooractive.png";
                    	if(i==9 && j==0)imageName = "booactive.png";
                	}
                	else
                	{
                		imageName = "doorinactive.png";
                    	if(i==9 && j==0)imageName = "booinactive.png";
                	}

                } else if (currentCell instanceof ConveyorBelt) {
                    imageName = "conveyor.png"; 
                } else if (currentCell instanceof ContaminationSock) {
                    imageName = "sock.png"; 
                } else if (currentCell instanceof CardCell) {
                	imageName = "cardCell.png"; 
                } else if (currentCell instanceof MonsterCell) {
                	imageName = currentCell.getName() + ".png";
                }
                else{imageName = "empty.png";}
                

                if (imageName != null) {
                    // 2. Pass i (row) and j (column) to the method
                    addSprite(imageName, i, j); 
                }
            }
        }
    }

    // 3. Update method signature to accept row (i) and column (j)

    private void addSprite(String imageName, int i, int j) {
        try {
            Image image = new Image(getClass().getResourceAsStream("assets/" + imageName));
            ImageView imageView = new ImageView(image);
            
            imageView.setFitWidth(59);
            imageView.setFitHeight(59);
            imageView.setPreserveRatio(false);
            
            // 1. Center the image perfectly inside the grid cell
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);
            
            // 2. Flip the row so the board draws right-side up!
            // Assuming your grid is 10x10, the max row index is 9.
            int visualRow = 9 - i; 
            
            // Add to grid (Column j, Row visualRow)
            boardGrid.add(imageView, j, visualRow);
            
        } catch (NullPointerException e) {
            System.out.println("Error: Could not load image -> " + imageName);
        }
    }

    @FXML
    public void onQuestionMarkClicked(ActionEvent event) {
        System.out.println("Help button clicked in the game!");
        InstructionsController.openInstructions();
    }
    
    @FXML
    public void onReturnButtonClicked(ActionEvent event)
    {
    	System.out.println("Return Button was clicked in game!");
            try {
               
                javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(root, 1280, 800));
                stage.show();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
