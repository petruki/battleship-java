package com.github.petruki.battleship.broker.data;

import com.github.petruki.battleship.broker.BrokerEvents;

public class PlayerDTO extends BrokerData {
	
	private String player;
	
	public PlayerDTO(String room, BrokerEvents brokerEvent) {
		super(room, brokerEvent.toString());
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

}
