package com.rogerio.util;

public enum ResourceConstants {
	
	IMG_BOARD("board"),
	IMG_BOARD_END_GOOD("board_good"),
	IMG_BOARD_END_BAD("board_bad"),
	IMG_MISS("miss"),
	IMG_HIT("hit");
	
	private String key;
	
	private ResourceConstants(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return key;
	}

}
