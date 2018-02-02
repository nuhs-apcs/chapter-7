package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.model.Board;
import chess.model.Color;
import chess.model.moves.Move;
import chess.model.moves.SimpleMove;
import chess.ui.ImageUtil;

public class Pawn extends Piece {

	public Pawn(Color color) {
		super(color, ImageUtil.getPawn(color));
	}

	@Override
	public List<Move> getValidMoves(Board board, int row, int col) {
		List<Move> validMoves = new ArrayList<Move>();
		if (getColor() == Color.BLACK) {
			if (row != 7) {
				if (board.getPieceAt(row + 1, col) == null) {
					validMoves.add(new SimpleMove(row, col, row + 1, col));
					if (row == 1 && board.getPieceAt(row + 2, col) == null) {
						validMoves.add(new SimpleMove(row, col, row + 2, col));
					}
				}
				Piece capture1 = board.getPieceAt(row + 1, col + 1);
				Piece capture2 = board.getPieceAt(row + 1, col - 1);
				if (capture1 != null && capture1.getColor() == Color.WHITE) {
					validMoves.add(new SimpleMove(row, col, row + 1, col + 1));
				}
				if (capture2 != null && capture2.getColor() == Color.WHITE) {
					validMoves.add(new SimpleMove(row, col, row + 1, col - 1));
				}
			}
		} else {
			if (row != 0) {
				if (board.getPieceAt(row - 1, col) == null) {
					validMoves.add(new SimpleMove(row, col, row - 1, col));
					if (row == 6 && board.getPieceAt(row - 2, col) == null) {
						validMoves.add(new SimpleMove(row, col, row - 2, col));
					}
				}
				Piece capture1 = board.getPieceAt(row - 1, col + 1);
				Piece capture2 = board.getPieceAt(row - 1, col - 1);
				if (capture1 != null && capture1.getColor() == Color.BLACK) {
					validMoves.add(new SimpleMove(row, col, row - 1, col + 1));
				}
				if (capture2 != null && capture2.getColor() == Color.BLACK) {
					validMoves.add(new SimpleMove(row, col, row - 1, col - 1));
				}
			}
		}
		return validMoves;
	}

}
