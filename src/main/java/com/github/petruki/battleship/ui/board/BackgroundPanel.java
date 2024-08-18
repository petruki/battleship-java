package com.github.petruki.battleship.ui.board;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	private transient Image backgroundImage;

	public BackgroundPanel(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}

	public void setBackground(Image background) {
		this.backgroundImage = background;
		repaint();
	}
	
}