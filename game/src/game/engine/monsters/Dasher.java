package game.engine.monsters;

import game.engine.Role;

public class Dasher extends Monster {
	
	//Attributes
	private int momentumTurns;	//An integer representing the number of turns the Dasher will be having momentum. This attribute is READ AND WRITE.
	
	//Constructors
	public Dasher(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.momentumTurns = 0;
	}
	
	//MomentumTurns
	public int getMomentumTurns() {
		return momentumTurns;
	}
	
	public void setMomentumTurns(int momentumTurns) {
		this.momentumTurns = momentumTurns;
	}
}
