package game.engine.cards;

public abstract class Card {
	
	//Attributes
	private String name;
	private String description;
	private int rarity;
	private boolean lucky;
	 
	 //Constructors
	 public Card(String name, String description, int rarity, boolean lucky) {
		 this.name = name;
		 this.description = description;
		 this.rarity = rarity;
		 this.lucky = lucky;
	 }
	 
	 // Getters for read-only fields
	 public String getName() {
		 return name;
	 }
	 
	 public String getDescription() {
		 return description;
	 }
	 
	 public int getRarity() {
		 return rarity;
	 }
	 
	 public boolean isLucky() {
		 return lucky;
	 }
}
