package chess.network;

public interface GameClientListener {
	void onAddGame(GameInfo game);
	void onRemoveGame(GameInfo game);
}
