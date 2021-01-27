package com.rogerio.controller;

import java.util.Arrays;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rogerio.model.Scoreboard;
import com.rogerio.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public class GameController {
	
	private static final Logger logger = LogManager.getLogger(GameController.class);
	
	private Scoreboard scoreBoard;

	/**
	 * Generate game matrix targets
	 */
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
	
	/**
	 * Add ship to the board based on the coordinates and size
	 * 
	 * @return true if ship has been added
	 */
	public boolean addShip(Object[][] matrix, int randomRow, 
			int randomColumn, int shipSize, int shipId) {
		boolean vertical = Math.random() > 0.5;
		
		if (!hasCollided(matrix, randomRow, randomColumn, shipSize, vertical)) {
			matrix[randomRow][randomColumn] = shipId;
			if (vertical) {
				for (int i = 0; i < shipSize; i++) {
					matrix[randomRow++][randomColumn] = shipId;
				}
			} else {
				for (int i = 0; i < shipSize; i++) {
					matrix[randomRow][randomColumn++] = shipId;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Validate if ship position collides or overlap with another ship.
	 * 
	 * @return return true if it collides
	 */
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
	
	/**
	 * Validates if ship has been sank
	 */
	public boolean hasSink(Object[][] matrix, Object shipId) {
		for (Object[] rows : matrix) {
			for (Object cell : rows) {
				if (cell.equals(shipId))
					return false;
			}
		}
		return true;
	}
	
	public Target onFire(Object[][] boardMatrix, String coord) throws Exception {
		if (!coord.isEmpty() && coord.length() == 2) {
			int row = Arrays.binarySearch(Target.CHARS, String.valueOf(coord.charAt(0)).toUpperCase());
			int col = Integer.parseInt(String.valueOf(coord.charAt(1)));
			
			//validates board boundaries
			if (row < boardMatrix.length && col < boardMatrix[row].length) {
				Object result = boardMatrix[row][col];
				
				//already assigned shot
				if (result instanceof Target) {
					return (Target) result;
				} else {
					int slot = Integer.parseInt(result.toString());
					if (slot != 0)
						boardMatrix[row][col] = -1;
					return Target.createTarget(slot, row, col);
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Coordinate: %s", coord));
		}

		throw new Exception("Oops, that's off the board!");
	}

	public Scoreboard getScoreBoard() {
		return scoreBoard;
	}

}
