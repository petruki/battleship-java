package com.github.petruki.battleship.util;

import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadImage {
	
	private static final Logger logger = LogManager.getLogger(LoadImage.class);
	
	public static final String IMAGE_ERROR = "Failed to load image %s";
	
	/**
	 * Load image from src/main/resources
	 */
	public static BufferedImage load(String image) {
		try {
			//print generated board
			if (logger.isDebugEnabled()) {
				logger.info(image);
			}
			
			return ImageIO.read(Objects.requireNonNull(LoadImage.class.getClassLoader().getResource(image)));
		} catch (Exception e) {
			logger.error(e);
			JOptionPane.showMessageDialog(null, String.format(IMAGE_ERROR, image), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}
