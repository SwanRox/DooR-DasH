package game.engine.cells;

public class Cell {
	
	//Attriubutes
	private String name; //A string representing the name of the cell. This attribute is READ ONLY.
	private Monster monster; //A Monster object representing the monster currently landed this cell. This attribute is READ AND WRITE

	//Constructors
	 public Cell(String name){
		 this.name = name;
		 this.monster = null;
	 }
	
	//Getters for read-only fields
	public String getName() {
		return name;
	}
	
	//Monster
	public Monster getMonster() {
		return monster;
	}
	
	public void setMonster(Monster monster) {
		this.monster = monster;
	}
}
