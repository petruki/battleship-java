package com.github.petruki.battleship.broker.data;

import java.util.List;

import com.github.petruki.battleship.broker.BrokerEvents;
import com.github.petruki.battleship.model.Player;

public class RoomDataDTO extends BrokerData {
	
	private List<Player> players;
	private String message;
	
	public RoomDataDTO(String room, BrokerEvents brokerEvent) {
		super(room, brokerEvent.toString());
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
