package com.github.petruki.battleship.util;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ResourcesCache {
	
	private static ResourcesCache instance;
	
	private Map<String, BufferedImage> images;
	
	private ResourcesCache() {
		initializeImages();
	}
	
	public void initializeImages() {
		if (images == null) {
			images = new HashMap<String, BufferedImage>();
			images.put(ResourceConstants.IMG_BOARD.toString(), LoadImage.load("board.jpg"));
			images.put(ResourceConstants.IMG_BOARD_END_BAD.toString(), LoadImage.load("board_gameover.png"));
			images.put(ResourceConstants.IMG_BOARD_END_GOOD.toString(), LoadImage.load("board_end.png"));
			images.put(ResourceConstants.IMG_BOARD_GG.toString(), LoadImage.load("board_gg.png"));
			images.put(ResourceConstants.IMG_MISS.toString(), LoadImage.load("miss.png"));
			images.put(ResourceConstants.IMG_HIT.toString(), LoadImage.load("ship.png"));
		}
	}
	
	public static ResourcesCache getInstance() {
		if (instance == null)
			instance = new ResourcesCache();
		return instance;
	}

	public BufferedImage getImages(ResourceConstants key) {
		return images.get(key.toString());
	}

}
