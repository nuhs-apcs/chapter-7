package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.model.Board;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Color;
import chess.ui.ImageUtil;

public class Pawn extends Piece {
	
	public Pawn(Color color, int row, int col) {
		super(color, row, col, ImageUtil.getPawn(color));
	}

	@Override
	public List<Move> getValidMoves(Board board) {
		List<Move> validMoves = new ArrayList<Move>();
		int row = getRow();
		int col = getCol();
		if (getColor() == Color.BLACK) {
			if (row != 7) {
				if (board.getPieceAt(row + 1, col) == null) {
					validMoves.add(new Move(this, row + 1, col));
					if (row == 1 && board.getPieceAt(row + 2, col) == null) {
						validMoves.add(new Move(this, row + 2, col));
					}
				}
				Piece capture1 = board.getPieceAt(row + 1, col + 1);
				Piece capture2 = board.getPieceAt(row + 1, col - 1);
				if (capture1 != null && capture1.getColor() == Color.WHITE) {
					validMoves.add(new Move(this, row + 1, col + 1));
				}
				if (capture2 != null && capture2.getColor() == Color.WHITE) {
					validMoves.add(new Move(this, row + 1, col - 1));
				}
			}
		} else {
			if (row != 0) {
				if (board.getPieceAt(row - 1, col) == null) {
					validMoves.add(new Move(this, row - 1, col));
					if (row == 6 && board.getPieceAt(row - 2, col) == null) {
						validMoves.add(new Move(this, row - 2, col));
					}
				}
				Piece capture1 = board.getPieceAt(row - 1, col + 1);
				Piece capture2 = board.getPieceAt(row - 1, col - 1);
				if (capture1 != null && capture1.getColor() == Color.BLACK) {
					validMoves.add(new Move(this, row - 1, col + 1));
				}
				if (capture2 != null && capture2.getColor() == Color.BLACK) {
					validMoves.add(new Move(this, row - 1, col - 1));
				}
			}
		}
		return validMoves;
	}

}
