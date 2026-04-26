package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.monsters.*;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
		setCardsByRarity();
		reloadCards();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	
//<<<<<<< HEAD
//<<<<<<< HEAD
//=======
//>>>>>>> ea1a9aeec0e430835499029a6cacac2c05e54b14
	private int[] indexToRowCol(int index) {
		int row = index/10;
		int column;
		if(row%2 == 1)
			column = 9-index%10;
		else
			column = index%10;
		
		int[] a = {row,column};
		return a;
	}
	
	private Cell getCell(int index) {
		int[] RowCol = indexToRowCol(index);
		int Row = RowCol[0];
		int Col = RowCol[1];
		return this.boardCells[Row][Col];
	}
	
	private void setCell(int index, Cell cell) {
		int[] RowCol = indexToRowCol(index);
		int Row = RowCol[0];
		int Col = RowCol[1];
		this.boardCells[Row][Col] = cell;
	}
	
	public void initializeBoard(ArrayList<Cell> specialCells) {
		int c = 0;
		int s = 0;
		for(int i = 1, j = 0; i<Constants.BOARD_SIZE;i+=2, j++) 
			setCell(i, specialCells.get(j));
		
		for(int x:Constants.CONVEYOR_CELL_INDICES) {
			setCell(x, specialCells.get(50 + c));
			c+=2;
		}
		
		for(int x:Constants.SOCK_CELL_INDICES) {
			setCell(x, specialCells.get(51 + s));
			s+=2;
		}
		
		for(int x:Constants.CARD_CELL_INDICES) {
			setCell(x, new CardCell("Card Cell"));
		}
		
		for(int x:Constants.MONSTER_CELL_INDICES) {
			setCell(x, new MonsterCell("MonsterCell", null));
		}
		
		for(int i = 0; i<Constants.BOARD_SIZE; i+=2){
			if(getCell(i) == null) 
				setCell(i, new Cell("Rest Cell"));
		}
		
		
		//for(int i = 0; i<Constants.BOARD_SIZE; i++)
		//	System.out.println(getCell(i).getName());
	}
	
	private void setCardsByRarity() {
		ArrayList<Card> a = new ArrayList<>();
		for(Card c: getOriginalCards()) {
			for(int i = 0; i<c.getRarity(); i++)
				a.add(c);
		}
		originalCards = a;
	}
	
	public static void reloadCards() {
		cards = getOriginalCards();
		Collections.shuffle(cards);
	}
	
	public static Card drawCard() {
		if(cards.size() == 0)
			reloadCards();
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
	
	public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException {
	   
	    int originalPosition = currentMonster.getPosition();
	    int originalEnergy = currentMonster.getEnergy();
	    boolean originalShield = currentMonster.isShielded();
	    
	  
	    currentMonster.move(roll);
	    Cell landedCell = getCell(currentMonster.getPosition());
	    landedCell.onLand(currentMonster, opponentMonster);
	    
	    
	    if (currentMonster.getPosition() == opponentMonster.getPosition()) {
	        
	        currentMonster.setPosition(originalPosition);
	        currentMonster.setEnergy(originalEnergy);
	        currentMonster.setShielded(originalShield);
	        
	        this.updateMonsterPositions(currentMonster, opponentMonster);
	        throw new InvalidMoveException();
	    }
	    
	   
	    currentMonster.decrementConfusion(); 
	    opponentMonster.decrementConfusion();
	    
	    this.updateMonsterPositions(currentMonster, opponentMonster);
	}
	
	private void updateMonsterPositions(Monster player, Monster opponent) {
		for(int i = 0;i<Constants.BOARD_SIZE;i++) {
			Cell c = this.getCell(i);
			c.setMonster(null);
		}
		Cell playerCell = this.getCell(player.getPosition());
		playerCell.setMonster(player);
		Cell opponentCell = this.getCell(opponent.getPosition());
		opponentCell.setMonster(opponent);
	}




}
