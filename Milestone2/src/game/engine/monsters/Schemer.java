package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Constants;
import game.engine.Role;
import game.engine.dataloader.DataLoader;

public class Schemer extends Monster {

	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	public void executePowerupEffect(Monster opponentMonster) {
		ArrayList<Monster> monsters = DataLoader.readMonsters();
		


	}

	public void setEnergy(int energy) {
		super.setEnergy(energy + Constants.SCHEMER_STEAL);
	}

	private int stealEnergyFrom(Monster target) {
		if (target.getEnergy() < 10) {
			return target.getEnergy();
		} else {
			return 10;
		}

	}
}
