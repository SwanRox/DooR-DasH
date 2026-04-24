package game.engine.monsters;

import game.engine.*;
import game.engine.exceptions.OutOfEnergyException;

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
	

	    
	public void alterEnergy(int energy) {
	    if (this.isShielded() && energy < 0)
	    	this.setShielded(false);
	    else 
	    	this.setEnergy(this.getEnergy() + energy + 10);
	}
		
	public void executePowerupEffect(Monster opponentMonster) {
		
	}
}
