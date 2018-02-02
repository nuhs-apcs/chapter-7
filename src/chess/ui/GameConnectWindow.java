package chess.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import chess.network.GameClient;
import chess.network.GameClientListener;
import chess.network.GameInfo;

@SuppressWarnings("serial")
public class GameConnectWindow extends JFrame implements GameClientListener {
	
	private DefaultListModel<GameInfo> listModel;
	private JList<GameInfo> list;
	private JButton connectButton;
	private Font font;
	
	public GameConnectWindow(GameClient client) {
		client.setGameClientListener(this);
		client.scan();
		
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel conns = new JLabel("Games");
		conns.setHorizontalAlignment(SwingConstants.CENTER);
		conns.setFont(font);
		
		panel.add(conns, BorderLayout.PAGE_START);
		
		list = new JList<GameInfo>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(5);
		list.setFont(font);
		
		listModel = new DefaultListModel<GameInfo>();
		list.setModel(listModel);
		
		panel.add(list, BorderLayout.CENTER);
		
		connectButton = new JButton("Connect");
		connectButton.setFont(font);
		final JFrame frame = this;
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameInfo game = list.getSelectedValue();
					if (game == null) return;
					client.connect(game);
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		panel.add(connectButton, BorderLayout.PAGE_END);
		
		add(panel);
		
		setTitle("Client Connect Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
	}
	
	public static void showConnectWindow(GameClient client) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				GameConnectWindow window = new GameConnectWindow(client);
				window.setVisible(true);
			}
			
		});
		
		while (!client.isConnected()) {
			Thread.yield();
		}
	}

	@Override
	public void onAddGame(GameInfo game) {
		listModel.addElement(game);
	}

	@Override
	public void onRemoveGame(GameInfo game) {
		listModel.removeElement(game);
	}

}
