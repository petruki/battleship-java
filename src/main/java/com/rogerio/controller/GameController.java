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
	public Object[][] generateMatrix(int rows, int columns, int targets) {
		Object[][] matrix = new Object[rows][columns];
		Arrays.stream(matrix).forEach(r -> Arrays.fill(r, 0));
		
		Random random = new Random();
		int randomRow;
		int randomColumn;
		
		while (targets != 0) {
			randomRow = random.nextInt(rows) + 0;
			randomColumn = random.nextInt(columns) + 0;
			
			if (Integer.parseInt(matrix[randomRow][randomColumn].toString()) == 0) {
				matrix[randomRow][randomColumn] = 1;
				targets--;
			}
		}
		
		scoreBoard = new Scoreboard(targets);
		
		Arrays.stream(matrix).forEach(row -> {
			Arrays.stream(row).forEach(col -> System.out.print(col + " "));
			System.out.println();
		});
		
		return matrix;
	}
	
	public Target onFire(Object[][] boardMatrix, String coord) throws Exception {
		if (!coord.isEmpty() && coord.length() == 2) {
			int row = Arrays.binarySearch(Target.CHARS, String.valueOf(coord.charAt(0)));
			int col = Integer.parseInt(String.valueOf(coord.charAt(1)));
			
			if (row < boardMatrix.length && col < boardMatrix[row].length) {
				Object result = boardMatrix[row][col];
				
				if (result instanceof Target) {
					return (Target) result;
				} else {
					int slot = Integer.parseInt(result.toString());
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
