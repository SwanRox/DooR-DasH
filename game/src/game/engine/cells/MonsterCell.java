package game.engine.cells;

import game.engine.Board;
import game.engine.cards.Card;
import game.engine.monsters.Monster;

public class MonsterCell extends Cell {
	
	//Attributes
	private Monster cellMonster; //The monster stationed at this cell. This attribute is READ ONLY.
	
	//Constructors
	 public MonsterCell(String name, Monster cellMonster) {
		 super(name);
		 this.cellMonster = cellMonster;
	 }
	
	//Getters for read-only fields
	public Monster getCellMonster() {
		return cellMonster;
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		this.setMonster(landingMonster);
		if(landingMonster.getRole() == this.getCellMonster().getRole())
			landingMonster.executePowerupEffect(opponentMonster);
		else if(landingMonster.getEnergy()>this.getCellMonster().getEnergy()) {
			int energyDifference = landingMonster.getEnergy() - this.getCellMonster().getEnergy();
			if(landingMonster.isShielded() == false)
				landingMonster.alterEnergy(-energyDifference);
			this.getCellMonster().alterEnergy(energyDifference);
		}
	}
}
