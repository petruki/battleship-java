package com.rogerio;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rogerio.ui.MainUI;
import com.rogerio.util.ResourcesCache;

/**
 * @author petruki (Roger Floriano)
 */
public class GameInitializer {
	
	private static final Logger logger = LogManager.getLogger(GameInitializer.class);
	
	public static final int SHIPS = 4;
	public static final int SHIP_SIZE = 3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourcesCache.getInstance().initializeImages();
					MainUI frame = new MainUI(SHIPS, SHIP_SIZE);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
