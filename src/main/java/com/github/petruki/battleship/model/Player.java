package com.github.petruki.battleship.model;

public class Player {
	
	private String id;
	private String username;
	private String room;
	
	private Scoreboard scoreboard;
	private boolean host;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
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
