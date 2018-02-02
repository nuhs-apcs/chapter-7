package chess.model.moves;

import chess.model.Board;
import chess.model.pieces.Piece;

@SuppressWarnings("serial")
public class SimpleMove implements Move {
	private int startRow, startCol, endRow, endCol;
	private transient Piece capturedPiece;
	
	public SimpleMove(int startRow, int startCol, int endRow, int endCol) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;
	}
	
	public int getStartRow() {
		return startRow;
	}
	
	public int getStartCol() {
		return startCol;
	}
	
	public int getEndRow() {
		return endRow;
	}
	
	public int getEndCol() {
		return endCol;
	}

	@Override
	public int getDisplayRow() {
		return endRow;
	}

	@Override
	public int getDisplayCol() {
		return endCol;
	}

	@Override
	public void apply(Board board) {
		Piece piece = board.getPieceAt(startRow, startCol);
		capturedPiece = board.getPieceAt(endRow, endCol);
		board.setPieceAt(endRow, endCol, piece);
		board.setPieceAt(startRow, startCol, null);
	}

	@Override
	public void unapply(Board board) {
		Piece piece = board.getPieceAt(endRow, endCol);
		board.setPieceAt(startRow, startCol, piece);
		board.setPieceAt(endRow, endCol, capturedPiece);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SimpleMove) {
			SimpleMove move = (SimpleMove) other;
			return startRow == move.startRow && startCol == move.startCol
					&& endRow == move.endRow && endCol == move.endCol;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + startRow + ", " + startCol + ") -> (" + endRow + ", " + endCol + ")";
	}
}
