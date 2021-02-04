package com.github.petruki.battleship.model;

public class Player {
	
	private String id;
	private String username;
	private String room;
	
	private int score;
	private boolean host;
	
	public Player() {
		this.score = 0;
		this.host = false;
	}
	
	public Player(String username) {
		this.username = username;
		this.score = 0;
	}
	
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
	
	public boolean isHost() {
		return host;
	}
	
	public void setHost(boolean host) {
		this.host = host;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addPoint() {
		this.score+=3;
	}
	
	public void removePoint() {
		this.score--;
	}

	@Override
	public String toString() {
		return "Player [username=" + username + ", score=" + score + "]";
	}
	
}
