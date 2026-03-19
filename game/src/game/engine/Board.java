package game.engine;

import java.util.ArrayList;

import game.engine.cards.Card;
import game.engine.cells.Cell;
import game.engine.monsters.Monster;

public class Board {
	
	private Cell [][] boardCells; // A 2D arrayofcells representing the game board with dimensions BOARD_ROWS x BOARD_COLS

	private static ArrayList<Monster> stationedMonsters; //ArrayList containing monsters stationed on the board at monster cells. This attribute is READ AND WRITE
	private static ArrayList<Card> originalCards; //ArrayList containing the original cards read from CSV.
	public static ArrayList<Card> cards; //ArrayList containing the current available cards. This attribute is READ AND WRITE.
	
	//Constructors
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[10][10];
		stationedMonsters = new ArrayList<>();
		cards = new ArrayList<>();
		originalCards = new ArrayList<>(readCards);
	}
	
	// Getters for read-only fields
	public Cell[][] getBoardCells() {
	    return boardCells;
	}

	public static ArrayList<Card> getOriginalCards(){
		return originalCards;
	}
	
	// Stationed Monsters
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> newStationedMonsters) {
		stationedMonsters = newStationedMonsters;
	}
	
	// cards
	public static ArrayList<Card> getCards(){
		return cards;
	}
	
	public static void setCards(ArrayList<Card> newCards){
		cards = newCards;
	}
}
