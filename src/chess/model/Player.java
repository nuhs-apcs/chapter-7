package chess.model;

import chess.model.moves.Move;

public abstract class Player {
	
	private Color color;
	
	public Player(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract Move makeMove(Board board, Move lastMove);
	
}
