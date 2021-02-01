package com.github.petruki.battleship;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.ui.MainUI;
import com.github.petruki.battleship.ui.dialog.GameSettingsDialog;
import com.github.petruki.battleship.util.ResourcesCache;

/**
 * @author petruki (Roger Floriano)
 */
public class GameInitializer {
	
	private static final Logger logger = LogManager.getLogger(GameInitializer.class);

	public static void main(String[] args) {
		setupNimbus();
		setupGame();
	}
	
	private static void setupGame() {
		GameSettingsDialog gameSettings = new GameSettingsDialog();
		gameSettings.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		gameSettings.setVisible(true);
		gameSettings.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	if (gameSettings.isStartGame()) {
		    		startGame(gameSettings.getSettings());
		    	}		    		
	    	}
		});
		
	}
	
	private static void startGame(final GameSettings gameSettings) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourcesCache.getInstance().initializeImages();
					MainUI frame = new MainUI(gameSettings);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private static void setupNimbus() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
