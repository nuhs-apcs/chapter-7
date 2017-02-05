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
import chess.model.Move;
import chess.model.Piece;
import chess.model.pieces.King;

public class BoardView extends JPanel implements MouseListener {
	
	public static final Color LIGHT_SQUARE_COLOR = Color.WHITE;
	public static final Color DARK_SQUARE_COLOR = Color.LIGHT_GRAY;
	
	private Board board;
	private OnCellClickListener listener;
	private List<Move> moves;
	
	private int cellWidth, cellHeight;
	
	public BoardView(Board board) {
		this.board = board;
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
		List<Piece> pieces = board.getPiecesInPlay();
		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = pieces.get(i);
			if (piece instanceof King) {
				if (board.isCheck(piece.getColor())) {
					g2d.setColor(Color.RED);
					g2d.fillRect(cellWidth * piece.getCol(), cellHeight * piece.getRow(), cellWidth, cellHeight);
				}
			}
			if (moves != null && moves.size() != 0 && moves.get(0).getStartRow() == piece.getRow() && moves.get(0).getStartCol() == piece.getCol()) {
				g2d.setColor(Color.BLUE);
				g2d.fillRect(cellWidth * piece.getCol(), cellHeight * piece.getRow(), cellWidth, cellHeight);
			}
			g2d.drawImage(piece.getImage(), cellWidth * piece.getCol(), cellHeight * piece.getRow(), cellWidth, cellHeight, null);
		}
		
		// draw the valid moves
		if (moves != null) {
			for (Move move : moves) {
				int row = move.getEndRow();
				int col = move.getEndCol();
				if (board.getPieceAt(row, col) == null) {
					g2d.setColor(Color.CYAN);
				} else {
					g2d.setColor(Color.RED);
				}
				int cx = (int) (cellWidth * (col + 0.5));
				int cy = (int) (cellHeight * (row + 0.5));
				int w = (int) (cellWidth / 4.0);
				int h = (int) (cellHeight / 4.0);
				g2d.fillOval(cx - w/2, cy - h/2, w, h);
			}
		}
	}
	
	public void showMoves(List<Move> moves) {
		this.moves = moves;
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
			listener.onCellClick(row, col);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
