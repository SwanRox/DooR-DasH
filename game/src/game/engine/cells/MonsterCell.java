package game.engine.cells;

public class MonsterCell extends Cell {
	
	//Attriubutes
	private Monster cellMonster; //The monster stationed at this cell. This attribute is READ ONLY.
	
	//Constructors
	 public MonsterCell(String name, Monster cellMonster) {
		 super(name);
		 this.cellMonster = cellMonster;
	 }
	
	//Getters for read-only fields
	public Monster getCellMonster() {
		return cellMonster;
	}
}
