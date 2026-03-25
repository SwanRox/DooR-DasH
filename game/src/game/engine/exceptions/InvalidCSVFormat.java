package game.engine.exceptions;

import java.io.IOException;

public class InvalidCSVFormat extends IOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 //Attributes
	 static final private String MSG = "Invalid input detected while reading csv file, input = \n";
	 static private String inputLine; //A variable representing the csv file line. This attribute is READ AND WRITE.
	 
	//Constructors
	 public InvalidCSVFormat(String newInputLine){
		 super(MSG + newInputLine);
		 inputLine = newInputLine;

	 }
	 
	 public InvalidCSVFormat(String message, String newInputLine){
		 super(message + inputLine);
		 inputLine = newInputLine;
	 }
	 
	 // InputLine
	 public String getInputLine() {
		 return inputLine;
	 }
	 
	 public void setInputLine(String newInputLine) {
		 inputLine = newInputLine;
	 }
}
