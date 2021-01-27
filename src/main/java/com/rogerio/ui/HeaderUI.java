package com.rogerio.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.rogerio.model.Scoreboard;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class HeaderUI extends JPanel {
	
	private JLabel txtTimer;
	private JLabel txtMessage;
	private final MainUI context;
	
	private Timer timer;
	private int secs = 0;
	private int minutes = 0;
	private String pattern;
	
	public HeaderUI(final MainUI context) {
		this.context = context;
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
	}
	
	private void updateTxtTimer() {
		pattern = "%s:0%s";
		if (++secs == 60) {
			secs = 0;
			minutes++;
		} else if (secs > 9) {
			pattern = "%s:%s";
		}

		txtTimer.setText(String.format(pattern, minutes, secs));
	}
	
	public void updateScoreUI(boolean hit) {
		Scoreboard scoreBoard = context.getGameController().getScoreBoard();
		
		if (hit) {
			if (scoreBoard.addHit()) {
				timer.cancel();
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
