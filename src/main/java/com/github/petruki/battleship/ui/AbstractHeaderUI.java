package com.github.petruki.battleship.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.petruki.battleship.model.Scoreboard;

public abstract class AbstractHeaderUI extends JPanel {
	
	protected JButton btnStart;
	protected JButton btnStop;
	protected JButton btnSettings;
	protected JButton btnMode;
	protected JLabel txtTimer;
	protected JLabel txtMessage;
	
	protected transient Timer timer;
	protected int secs = 0;
	protected int minutes = 0;
	protected String pattern;
	protected String timeLimit;
	private final boolean online;
	
	protected final transient MainUIActionEvent context;
	
	protected AbstractHeaderUI(MainUIActionEvent context, boolean online) {
		this(context, null, online);
	}
	
	protected AbstractHeaderUI(MainUIActionEvent context, String timeLimit, boolean online) {
		this.online = online;
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

		btnStart = new JButton("Start");
		btnStart.setForeground(Color.WHITE);
		btnStart.setBackground(new Color(0, 128, 0));
		btnStart.setBounds(612, 11, 87, 30);
		btnStart.setFocusable(false);
		btnStart.addActionListener(context::onStartNewGame);
		add(btnStart);
		
		btnStop = new JButton("End");
		btnStop.setForeground(Color.WHITE);
		btnStop.setBackground(new Color(0, 128, 0));
		btnStop.setBounds(903, 10, 87, 30);
		btnStop.setFocusable(false);
		btnStop.setEnabled(false);
		btnStop.addActionListener(this::onEndGame);
		add(btnStop);
		
		btnSettings = new JButton("Settings");
		btnSettings.setVisible(true);
		btnSettings.setForeground(Color.WHITE);
		btnSettings.setFocusable(false);
		btnSettings.setBackground(new Color(0, 128, 0));
		btnSettings.setBounds(806, 11, 87, 30);
		btnSettings.addActionListener(this::onSettings);
		add(btnSettings);
		
		btnMode = new JButton(online ? "Online" : "Offline");
		btnMode.setForeground(Color.WHITE);
		btnMode.setFocusable(false);
		btnMode.setBackground(new Color(0, 128, 0));
		btnMode.setBounds(709, 11, 87, 30);
		btnMode.addActionListener(this::onChangeMode);
		add(btnMode);
	}
	
	public void updateScoreUI(boolean hit, String message) {
		Scoreboard scoreBoard = context.getGameController().getScoreBoard();
		
		if (hit) {
			if (scoreBoard.addHit()) {
				timer.cancel();
				context.onGameFinished();
				txtMessage.setText(message);
			}
		} else {
			scoreBoard.addMiss();
		}
		
		context.getScoreUI().updateScore(scoreBoard);
	}
	
	public void updateScoreUI(boolean hit) {
		this.updateScoreUI(hit, "Good game!");
	}
	
	public void onEndGame(ActionEvent event) {
		btnStop.setEnabled(false);
		context.onGameEnded();
	}
	
	protected abstract void onSettings(ActionEvent event);
	
	protected abstract void onChangeMode(ActionEvent event);
	
	protected abstract void onGameFinished();
	
	protected abstract void reloadGame();
	
	protected abstract void updateTxtTimer();
	
	public Timer getTimer() {
		return timer;
	}

	public JLabel getTxtMessage() {
		return txtMessage;
	}
	
	public void updateText(String textMessage) {
		txtMessage.setText(textMessage);
	}

}
