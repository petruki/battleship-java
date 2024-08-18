package com.github.petruki.battleship.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * @author petruki (Roger Floriano)
 */
public abstract class AbstractControlUI extends JPanel {
	
	protected JTextPane txtCoordinate;
	protected final transient MainUIActionEvent context;
	
	protected AbstractControlUI(final MainUIActionEvent context) {
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
	
	public void updateCoordinates(String coordinates) {
		txtCoordinate.setText(coordinates);
	}
	
	public String getCoordinates() {
		return txtCoordinate.getText();
	}
	
	public abstract void onFire(ActionEvent event);

}
