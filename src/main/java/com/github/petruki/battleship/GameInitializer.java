package com.github.petruki.battleship;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.ui.MainUI;
import com.github.petruki.battleship.util.ResourcesCache;

/**
 * @author petruki (Roger Floriano)
 */
public class GameInitializer {
	
	private static final Logger logger = LogManager.getLogger(GameInitializer.class);

	public static void main(String[] args) {
		setupNimbus();
		startGame(new GameSettings());
	}
	
	private static void startGame(final GameSettings gameSettings) {
		EventQueue.invokeLater(() -> {
			try {
				ResourcesCache.getInstance().initializeImages();
				MainUI frame = new MainUI(gameSettings);
				frame.setVisible(true);
			} catch (Exception e) {
				logger.error(e);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
