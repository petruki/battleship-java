package com.github.petruki.battleship.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Objects;

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

public class GameSettingsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<Integer> comboShips;
	private JComboBox<Integer> comboShipSize;
	private JComboBox<String> comboTimeLimit;
	
	private boolean startGame = false;
	private boolean hideTimelimit = false;
	
	public GameSettingsDialog() {
		buildPanel();
	}
	
	public GameSettingsDialog(boolean hideTimelimit) {
		this.hideTimelimit = hideTimelimit;
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Settings");
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setBounds(100, 100, 225, 179);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerUI();
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Ships");
		contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
	
		comboShips = new JComboBox<>();
		comboShips.setModel(new DefaultComboBoxModel<>(new Integer[]{1, 2, 3, 4, 5}));
		comboShips.setSelectedIndex(2);
		contentPanel.add(comboShips, "cell 1 0,growx");

		JLabel lblShipSize = new JLabel("Ship Size");
		contentPanel.add(lblShipSize, "cell 0 1,alignx trailing");

		comboShipSize = new JComboBox<>();
		comboShipSize.setModel(new DefaultComboBoxModel<>(new Integer[]{1, 2, 3, 4}));
		comboShipSize.setSelectedIndex(2);
		contentPanel.add(comboShipSize, "cell 1 1,growx");
		
		if (!hideTimelimit) {
			JLabel lblTimeLimit = new JLabel("Time limit");
			
			comboTimeLimit = new JComboBox<>();
			comboTimeLimit.setModel(new DefaultComboBoxModel<>(new String[]{"0:10", "0:20", "0:30", "0:40", "0:50"}));
			comboTimeLimit.setSelectedIndex(1);
			contentPanel.add(lblTimeLimit, "cell 0 2,alignx trailing");
			contentPanel.add(comboTimeLimit, "cell 1 2,growx");
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnApply = new JButton("Apply");
		buttonPane.add(btnApply);
		btnApply.setForeground(new Color(255, 255, 255));
		btnApply.setBackground(new Color(128, 0, 0));
		btnApply.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnApply.addActionListener(this::onApply);
		
		getRootPane().setDefaultButton(btnApply);
	}
	
	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	private void onApply(ActionEvent event) {
		startGame = true;
		dispose();
	}
	
	public void setSettings(GameSettings gameSettings) {
		comboShips.setSelectedItem(gameSettings.getShips());
		comboShipSize.setSelectedItem(gameSettings.getShipSize());
		
		if (!hideTimelimit)
			comboTimeLimit.setSelectedItem(gameSettings.getTimeLimit());
	}
	
	public GameSettings getSettings() {
		final GameSettings gameSettings = new GameSettings();
		gameSettings.setShips(Integer.parseInt(Objects.requireNonNull(comboShips.getSelectedItem()).toString()));
		gameSettings.setShipSize(Integer.parseInt(Objects.requireNonNull(comboShipSize.getSelectedItem()).toString()));
		
		if (!hideTimelimit) {
			gameSettings.setTimeLimit(Objects.requireNonNull(comboTimeLimit.getSelectedItem()).toString());
		}
		
		return gameSettings;
	}

	public boolean isStartGame() {
		return startGame;
	}

}
