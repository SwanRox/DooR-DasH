package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	
	//Attributes
	private int normalSpeedTurns;
	
	//Constructors
	public MultiTasker(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.normalSpeedTurns = 0;
	}
	
	//NormalSpeedTurns
	public int getNormalSpeedTurns() {
		return normalSpeedTurns;
	}
	
	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}
	
	//Methods
	 public void move(int distance) {
		 if(this.getNormalSpeedTurns() > 0) {
			 this.setPosition(this.getPosition() + distance);
			 this.setNormalSpeedTurns(this.getNormalSpeedTurns()-1);
		 }else
			 this.setPosition(this.getPosition() + (distance/2));
	 }
	    
	public void executePowerupEffect(Monster opponentMonster) {
		this.setNormalSpeedTurns(2);
	}
}
