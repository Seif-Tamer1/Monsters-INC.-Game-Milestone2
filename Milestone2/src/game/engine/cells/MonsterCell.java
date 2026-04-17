package game.engine.cells;

import game.engine.Constants;
import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() {
		return cellMonster;
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		if (landingMonster.getRole()==getCellMonster().getRole())
			landingMonster.executePowerupEffect(opponentMonster);
		else{
			if (landingMonster.getEnergy() > getCellMonster().getEnergy()){
				int diffEnergy=landingMonster.getEnergy()-getCellMonster().getEnergy();
				landingMonster.alterEnergy(diffEnergy*(-1));
				this.cellMonster.alterEnergy(diffEnergy);
			}
		}
	}

}
