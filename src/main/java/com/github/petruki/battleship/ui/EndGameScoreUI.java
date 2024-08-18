package com.github.petruki.battleship.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.github.petruki.battleship.model.Scoreboard;

public class EndGameScoreUI extends JPanel {
	
	private JLabel txtHit;
	private JLabel txtMissed;
	private JLabel txtScore;

	public EndGameScoreUI() {
		buildPanel();
	}

	private void buildPanel() {
		setBounds(438, 539, 142, 108);
		setBackground(new Color(83, 175, 19));
		setLayout(null);
		setVisible(false);
		
		Font font = new Font("Tahoma", Font.PLAIN, 18);
		
		JLabel lblHit = new JLabel("Hit");
		lblHit.setBounds(10, 11, 46, 14);
		lblHit.setFont(font);
		lblHit.setForeground(Color.WHITE);
		add(lblHit);
		
		JLabel lblMissed = new JLabel("Missed");
		lblMissed.setBounds(10, 36, 62, 14);
		lblMissed.setFont(font);
		lblMissed.setForeground(Color.WHITE);
		add(lblMissed);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setBounds(10, 72, 46, 14);
		lblScore.setFont(font);
		lblScore.setForeground(Color.WHITE);
		add(lblScore);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 61, 118, 2);
		add(separator);
		
		txtHit = new JLabel();
		txtHit.setBounds(106, 14, 30, 15);
		txtHit.setFont(font);
		txtHit.setForeground(Color.WHITE);
		txtHit.setHorizontalTextPosition(SwingConstants.RIGHT);
		add(txtHit);
		
		txtMissed = new JLabel();
		txtMissed.setBounds(106, 36, 30, 15);
		txtMissed.setFont(font);
		txtMissed.setForeground(Color.WHITE);
		txtMissed.setHorizontalTextPosition(SwingConstants.RIGHT);
		add(txtMissed);
		
		txtScore = new JLabel();
		txtScore.setBounds(106, 75, 30, 15);
		txtScore.setFont(font);
		txtScore.setForeground(Color.WHITE);
		txtScore.setHorizontalTextPosition(SwingConstants.RIGHT);
		add(txtScore);
	}
	
	public void showScore(Scoreboard scoreBoard) {
		txtHit.setText(String.valueOf(scoreBoard.getHit()));
		txtMissed.setText(String.valueOf(scoreBoard.getMiss()));
		txtScore.setText(String.valueOf(scoreBoard.getFinalScore()));
		setVisible(true);
	}

}
