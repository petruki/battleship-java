package com.github.petruki.battleship.model;

public class GameSettings {
	
	private int ships;
	private int shipSize;
	private String timeLimit;
	
	public GameSettings() {
		this.ships = 3;
		this.shipSize = 3;
		this.timeLimit = "0:20";
	}
	
	public int getShips() {
		return ships;
	}
	
	public void setShips(int ships) {
		this.ships = ships;
	}
	
	public int getShipSize() {
		return shipSize;
	}
	
	public void setShipSize(int shipSize) {
		this.shipSize = shipSize;
	}
	
	public String getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public int getTargets() {
		return ships * shipSize;
	}

}
