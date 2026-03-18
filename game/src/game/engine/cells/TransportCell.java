package game.engine.cells;

public abstract class TransportCell extends Cell {
	
	//Attributes
	private int effect; //The transport effect value (positive for forward, negative for backward). This attribute is READ ONLY.

	//Constructors
	public TransportCell(String name, int effect) {
		super(name);
		this.effect = effect;
	}
	
	//Getters for read-only fields
	public int getEffect() {
		return effect;
	}
}
