package com.github.petruki.battleship.controller;

import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.model.ScoreboardOnline;
import com.github.petruki.battleship.model.Target;

public interface GameController {
	
	default public ScoreboardOnline getOnlineScoreBoard() {
		return null;
	}
	
	/**
	 * Generate game matrix targets
	 */
	public Object[][] generateMatrix(int rows, int columns, int ships, int shipSize);
	
	/**
	 * Add ship to the board based on the coordinates and size
	 * 
	 * @return true if ship has been added
	 */
	public boolean addShip(Object[][] matrix, int randomRow, 
			int randomColumn, int shipSize, int shipId);
	
	/**
	 * Validate if ship position collides or overlap with another ship.
	 * 
	 * @return return true if it collides
	 */
	public boolean hasCollided(Object[][] matrix, int randomRow, 
			int randomColumn, int shipSize, boolean vertical);
	
	/**
	 * Validates if ship has been sank
	 */
	public boolean hasSink(Object[][] matrix, Object shipId);
	
	public Target onFire(Object[][] boardMatrix, String coord) throws Exception;

	public boolean activateRadar();

	public Scoreboard getScoreBoard();
	
	public boolean isRadarActivated();

}
