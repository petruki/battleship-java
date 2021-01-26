package com.rogerio.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import com.rogerio.model.Scoreboard;
import com.rogerio.model.Target;

@SuppressWarnings("serial")
public class ControlUI extends JPanel {
	
	private JTextPane txtCoordinate;
	private final MainUI context;
	
	public ControlUI(final MainUI context) {
		this.context = context;
		buildPanel();
	}
	
	private void buildPanel() {
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBackground(new Color(83, 175, 19));
		setBounds(864, 98, 130, 96);
		setLayout(null);

		txtCoordinate = new JTextPane();
		txtCoordinate.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtCoordinate.setBounds(10, 11, 110, 23);
		add(txtCoordinate);

		JButton btnFire = new JButton("Fire!");
		btnFire.setForeground(new Color(255, 255, 255));
		btnFire.setBackground(new Color(128, 0, 0));
		btnFire.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnFire.setBounds(10, 45, 107, 40);
		btnFire.setFocusable(false);
		btnFire.addActionListener(this::onFire);
		add(btnFire);
	}
	
	public void onFire(ActionEvent event) {
		try {
			final BoardUI boardUI = context.getBoardUI();
			final Target result = context
					.getGameController()
					.onFire(boardUI.getTableModel().getBoard(), txtCoordinate.getText());
			
			switch (result.getSlotType()) {
			case HIT:
				if (boardUI.getModel().getValueAt(result.getRowCoord(), result.getColCoord()).equals(result))
					context.getHeaderUI().getTxtMessage().setText("Oops, you already hit that location!");
				else {
					if (context.getGameController().hasSink(boardUI.getTableModel().getBoard(), result.getShipId())) {
						context.getHeaderUI().getTxtMessage().setText("You sank my battleship!");
					} else {
						context.getHeaderUI().getTxtMessage().setText("HIT!");
					}

					updateScoreUI(true);
				}
				break;
			case MISSED:
				context.getHeaderUI().getTxtMessage().setText("You missed.");
				updateScoreUI(false);
				break;
			default:
				break;
			}
			
			boardUI.selectAll();
			boardUI.getTableModel().getTableModel().setValueAt(result, result.getRowCoord(), result.getColCoord());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateScoreUI(boolean hit) {
		Scoreboard scoreBoard = context.getGameController().getScoreBoard();
		
		if (hit) {
			if (scoreBoard.addHit()) {
				context.getHeaderUI().getTimer().cancel();
				context.getHeaderUI().getTxtMessage()
					.setText(String.format("You sank all my battleships, in %s guesses.", 
								scoreBoard.getHit() + scoreBoard.getMiss()));
			}
		} else {
			scoreBoard.addMiss();
		}
		
		context.getScoreUI().updateScore(scoreBoard);
	}

	public JTextPane getTxtCoordinate() {
		return txtCoordinate;
	}

}
