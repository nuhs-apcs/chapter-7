package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.model.moves.Move;
import chess.model.moves.SimpleMove;
import chess.model.pieces.Bishop;
import chess.model.pieces.King;
import chess.model.pieces.Knight;
import chess.model.pieces.Pawn;
import chess.model.pieces.Piece;
import chess.model.pieces.Queen;
import chess.model.pieces.Rook;

public class Board {
	
	private List<Move> moveHistory;
	
	private Piece[][] pieces;
	
	private King whiteKing, blackKing;
	
	public Board() {
		moveHistory = new ArrayList<>();
		
		pieces = new Piece[8][8];
		
		whiteKing = new King(Color.WHITE);
		blackKing = new King(Color.BLACK);
		
		pieces[0][4] = blackKing;
		pieces[7][3] = whiteKing;
		
		pieces[1][4] = new Pawn(Color.BLACK);
		pieces[6][3] = new Pawn(Color.WHITE);
	}
	
	public Piece getPieceAt(int row, int col) {
		if (row < 0 || row >= 8 || col < 0 || col >= 8) {
			return null;
		} else {
			return pieces[row][col];
		}
	}
	
	public void setPieceAt(int row, int col, Piece piece) {
		pieces[row][col] = piece;
	}
	
	public int[] getPieceLocation(Piece piece) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (pieces[row][col] == piece) {
					return new int[]{row, col};
				}
			}
		}
		return null;
	}
	
	public void apply(Move move) {
		move.apply(this);
		moveHistory.add(0, move);
	}
	
	public void undo() {
		Move lastMove = moveHistory.remove(0);
		lastMove.unapply(this);
	}

	private List<Move> getValidMovesIgnoreCheck(Color color) {
		List<Move> allValidMoves = new ArrayList<>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = pieces[row][col];
				if (piece == null || piece.getColor() != color) continue;
				allValidMoves.addAll(piece.getValidMoves(this, row, col));
			}
		}
		return allValidMoves;
	}
	
	public boolean isCheck(Color color) {
		Piece king = (color == Color.BLACK) ? blackKing : whiteKing;
		int[] kingLoc = getPieceLocation(king);
		int kingRow = kingLoc[0], kingCol = kingLoc[1];
		List<Move> possibleNextMoves = getValidMovesIgnoreCheck(color.opposite());
		for (Move move : possibleNextMoves) {
			if (move instanceof SimpleMove) {
				SimpleMove simpleMove = (SimpleMove) move;
				if (simpleMove.getEndRow() == kingRow && simpleMove.getEndCol() == kingCol) {
					return true;
				}	
			}
		}
		return false;
	}
	
	public List<Move> getValidMoves(Piece piece) {
		int[] pieceLoc = getPieceLocation(piece);
		int pieceRow = pieceLoc[0], pieceCol = pieceLoc[1];
		List<Move> moves = piece.getValidMoves(this, pieceRow, pieceCol);
		int i = 0;
		while (i < moves.size()) {
			Move move = moves.get(i);
			apply(move);
			if (isCheck(piece.getColor())) {
				moves.remove(i);
			} else {
				i++;
			}
			undo();
		}
		return moves;
	}

	public List<Move> getValidMoves(Color color) {
		List<Move> allValidMoves = new ArrayList<>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = pieces[row][col];
				if (piece == null || piece.getColor() != color) continue;
				allValidMoves.addAll(getValidMoves(piece));
			}
		}
		return allValidMoves;
	}
	
	public boolean isCheckmate(Color color) {
		return getValidMoves(color).size() == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = getPieceAt(row, col);
				if (piece == null) {
					builder.append(" ");
				} else if (piece instanceof Pawn) {
					builder.append("P");
				} else if (piece instanceof Knight) {
					builder.append("N");
				} else if (piece instanceof Bishop) {
					builder.append("B");
				} else if (piece instanceof Rook) {
					builder.append("R");
				} else if (piece instanceof Queen) {
					builder.append("Q");
				} else if (piece instanceof King) {
					builder.append("K");
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}

}
