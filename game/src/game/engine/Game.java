package game.engine;

import java.io.*;
import java.util.*;

import game.engine.dataloader.DataLoader;
import game.engine.monsters.Monster;

public class Game {
	
	//Attributes
	private Board board; //The game board.
	private ArrayList<Monster> allMonsters; //List of all available monsters read from the CSV
	private Monster player; //The player’s monster.
	private Monster opponent; //The opponent’s monster.
	private Monster current; //The monster whose currently playing the turn. This attribute is READ AND WRITE.
	//Helper Variable
	private static ArrayList<Monster> availableMonsters;
	
	//Constructors
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		this.allMonsters = DataLoader.readMonsters();
		availableMonsters = this.allMonsters;
		Role opponentRole;
		if (playerRole == Role.SCARER) {
			opponentRole = Role.LAUGHER;
		}else {
			opponentRole = Role.SCARER;
		}
		this.player = this.selectRandomMonsterByRole(playerRole);
		this.opponent = this.selectRandomMonsterByRole(opponentRole);
		this.current = this.player;
				
	}
	
	
	// Getters for read-only fields
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters(){
		return allMonsters;
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	// Current
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	//Methods
	
	//Helper
	private Monster selectRandomMonsterByRole(Role role) {
		ArrayList<Monster> monstersOfRole = new ArrayList<>();
		for(int i = 0; i< availableMonsters.size(); i++) {
			Monster m = availableMonsters.get(i);
			if (m.getOriginalRole() == role) {
				monstersOfRole.add(m);
			}
		}
		Monster selectedMonster = monstersOfRole.get((int) (Math.random()*monstersOfRole.size()));
		return selectedMonster;
	}
}
