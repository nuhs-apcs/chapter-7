package chess.model;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
	
	private int row, col;
	private Color color;
	private Image image;
	
	public Piece(Color color, int row, int col, Image image) {
		this.row = row;
		this.col = col;
		this.color = color;
		this.image = image;
	}
	
	public Piece copy() {
		try {
			return (Piece) (getClass().getConstructors()[0].newInstance(color, row, col));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int newRow) {
		row = newRow;
	}
	
	public void setCol(int newCol) {
		col = newCol;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract List<Move> getValidMoves(Board board);
	
	public static List<Move> exploreDirection(Piece source, Board board, int rowIncr, int colIncr) {
		List<Move> validMoves = new ArrayList<Move>();
		int row = source.getRow();
		int col = source.getCol();
		Piece piece = null;
		while(true) {
			row += rowIncr;
			col += colIncr;
			if (row < 0 || col < 0 || row >= 8 || col >= 8) {
				break;
			}
			piece = board.getPieceAt(row, col);
			if (piece == null) {
				validMoves.add(new Move(source, row, col));
			} else if (piece.getColor() != source.getColor()) {
				validMoves.add(new Move(source, row, col));
				break;
			} else {
				break;
			}
		}
		return validMoves;
	}
	
	public static List<Move> explorePerimeter(Piece source, Board board, int rowIncr, int colIncr) {
		List<Move> validMoves = new ArrayList<Move>();
		int row = source.getRow();
		int col = source.getCol();
		int[][] positions = {
				{ row + rowIncr, col + colIncr },
				{ row + rowIncr, col - colIncr },
				{ row - rowIncr, col + colIncr },
				{ row - rowIncr, col - colIncr }
		};
		for (int[] pos : positions) {
			if (pos[0] >= 0 && pos[1] >= 0 && pos[0] < 8 && pos[1] < 8) {
				Piece piece = board.getPieceAt(pos[0], pos[1]);
				if (piece == null || piece.getColor() != source.getColor()) {
					validMoves.add(new Move(source, pos[0], pos[1]));
				}
			}
		}
		return validMoves;
	}

}
