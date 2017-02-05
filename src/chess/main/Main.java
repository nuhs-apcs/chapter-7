package chess.main;

import javax.swing.UIManager;

import chess.model.Board;
import chess.model.GameLoop;
import chess.model.Color;
import chess.model.UserPlayer;
import chess.ui.BoardView;
import chess.ui.GameWindow;

public class Main {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());

		BoardView boardView = new BoardView(new Board());
		GameWindow window = new GameWindow(boardView);
		GameLoop loop = new GameLoop(window, new UserPlayer(Color.WHITE), new UserPlayer(Color.BLACK));
		loop.start();
		window.setVisible(true);
	}
}
