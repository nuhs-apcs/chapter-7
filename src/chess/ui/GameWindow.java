package chess.ui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
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

}
