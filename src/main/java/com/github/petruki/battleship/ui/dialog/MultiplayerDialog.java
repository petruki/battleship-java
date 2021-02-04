package com.github.petruki.battleship.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.stream.Collectors;

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

import com.github.petruki.battleship.broker.BrokerClient;
import com.github.petruki.battleship.broker.BrokerEvents;
import com.github.petruki.battleship.broker.data.BrokerData;
import com.github.petruki.battleship.broker.data.CreateRoomDTO;
import com.github.petruki.battleship.broker.data.PlayerDTO;
import com.github.petruki.battleship.broker.data.RoomDataDTO;
import com.github.petruki.battleship.model.Player;
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
	private JList<String> listPlayers;
	private DefaultListModel<String> listPlayersModel;
	
	private final BrokerClient client;
	
	private boolean lobbyCreated;
	private boolean matchStarted;
	private boolean host;
	
	public MultiplayerDialog() {
		client = BrokerClient.getInstance();
		client.connect();
		client.subscribe(BrokerEvents.CHECK_CREATE_ROOM.toString(), this::onWaitForRoomToBeCreated);
		client.subscribe(BrokerEvents.ROOM_DATA.toString(), this::onRoomData);
		client.subscribe(BrokerEvents.MATCH_STARTED.toString(), this::onMatchStarted);
		
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
		
		listPlayers = new JList<>();
		listPlayers.setFont(new Font("Tahoma", Font.BOLD, 26));
		listPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPlayersModel = new DefaultListModel<>();
		listPlayers.setModel(listPlayersModel);
		scrollPane.setViewportView(listPlayers);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnStart = new JButton("Start");
		btnStart.setEnabled(true);
		btnStart.setForeground(new Color(255, 255, 255));
		btnStart.setBackground(new Color(128, 0, 0));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnStart.addActionListener(this::onStart);
		buttonPane.add(btnStart);
		
		getRootPane().setDefaultButton(btnStart);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (lobbyCreated && !matchStarted) {
					lobbyCreated = false;
					PlayerDTO playerDTO = new PlayerDTO(txtRoomName.getText(), 
							BrokerEvents.PLAYER_HAS_DISCONNECTED);
					playerDTO.setPlayer(txtPlayerName.getText());
					client.emitEvent(playerDTO);
				}
			}
		});
	}
	
	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
	
	private void onHost(ActionEvent event) {
		if (validateMatchParams()) {
			CreateRoomDTO createRoomDTO = new CreateRoomDTO(txtRoomName.getText(), 
					BrokerEvents.CHECK_CREATE_ROOM);
			createRoomDTO.setPlayer(txtPlayerName.getText());
			client.emitEvent(createRoomDTO);
		}
	}
	
	private void onJoin(ActionEvent event) {
		if (validateMatchParams()) {
			PlayerDTO playerDTO = new PlayerDTO(txtRoomName.getText(), BrokerEvents.PLAYER_HAS_JOINED);
			playerDTO.setPlayer(txtPlayerName.getText());
			client.emitEvent(playerDTO);
			
			listPlayersModel.addElement(txtPlayerName.getText());
			txtStatus.setText("Waiting for host to start...");
			
			disableMatchControls();
		}
	}
	
	private boolean validateMatchParams() {
		if (txtPlayerName.getText().isEmpty()) {
			txtStatus.setText("Must set Player name");
			return false;
		}
		
		if (txtRoomName.getText().isEmpty()) {
			txtStatus.setText("Must set Room name");
			return false;
		}
		return true;
	}
	
	private void onStart(ActionEvent event) {
		matchStarted = true;
		
		Player player = new Player();
		player.setUsername(txtPlayerName.getText());
		player.setRoom(txtRoomName.getText());
		player.setHost(false);
		
		if (event != null) {
			player.setHost(true);
			client.emitEvent(new BrokerData(txtRoomName.getText(), 
					BrokerEvents.MATCH_STARTED.toString()));
		}

		client.setPlayer(player);
		client.unsubscribeAll();
		dispose();
	}
	
    private void onWaitForRoomToBeCreated(Object... args) {
		CreateRoomDTO createRoomDTO = BrokerData.getDTO(CreateRoomDTO.class, args);
		
		if (createRoomDTO.isExist()) {
			txtStatus.setText("Room already exist, try a different one");
		} else {
			lobbyCreated = true;
			host = true;
			listPlayersModel.addElement(txtPlayerName.getText());
			txtStatus.setText("Waiting for players...");
			
			disableMatchControls();
		}
    }
    
    private void onRoomData(Object... args) {
		RoomDataDTO roomDataDTO = BrokerData.getDTO(RoomDataDTO.class, args);
		
		//update player list
		listPlayersModel.clear();
		listPlayersModel.addAll(
				roomDataDTO.getPlayers()
				.stream().map(p -> p.getUsername())
				.collect(Collectors.toList()));
		
		//update lobby status
		txtStatus.setText(roomDataDTO.getMessage());
		lobbyCreated = true;
		
		//update host match control
		if (host && roomDataDTO.getPlayers().size() > 1) {
			btnStart.setEnabled(true);
			btnStart.setBackground(new Color(0, 128, 0));    			
		} else {
			btnStart.setEnabled(false);
			btnStart.setBackground(new Color(128, 0, 0));    		
		}
    }
    
    private void onMatchStarted(Object... args) {
		onStart(null);
    }
    
    private void disableMatchControls() {
    	btnHost.setEnabled(false);
		btnJoin.setEnabled(false);
		txtRoomName.setEnabled(false);
		txtPlayerName.setEnabled(false);
    }

	public boolean isLobbyCreated() {
		return lobbyCreated;
	}

}
