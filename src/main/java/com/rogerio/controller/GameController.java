package com.rogerio.controller;

import java.util.Arrays;
import java.util.Random;

import com.rogerio.model.Scoreboard;
import com.rogerio.model.Target;

public class GameController {
	
	private Scoreboard scoreBoard;

	/**
	 * Generate game matrix targets
	 */
	public Object[][] generateMatrix(int rows, int columns, int targets, int shipSize) {
		scoreBoard = new Scoreboard(targets * shipSize);
		
		Object[][] matrix = new Object[rows][columns];
		Arrays.stream(matrix).forEach(r -> Arrays.fill(r, 0));
		
		Random random = new Random();
		int randomRow;
		int randomColumn;
		
		while (targets != 0) {
			randomRow = random.nextInt(rows) + 0;
			randomColumn = random.nextInt(columns) + 0;
			
			if (Integer.parseInt(matrix[randomRow][randomColumn].toString()) == 0) {
				if (!addShip(matrix, randomRow, randomColumn, shipSize, targets--)) {
					targets++;
				}
			}
		}
		
		Arrays.stream(matrix).forEach(row -> {
			Arrays.stream(row).forEach(col -> System.out.print(col + " "));
			System.out.println();
		});
		
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
		
		if (!hasCollided(matrix, randomRow, randomColumn, shipSize, shipId, vertical)) {
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
			int randomColumn, int shipSize, int shipId, boolean vertical) {
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
		for (Object[] row : matrix) {
			if (Arrays.stream(row).filter(r -> r.equals(shipId)).count() > 0)
				return false;
		}
		return true;
	}
	
	public Target onFire(Object[][] boardMatrix, String coord) throws Exception {
		if (!coord.isEmpty() && coord.length() == 2) {
			int row = Arrays.binarySearch(Target.CHARS, String.valueOf(coord.charAt(0)).toUpperCase());
			int col = Integer.parseInt(String.valueOf(coord.charAt(1)));
			
			if (row < boardMatrix.length && col < boardMatrix[row].length) {
				Object result = boardMatrix[row][col];
				
				if (result instanceof Target) {
					return (Target) result;
				} else {
					int slot = Integer.parseInt(result.toString());
					boardMatrix[row][col] = -1;
					return Target.createTarget(slot, row, col);
				}
			}
		}

		throw new Exception("Oops, that's off the board!");
	}

	public Scoreboard getScoreBoard() {
		return scoreBoard;
	}

}
