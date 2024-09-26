package logic.board;

import java.util.ArrayList;
import java.util.List;

import logic.pieces.Alliance;
import logic.pieces.Pawn;
import logic.pieces.Piece;

public class Square {

    private final int x;
    private final int y;
    private final Board board;
    
    private Piece piece;

    public Square(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        piece = null;
    }

    public void setPiece(Piece piece) {
        piece.placeOnSquare(this);
        this.piece = piece;
    }

    public void removePiece() {
        piece = null;
    }
    
    public boolean isOccupied() {
        return piece != null;
    }
    
    public boolean isUnderAttack(Alliance attackerColor) {
    	return !getAttackers(attackerColor).isEmpty();
    }
    
    public List<Piece> getAttackers(Alliance attackerColor) {
    	List<Piece> attackers = new ArrayList<>();
    	
    	for (Square[] row : board.getAllSquares()) {
    		for (Square square : row) {
        		if (square.isOccupied() && square.getPiece().getColor() == attackerColor &&
                        square.getPiece().getAllPseudoLegalMoves().contains(this)) {
        			attackers.add(square.getPiece());
        		}
    		}
    	}
    		
    	return attackers;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getRank() {
    	return 8 - x;
    }
    
    public char getFile() {
    	return (char) (97 + y);
    }

    public Piece getPiece() {
        return piece;
    }

    public Board getBoard() {
    	return board;
    }
    
    @Override
    public String toString() {
    	return getFile() + "" + getRank();
    }
}