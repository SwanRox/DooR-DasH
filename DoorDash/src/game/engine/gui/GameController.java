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
import java.util.Random;

public class GameController {

    // 1. Link the GridPane from your FXML to the controller
    @FXML
    private GridPane boardGrid;
    private String[][] spriteGrid = new String[10][10];
    

    private Game gameEngine;

    public void setGameEngine(Game gameEngine) {
        this.gameEngine = gameEngine;
        initializeBoardGraphics();
        //updateBoardGraphics(); 
    }

    private void initializeBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        Random random = new Random();
        
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                
                String imageName = null;
                Cell currentCell = cellArray[i][j];

                if (currentCell instanceof DoorCell) {
                    int randomDoor = random.nextInt(1) + 1;
                	imageName = "doorinactive"+ randomDoor +".png";
                    if(i==9 && j==0) imageName = "booinactive.png";
     
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
                    setSprite(imageName, i, j); 
                }
            }
        }
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
                		imageName = setDoorActive(getSpriteName(i,j));
                    	if(i==9 && j==0)imageName = "booactive.png";
                	}
                	else
                	{
                		imageName = getSpriteName(i,j);
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
                    setSprite(imageName, i, j); 
                }
            }
        }
    }

    // 3. Update method signature to accept row (i) and column (j)
    private String setDoorActive(String doorName){
    	if (doorName.length() == 13){
    		String doorNumber = doorName.charAt(12) + "";
    		return "dooractive" +  doorNumber;
    	}
    	return doorName;
    	
    }
    
    private String getSpriteName(int i, int j){
    	return spriteGrid[i][j];
    }
    
    private void setSprite(String imageName, int i, int j) {
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
            spriteGrid[i][j] = imageName;
            
        } catch (NullPointerException e) {
            System.out.println("Error: Could not load image -> " + imageName);
        }
    }

    @FXML
    public void onQuestionMarkClicked(ActionEvent event) {
        System.out.println("Help button clicked in the game!");
        InstructionsController.openInstructions();
    }
}