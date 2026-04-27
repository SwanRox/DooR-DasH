package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		
		ArrayList<Monster> stationedMonsters = new ArrayList<>();
		
		stationedMonsters = this.getAllMonsters();
		stationedMonsters.remove(getPlayer());
		stationedMonsters.remove(getOpponent());
		
		Board.setStationedMonsters(stationedMonsters);
		
		this.getBoard().initializeBoard(DataLoader.readCells());
		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	
	private Monster getCurrentOpponent() {
		if (this.getCurrent() == this.getPlayer())
			return this.getOpponent();
		else
			return this.getPlayer();
	}
	
	private int rollDice() {
		return (int)(Math.random() * 6) + 1;
	}
	
	public void usePowerup() throws OutOfEnergyException {
		
		if(this.getCurrent().getEnergy()>=Constants.POWERUP_COST) {
			
			this.getCurrent().alterEnergy(-Constants.POWERUP_COST);
			this.getCurrent().executePowerupEffect(this.getCurrentOpponent());
		}else {
			throw new OutOfEnergyException();
		}
	}
	
	public void playTurn() throws InvalidMoveException {
			if (this.getCurrent().isFrozen()){
				this.getCurrent().setFrozen(false);
				this.switchTurn();
			}
			else {
				int distance = this.rollDice();
				this.board.moveMonster(getCurrent(), distance, getOpponent());
				this.switchTurn();
			}
	}
	
	private void switchTurn() {
		this.setCurrent(this.getCurrentOpponent());
	}
	
	private boolean checkWinCondition(Monster monster) {
		if(monster.getPosition() == Constants.WINNING_POSITION && monster.getEnergy()>=Constants.WINNING_ENERGY)
			return true;
		return false;
	}
	
	public Monster getWinner() {
		System.out.println(this.getCurrent());
		if(this.checkWinCondition(this.getCurrent()))
			return this.getCurrent();
		if(this.checkWinCondition(this.getCurrentOpponent()))
			return this.getCurrentOpponent();
		return null;
	}
}
