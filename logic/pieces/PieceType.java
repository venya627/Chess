package logic.pieces;

public enum PieceType {
	PAWN("P", 100, false, false),
	KNIGHT("N", 300, false, true),
	BISHOP("B", 300, false, true),
	ROOK("R", 500, true, false),
	QUEEN("Q", 900, true, false),
	KING("K", 10000, false, false);
	
	private final String symbol;
	private final int value;
	private final boolean isMajor;
	private final boolean isMinor;
	
	private PieceType(String symbol, int value, boolean isMajor, boolean isMinor) {
		this.symbol = symbol;
		this.value = value;
		this.isMajor = isMajor;
		this.isMinor = isMinor;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getValue() {
		return value;
	}

	public boolean isMajor() {
		return isMajor;
	}

	public boolean isMinor() {
		return isMinor;
	}
}