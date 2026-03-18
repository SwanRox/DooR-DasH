package game.engine.monsters;

import game.engine.Role;

public class Monster {
	
	//Attributes
    private final String name; //A string representing the monster’s name. This attribute is READ ONLY.
    private final String description; //A string representing the description of the monster’s special power. This attribute is READ ONLY.
    private Role role;	//An enum representing the role of the monster (SCARER or LAUGHER).
    private final Role originalRole; //An enum representing the original role of the monster (SCARER or LAUGHER).This attribute is READ ONLY.
    private int energy;	//An integer representing the amount of energy the monster has collected. (>= 0)
    private int position;	//An integer representing the monster’s current position on the board (0-99).
    private boolean frozen;	//A boolean indicating whether the monster is currently frozen.
    private boolean shielded;	//A boolean indicating whether the monster is currently shielded.
    private int confusionTurns;	//An integer representing the number of turns the monster will be confused (becomes the other role)

    //Constructors
    public Monster(String name, String description, Role originalRole, int energy) {
    	this.name = name;
    	this.description = description;
    	this.role = originalRole;
    	this.originalRole = originalRole;
    	this.energy = energy;
    	this.position = 0;
    	this.frozen = false;
    	this.shielded = false;
    	this.confusionTurns = 0;
    }
    
    
    
    // Getters for read-only fields
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Role getOriginalRole() {
        return originalRole;
    }

    // Role
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Energy
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    // Position
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // Frozen
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    // Shielded
    public boolean isShielded() {
        return shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    // Confusion Turns
    public int getConfusionTurns() {
        return confusionTurns;
    }

    public void setConfusionTurns(int confusionTurns) {
        this.confusionTurns = confusionTurns;
    }
    
    //overrides
    public int compareTo(Monster o) {
    	return this.position - o.position;
    }
}
