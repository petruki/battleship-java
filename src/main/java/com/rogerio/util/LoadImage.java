package com.rogerio.util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.rogerio.ui.MainUI;

public class LoadImage {
	
	public static final String IMAGE_ERROR = "Failed to load image %s";
	
	public static BufferedImage load(String image) {
		try {
			return ImageIO.read(MainUI.class.getClassLoader().getResource(image));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, String.format(IMAGE_ERROR, image), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}
