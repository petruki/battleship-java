package com.github.petruki.battleship.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.github.petruki.battleship.util.ResourceConstants;
import com.github.petruki.battleship.util.ResourcesCache;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MultiplayerDialog extends JDialog {

	private JButton btnHost;
	private JButton btnJoin;
	private JButton btnStart;
	private JPanel contentPanel = new JPanel();
	private JTextField txtRoomName;
	private JTextField txtPlayerName;
	private JLabel txtStatus;
	private DefaultListModel<String> listPlayersModel;
	
	private boolean lobbyCreated;
	
	public MultiplayerDialog() {
		buildPanel();
	}
	
	private void buildPanel() {
		setTitle("Battleship Online");
		setIconImage(ResourcesCache.getInstance().getImages(ResourceConstants.IMG_HIT));
		setBounds(100, 100, 328, 390);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerUI();
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelRoom = new JPanel();
		contentPanel.add(panelRoom, BorderLayout.NORTH);
		panelRoom.setLayout(new MigLayout("", "[][grow]", "[][]"));
		
		JLabel lblPlayer = new JLabel("Player");
		panelRoom.add(lblPlayer, "cell 0 0,alignx trailing");
		
		txtPlayerName = new JTextField();
		panelRoom.add(txtPlayerName, "cell 1 0,growx");
		txtPlayerName.setColumns(10);
		
		JLabel lblRoom = new JLabel("Room");
		panelRoom.add(lblRoom, "cell 0 1,alignx trailing");
		
		txtRoomName = new JTextField();
		panelRoom.add(txtRoomName, "flowx,cell 1 1,growx");
		txtRoomName.setColumns(10);
		
		btnHost = new JButton("Host");
		btnHost.addActionListener(this::onHost);
		panelRoom.add(btnHost, "cell 1 1");
		
		btnJoin = new JButton("Join");
		btnJoin.addActionListener(this::onJoin);
		panelRoom.add(btnJoin, "cell 1 1");
		
		txtStatus = new JLabel();
		contentPanel.add(txtStatus, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		JList<String> listPlayers = new JList<>();
		listPlayers.setFont(new Font("Tahoma", Font.BOLD, 26));
		listPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPlayersModel = new DefaultListModel<>();
		listPlayers.setModel(listPlayersModel);
		scrollPane.setViewportView(listPlayers);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnStart = new JButton("Start");
		btnStart.setEnabled(false);
		btnStart.setForeground(new Color(255, 255, 255));
		btnStart.setBackground(new Color(128, 0, 0));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnStart.addActionListener(this::onStart);
		buttonPane.add(btnStart);
		
		getRootPane().setDefaultButton(btnStart);
	}
	
	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	private void onHost(ActionEvent event) {
		if (validateMatchParams()) {
			listPlayersModel.addElement(txtPlayerName.getText());
			waitForPlayers();
			txtStatus.setText("Waiting for players...");
			
			btnHost.setEnabled(false);
			btnJoin.setEnabled(false);
		}
	}
	
	private void onJoin(ActionEvent event) {
		if (validateMatchParams()) {
			listPlayersModel.addElement(txtPlayerName.getText());
			txtStatus.setText("Waiting for host to start...");
			
			btnHost.setEnabled(false);
			btnJoin.setEnabled(false);
		}
	}
	
	private void waitForPlayers() {
		// TODO: thread broker
		btnStart.setEnabled(true);
		btnStart.setBackground(new Color(0, 128, 0));
	}
	
	private boolean validateMatchParams() {
		if (txtPlayerName.getText().isBlank()) {
			txtStatus.setText("Must set Player name");
			return false;
		}
		
		if (txtRoomName.getText().isBlank()) {
			txtStatus.setText("Must set Room name");
			return false;
		}
		return true;
	}
	
	private void onStart(ActionEvent event) {
		lobbyCreated = true;
		dispose();
	}

	public boolean isLobbyCreated() {
		return lobbyCreated;
	}

}
