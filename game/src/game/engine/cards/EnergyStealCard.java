package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier{
	
	//Attributes
	private int energy;
	
	//Constructors
	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description,  rarity, true);
		this.energy = energy;
	}
	
	 // Getters for read-only fields
	public int getEnergy() {
		return energy;
	}
	
	public void performAction(Monster player, Monster opponent){
		if(opponent.isShielded()) {
			opponent.setShielded(false);
			return;
		}
		if(opponent.getEnergy()<this.getEnergy()){
			player.alterEnergy(opponent.getEnergy());
			opponent.alterEnergy(-opponent.getEnergy());
		}else {
			player.alterEnergy(this.getEnergy());
			opponent.alterEnergy(-this.getEnergy());
		}
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	}
}
