package chess.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import chess.model.pieces.Bishop;
import chess.model.pieces.King;
import chess.model.pieces.Knight;
import chess.model.pieces.Pawn;
import chess.model.pieces.Queen;
import chess.model.pieces.Rook;

public class Board {
	
	private List<Piece> piecesInPlay;
	private Stack<Piece> capturedPieces;
	
	private King whiteKing, blackKing;
	
	public Board() {
		piecesInPlay = new ArrayList<Piece>();
		capturedPieces = new Stack<Piece>();
		
		piecesInPlay.add(new Rook(Color.BLACK, 0, 0));
		piecesInPlay.add(new Knight(Color.BLACK, 0, 1));
		piecesInPlay.add(new Bishop(Color.BLACK, 0, 2));
		piecesInPlay.add(new Queen(Color.BLACK, 0, 3));
		
		blackKing = new King(Color.BLACK, 0, 4);
		
		piecesInPlay.add(blackKing);
		piecesInPlay.add(new Bishop(Color.BLACK, 0, 5));
		piecesInPlay.add(new Knight(Color.BLACK, 0, 6));
		piecesInPlay.add(new Rook(Color.BLACK, 0, 7));
		
		for (int i = 0; i < 8; i++) {
			piecesInPlay.add(new Pawn(Color.BLACK, 1, i));
			piecesInPlay.add(new Pawn(Color.WHITE, 6, i));
		}
		
		piecesInPlay.add(new Rook(Color.WHITE, 7, 0));
		piecesInPlay.add(new Knight(Color.WHITE, 7, 1));
		piecesInPlay.add(new Bishop(Color.WHITE, 7, 2));
		piecesInPlay.add(new Queen(Color.WHITE, 7, 3));
		
		whiteKing = new King(Color.WHITE, 7, 4);
		
		piecesInPlay.add(whiteKing);
		piecesInPlay.add(new Bishop(Color.WHITE, 7, 5));
		piecesInPlay.add(new Knight(Color.WHITE, 7, 6));
		piecesInPlay.add(new Rook(Color.WHITE, 7, 7));
	}
	
	public List<Piece> getPiecesInPlay() {
		return piecesInPlay;
	}
	
	public boolean apply(Move m) {
		if (m == null) return false;
		Piece piece = getPieceAt(m.getStartRow(), m.getStartCol());
		if (piece == null) return false;
		Piece capturedPiece = getPieceAt(m.getEndRow(), m.getEndCol());
		if (capturedPiece != null) {
			piecesInPlay.remove(capturedPiece);
		}
		capturedPieces.add(capturedPiece);
		piece.setRow(m.getEndRow());
		piece.setCol(m.getEndCol());
		return true;
	}
	
	public boolean unapply(Move m) {
		Piece piece = getPieceAt(m.getEndRow(), m.getEndCol());
		if (piece == null) return false;
		Piece captured = capturedPieces.pop();
		if (captured != null) {
			piecesInPlay.add(captured);
		}
		piece.setRow(m.getStartRow());
		piece.setCol(m.getStartCol());
		return true;
	}
	
	public List<Move> getValidMoves(Piece piece, boolean removeChecks) {
		List<Move> moves = piece.getValidMoves(this);
		if (removeChecks) {
			removeChecks(moves, piece.getColor());
		}
		return moves;
	}
	
	public List<Move> getValidMoves(Color color, boolean removeChecks) {
		List<Move> validMoves = new ArrayList<Move>();
		for (int i = 0; i < piecesInPlay.size(); i++) {
			Piece piece = piecesInPlay.get(i);
			if (piece.getColor() == color) {
				validMoves.addAll(getValidMoves(piece, removeChecks));
			}
		}
		return validMoves;
	}
	
	public List<Move> removeChecks(List<Move> moves, Color color) {
		int i = 0;
		while (i < moves.size()) {
			Move m = moves.get(i);
			if (resultsInCheck(m, color)) {
				moves.remove(i);
			} else {
				i++;
			}
		}
		return moves;
	}
	
	public boolean resultsInCheck(Move m, Color color) {
		boolean result;
		apply(m);
		result = isCheck(color);
		unapply(m);
		return result;
	}
	
	public Piece getPieceAt(int row, int col) {
		for (Piece piece : piecesInPlay) {
			if (piece.getRow() == row && piece.getCol() == col) {
				return piece;
			}
		}
		return null;
	}
	
	public boolean isCheckmate(Color color) {
		return getValidMoves(color, true).size() == 0;
	}
	
	public boolean isCheck(Color color) {
		List<Move> validMoves = getValidMoves(color.opposite(), false);
		King king = (color == Color.WHITE) ? whiteKing : blackKing;
		for (Move move : validMoves) {
			if (move.getEndRow() == king.getRow() && move.getEndCol() == king.getCol()) {
				return true;
			}
		}
		return false;
	}

}
