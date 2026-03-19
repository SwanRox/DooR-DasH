package game.engine.exceptions;

import java.io.IOException;

public class InvalidCSVFormat extends IOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 //Attributes
	 static final String MSG = "Invalid input detected while reading csv file, input = \n";
	 String inputLine; //A variable representing the csv file line. This attribute is READ AND WRITE.
	 
	//Constructors
	 public InvalidCSVFormat(String inputLine){
		 this.inputLine = inputLine;
		 super(MSG + inputLine);
	 }
	 
	 public InvalidCSVFormat(String message, String inputLine){
		 this.inputLine = inputLine;
		 super(message + inputLine);
	 }
	 
	 // InputLine
	 public String getInputLine() {
		 return inputLine;
	 }
	 
	 public void setInputLine(String inputLine) {
		 this.inputLine = inputLine;
	 }
}
