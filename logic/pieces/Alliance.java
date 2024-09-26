package logic.pieces;

public enum Alliance {
    
    WHITE, BLACK;
    
    public Alliance getOpponent() {
        return this == WHITE ? BLACK : WHITE;
    }

    public boolean isWhite() {
        return this == WHITE;
    }
}
