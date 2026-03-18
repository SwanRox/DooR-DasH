package game.engine.cards;

public class ConfusionCard extends Card {
	
	//Attributes
	private int duration;
	
	//Constructors
	public ConfusionCard(String name, String description, int rarity, int duration){
		super(name, description, rarity, false);
		this.duration = duration;
	}
	
	// Getters for read-only fields
	public int getDuration() {
		return duration;
	}
}
