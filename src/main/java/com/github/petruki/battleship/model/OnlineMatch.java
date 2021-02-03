package com.github.petruki.battleship.model;

import java.util.List;

public class OnlineMatch {
	
	private String room;
	private List<Player> players;

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
