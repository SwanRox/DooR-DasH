package game.engine.cells;

import game.engine.Board;
import game.engine.cards.Card;
import game.engine.monsters.Monster;

public class CardCell extends Cell {
	
	//Constructors
	 public CardCell(String name){
		 super(name);
	 }
	 
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		this.setMonster(landingMonster);
		Card c = Board.drawCard();
		c.performAction(landingMonster, opponentMonster);
	}
}
