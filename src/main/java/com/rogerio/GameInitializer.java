package com.rogerio;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rogerio.ui.GameSettingsDialog;
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
		setupGame();
	}
	
	private static void setupGame() {
		GameSettingsDialog gameSettings = new GameSettingsDialog();
		gameSettings.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		gameSettings.setVisible(true);
		
		gameSettings.setDefaultCloseOperation(
			    JDialog.DISPOSE_ON_CLOSE);
		
		gameSettings.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	if (gameSettings.isStartGame()) {
		    		startGame(gameSettings.getShips(), 
		    				gameSettings.getShipSize(), 
		    				gameSettings.getTimeLimit());
		    	}		    		
	    	}
		});
		
	}
	
	private static void startGame(int ships, int shipSize, String timeLimit) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourcesCache.getInstance().initializeImages();
					MainUI frame = new MainUI(ships, shipSize, timeLimit);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
