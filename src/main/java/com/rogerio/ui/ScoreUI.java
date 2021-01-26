package com.rogerio.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.rogerio.model.Scoreboard;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class ScoreUI extends JPanel {
	
	private JTextPane txtScoreHit;
	private JTextPane txtScoreMissed;
	
	public ScoreUI() {
		buildPanel();
	}
	
	private void buildPanel() {
		setLayout(null);
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBackground(new Color(83, 175, 19));
		setBounds(864, 207, 130, 144);

		JLabel lblScore = new JLabel("Hit");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setForeground(Color.WHITE);
		lblScore.setBounds(10, 11, 110, 14);
		add(lblScore);

		JLabel lblMissed = new JLabel("Missed");
		lblMissed.setHorizontalAlignment(SwingConstants.LEFT);
		lblMissed.setForeground(Color.WHITE);
		lblMissed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMissed.setBounds(10, 81, 110, 14);
		add(lblMissed);

		txtScoreHit = new JTextPane();
		txtScoreHit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtScoreHit.setEditable(false);
		txtScoreHit.setBounds(10, 36, 110, 23);
		add(txtScoreHit);

		txtScoreMissed = new JTextPane();
		txtScoreMissed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtScoreMissed.setEditable(false);
		txtScoreMissed.setBounds(10, 106, 110, 23);
		add(txtScoreMissed);
	}
	
	public void updateScore(Scoreboard scoreBoard) {
		txtScoreHit.setText(String.valueOf(scoreBoard.getHit()));
		txtScoreMissed.setText(String.valueOf(scoreBoard.getMiss()));
	}

}
