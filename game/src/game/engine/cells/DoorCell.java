package game.engine.cells;

import game.engine.Board;
import game.engine.Role;
import game.engine.cards.Card;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier{
	
	//Attributes
	private Role role; //The role type (SCARER or LAUGHER) that this door supports. This attribute is READ ONLY.
	private int energy; //The amount of energy this door provides. This attribute is READ ONLY.
	private boolean activated; //Whether this door has been activated or not. Doors start with deactivated and activates when a monster passes on it. Once its activated, no monster can reuse it. This attribute is READ AND WRITE.
	
	//Constructors
	 public DoorCell(String name, Role role, int energy){
		 super(name);
		 this.role = role;
		 this.energy = energy;
		 this.activated = false;
	 }
	
	//Getters for read-only fields
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	//Activated
	public boolean isActivated() {
		return activated;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		this.setMonster(landingMonster);
		if(this.isActivated())
			return;
		if(this.getRole() == landingMonster.getRole()) {
			landingMonster.alterEnergy(this.getEnergy());
			for(Monster m : Board.getStationedMonsters()) {
				if(m.getRole() == landingMonster.getRole()) {
					m.alterEnergy(getEnergy());
				}
			}
			this.setActivated(true);
		}else if(landingMonster.isShielded()){
			landingMonster.setShielded(false);
		}else {
			landingMonster.alterEnergy(-this.getEnergy());
			for(Monster m : Board.getStationedMonsters()) {
				if(m.getRole() == landingMonster.getRole()) {
					m.alterEnergy(-getEnergy());
				}
			}
			this.setActivated(true);
		}
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		if(monster.getRole() == this.getRole())
			monster.alterEnergy(canisterValue);
		else
			monster.alterEnergy(-canisterValue);
		
	}
}
