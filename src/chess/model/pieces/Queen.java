package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.model.Board;
import chess.model.Color;
import chess.model.moves.Move;
import chess.ui.ImageUtil;

public class Queen extends Piece {

	public Queen(Color color) {
		super(color, ImageUtil.getQueen(color));
	}

	@Override
	public List<Move> getValidMoves(Board board, int row, int col) {
		List<Move> validMoves = new ArrayList<Move>();
		return validMoves;
	}

}
