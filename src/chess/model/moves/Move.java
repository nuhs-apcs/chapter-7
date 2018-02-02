package chess.model.moves;

import java.io.Serializable;

import chess.model.Board;

public interface Move extends Serializable {
	/**
	 * @return the row component of the move dot in the user interface (UI)
	 */
	public int getDisplayRow();
	
	/**
	 * @return the column component of the move dot in the user interface (UI)
	 */
	public int getDisplayCol();

	/**
	 * Apply this move by appropriately modifying the board state
	 * @param board
	 */
	public void apply(Board board);
	
	/**
	 * Undo the actions of {@link #apply(Board)}. It is safe to assume that this will
	 * always be called after {@link #apply(Board)}.
	 * @param board
	 */
	public void unapply(Board board);
}