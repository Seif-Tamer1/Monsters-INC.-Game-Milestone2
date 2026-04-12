package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {

	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	public void executePowerupEffect(Monster opponentMonster) {
		ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
		int sum = 0;
		sum += stealEnergyFrom(opponentMonster);
		for (int i = 0; i < stationedMonsters.size(); i++) {
			sum += stealEnergyFrom(stationedMonsters.get(i));
		}
		this.alterEnergy(sum);

	}

	public void setEnergy(int energy) {
		super.setEnergy(energy + Constants.SCHEMER_STEAL);
	}

	private int stealEnergyFrom(Monster target) {
		
		if (target.getEnergy() < Constants.SCHEMER_STEAL) {
			int returnedEnergy=target.getEnergy();
			target.setEnergy(0);
			return returnedEnergy;
		} else {
			target.setEnergy(target.getEnergy()+Constants.SCHEMER_STEAL*(-1));
			return Constants.SCHEMER_STEAL;
		}

	}
}
