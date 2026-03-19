package game.engine.dataloader;

import java.io.*;
import java.util.*;

import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;
import game.engine.Role;

public class DataLoader {
	 
	 static final private String CARDS_FILE_NAME = "cards.csv"; //A String containing the name of the card’s csv file.
	 static final private String CELLS_FILE_NAME = "cells.csv"; //A String containing the name of the cell’s csv file.
	 static final private String MONSTERS_FILE_NAME = "monsters.csv"; //A String containing the name of the monster’s csv file.
	 
	 //Methods
	 public static ArrayList<Card> readCards() throws IOException {
		 ArrayList<Card> cards = new ArrayList<>();
		 try (BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE_NAME))){
			 String line;
			 while ((line = br.readLine()) != null) {
				 String[] values = line.split(",");
				 String classType = values[0];
				 String name = values[1];
				 String description = values[2];
				 int rarity = Integer.parseInt(values[3]);
				 switch(classType) {
             		case "CONFUSION":
             			int duration = Integer.parseInt(values[4]);
             			cards.add(new ConfusionCard(name, description, rarity, duration)); break;
             		case "SWAPPER":
             			cards.add(new SwapperCard(name, description, rarity)); break;
             		case "SHIELD":
             			cards.add(new ShieldCard(name, description, rarity)); break;
             		case "STARTOVER":
             			boolean lucky = Boolean.parseBoolean(values[4]);
             			cards.add(new StartOverCard(name, description, rarity, lucky)); break;
             		case "ENERGYSTEAL":
             			int energy = Integer.parseInt(values[4]);
             			cards.add(new EnergyStealCard(name, description, rarity, energy)); break;
				 }
             }
         }catch (IOException e) {
             e.printStackTrace();
         }
		 return cards;
     }
	 

	 
	 public static ArrayList<Cell> readCells() throws IOException {
		 ArrayList<Cell> cells = new ArrayList<>();
		 try (BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME))){
			 String line;
			 while ((line = br.readLine()) != null) {
				 String[] values = line.split(",");
				 String name = values[0];
				 if (values.length == 3) {
					Role role = Role.valueOf(values[1]);
				 	int energy = Integer.parseInt(values[2]);
				 	cells.add(new DoorCell(name, role, energy));
				 }else if (values.length == 2) {
				 	int effect = Integer.parseInt(values[1]);
				 	if (effect >= 0) {
				 		cells.add(new ConveyorBelt(name, effect));
				 	}else {
				 		cells.add(new ContaminationSock(name, effect));
				 	}
				 }	
			 }
         }catch (IOException e) {
             e.printStackTrace();
         }
		 return cells;
	 }
      
	 
	 public static ArrayList<Monster> readMonsters() throws IOException {
		 ArrayList<Monster> monsters = new ArrayList<>();
		 try (BufferedReader br = new BufferedReader(new FileReader(MONSTERS_FILE_NAME))){
			 String line;
			 while ((line = br.readLine()) != null) {
				 String[] values = line.split(",");
				 if (values.length == 5) {
					 String monsterType = values[0];
					 String name = values[1]; 
					 String description = values[2];
					 Role role = Role.valueOf(values[3]);
					 int energy = Integer.parseInt(values[4]);
					 switch(monsterType) {
					 case "DYNAMO":
						 monsters.add(new Dynamo(name, description, role, energy)); break;
					 case "DASHER":
						 monsters.add(new Dasher(name, description, role, energy)); break;
					 case "MULTITASKER":
						 monsters.add(new MultiTasker(name, description, role, energy)); break;
					 case "SCHEMER":
						 monsters.add(new Schemer(name, description, role, energy)); break;
					 }
				 }
			 }
		 }catch (IOException e) {
             e.printStackTrace();
         }
		 return monsters;
	 }
}
	 

		 
		
		 
