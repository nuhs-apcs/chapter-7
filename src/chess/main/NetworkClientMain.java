package chess.main;

import javax.swing.UIManager;

import chess.model.Board;
import chess.model.GameLoop;
import chess.model.Color;
import chess.model.UserPlayer;
import chess.network.NetworkPlayerClient;
import chess.ui.BoardView;
import chess.ui.GameWindow;

public class NetworkClientMain {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());

		BoardView boardView = new BoardView(new Board());
		GameWindow window = new GameWindow(boardView);
		GameLoop loop = new GameLoop(window, new UserPlayer(Color.WHITE), new NetworkPlayerClient());
		loop.start();
		window.setVisible(true);
	}
}
       