package com.github.petruki.battleship.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.petruki.battleship.model.GameSettings;
import com.github.petruki.battleship.util.ResourceConstants;
import com.github.petruki.battleship.util.ResourcesCache;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class GameSettingsDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JComboBox<Integer> comboShips;
	private JComboBox<Integer> comboShipSize;
	private JComboBox<String> comboTimeLimit;
	private boolean startGame = false;
	
	public GameSettingsDialog() {
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Settings");
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setBounds(100, 100, 225, 179);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerUI();
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Ships");
		contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
	
		comboShips = new JComboBox<>();
		comboShips.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4, 5}));
		comboShips.setSelectedIndex(2);
		contentPanel.add(comboShips, "cell 1 0,growx");

		JLabel lblShipSize = new JLabel("Ship Size");
		contentPanel.add(lblShipSize, "cell 0 1,alignx trailing");

		comboShipSize = new JComboBox<>();
		comboShipSize.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4}));
		comboShipSize.setSelectedIndex(2);
		contentPanel.add(comboShipSize, "cell 1 1,growx");

		JLabel lblTimeLimit = new JLabel("Time limit");
		contentPanel.add(lblTimeLimit, "cell 0 2,alignx trailing");

		comboTimeLimit = new JComboBox<>();
		comboTimeLimit.setModel(new DefaultComboBoxModel<String>(new String[] {"0:10", "0:20", "0:30", "0:40", "0:50"}));
		comboTimeLimit.setSelectedIndex(1);
		contentPanel.add(comboTimeLimit, "cell 1 2,growx");

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("Apply");
		buttonPane.add(okButton);
		okButton.setForeground(new Color(255, 255, 255));
		okButton.setBackground(new Color(128, 0, 0));
		okButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		getRootPane().setDefaultButton(okButton);
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame = true;
				dispose();
			}
		});
	}
	
	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	public void setSettings(GameSettings gameSettings) {
		comboShips.setSelectedItem(gameSettings.getShips());
		comboShipSize.setSelectedItem(gameSettings.getShipSize());
		comboTimeLimit.setSelectedItem(gameSettings.getTimeLimit());
	}
	
	public GameSettings getSettings() {
		final GameSettings gameSettings = new GameSettings();
		gameSettings.setShips(Integer.parseInt(comboShips.getSelectedItem().toString()));
		gameSettings.setShipSize(Integer.parseInt(comboShipSize.getSelectedItem().toString()));
		gameSettings.setTimeLimit(comboTimeLimit.getSelectedItem().toString());
		
		return gameSettings;
	}

	public boolean isStartGame() {
		return startGame;
	}

}
