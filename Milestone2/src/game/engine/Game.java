package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

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
		
		ArrayList<Monster> stationedMonsters = new ArrayList<>(allMonsters);
		stationedMonsters.remove(player);
		stationedMonsters.remove(opponent);
		board.setStationedMonsters(stationedMonsters);
		
		board.initializeBoard(DataLoader.readCells());
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
	
	private Monster getCurrentOpponent(){
		if (current==player)
			return opponent;
		else
			return player;
	}
	
	private int rollDice(){
		Random rand=new Random();
    	int roll=rand.nextInt(6)+1;
    	return roll;
	}
	
	public void usePowerup() throws OutOfEnergyException{
		if (current.getEnergy()>= Constants.POWERUP_COST){
			current.executePowerupEffect(getCurrentOpponent());
			
		}else{
			throw new OutOfEnergyException();
		}
	}
	
	private void switchTurn(){
		if (current==player)
			current=opponent;
		else
			current=player;
	}
	
	public void playTurn() throws InvalidMoveException{
		if (current.isFrozen()==true){
			switchTurn();
			current.setFrozen(false);
		}else{
			int roll=rollDice();
			board.moveMonster(current,roll,opponent);
			switchTurn();
		}
	}
	
	private boolean checkWinCondition(Monster monster){
		if (monster.getEnergy()>=Constants.WINNING_ENERGY && monster.getPosition()==Constants.WINNING_POSITION)
			return true;
		else
			return false;
	}
	
	public Monster getWinner(){
		if (checkWinCondition(player))
			return player;
		else{
			if (checkWinCondition(opponent)){
				return opponent;
			}else{
				return null;
			}
		}
	}


}