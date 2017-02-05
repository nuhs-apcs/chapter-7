package chess.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chess.model.Board;

public class GameWindow extends JFrame {
	
	private BoardView boardView;
	
	public GameWindow(BoardView view) {
		boardView = view;
		add(view);
		
		setTitle("Chess Game");
		setSize(1056, 1056);
//		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BoardView getBoardView() {
		return boardView;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				(new GameWindow(new BoardView(new Board()))).setVisible(true);
			}
		});
	}

}
