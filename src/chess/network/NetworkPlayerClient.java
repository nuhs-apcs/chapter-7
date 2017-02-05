package chess.network;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import chess.model.Board;
import chess.model.Color;
import chess.model.Move;
import chess.model.Player;
import chess.ui.GameConnectWindow;

public class NetworkPlayerClient extends Player {
	
	private GameClient client;
	
	public NetworkPlayerClient() {
		super(Color.BLACK);
		
		try {
			InetAddress address = InetAddress.getByName("230.0.0.1");
			client = new GameClient(address, 4456, 4455);
			GameConnectWindow.showConnectWindow(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Move makeMove(Board board, Move lastMove) {
		if (lastMove != null) sendMove(lastMove);
		Move m = getMove();
		board.apply(m);
		return m;
	}
	
	public void sendMove(Move m) {
		try {
			client.getOutputStream().writeObject(m);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Client Network Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
	
	public Move getMove() {
		try {
			return (Move) client.getInputStream().readObject();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Client Network Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return null;
	}

}
