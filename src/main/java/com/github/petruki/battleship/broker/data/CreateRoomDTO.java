package com.github.petruki.battleship.broker.data;

import com.github.petruki.battleship.broker.BrokerEvents;

public class CreateRoomDTO extends BrokerData {
	
	private String player;
	private boolean exist;

	public CreateRoomDTO(String room, BrokerEvents brokerEvent) {
		super(room, brokerEvent.toString());
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

}
