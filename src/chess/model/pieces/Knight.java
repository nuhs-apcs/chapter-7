package chess.model.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.model.Board;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Color;
import chess.ui.ImageUtil;

public class Knight extends Piece {

	public Knight(Color color, int row, int col) {
		super(color, row, col, ImageUtil.getKnight(color));
	}

	@Override
	public List<Move> getValidMoves(Board board) {
		List<Move> validMoves = new ArrayList<Move>();
		return validMoves;
	}

}