package com.rogerio;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import com.rogerio.ui.MainUI;

public class GameInitializer {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
