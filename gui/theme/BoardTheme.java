package gui.theme;

import javafx.scene.paint.Color;

public class BoardTheme {
	
	private final String themeName;
	private final Color whiteSquare;
	private final Color blackSquare;
	private final Color whiteSelectedSquare;
	private final Color blackSelectedSquare;
	private final Color whiteHighlightedSquare;
	private final Color blackHighlightedSquare;
	
	public BoardTheme(String themeName, String whiteSquare, String blackSquare, String whiteSelectedSquare, String blackSelectedSquare, String whiteHighlightedSquare, String blackHighlightedSquare) {
		this(themeName, Color.web(whiteSquare), Color.web(blackSquare), Color.web(whiteSelectedSquare), Color.web(blackSelectedSquare), Color.web(whiteHighlightedSquare), Color.web(blackHighlightedSquare));
	}
	
	public BoardTheme(String themeName, Color whiteSquare, Color blackSquare, Color whiteSelectedSquare, Color blackSelectedSquare, Color whiteHighlightedSquare, Color blackHighlightedSquare) {
		this.themeName = themeName;
		this.whiteSquare = whiteSquare;
		this.blackSquare = blackSquare;
		this.whiteSelectedSquare = whiteSelectedSquare;
		this.blackSelectedSquare = blackSelectedSquare;
		this.whiteHighlightedSquare = whiteHighlightedSquare;
		this.blackHighlightedSquare = blackHighlightedSquare;
	}
	
	public Color getSquareColor(int x, int y) {
		return x % 2 == y % 2 ? whiteSquare : blackSquare;
	}

	public Color getSelectedSquareColor(int x, int y) {
		return x % 2 == y % 2 ? whiteSelectedSquare : blackSelectedSquare;
	}

	public Color getHighlightedSquareColor(int x, int y) {
		return x % 2 == y % 2 ? whiteHighlightedSquare : blackHighlightedSquare;
	}
	
	@Override
	public String toString() {
		return themeName;
	}
}
