package game.engine.cells;

public class DoorCell extends Cell {
	
	//Attriubutes
	Role role; //The role type (SCARER or LAUGHER) that this door supports. This attribute is READ ONLY.
	int energy; //The amount of energy this door provides. This attribute is READ ONLY.
	boolean activated; /*Whether this door has been activated or not. Doors start with deactivated and activates 
						when a monster passes on it. Once its activated, no monster can reuse it. This attribute is READ AND WRITE.*/
	
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
}
