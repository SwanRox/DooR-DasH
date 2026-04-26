package game.engine.cells;

import game.engine.monsters.Monster;

public class ConveyorBelt extends TransportCell {
	
	//Constructors
	public ConveyorBelt(String name, int effect) { //The effect value is always positive.
		super(name, effect);
	}
	
//	public void transport(Monster monster) {
//		
//		monster.move(this.getEffect());
//	}
}
