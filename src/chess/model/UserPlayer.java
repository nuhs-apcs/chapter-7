package chess.model;

import java.util.concurrent.CountDownLatch;

import chess.model.moves.Move;

public class UserPlayer extends Player {
	
	private Move userMove;
	private CountDownLatch latch;

	public UserPlayer(Color color) {
		super(color);
	}

	@Override
	public Move makeMove(Board board, Move lastMove) {
		userMove = null;
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return userMove;
	}
	
	public void onUserMove(Move move) {
		userMove = move;
		latch.countDown();
	}

}
