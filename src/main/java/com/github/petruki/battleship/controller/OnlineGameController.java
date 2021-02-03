package com.github.petruki.battleship.controller;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
public class OnlineGameController extends OfflineGameController {
	
	private static final Logger logger = LogManager.getLogger(OnlineGameController.class);
	
	public OnlineGameController(int targets) {
		super(targets);
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
		return false;
	}

}
