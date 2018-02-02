package chess.main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import chess.model.Board;
import chess.model.Color;
import chess.model.GameLoop;
import chess.model.UserPlayer;
import chess.network.NetworkPlayerServer;
import chess.ui.BoardView;
import chess.ui.GameWindow;

public class NetworkServerMain {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		
		String gameName = JOptionPane.showInputDialog(null, "Enter a name for this game:", "New Game", JOptionPane.PLAIN_MESSAGE);

		if (gameName == null || gameName == "") {
			System.exit(0);
		}
		
		BoardView boardView = new BoardView(new Board(), true);
		GameWindow window = new GameWindow(boardView);
		GameLoop loop = new GameLoop(window, new NetworkPlayerServer(gameName), new UserPlayer(Color.BLACK));
		loop.start();
		window.setVisible(true);
	}
}
