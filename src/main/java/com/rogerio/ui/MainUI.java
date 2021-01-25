package com.rogerio.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.rogerio.util.LoadImage;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

	private BackgroundPanel contentPane;
	private JTable table;

	public MainUI() {
		setTitle("Battleship Java");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 900);
		setResizable(false);
		centerUI();

		contentPane = new BackgroundPanel(LoadImage.load("board.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().setLayout(null);

		table = new JTable() {{
            setOpaque(false);
            setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
                setOpaque(false);
            }});
        }};
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(97);
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null},
					{null, null, null, null, new ImageIcon(LoadImage.load("ship.png")), null, null},
					{null, null, null, null, new ImageIcon(LoadImage.load("miss.png")), null, null},
					{null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null},
				},
				new String[] {
						"0", "1", "2", "3", "4", "5", "6"
				}
				) {
			@Override
			public Class<?> getColumnClass(int column) {
				return ImageIcon.class;
			}
		});
		table.setBounds(172, 98, 676, 676);
		table.setBackground(new Color(0, 0, 0, 0));
		table.setShowGrid(false);
		getContentPane().add(table);

		JPanel panelControl = new JPanel();
		panelControl.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelControl.setBackground(new Color(83, 175, 19));
		panelControl.setBounds(864, 98, 130, 96);
		getContentPane().add(panelControl);
		panelControl.setLayout(null);

		JTextPane txtCoordinate = new JTextPane();
		txtCoordinate.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtCoordinate.setText("A2");
		txtCoordinate.setBounds(10, 11, 110, 23);
		panelControl.add(txtCoordinate);

		JButton btnFire = new JButton("Fire!");
		btnFire.setForeground(new Color(255, 255, 255));
		btnFire.setBackground(new Color(128, 0, 0));
		btnFire.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnFire.setBounds(10, 45, 107, 40);
		btnFire.setFocusable(false);
		panelControl.add(btnFire);

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(83, 175, 19));
		panelHeader.setBounds(0, 0, 1018, 46);
		getContentPane().add(panelHeader);
		panelHeader.setLayout(null);

		JLabel txtMessage = new JLabel("You missed.");
		txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMessage.setForeground(new Color(255, 255, 255));
		txtMessage.setBounds(10, 11, 388, 24);
		panelHeader.add(txtMessage);

		JLabel txtTimer = new JLabel("0:00");
		txtTimer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimer.setForeground(Color.WHITE);
		txtTimer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimer.setBounds(471, 11, 68, 24);
		panelHeader.add(txtTimer);

		JButton btnReload = new JButton("Reload");
		btnReload.setForeground(new Color(255, 255, 255));
		btnReload.setBackground(new Color(0, 128, 0));
		btnReload.setBounds(903, 10, 87, 30);
		btnReload.setFocusable(false);
		panelHeader.add(btnReload);

		JPanel panelScore = new JPanel();
		panelScore.setLayout(null);
		panelScore.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelScore.setBackground(new Color(83, 175, 19));
		panelScore.setBounds(864, 207, 130, 144);
		getContentPane().add(panelScore);

		JLabel lblScore = new JLabel("Hit");
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setForeground(new Color(255, 255, 255));
		lblScore.setBounds(10, 11, 110, 14);
		panelScore.add(lblScore);

		JLabel lblMissed = new JLabel("Missed");
		lblMissed.setHorizontalAlignment(SwingConstants.LEFT);
		lblMissed.setForeground(Color.WHITE);
		lblMissed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMissed.setBounds(10, 81, 110, 14);
		panelScore.add(lblMissed);

		JTextPane txtScoreHit = new JTextPane();
		txtScoreHit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtScoreHit.setText("10");
		txtScoreHit.setEditable(false);
		txtScoreHit.setBounds(10, 36, 110, 23);
		panelScore.add(txtScoreHit);

		JTextPane txtScoreMissed = new JTextPane();
		txtScoreMissed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtScoreMissed.setText("5");
		txtScoreMissed.setEditable(false);
		txtScoreMissed.setBounds(10, 106, 110, 23);
		panelScore.add(txtScoreMissed);
	}

	private void centerUI() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}

	class BackgroundPanel extends JPanel {

		private Image background;

		public BackgroundPanel(Image background) {
			this.background = background;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
		}
	}
}
