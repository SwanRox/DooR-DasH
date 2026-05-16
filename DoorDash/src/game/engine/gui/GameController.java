package game.engine.gui;

import game.engine.Constants;
import game.engine.Game;
import game.engine.cells.CardCell;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.cells.MonsterCell;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.Monster;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane; // Make sure to import this!
import javafx.geometry.HPos;
import javafx.geometry.VPos;

import java.util.Random;

public class GameController {

    // 1. Link the GridPane from your FXML to the controller
	@FXML
    private Label playerLabel;
	@FXML
    private Label opponentLabel;
	@FXML
    private ProgressBar playerProgressBar;
	@FXML
    private ProgressBar opponentProgressBar;
    @FXML
    private GridPane boardGrid;
    private String[][] originalSpriteGrid = new String[10][10];
    private String[][] spriteGrid = new String[10][10];

    private Game gameEngine;

    private String opponentSprite;
    private String playerSprite;
    private Monster opponent;
    private Monster player;

    public void setGameEngine(Game gameEngine) {
        this.gameEngine = gameEngine;

        // 2. ASSIGN the values here, because gameEngine actually exists now!
        this.opponent = gameEngine.getOpponent();
        this.player = gameEngine.getPlayer();
        this.opponentSprite = opponent.getName() + ".png";
        this.playerSprite = player.getName() + ".png";

        initializeBoardGraphics();
    }

	private int[] indexToRowCol(int index) {
	    int cols = Constants.BOARD_COLS;

	    int row = index / cols;
	    int col = index % cols;

	    if (row % 2 == 1)
	        col = cols - 1 - col;

	    return new int[]{row, col};
	}
    
    private void initializeBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        Random random = new Random();
        
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                
                String imageName = null;
                Cell currentCell = cellArray[i][j];

                if (currentCell instanceof DoorCell) {
                    int randomDoor = random.nextInt(1) + 1; //change this number to 8
                	imageName = "doorinactive"+ randomDoor +".png";
                    if(i==9 && j==0) imageName = "booinactive.png";
     
                } else if (currentCell instanceof ConveyorBelt) {
                    imageName = "conveyor.png"; 
                } else if (currentCell instanceof ContaminationSock) {
                    imageName = "sock.png"; 
                } else if (currentCell instanceof CardCell) {
                	imageName = "card.png"; 
                } else if (currentCell instanceof MonsterCell) {
                	imageName = currentCell.getName() + ".png";
                }
                else{imageName = "empty.png";}
                

                if (imageName != null) {
                    // 2. Pass i (row) and j (column) to the method
                    setOriginalSprite(imageName, i, j); 
                }
                
                
            }
        }
        
        setOriginalSprite(opponentSprite, 0, 0 );
        setOriginalSprite(playerSprite, 0, 0 );
 
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                spriteGrid[i][j] = originalSpriteGrid[i][j];
            }
        }

        setSprite(opponentSprite, 0, 0 );
        setSprite(playerSprite, 0, 0 );
    }
    
    private void updateEnergyProgress(){
    	double playerEnergy = player.getEnergy();
    	double opponentEnergy = opponent.getEnergy();
    	playerProgressBar.setProgress(playerEnergy/1000);
    	opponentProgressBar.setProgress(opponentEnergy/1000);
    	playerLabel.setText((int)playerEnergy+"");
    	opponentLabel.setText((int)opponentEnergy+"");
    	
    }
    
    private void updateBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        boardGrid.getChildren().removeIf(node -> node instanceof ImageView);
        updateEnergyProgress();
        
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
                		System.out.println("Door activated");
                		if(!((DoorCell) currentCell).isOccupied()){
                			imageName = setDoorActive(i,j);
                    		if(i==9 && j==0)imageName = "booactive.png";
                		}
                	}
                	else
                	{
                		System.out.println("Door is inactivate");
                		imageName = getOriginalSpriteName(i,j);
                    	if(i==9 && j==0)imageName = "booinactive.png";
                	}

                } else if (currentCell instanceof ConveyorBelt) {
                    imageName = "conveyor.png"; 
                } else if (currentCell instanceof ContaminationSock) {
                    imageName = "sock.png"; 
                } else if (currentCell instanceof CardCell) {
                	imageName = "card.png"; 
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
        
        int[] playerIndex = indexToRowCol(player.getPosition());
        int[] opponentIndex = indexToRowCol(opponent.getPosition());
        setSprite(playerSprite,playerIndex[0],playerIndex[1]);
        setSprite(opponentSprite,opponentIndex[0],opponentIndex[1]);
    }


    // 3. Update method signature to accept row (i) and column (j)
    private String setDoorActive(int i, int j){
    		System.out.println(originalSpriteGrid[i][j]);
    		String doorNumber = originalSpriteGrid[i][j].charAt(12) + "";
    		doorNumber="1"; //temporary until we add more icons for doors
    		return "dooractive" +  doorNumber + ".png";
    }
    
    private String getOriginalSpriteName(int i, int j){
    	return originalSpriteGrid[i][j];
    }
    
    private void setOriginalSprite(String imageName, int i, int j) {
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
            System.out.println("Original sprite changed to "+ imageName);
            originalSpriteGrid[i][j] = imageName;

        } catch (NullPointerException e) {
            System.out.println("Error: Could not load image -> " + imageName);
        }
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
    @FXML
    public void onPowerup(ActionEvent event) throws OutOfEnergyException{gameEngine.usePowerup();
    updateEnergyProgress();}
   
    public void onRollDice(ActionEvent event) throws InvalidMoveException{gameEngine.playTurn();
    	updateBoardGraphics();}
    }
