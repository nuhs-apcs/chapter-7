package chess.network;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import chess.model.Board;
import chess.model.Color;
import chess.model.Player;
import chess.model.moves.Move;

public class NetworkPlayerServer extends Player {
	
	private GameServer server;
	
	public NetworkPlayerServer(String name) {
		super(Color.WHITE);
		
		try {
			InetAddress address = InetAddress.getByName("230.0.0.1");
			server = new GameServer(address, 4455, 4456, name);
			server.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Move makeMove(Board board, Move lastMove) {
		if (lastMove != null) sendMove(lastMove);
		return getMove();
	}
	
	public void sendMove(Move move) {
		try {
			server.getOutputStream().writeObject(move);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Server Network Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
	
	public Move getMove() {
		try {
			return (Move) server.getInputStream().readObject();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Server Network Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return null;
	}

}
