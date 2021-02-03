package com.github.petruki.battleship.model;

public class Player {
	
	private String name;
	private Scoreboard scoreboard;
	private boolean host;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public boolean isHost() {
		return host;
	}

	public void setHost(boolean host) {
		this.host = host;
	}
	
}
