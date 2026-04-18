package game.engine.cells;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);	
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		
		if (this.activated==false){
			ArrayList<Monster> stationedMonsters = Board.getStationedMonsters();
			ArrayList<Monster> teammates = new ArrayList<Monster>();
			for(int i=0; i<stationedMonsters.size(); i++){
				if(stationedMonsters.get(i).getRole()==landingMonster.getRole()){
					teammates.add(stationedMonsters.get(i));
				}
			}
			
			if (this.role==landingMonster.getRole()){
				modifyCanisterEnergy(landingMonster, this.energy);
				for(int i=0; i<teammates.size(); i++){
					modifyCanisterEnergy(teammates.get(i), this.energy);
				}
				setActivated(true);
			}else{
				boolean var=landingMonster.isShielded();
				modifyCanisterEnergy(landingMonster, this.energy*(-1));
				if (var==false){
					for(int i=0; i<teammates.size(); i++){
						modifyCanisterEnergy(teammates.get(i), this.energy*(-1));
					}
					setActivated(true);
				}
				
			}
		}
	}

}
