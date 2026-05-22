package game.engine.gui;

import game.engine.Constants;
import game.engine.Game;
import game.engine.Board; // <--- ADDED EXPLICIT BOARD IMPORT
import game.engine.cards.Card;
import game.engine.cells.CardCell;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.cells.MonsterCell;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane; 
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

import java.io.IOException;
import java.util.Random;

public class GameController {

    @FXML
    private Text gameInfo;
    @FXML
    private Text playerStats;
    @FXML
    private Text opponentStats;
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
    
    @FXML
    private ImageView diceImageView;
    private Image[] diceFaces = new Image[6];

    private String[][] originalSpriteGrid = new String[10][10];
    private String[][] spriteGrid = new String[10][10];
    
    private boolean pShieldBroken = false;
    private boolean pShieldUp = false;
    private boolean oShieldBroken = false;
    private boolean oShieldUp = false;

    private Game gameEngine;

    private String opponentSprite;
    private String playerSprite;
    private Monster opponent;
    private Monster player;
    private String playerType;
    private String opponentType;

    @FXML
    public void initialize() {
        for (int i = 0; i < 6; i++) {
            try {
                String path = "assets/dice" + (i + 1) + ".png"; 
                diceFaces[i] = new Image(getClass().getResourceAsStream(path));
            } catch (NullPointerException e) {
                System.out.println("Error: Could not load dice image -> dice" + (i + 1) + ".png");
            }
        }
        if (diceFaces[0] != null && diceImageView != null) {
            diceImageView.setImage(diceFaces[0]); 
        }
    }
    
