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
	
		
	public void executePowerupEffect(Monster opponentMonster) {
		int energyFromOpponent;
		if(opponentMonster.isShielded())
			energyFromOpponent = 0;
		else
			energyFromOpponent = stealEnergyFrom(opponentMonster);
		
		opponentMonster.alterEnergy(-energyFromOpponent);
		int totalEnergy = energyFromOpponent;
		
		for(Monster m: Board.getStationedMonsters()) {
			int stolenAmount = stealEnergyFrom(m);
			totalEnergy+= stolenAmount;
			m.alterEnergy(-stolenAmount);
		}
		this.alterEnergy(totalEnergy);
	}
}
