package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {

	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	public void executePowerupEffect(Monster opponentMonster) {

	}
	public void setEnergy(int energy) {
		super.setEnergy(energy+Constants.SCHEMER_STEAL);
	}
}
