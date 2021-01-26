package com.rogerio.util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rogerio.ui.MainUI;

public class LoadImage {
	
	private static final Logger logger = LogManager.getLogger(LoadImage.class);
	
	public static final String IMAGE_ERROR = "Failed to load image %s";
	
	/**
	 * Load image from src/main/resources
	 */
	public static BufferedImage load(String image) {
		try {
			return ImageIO.read(MainUI.class.getClassLoader().getResource(image));
		} catch (Exception e) {
			logger.error(e);
			JOptionPane.showMessageDialog(null, String.format(IMAGE_ERROR, image), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}
