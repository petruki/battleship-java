package com.github.petruki.battleship.ui.board;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {

	private Image background;

	public BackgroundPanel(Image background) {
		this.background = background;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
	}

	public void setBackground(Image background) {
		this.background = background;
		repaint();
	}
	
}