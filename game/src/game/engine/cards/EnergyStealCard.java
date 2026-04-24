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
		if(opponent.getEnergy()<this.energy){
			player.setEnergy(player.getEnergy() + opponent.getEnergy());
			opponent.setEnergy(0);
		}
		player.setEnergy(player.getEnergy()+this.energy);
		opponent.setEnergy(opponent.getEnergy()-this.energy);
	}
}
