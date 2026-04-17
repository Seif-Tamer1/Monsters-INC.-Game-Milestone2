package game.engine.cells;

import game.engine.Board;
import game.engine.cards.Card;
import game.engine.monsters.Monster;

public class ConveyorBelt extends TransportCell {

	public ConveyorBelt(String name, int effect) {
		super(name, effect);
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		super.transport(landingMonster);
	}

}
