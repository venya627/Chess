package logic.pieces;

import java.util.List;

import logic.board.Square;

import java.util.ArrayList;

public class Knight extends Piece {
    
    private static final int[][] offsets = { {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2} };
    
    public Knight(Alliance color) {
        super(PieceType.KNIGHT, color);
    }

	@Override
	public List<Square> getAllPseudoLegalMoves() {
		List<Square> candidates = new ArrayList<>();
        
        for (int i = 0; i < offsets.length; i++) {
            Square possibleTargetSquare = getBoard().getSquare(getX() + offsets[i][0], getY() + offsets[i][1]);
            
            if (possibleTargetSquare != null && (!possibleTargetSquare.isOccupied() || possibleTargetSquare.getPiece().getColor() != color)) {
                candidates.add(possibleTargetSquare);
            }
        }
        
        return candidates;
	}
}