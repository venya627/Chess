package logic.board.bitboard;

public class Move {

    private final int sourceSquare;
    private final int targetSquare;
    private final int movingPiece;
    private final int movingPieceColor;
    private final int capturedPiece;
    private final int promotedPiece;
    private final int enpassant;
    private final int castling;
    private final int check;
    private final int checkmate;

    public Move(int sourceSquare, int targetSquare, int movingPiece, int movingPieceColor, int capturedPiece, int promotedPiece, int enpassant, int castling, int check, int checkmate) {
        this.sourceSquare = sourceSquare;
        this.targetSquare = targetSquare;
        this.movingPiece = movingPiece;
        this.movingPieceColor = movingPieceColor;
        this.capturedPiece = capturedPiece;
        this.promotedPiece = promotedPiece;
        this.enpassant = enpassant;
        this.castling = castling;
        this.check = check;
        this.checkmate = checkmate;
    }

    public static Move encode(int sourceSquare, int targetSquare, int movingPiece, int movingPieceColor, int capturedPiece, int promotedPiece, int enpassant, int castling, int check, int checkmate) {
        return new Move(sourceSquare, targetSquare, movingPiece, movingPieceColor, capturedPiece, promotedPiece, enpassant, castling, check, checkmate);
    }

    public static int getSourceSquare(int move) {
        return move & 63;
    }

    public static int getTargetSquare(int move) {
        return (move & 4032) >>> 6;
    }

    public static int getMovingPiece(int move) {
        return (move & 28672) >>> 12;
    }

    public static int getMovingPieceColor(int move) {
        return (move & 32768) >>> 15;
    }

    public static int getCapturedPiece(int move) {
        return (move & 458752) >>> 16;
    }

    public static int getPromotedPiece(int move) {
        return (move & 3670016) >>> 19;
    }

    public static int getEnPassant(int move) {
        return (move & 4194304) >>> 22;
    }

    public static int getCastling(int move) {
        return (move & 8388608) >>> 23;
    }
}
