package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.model.Board;
import chess.model.Color;
import chess.model.moves.Move;
import chess.ui.ImageUtil;

public class Knight extends Piece {

	public Knight(Color color) {
		super(color, ImageUtil.getKnight(color));
	}

	@Override
	public List<Move> getValidMoves(Board board, int row, int col) {
		List<Move> validMoves = new ArrayList<Move>();
		return validMoves;
	}

}
