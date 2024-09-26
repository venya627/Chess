package logic.board;

import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.PieceType;

public enum Move {
    REGULAR, CAPTURE, CASTLING, PROMOTION, ENPASSANT;

    // Returns the move type
    public static Move getMoveType(Piece movingPiece, Square targetSquare) {
        if (movingPiece.getType() == PieceType.KING && Math.abs(movingPiece.getY() - targetSquare.getY()) == 2) {
            return CASTLING;
        }

        if (movingPiece.getType() == PieceType.PAWN) {
            if (targetSquare.getX() == 0 || targetSquare.getX() == 7)
                return PROMOTION;

            if (!targetSquare.isOccupied() && movingPiece.getY() != targetSquare.getY())
                return ENPASSANT;
        }

        if (targetSquare.isOccupied())
            return CAPTURE;

        return REGULAR;
    }

    // Checks if the move is valid
    public static boolean isLegalMove(Piece movingPiece, Square targetSquare) {
        return movingPiece.getAllPseudoLegalMoves().contains(targetSquare) &&
                (!targetSquare.isOccupied() || targetSquare.getPiece().getType() != PieceType.KING) &&
                !movingPiece.getBoard().getKing(movingPiece.getColor()).isInCheck(movingPiece, targetSquare) &&
                (Move.getMoveType(movingPiece, targetSquare) != Move.CASTLING ||
                        (!movingPiece.getBoard().getKing(movingPiece.getColor()).isInCheck() &&
                                !movingPiece.getBoard().getKing(movingPiece.getColor()).isInCheck(movingPiece, movingPiece.getBoard().getSquare(movingPiece.getX(), targetSquare.getY() == 2 ? 3 : 5))));

    }

    public static boolean isPseudoLegalMove(Piece movingPiece, Square targetSquare) {
        return movingPiece.getAllPseudoLegalMoves().contains(targetSquare);
    }

    // Returns a move in the standard algebraic notation
    public static String toAlgebraicNotation(Piece movingPiece, Square targetSquare) {
        String notation = "";

        boolean isCastling = getMoveType(movingPiece, targetSquare) == CASTLING;
        boolean isPromotion = getMoveType(movingPiece, targetSquare) == PROMOTION;
        boolean isPawn = movingPiece.getType() == PieceType.PAWN;
        boolean disambiguateByFile = false;
        boolean disambiguateByRank = false;
        boolean isCapture = targetSquare.isOccupied() || getMoveType(movingPiece, targetSquare) == ENPASSANT;
        boolean isCheck = movingPiece.getBoard().getKing(movingPiece.getColor().getOpponent()).isInCheck(movingPiece, targetSquare);
        boolean isCheckmate = movingPiece.getBoard().getKing(movingPiece.getColor().getOpponent()).isCheckmated(movingPiece, targetSquare);

        for (Piece attacker : targetSquare.getAttackers(movingPiece.getColor())) {
            if (movingPiece.getType() == attacker.getType() && movingPiece != attacker) {
                if (movingPiece.getX() == attacker.getX()) {
                    disambiguateByFile = true;
                }

                if (movingPiece.getY() == attacker.getY()) {
                    disambiguateByRank = true;
                }
            }
        }

        if (isCastling) {
            notation = "O";

            for (int o = Math.abs(movingPiece.getY() - targetSquare.getY()) - 1; o > 0; o--) {
                notation += "-O";
            }
        } else {
            notation += (isPawn ? "" : movingPiece.getType().getSymbol()) +
                    (disambiguateByFile ? movingPiece.getFile() : "") +
                    (disambiguateByRank ? movingPiece.getRank() : "") +
                    (isCapture ? "x" : "") +
                    targetSquare +
                    (isPromotion ? "=" + ((Pawn) (movingPiece)).getPromotionPiece().getType().getSymbol() : "");
        }

        notation += (isCheckmate ? "#" : (isCheck ? "+" : ""));

        return notation;
    }
}
