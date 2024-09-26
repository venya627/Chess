package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.board.Board;
import logic.board.Move;
import logic.board.Square;

public abstract class Piece {
	
	protected final PieceType type;
	protected final Alliance color;
    
    protected Square square;
    protected boolean hasMoved;
    
    public Piece(PieceType type, Alliance color) {
    	this.type = type;
        this.color = color;
        hasMoved = false;
        square = null;
    }
    
    public void placeOnSquare(Square square) {
        this.square = square;
    }
    
    public void setHasMoved() {
        hasMoved = true;
    }

    public Square getSquare() {
        return square;
    }

    public Board getBoard() {
    	return square.getBoard();
    }
    
    public int getX() {
        return square.getX();
    }
    
    public int getY() {
        return square.getY();
    }
    
    public int getRank() {
    	return square.getRank();
    }
    
    public char getFile() {
    	return square.getFile();
    }
    
    public PieceType getType() {
    	return type;
    }
    
    public Alliance getColor() {
        return color;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public abstract List<Square> getAllPseudoLegalMoves();
    
    public List<Square> getAllLegalMoves() {
    	List<Square> candidates = new ArrayList<>();

        for (Square possibleTargetSquare : getAllPseudoLegalMoves()) {
            if (Move.isLegalMove(this, possibleTargetSquare)) candidates.add(possibleTargetSquare);
        }
    	
        return candidates;
    } 
    
    @Override
    public String toString() {
    	return color.isWhite() ? type.getSymbol() : type.getSymbol().toLowerCase();
    }
}