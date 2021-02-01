package com.github.petruki.battleship.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.ui.dialog.GameSettingsDialog;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class HeaderUI extends JPanel {
	
	private JButton btnSettings;
	private JLabel txtTimer;
	private JLabel txtMessage;
	private final MainUI context;
	private String timeLimit;
	
	private Timer timer;
	private int secs = 0;
	private int minutes = 0;
	private String pattern;
	
	public HeaderUI(final MainUI context, final String timeLimit) {
		this.context = context;
		this.timeLimit = timeLimit;
		buildPanel();
	}
	
	private void buildPanel() {
		setBackground(new Color(83, 175, 19));
		setBounds(0, 0, 1018, 46);
		setLayout(null);

		final Font labelFont = new Font("Tahoma", Font.PLAIN, 18);
		
		txtMessage = new JLabel();
		txtMessage.setFont(labelFont);
		txtMessage.setForeground(Color.WHITE);
		txtMessage.setBounds(10, 11, 388, 24);
		add(txtMessage);

		txtTimer = new JLabel("0:00");
		txtTimer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimer.setForeground(Color.WHITE);
		txtTimer.setFont(labelFont);
		txtTimer.setBounds(471, 11, 68, 24);
		add(txtTimer);

		JButton btnReload = new JButton("Reload");
		btnReload.setForeground(Color.WHITE);
		btnReload.setBackground(new Color(0, 128, 0));
		btnReload.setBounds(903, 10, 87, 30);
		btnReload.setFocusable(false);
		btnReload.addActionListener(context::onStartNewGame);
		add(btnReload);
		
		btnSettings = new JButton("Settings");
		btnSettings.setVisible(false);
		btnSettings.setForeground(Color.WHITE);
		btnSettings.setFocusable(false);
		btnSettings.setBackground(new Color(0, 128, 0));
		btnSettings.setBounds(806, 11, 87, 30);
		btnSettings.addActionListener(this::onSettings);
		add(btnSettings);
	}
	
	private void onSettings(ActionEvent event) {
		GameSettingsDialog gameSettingsDialog = new GameSettingsDialog();
		gameSettingsDialog.setSettings(context.getSettings());
		gameSettingsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		gameSettingsDialog.setVisible(true);
		gameSettingsDialog.setDefaultCloseOperation(
			    JDialog.DISPOSE_ON_CLOSE);
		
		gameSettingsDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	if (gameSettingsDialog.isStartGame()) {
		    		timeLimit = gameSettingsDialog.getSettings().getTimeLimit();
		    		context.changeSettings(gameSettingsDialog.getSettings());
		    	}
	    	}
		});
	}
	
	private void updateTxtTimer() {
		pattern = "%s:0%s";
		if (++secs == 60) {
			secs = 0;
			minutes++;
		} else if (secs > 9) {
			pattern = "%s:%s";
		}
		
		if (timeLimit.equals(String.format(pattern, minutes, secs))) {
			context.onGameFinished(context.getGameController().getScoreBoard());
		}

		txtTimer.setText(String.format(pattern, minutes, secs));
	}
	
	public void updateScoreUI(boolean hit) {
		Scoreboard scoreBoard = context.getGameController().getScoreBoard();
		
		if (hit) {
			if (scoreBoard.addHit()) {
				timer.cancel();
				context.onGameFinished(scoreBoard);
				txtMessage.setText(
						String.format("You sank all my battleships, in %s guesses.", 
								scoreBoard.getHit() + scoreBoard.getMiss()));
			}
		} else {
			scoreBoard.addMiss();
		}
		
		context.getScoreUI().updateScore(scoreBoard);
	}
	
	public void reloadGame() {
		txtTimer.setText("0:00");
		txtMessage.setText("");
		btnSettings.setVisible(false);
		
		TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
            	updateTxtTimer();
            }
        };
        
        if (timer != null) {
        	timer.cancel();
        	secs = 0;
        	minutes = 0;
        }
        
       	timer = new Timer();
    	timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
	
	public void onGameFinished() {
		timer.cancel();
		btnSettings.setVisible(true);
	}
	
	public void updateText(String textMessage) {
		txtMessage.setText(textMessage);
	}

	public Timer getTimer() {
		return timer;
	}

	public JLabel getTxtMessage() {
		return txtMessage;
	}

}
