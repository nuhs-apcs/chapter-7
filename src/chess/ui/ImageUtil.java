package chess.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import chess.model.Color;

public class ImageUtil {
	
	private static final int SPRITE_WIDTH = 132;
	private static final int SPRITE_HEIGHT = 132;
	
	private static BufferedImage pieces;
	private static boolean loaded = false;
	
	public static boolean load() {
		try {
			pieces = ImageIO.read(new File("pieces.png"));
			loaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loaded;
	}
	
	private static Image getPiece(int row, int col) {
		if (!loaded) load();
		return pieces.getSubimage(col * SPRITE_WIDTH, row * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	private static int getRow(Color color) {
		if (color == Color.WHITE) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static Image getPawn(Color color) {
		return getPiece(getRow(color), 5);
	}
	
	public static Image getKnight(Color color) {
		return getPiece(getRow(color), 1);
	}
	
	public static Image getBishop(Color color) {
		return getPiece(getRow(color), 2);
	}
	
	public static Image getRook(Color color) {
		return getPiece(getRow(color), 0);
	}
	
	public static Image getQueen(Color color) {
		return getPiece(getRow(color), 3);
	}
	
	public static Image getKing(Color color) {
		return getPiece(getRow(color), 4);
	}

}
