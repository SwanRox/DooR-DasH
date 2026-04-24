package game.engine.monsters;

import game.engine.Role;

public class Dynamo extends Monster{
	
	//Constructors
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	//Methods
	public void executePowerupEffect(Monster opponentMonster) {
		opponentMonster.setFrozen(true);
	}
	
    public void alterEnergy(int energy) {
    	if (this.isShielded() && energy < 0)
    		this.setShielded(false);
    	else 
    		this.setEnergy(this.getEnergy() + (energy * 2));
    }
}
