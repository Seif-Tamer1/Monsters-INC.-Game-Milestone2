package game.engine;

import java.util.ArrayList;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.exceptions.InvalidMoveException;
import game.engine.monsters.Monster;

import java.util.Collections;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters;
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;

	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
	}

	public Cell[][] getBoardCells() {
		return boardCells;
	}

	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}

	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}

	public static ArrayList<Card> getCards() {
		return cards;
	}

	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}

	public static Card drawCard() {
		if (cards == null) {
			reloadCards();
		}
		return cards.remove(0);
	}

	private int[] indexToRowCol(int index) {
		int row = index / Constants.BOARD_ROWS;
		int column;
		if (row % 2 == 0) {
			column = index % Constants.BOARD_COLS;
		} else {
			column = Constants.BOARD_COLS - (index % Constants.BOARD_COLS) - 1;
		}
		int[] arr = new int[2];
		arr[0] = row;
		arr[1] = column;
		return arr;
	}

	private Cell getCell(int index) {
		int[] arr = indexToRowCol(index);
		return boardCells[arr[0]][arr[1]];
	}

	private void setCell(int index, Cell cell) {
		int[] arr = indexToRowCol(index);
		boardCells[arr[0]][arr[1]] = cell;
	}

	public void initializeBoard(ArrayList<Cell> specialCells) {
		int oddindex = 1;
		int convoyerCounter = 0;
		int sockCounter = 0;

		for (int i = 0; i < specialCells.size(); i++) {
			if (specialCells.get(i) instanceof DoorCell) {
				setCell(oddindex, specialCells.get(i));
				oddindex += 2;
			} else {
				if (specialCells.get(i) instanceof ConveyorBelt) {
					setCell(Constants.CONVEYOR_CELL_INDICES[convoyerCounter++],
							specialCells.get(i));
				} else {
					if (specialCells.get(i) instanceof ContaminationSock) {
						setCell(Constants.SOCK_CELL_INDICES[sockCounter++],
								specialCells.get(i));
					}
				}
			}
		}

		for (int i = 0; i < Constants.CARD_CELL_INDICES.length; i++) {
			setCell(Constants.CARD_CELL_INDICES[i], new CardCell(null));
		}

		for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length; i++) {
			setCell(Constants.MONSTER_CELL_INDICES[i], new MonsterCell(null,
					stationedMonsters.get(i)));
		}

	}

	private void setCardsByRarity() {
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < originalCards.size(); i++) {
			for (int j = 0; j < originalCards.get(i).getRarity(); j++) {
				temp.add(originalCards.get(i));
			}
		}
		originalCards = temp;
	}

	public static void reloadCards() {
		cards = originalCards;
		Collections.shuffle(cards);
	}

	public void moveMonster(Monster currentMonster, int roll,
			Monster opponentMonster) throws InvalidMoveException {
		currentMonster.move(roll);
		if (currentMonster.compareTo(opponentMonster) == 0) {
			currentMonster.move(roll * (-1));
			throw new InvalidMoveException();
		}
		boolean lastTurnConfused = currentMonster.isConfused();
		Cell currentCell = getCell(currentMonster.getPosition());
		currentCell.onLand(currentMonster, opponentMonster);

		if (!(lastTurnConfused == false && currentMonster.isConfused() == true)) {
			currentMonster.decrementConfusion();
			opponentMonster.decrementConfusion();
		}

	}
}
