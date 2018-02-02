package chess.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import chess.model.Board;
import chess.model.moves.Move;
import chess.model.pieces.King;
import chess.model.pieces.Piece;

@SuppressWarnings("serial")
public class BoardView extends JPanel implements MouseListener {
	
	public static final Color LIGHT_SQUARE_COLOR = Color.WHITE;
	public static final Color DARK_SQUARE_COLOR = Color.LIGHT_GRAY;
	
	private Board board;
	private OnCellClickListener listener;
	private List<Move> moves;
	private int activeRow, activeCol;
	
	private int cellWidth, cellHeight;
	private boolean flipped;
	
	public BoardView(Board board, boolean flipped) {
		this.board = board;
		this.flipped = flipped;
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		int width = getWidth();
		int height = getHeight();
		cellWidth = width / 8;
		cellHeight = height / 8;
		
		// draw the board squares
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					g2d.setColor(LIGHT_SQUARE_COLOR);
				} else {
					g2d.setColor(DARK_SQUARE_COLOR);
				}
				g2d.fillRect(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
			}
		}
		
		// draw the pieces
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board.getPieceAt(row, col);
				if (piece == null) continue;
				if (piece instanceof King) {
					if (board.isCheck(piece.getColor())) {
						g2d.setColor(Color.RED);
						if (flipped) {
							g2d.fillRect(cellWidth * (7 - col), cellHeight * (7 - row), cellWidth, cellHeight);
						} else {
							g2d.fillRect(cellWidth * col, cellHeight * row, cellWidth, cellHeight);
						}
					}
				}
				if (moves != null && moves.size() != 0 && activeRow == row && activeCol == col) {
					g2d.setColor(Color.BLUE);
					if (flipped) {
						g2d.fillRect(cellWidth * (7 - col), cellHeight * (7 - row), cellWidth, cellHeight);
					} else {
						g2d.fillRect(cellWidth * col, cellHeight * row, cellWidth, cellHeight);
					}
				}
				if (flipped) {
					g2d.drawImage(piece.getImage(), cellWidth * (7 - col), cellHeight * (7 - row), cellWidth, cellHeight, null);
				} else {
					g2d.drawImage(piece.getImage(), cellWidth * col, cellHeight * row, cellWidth, cellHeight, null);
				}
			}
		}
		
		// draw the valid moves
		if (moves != null) {
			for (Move move : moves) {
				if (board.getPieceAt(move.getDisplayRow(), move.getDisplayCol()) == null) {
					g2d.setColor(Color.CYAN);
				} else {
					g2d.setColor(Color.RED);
				}
				int cx, cy;
				if (flipped) {
					cx = (int) (cellWidth * (7 - move.getDisplayRow() + 0.5));
					cy = (int) (cellHeight * (7 - move.getDisplayCol() + 0.5));
				} else {
					cx = (int) (cellWidth * (move.getDisplayCol() + 0.5));
					cy = (int) (cellHeight * (move.getDisplayRow() + 0.5));
				}
				int w = (int) (cellWidth / 4.0);
				int h = (int) (cellHeight / 4.0);
				g2d.fillOval(cx - w/2, cy - h/2, w, h);
			}
		}
	}
	
	public void showMoves(int row, int col, List<Move> moves) {
		this.moves = moves;
		activeRow = row;
		activeCol = col;
		repaint();
	}
	
	public void hideMoves() {
		this.moves = null;
		repaint();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setOnCellClickListener(OnCellClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (listener != null) {
			int row = e.getY() / cellHeight;
			int col = e.getX() / cellWidth;
			if (flipped) {
				listener.onCellClick(7 - row, 7 - col);
			} else {
				listener.onCellClick(row, col);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
