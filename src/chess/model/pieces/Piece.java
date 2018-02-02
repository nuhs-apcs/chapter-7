package chess.model.pieces;

import java.awt.Image;
import java.util.List;

import chess.model.Board;
import chess.model.Color;
import chess.model.moves.Move;

public abstract class Piece {
	
	private Color color;
	private Image image;
	
	public Piece(Color color, Image image) {
		this.color = color;
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract List<Move> getValidMoves(Board board, int row, int col);
}
