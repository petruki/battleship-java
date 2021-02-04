package com.github.petruki.battleship.ui.mp;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.github.petruki.battleship.model.Player;
import com.github.petruki.battleship.model.ScoreboardOnline;

@SuppressWarnings("serial")
public class EndGameScoreMPUI extends JPanel {
	
	private JTable table;

	public EndGameScoreMPUI() {
		buildPanel();
	}

	private void buildPanel() {
		setBounds(310, 500, 397, 261);
		setBackground(new Color(83, 175, 19));
		setLayout(null);
		setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 377, 216);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Scoreboard");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(240, 8, 147, 15);
		add(lblNewLabel);
	}
	
	public void showScore(ScoreboardOnline scoreBoard) {
		setVisible(true);
		
		DefaultTableModel scoreModel = new DefaultTableModel(
				new String[][] {},
				new String[] {"Player", "Score"});
		
		for (Player	player : scoreBoard.getPlayers()) {
			scoreModel.addRow(new Object[] { player.getUsername(), player.getScore() });
		}
		
		table.setModel(scoreModel);
	}
}
