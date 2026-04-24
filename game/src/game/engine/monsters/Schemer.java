package game.engine.monsters;

import game.engine.*;

public class Schemer extends Monster {
	
	//Constructors
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	//Methods
	private int stealEnergyFrom(Monster target) {
		if(Constants.SCHEMER_STEAL > target.getEnergy())
			return target.getEnergy();
		else
			return Constants.SCHEMER_STEAL;
	}
		
	public void executePowerupEffect(Monster opponentMonster) {
		
	}
}