    public void setGameEngine(Game gameEngine) {
        this.gameEngine = gameEngine;

        this.opponent = gameEngine.getOpponent();
        this.player = gameEngine.getPlayer();
        this.opponentSprite = opponent.getName() + ".png";
        this.playerSprite = player.getName() + ".png";
        
        if(player instanceof Dasher) playerType = "Dasher";
        else if(player instanceof Dynamo) playerType = "Dynamo";
        else if(player instanceof MultiTasker) playerType = "MultiTasker";
        else if(player instanceof Schemer) playerType = "Schemer";
        if(opponent instanceof Dasher) opponentType = "Dasher";
        else if(opponent instanceof Dynamo) opponentType = "Dynamo";
        else if(opponent instanceof MultiTasker) opponentType = "MultiTasker";
        else if(opponent instanceof Schemer) opponentType = "Schemer";

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
    
    private void invalidActionPopup(String message){
        javafx.stage.Stage popup = new javafx.stage.Stage();
        popup.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
        
        if (boardGrid.getScene() != null && boardGrid.getScene().getWindow() != null) {
            popup.initOwner(boardGrid.getScene().getWindow()); 
        }
        
        // FIX: Disables the 'X' button to force players to use your Close button
        popup.setOnCloseRequest(e -> e.consume());
        
        popup.setTitle("Invalid action!");

        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(20);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        
        javafx.scene.control.Label title = new javafx.scene.control.Label("Invalid action: " + message);
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        javafx.scene.control.Button returnBtn = new javafx.scene.control.Button("Close");
        returnBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        returnBtn.setOnAction(e -> {
            popup.close(); 
        });
        
        layout.getChildren().addAll(title, returnBtn);
        javafx.scene.Scene scene = new javafx.scene.Scene(layout, 800, 200);
        popup.setScene(scene);
        popup.showAndWait();
    }
    
    private void displayCard(Card card){
        javafx.stage.Stage popup = new javafx.stage.Stage();
        popup.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
        
        if (boardGrid.getScene() != null && boardGrid.getScene().getWindow() != null) {
            popup.initOwner(boardGrid.getScene().getWindow()); 
        }
        
        // FIX: Disables the 'X' button to force players to use your Close button
        popup.setOnCloseRequest(e -> e.consume());
        
        popup.setTitle(card.getName());

        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(20);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        
        javafx.scene.control.Label title = new javafx.scene.control.Label(card.getName()+"\nCard action: " + card.getDescription());
        title.setStyle("-fx-font-size: 24px; -fx-font-family:Cambria; -fx-font-weight:bold; -fx-text-alignment:center;");
        
        javafx.scene.control.Button returnBtn = new javafx.scene.control.Button("Close");
        returnBtn.setStyle("-fx-font-size: 20px; -fx-padding: 10px; -fx-font-family:'Berlin Sans FB'; -fx-background-radius:100px;");
        returnBtn.setOnAction(e -> {
            popup.close(); 
        });
        
        layout.getChildren().addAll(title, returnBtn);
        javafx.scene.Scene scene = new javafx.scene.Scene(layout, 800, 200);
        popup.setScene(scene);
        popup.showAndWait();
    }
    
    private int rowColToIndex(int i, int j) {
        if (i % 2 == 1) {
            return (i * 10) + (9 - j);
        } else {
            return (i * 10) + j;
        }
    }
    
    private void initializeBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        Random random = new Random();
        updateGameInfo();
        
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                
                String imageName = null;
                Cell currentCell = cellArray[i][j];
                
                if (currentCell instanceof DoorCell) {
                    int randomDoor = random.nextInt(10); 
                    imageName = "door"+ randomDoor +"inactive.png";
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
                else { imageName = "empty.png"; }
                
                if (imageName != null) {
                    setOriginalSprite(imageName, i, j); 
                }
                
                if (currentCell instanceof DoorCell) {
                    int energyAmount = (int) ((DoorCell) currentCell).getEnergy(); 
                    Label energyLabel = new Label(energyAmount + "E");
                    
                    energyLabel.setStyle("-fx-text-fill: #8b0000; -fx-font-weight: bold; -fx-padding: 2px; -fx-font-size: 11px;");
                    
                    GridPane.setHalignment(energyLabel, HPos.RIGHT);
                    GridPane.setValignment(energyLabel, VPos.BOTTOM);
                    
                    int visualRow = 9 - i;
                    boardGrid.add(energyLabel, j, visualRow);
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
        
        drawCellIndices();
    }
    
    private void updateEnergyProgress(){
        double playerEnergy = player.getEnergy();
        double opponentEnergy = opponent.getEnergy();
        playerProgressBar.setProgress(playerEnergy/1000);
        opponentProgressBar.setProgress(opponentEnergy/1000);
        playerLabel.setText((int)playerEnergy+"");
        opponentLabel.setText((int)opponentEnergy+"");
    }
    
    private void drawCellIndices() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int cellNumber = rowColToIndex(i, j);
                Label indexLabel = new Label(String.valueOf(cellNumber));
                
                indexLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 2px; -fx-font-size: 13px;");
                
                GridPane.setHalignment(indexLabel, HPos.LEFT);
                GridPane.setValignment(indexLabel, VPos.TOP);
                
                int visualRow = 9 - i;
                boardGrid.add(indexLabel, j, visualRow);
            }
        }
    }
    
    private void updateGameInfo() {
        if (player.isShielded() == true) pShieldUp = true;
        if (opponent.isShielded() == true) oShieldUp = true;
        if (pShieldUp == true && player.isShielded() == false) { pShieldBroken = true; pShieldUp = false; }
        if (oShieldUp == true && opponent.isShielded() == false) { oShieldBroken = true; oShieldUp = false; }

        String upNextSide = (gameEngine.getCurrent() == player) ? "(PLAYER)" : "(OPPONENT)";
        
        gameInfo.setText("Last Action: " + gameEngine.getLastAction() + "\n" +
                         "Up Next: " + upNextSide + " " + gameEngine.getCurrent().getOriginalRole() + "'s turn!");
                         
        String playerConfused = (!player.isConfused()) ? "" : "Player is confused for " + player.getConfusionTurns() + " more turns";
        String opponentConfused = (!opponent.isConfused()) ? "" : "Opponent is confused for " + opponent.getConfusionTurns() + " more turns";
        String playerFrozen = (!player.isFrozen()) ? "" : "Player is frozen!";
        String opponentFrozen = (!opponent.isFrozen()) ? "" : "Opponent is frozen!";
        String playerShielded = (!player.isShielded()) ? "" : "Player is shielded!";
        String opponentShielded = (!opponent.isShielded()) ? "" : "Opponent is shielded!";
        
        if (pShieldBroken) { playerShielded = "Player's shield was broken!"; pShieldBroken = false; }
        if (oShieldBroken) { opponentShielded = "Opponent's shield was broken!"; oShieldBroken = false; }
        
        playerStats.setText(
                "Name: " + player.getName() + " (PLAYER)\n" 
                +"Original Role: " + player.getOriginalRole() + "\n"
                +"Current Role: " + player.getRole() + "\n"
                +"Type: " + playerType + "\n"
                +"Energy: " + player.getEnergy() + "\n"
                +"Position: " + player.getPosition() + "\n"
                +playerShielded + "\n"
                +playerConfused + "\n"
                +playerFrozen + "\n"
                );
                
        opponentStats.setText(
                "Name: " + opponent.getName() + " (OPPONENT)\n"
                +"Original Role: " + opponent.getOriginalRole() + "\n"
                +"Current Role: " + opponent.getRole() + "\n"
                +"Type: " + opponentType + "\n"
                +"Energy: " + opponent.getEnergy() + "\n"
                +"Position: " + opponent.getPosition() + "\n"
                +opponentShielded + "\n"
                +opponentConfused + "\n"
                +opponentFrozen + "\n"
                );
    }
    
    private void updateBoardGraphics() {
        Cell[][] cellArray = gameEngine.getBoard().getBoardCells();
        boardGrid.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label);
        updateEnergyProgress();
        updateGameInfo();
        
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
                    setSprite(imageName, i, j); 
                }
                
                if (currentCell instanceof DoorCell) {
                    int energyAmount = (int) ((DoorCell) currentCell).getEnergy(); 
                    Label energyLabel = new Label(energyAmount + "E");
                    
                    energyLabel.setStyle("-fx-text-fill: #8b0000; -fx-font-weight: bold; -fx-padding: 2px; -fx-font-size: 11px;");
                    
                    GridPane.setHalignment(energyLabel, HPos.RIGHT);
                    GridPane.setValignment(energyLabel, VPos.BOTTOM);
                    
                    int visualRow = 9 - i;
                    boardGrid.add(energyLabel, j, visualRow);
                }
            }
        }
        
        int[] playerIndex = indexToRowCol(player.getPosition());
        int[] opponentIndex = indexToRowCol(opponent.getPosition());
        
        setSprite(playerSprite,playerIndex[0],playerIndex[1]);
        setSprite("playerborder.png",playerIndex[0],playerIndex[1]);
        
        if (player.isShielded())setSprite("shielded2.png",playerIndex[0],playerIndex[1]);
        if (player.isFrozen())setSprite("frozen2.png",playerIndex[0],playerIndex[1]);
        if (player.isConfused())setSprite("confused2.png",playerIndex[0],playerIndex[1]);
        
        setSprite(opponentSprite,opponentIndex[0],opponentIndex[1]);
        setSprite("opponentborder.png",opponentIndex[0],opponentIndex[1]);
        
        if (opponent.isShielded())setSprite("shielded2.png",opponentIndex[0],opponentIndex[1]);
        if (opponent.isFrozen())setSprite("frozen2.png",opponentIndex[0],opponentIndex[1]);
        if (opponent.isConfused())setSprite("confused2.png",opponentIndex[0],opponentIndex[1]);
        
        if (gameEngine.getWinner() != null) {
            Monster winner = gameEngine.getWinner();
            
            javafx.stage.Stage popup = new javafx.stage.Stage();
            popup.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
            if (boardGrid.getScene() != null && boardGrid.getScene().getWindow() != null) {
                popup.initOwner(boardGrid.getScene().getWindow()); 
            }
            
            // FIX: Disables the 'X' button to force players to transition to the main menu!
            popup.setOnCloseRequest(e -> e.consume());
            
            popup.setTitle("Game Over!");

            javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(20);
            layout.setAlignment(javafx.geometry.Pos.CENTER);
            
            javafx.scene.control.Label title = new javafx.scene.control.Label("Winner: " + winner.getName() + "!");
            title.setStyle("-fx-font-size: 33px; -fx-font-family:'Berlin Sans FB'");
            
            javafx.scene.control.Label details = new javafx.scene.control.Label(
                "Role: " + winner.getOriginalRole() + "\n\n" +
                "Final Scores:\n" +
                player.getName() + " Energy: " + player.getEnergy() + "\n" +
                opponent.getName() + " Energy: " + opponent.getEnergy()
            );
            details.setStyle("-fx-font-size: 26; -fx-alignment: center; -fx-text-alignment: center; -fx-font-family:'Cambria'");
            
            javafx.scene.control.Button returnBtn = new javafx.scene.control.Button("Return to Main Menu");
            returnBtn.setStyle("-fx-font-size: 30px; -fx-padding: 10px; -fx-font-family:'Berlin Sans FB'; -fx-background-radius:100px;");
            returnBtn.setOnAction(e -> {
                popup.close(); 
                
                try {
                    javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
                    javafx.stage.Stage mainStage = (javafx.stage.Stage) boardGrid.getScene().getWindow();
                    mainStage.setScene(new javafx.scene.Scene(root, 1280, 800));
                    Controller.sounds.playMenuMusic();
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            layout.getChildren().addAll(title, details, returnBtn);
            javafx.scene.Scene scene = new javafx.scene.Scene(layout, 500, 400);
            popup.setScene(scene);
            popup.showAndWait();
        }
        
        drawCellIndices();
    }

    private String setDoorActive(int i, int j){
            String doorNumber = originalSpriteGrid[i][j].charAt(4) + "";
            return "door" +  doorNumber + "active.png";
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
            
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);
            
            int visualRow = 9 - i; 
            
            boardGrid.add(imageView, j, visualRow);
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
            
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);
            
            int visualRow = 9 - i; 
            
            boardGrid.add(imageView, j, visualRow);
            spriteGrid[i][j] = imageName;
            
        } catch (NullPointerException e) {
            System.out.println("Error: Could not load image -> " + imageName);
        }
    }

    @FXML
    public void onQuestionMarkClicked(ActionEvent event) {
        InstructionsController.openInstructions();
    }
    
    @FXML
    public void onReturnButtonClicked(ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 1280, 800));
            stage.show();
            Controller.sounds.playMenuMusic();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
        
    @FXML
    public void onPowerup(ActionEvent event) throws OutOfEnergyException {
        try {
            gameEngine.usePowerup();
            updateBoardGraphics();
        }
        catch(OutOfEnergyException e) {
            invalidActionPopup(e.getMessage());
        }
    }
   
    @FXML
    public void onRollDice(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setDisable(true);

        Timeline rollAnimation = new Timeline(new KeyFrame(Duration.millis(80), e -> {
            int randomVisualFace = (int) (Math.random() * 6);
            if (diceFaces[randomVisualFace] != null) {
                diceImageView.setImage(diceFaces[randomVisualFace]);
            }
        }));

        rollAnimation.setCycleCount(15);
        
        rollAnimation.setOnFinished(e -> {
            
            boolean isInvalid = false;
            String invalidMsg = "";

            try {
                gameEngine.playTurn();
            } catch (InvalidMoveException ex) {
                isInvalid = true;
                invalidMsg = ex.getMessage();
            }

            try {
                int finalRoll = 1; 
                String actionStr = gameEngine.getLastAction();
                if (actionStr != null) {
                    int idx = actionStr.toLowerCase().indexOf("rolled a ");
                    if (idx != -1 && idx + 9 < actionStr.length()) {
                        char rollChar = actionStr.charAt(idx + 9);
                        if (rollChar >= '1' && rollChar <= '6') {
                            finalRoll = rollChar - '0';
                        }
                    }
                }
                
                if (diceFaces[finalRoll - 1] != null) {
                    diceImageView.setImage(diceFaces[finalRoll - 1]);
                }

                updateBoardGraphics();

                // FIX: Used explicit Board class call and a Try/Finally block 
                // to guarantee memory clearing even if window closure fails.
                Card drawnCard = Board.getLastDrawnCard(); 
                if (drawnCard != null) {
                    try {
                        displayCard(drawnCard);
                    } finally {
                        Board.clearLastDrawnCard(); 
                    }
                }

                if (isInvalid) {
                    invalidActionPopup(invalidMsg);
                }
            } finally {
                btn.setDisable(false);
            }
        });

        rollAnimation.play();
    }

    @FXML
    public void onSettingsButtonClicked(ActionEvent event){
        SettingsController.openSettings();
    }
}