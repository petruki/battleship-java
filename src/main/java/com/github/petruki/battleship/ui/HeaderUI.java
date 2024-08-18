package com.github.petruki.battleship.ui;

import com.github.petruki.battleship.model.Scoreboard;
import com.github.petruki.battleship.ui.dialog.GameSettingsDialog;
import com.github.petruki.battleship.ui.dialog.MultiplayerDialog;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * @author petruki (Roger Floriano)
 */
public class HeaderUI extends AbstractHeaderUI {
	
	public HeaderUI(final MainUI context, final String timeLimit) {
		super(context, timeLimit, true);
	}
	
	@Override
	protected void onSettings(ActionEvent event) {
		GameSettingsDialog gameSettingsDialog = new GameSettingsDialog();
		gameSettingsDialog.setSettings(context.getSettings());
		gameSettingsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameSettingsDialog.setVisible(true);
		gameSettingsDialog.setDefaultCloseOperation( DISPOSE_ON_CLOSE);
		
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
	
	@Override
	protected void onChangeMode(ActionEvent event) {
		MultiplayerDialog multiplayerDialog = new MultiplayerDialog();
		multiplayerDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		multiplayerDialog.setVisible(true);
		multiplayerDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		multiplayerDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	if (multiplayerDialog.isLobbyCreated()) {
					context.onSwitchModes();
				}
	    	}
		});
	}
	
	@Override
	protected void updateTxtTimer() {
		pattern = "%s:0%s";
		if (++secs == 60) {
			secs = 0;
			minutes++;
		} else if (secs > 9) {
			pattern = "%s:%s";
		}
		
		if (timeLimit.equals(String.format(pattern, minutes, secs))) {
			context.onGameFinished();
		}

		txtTimer.setText(String.format(pattern, minutes, secs));
	}
	
	@Override
	public void reloadGame() {
		btnMode.setVisible(false);
		btnStart.setVisible(false);
		btnStop.setEnabled(true);
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
	
	@Override
	public void onGameFinished() {
		timer.cancel();
		btnSettings.setVisible(true);
		btnStart.setVisible(true);
		btnMode.setVisible(true);
	}

	@Override
	public void updateScoreUI(boolean hit) {
		Scoreboard scoreBoard = context.getGameController().getScoreBoard();
		super.updateScoreUI(hit, String.format("You sank all my battleships, in %s guesses.", 
				scoreBoard.getHit() + scoreBoard.getMiss()));
	}

}
