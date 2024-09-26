package logic.pieces;

import java.util.List;

import logic.board.Board;
import logic.board.Square;

import java.util.ArrayList;

public class King extends Piece {
    
    private static final int[][] offsets = { {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1} };

    public King(Alliance color) {
        super(PieceType.KING, color);
    }

	@Override
	public List<Square> getAllPseudoLegalMoves() {
		List<Square> candidates = new ArrayList<>();
        
        for (int i = 0; i < offsets.length; i++) {
            Square possibleTargetSquare = getBoard().getSquare(getX() + offsets[i][0], getY() + offsets[i][1]);

            if (possibleTargetSquare != null && (!possibleTargetSquare.isOccupied() || possibleTargetSquare.isOccupied() && possibleTargetSquare.getPiece().getColor() != color)) {
                candidates.add(possibleTargetSquare);
            }
        }
        
        if (canCastleKingSide()) {
            candidates.add(getBoard().getSquare(getX(), 6));
        }
        
        if (canCastleQueenSide()) {
            candidates.add(getBoard().getSquare(getX(), 2));
        }
        
        return candidates;
	}

	public boolean canCastle() {
		return !hasMoved && (color.isWhite() && getX() == 7 || !color.isWhite() && getX() == 0) && getY() == 4;
	}

	public boolean canCastleKingSide() {
		if (canCastle()) {
			Piece castlingRook = getBoard().getSquare(getX(), 7).getPiece();

			if (castlingRook != null && castlingRook.getType() == PieceType.ROOK && castlingRook.getColor() == color && !castlingRook.hasMoved() &&
					!getBoard().getSquare(getX(), 5).isOccupied() && !getBoard().getSquare(getX(), 6).isOccupied()) {
				return true;
			}
		}

		return false;
	}

	public boolean canCastleQueenSide() {
		if (canCastle()) {
			Piece castlingRook = getBoard().getSquare(getX(), 0).getPiece();

			if (castlingRook != null && castlingRook.getType() == PieceType.ROOK && castlingRook.getColor() == color && !castlingRook.hasMoved() &&
					!getBoard().getSquare(getX(), 1).isOccupied() && !getBoard().getSquare(getX(), 2).isOccupied() && !getBoard().getSquare(getX(), 3).isOccupied()) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isInCheck() {
		return square.isUnderAttack(color.getOpponent());
	}
	
	public boolean isInCheck(Piece movingPiece, Square targetSquare) {
    	return Board.testMove(movingPiece, targetSquare).getKing(color).isInCheck();
	}

	public boolean isInStalemate() {
		for (Piece piece : getBoard().getTeam(color)) {
			if (!piece.getAllLegalMoves().isEmpty()) return false;
		}

		return !isInCheck();
	}

	public boolean isInStalemate(Piece movingPiece, Square targetSquare) {
		return Board.testMove(movingPiece, targetSquare).getKing(color).isInStalemate();
	}
	
	public boolean isCheckmated() {
		for (Piece piece : getBoard().getTeam(color)) {
			if (!piece.getAllLegalMoves().isEmpty()) return false;
		}
		
		return isInCheck();
	}
	
	public boolean isCheckmated(Piece movingPiece, Square targetSquare) {
		return Board.testMove(movingPiece, targetSquare).getKing(color).isCheckmated();
	}
}