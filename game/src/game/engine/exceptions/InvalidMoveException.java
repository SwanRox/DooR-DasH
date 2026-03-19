package game.engine.exceptions;

public class InvalidMoveException extends GameActionException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Attributes
	static final String MSG = "Invalid move attempted";
	
	//Constructors
	public InvalidMoveException(){
		super(MSG);
	}
	
	public InvalidMoveException(String message){
		super(message);
	}
}
