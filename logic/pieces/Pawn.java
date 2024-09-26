package logic.pieces;

import java.util.List;

import logic.board.Square;

import java.util.ArrayList;

public class Pawn extends Piece {
	
	private Piece promotionPiece;
    
    private final int[][] offsets;
    
    public Pawn(Alliance color) {
        super(PieceType.PAWN, color);
        
        promotionPiece = new Queen(color);
        
        if (color == Alliance.WHITE) {
            offsets = new int[][] { {-1, 0}, {-2, 0}, {-1, -1}, {-1, 1} };
        } else {
            offsets = new int[][] { {1, 0}, {2, 0}, {1, -1}, {1, 1} };
        }
    }

    public void setPromotionPiece(PieceType pieceType) {
        switch (pieceType) {
            case PAWN:

            case KING:

            case QUEEN:
                promotionPiece = new Queen(color);
                break;

            case KNIGHT:
                promotionPiece = new Knight(color);
                break;

            case ROOK:
                promotionPiece = new Rook(color);
                break;

            case BISHOP:
                promotionPiece = new Bishop(color);
                break;
        }
    }

    public void setPromotionPiece(Piece promotionPiece) {
    	this.promotionPiece = promotionPiece;
    }
    
    public Piece getPromotionPiece() {
    	promotionPiece.placeOnSquare(square);
    	return promotionPiece;
    }

	@Override
	public List<Square> getAllPseudoLegalMoves() {
		List<Square> candidates = new ArrayList<>();
        
        for (int i = 0; i < offsets.length; i++) {
            Square possibleTargetSquare = getBoard().getSquare(getX() + offsets[i][0], getY() + offsets[i][1]);
            
            if (possibleTargetSquare != null && 
               (((offsets[i][0] == 1 || offsets[i][0] == -1) && offsets[i][1] == 0 && !possibleTargetSquare.isOccupied()) ||
               ((offsets[i][0] == 2 || offsets[i][0] == -2) && offsets[i][1] == 0 && !getBoard().getSquare(getX() + (color == Alliance.WHITE ? -1 : 1), getY()).isOccupied() && !possibleTargetSquare.isOccupied() && !hasMoved && getX() == (color == Alliance.WHITE ? 6 : 1)) ||
               ((offsets[i][1] == 1 || offsets[i][1] == -1) && (possibleTargetSquare.isOccupied() && possibleTargetSquare.getPiece().getColor() != color || possibleTargetSquare == getBoard().getEnPassantTargetSquare())))) {
                candidates.add(possibleTargetSquare);
            }
        }
        
        return candidates;
	}
}