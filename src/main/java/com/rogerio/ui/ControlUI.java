package com.rogerio.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rogerio.model.SlotType;
import com.rogerio.model.Target;

/**
 * @author petruki (Roger Floriano)
 */
@SuppressWarnings("serial")
public class ControlUI extends JPanel {
	
	private static final Logger logger = LogManager.getLogger(ControlUI.class);
	
	private JTextPane txtCoordinate;
	private final MainUI context;
	
	public ControlUI(final MainUI context) {
		this.context = context;
		buildPanel();
	}
	
	private void buildPanel() {
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
			final HeaderUI headerUI = context.getHeaderUI();
			final Target result = context
					.getGameController()
					.onFire(boardUI.getBoard(), txtCoordinate.getText());
			
			if (result.getSlotType() == SlotType.HIT) {
				onHit(boardUI, headerUI, result);
			} else {
				onMiss(headerUI);
			}
			
			if (context.getGameController().activateRadar()) {
				headerUI.updateText("Radar is activated for 3 seconds!");
			}
			
			//updates the board
			boardUI.updateBoard(result, result.getRowCoord(), result.getColCoord());
		} catch (Exception e) {
			logger.error(e);
			if (event != null)
				JOptionPane.showMessageDialog(context, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void onHit(final BoardUI boardUI, final HeaderUI headerUI, final Target result) {
		if (boardUI.getModel().getValueAt(result.getRowCoord(), result.getColCoord()).equals(result))
			headerUI.updateText("Oops, you already hit that location!");
		else {
			if (context.getGameController().hasSink(boardUI.getBoard(), result.getShipId())) {
				headerUI.updateText("You sank my battleship!");
			} else {
				headerUI.updateText("HIT!");
			}

			headerUI.updateScoreUI(true);
		}
	}

	private void onMiss(final HeaderUI headerUI) {
		headerUI.updateText("You missed.");
		headerUI.updateScoreUI(false);
	}
	
	public void updateCoordinates(String coordinates) {
		txtCoordinate.setText(coordinates);
	}

}
