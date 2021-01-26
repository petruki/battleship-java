package com.rogerio.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class HeaderUI extends JPanel {
	
	private JLabel txtMessage;
	private final MainUI context;
	
	public HeaderUI(final MainUI context) {
		this.context = context;
		buildPanel();
	}
	
	private void buildPanel() {
		setBackground(new Color(83, 175, 19));
		setBounds(0, 0, 1018, 46);
		setLayout(null);

		txtMessage = new JLabel();
		txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMessage.setForeground(new Color(255, 255, 255));
		txtMessage.setBounds(10, 11, 388, 24);
		add(txtMessage);

		JLabel txtTimer = new JLabel("0:00");
		txtTimer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimer.setForeground(Color.WHITE);
		txtTimer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimer.setBounds(471, 11, 68, 24);
		add(txtTimer);

		JButton btnReload = new JButton("Reload");
		btnReload.setForeground(new Color(255, 255, 255));
		btnReload.setBackground(new Color(0, 128, 0));
		btnReload.setBounds(903, 10, 87, 30);
		btnReload.setFocusable(false);
		btnReload.addActionListener(context::onStartNewGame);
		add(btnReload);
	}

	public JLabel getTxtMessage() {
		return txtMessage;
	}

}
