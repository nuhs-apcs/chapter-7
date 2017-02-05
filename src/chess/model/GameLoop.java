package chess.model;

import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import chess.network.NetworkPlayerClient;
import chess.network.NetworkPlayerServer;
import chess.ui.BoardView;
import chess.ui.GameWindow;
import chess.ui.OnCellClickListener;

public class GameLoop extends Thread {
	
	private GameWindow window;
	private BoardView view;
	
	private Player whitePlayer, blackPlayer;
	private Board board;
	
	private List<Move> validMoves;
	private Move lastMove;
	
	public GameLoop(GameWindow window, Player whitePlayer, Player blackPlayer) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		this.window = window;
		this.view = window.getBoardView();
		this.board = view.getBoard();
	}
	
	@Override
	public void run() {
		Color winner;
		while (true) {
			makeMove(whitePlayer);
			if (board.isCheckmate(Color.BLACK)) {
				if (blackPlayer instanceof NetworkPlayerClient) {
					((NetworkPlayerClient) blackPlayer).sendMove(lastMove);
				}
				winner = Color.WHITE;
				break;
			}
			makeMove(blackPlayer);
			if (board.isCheckmate(Color.WHITE)) {
				if (whitePlayer instanceof NetworkPlayerServer) {
					((NetworkPlayerServer) whitePlayer).sendMove(lastMove);
				}
				winner = Color.BLACK;
				break;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(window, winner + " won!", "Game Result", JOptionPane.PLAIN_MESSAGE);
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
	
	public void makeMove(Player player) {
		if (player instanceof UserPlayer) {
			final UserPlayer userPlayer = (UserPlayer) player;
			view.setOnCellClickListener(new OnCellClickListener() {

				@Override
				public void onCellClick(int row, int col) {
					if (validMoves != null) {
						if (validMoves.size() != 0 && row == validMoves.get(0).getStartRow() && col == validMoves.get(0).getStartCol()) {
							view.hideMoves();
							validMoves = null;
							return;
						}
						for (Move m : validMoves) {
							if (m.getEndRow() == row && m.getEndCol() == col) {
								view.hideMoves();
								validMoves = null;
								userPlayer.onUserMove(m);
								return;
							}
						}
					}
					Piece piece = board.getPieceAt(row, col);
					if (piece != null && piece.getColor() == userPlayer.getColor()) {
						validMoves = board.getValidMoves(piece, true);
						view.showMoves(validMoves);
					}
				}
				
			});
		}
		List<Move> validMoves = board.getValidMoves(player.getColor(), true);
		Move move = player.makeMove(board, lastMove);
		lastMove = move;
		view.setOnCellClickListener(null);
		if (validMoves.contains(move)) {
			board.apply(move);
		} else {
			JOptionPane.showMessageDialog(null, "Invalid move from " + player.getColor() + " (" + player.getClass().getSimpleName() + ")", "Move Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		view.repaint();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
