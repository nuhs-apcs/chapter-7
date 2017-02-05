package chess.model;

import java.io.Serializable;

public class Move implements Serializable {
	
	private int startRow, startCol, endRow, endCol;
	
	public Move(int startRow, int startCol, int endRow, int endCol) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;
	}
	
	public Move(Piece piece, int endRow, int endCol) {
		this(piece.getRow(), piece.getCol(), endRow, endCol);
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
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move m = (Move) o;
			return m.getStartRow() == startRow 
					&& m.getStartCol() == startCol
					&& m.getEndRow() == endRow
					&& m.getEndCol() == endCol;
		} else {
			return false;
		}
	}
	
}
