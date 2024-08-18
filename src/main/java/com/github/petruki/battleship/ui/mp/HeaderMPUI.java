package com.github.petruki.battleship.ui.mp;

import com.github.petruki.battleship.broker.BrokerClient;
import com.github.petruki.battleship.ui.AbstractHeaderUI;
import com.github.petruki.battleship.ui.dialog.GameSettingsDialog;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * @author petruki (Roger Floriano)
 */
public class HeaderMPUI extends AbstractHeaderUI {
	
	public HeaderMPUI(final MainMPUI context) {
		super(context, false);
	}
	
	@Override
	protected void onSettings(ActionEvent event) {
		GameSettingsDialog gameSettingsDialog = new GameSettingsDialog(true);
		gameSettingsDialog.setSettings(context.getSettings());
		gameSettingsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameSettingsDialog.setVisible(true);
		gameSettingsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		gameSettingsDialog.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		    	if (gameSettingsDialog.isStartGame()) {
		    		context.changeSettings(gameSettingsDialog.getSettings());
		    	}
	    	}
		});
	}
	
	@Override
	protected void onChangeMode(ActionEvent event) {
		context.onSwitchModes();
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
		
		txtTimer.setText(String.format(pattern, minutes, secs));
	}
	
	@Override
	public void reloadGame() {
		if (!BrokerClient.getInstance().getPlayer().isHost()) {
			btnMode.setEnabled(true);
		} else {
			btnMode.setEnabled(false);
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
		}

		txtTimer.setText("0:00");
		txtMessage.setText("");
		btnSettings.setEnabled(false);
		
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
		
		if (BrokerClient.getInstance().getPlayer().isHost()) {
			btnSettings.setEnabled(true);
			btnStart.setEnabled(true);
			btnMode.setEnabled(true);
		}
	}
	
	public void hideMenu() {
		btnStart.setEnabled(false);
		btnSettings.setEnabled(false);
		btnStop.setEnabled(false);
	}
	
}
