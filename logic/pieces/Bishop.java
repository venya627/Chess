package logic.pieces;

import java.util.List;

import logic.board.Square;

import java.util.ArrayList;

public class Bishop extends Piece {
    
    private static final int[][][] offsets = { { {-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7} },
                                              { {-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7} },
                                              { {1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7} },
                                              { {1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7} } };
    
    public Bishop(Alliance color) {
        super(PieceType.BISHOP, color);
    }

	@Override
	public List<Square> getAllPseudoLegalMoves() {
		List<Square> candidates = new ArrayList<>();
        
        for (int i = 0; i < offsets.length; i++) {
            for (int j = 0 ; j < offsets[i].length; j++) {
                Square possibleTargetSquare = getBoard().getSquare(getX() + offsets[i][j][0], getY() + offsets[i][j][1]);
            
                if (possibleTargetSquare != null) {
                    if (possibleTargetSquare.isOccupied()) {
                        if (possibleTargetSquare.getPiece().getColor() != color) {
                            candidates.add(possibleTargetSquare);
                        }
                        
                        break;
                    }
                
                    candidates.add(possibleTargetSquare);
                }
            }
        }
        
        return candidates;
	}
}