package game.engine.cards;

public class EnergyStealCard extends Card{
	
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
}
