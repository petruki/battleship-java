package com.github.petruki.battleship.controller;

import java.util.Arrays;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public class OfflineGameController implements GameController {
	
	private static final Logger logger = LogManager.getLogger(OfflineGameController.class);
	
	protected Scoreboard scoreBoard;
	protected int missCounter = 0;
	protected boolean radar;
	
	public OfflineGameController(int targets) {
		scoreBoard = new Scoreboard(targets);
	}

	@Override
	public Object[][] generateMatrix(int rows, int columns, int ships, int shipSize) {
		//start scoreboard
		scoreBoard = new Scoreboard(ships * shipSize);
		
		//start empty board
		Object[][] matrix = new Object[rows][columns];
		Arrays.stream(matrix).forEach(r -> Arrays.fill(r, 0));
		
		Random random = new Random();
		int randomRow;
		int randomColumn;
		
		//initialize board
		while (ships != 0) {
			randomRow = random.nextInt(rows);
			randomColumn = random.nextInt(columns);
			
			if (Integer.parseInt(matrix[randomRow][randomColumn].toString()) == 0) {
				if (!addShip(matrix, randomRow, randomColumn, shipSize, ships--)) {
					ships++;
				}
			}
		}
		
		//print generated board
		if (logger.isDebugEnabled()) {
			System.out.println("Do not cheat!");
			Arrays.stream(matrix).forEach(row -> {
				Arrays.stream(row).forEach(col -> System.out.print(col + " "));
				System.out.println();
			});
		}
		
		return matrix;
	}
	
	@Override
	public boolean addShip(Object[][] matrix, int randomRow, 
			int randomColumn, int shipSize, int shipId) {
		boolean vertical = Math.random() > 0.5;
		
		if (!hasCollided(matrix, randomRow, randomColumn, shipSize, vertical)) {
			matrix[randomRow][randomColumn] = shipId;
			for (int i = 0; i < shipSize; i++) {
				if (vertical) {
					matrix[randomRow++][randomColumn] = shipId;
				} else {
					matrix[randomRow][randomColumn++] = shipId;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasCollided(Object[][] matrix, int randomRow, 
			int randomColumn, int shipSize, boolean vertical) {
		if (vertical) {
			for (int i = 0, row = randomRow; i < shipSize; i++, row++) {
				if (row >= matrix.length || Integer.parseInt(matrix[row][randomColumn].toString()) != 0)
					return true;
			}
		} else {
			for (int i = 0, col = randomColumn; i < shipSize; i++, col++) {
				if (col >= matrix[randomRow].length || Integer.parseInt(matrix[randomRow][col].toString()) != 0)
					return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean hasSink(Object[][] matrix, Object shipId) {
		for (Object[] rows : matrix) {
			for (Object cell : rows) {
				if (cell.equals(shipId))
					return false;
			}
		}
		scoreBoard.addBonus();
		return true;
	}
	
	@Override
	public Target onFire(Object[][] boardMatrix, String coord) throws Exception {
		if (coord.length() == 2) {
			int row = Arrays.binarySearch(Target.CHARS, String.valueOf(coord.charAt(0)).toUpperCase());
			int col = Integer.parseInt(String.valueOf(coord.charAt(1)));
			
			//validates board boundaries
			if (row < boardMatrix.length && col < boardMatrix[row].length) {
				Object result = boardMatrix[row][col];
				
				//already assigned shot
				if (result instanceof Target resultOf) {
					return resultOf;
				} else {
					int slot = Integer.parseInt(result.toString());
					if (slot != 0)
						boardMatrix[row][col] = -1;
					else
						missCounter++;
					
					return Target.createTarget(slot, row, col);
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Coordinate: %s", coord));
		}

		throw new Exception("Oops, that's off the board!");
	}

	@Override
	public boolean activateRadar() {
		if (missCounter >= 3) {
			missCounter = 0;
			radar = true;
			new Thread(() -> {
				try {
					Thread.sleep(5000);
					radar = false;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					logger.error(e);
				}
			}).start();
			return true;
		}
		return false;
	}

	@Override
	public Scoreboard getScoreBoard() {
		return scoreBoard;
	}
	
	@Override
	public boolean isRadarActivated() {
		return radar;
	}

}
