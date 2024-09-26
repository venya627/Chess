package logic.board;

import java.util.ArrayList;
import java.util.List;

import logic.pieces.Alliance;
import logic.pieces.Bishop;
import logic.pieces.King;
import logic.pieces.Knight;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.PieceType;
import logic.pieces.Queen;
import logic.pieces.Rook;

public class Board {

	public static final int BOARD_DIMENSIONS = 8;
	
	private final Square[][] board;

	private Alliance turn;
	private String castlingAvailability;
	private Square enPassantTargetSquare;
	private int halfmoves;
	private int fullmoves;

	// Standard starting position
	public Board() {
		this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	// Custom starting FEN position 
	public Board(String startingPosition) {
		board = new Square[BOARD_DIMENSIONS][BOARD_DIMENSIONS];
		initializeBoard();
		loadPosition(startingPosition);
	}

	// Fill the board with squares
	private void initializeBoard() {
		for (int row = 0; row < BOARD_DIMENSIONS; row++) {
			for (int col = 0; col < BOARD_DIMENSIONS; col++) {
				board[row][col] = new Square(row, col, this);
			}
		}
	}

	// Set up a position using the Forsyth-Edwards Notation (FEN)
	private void loadPosition(String fen) {
		String[] notation = fen.split(" ");

		// Ranks
		String[] ranks = notation[0].split("/");

		// Whose turn is it
		turn = notation[1].equals("w") ? Alliance.WHITE : Alliance.BLACK;

		// Castling availability
		castlingAvailability = notation[2];

		// Possible En Passant targets
		enPassantTargetSquare = getSquare(notation[3]);

		// Number of moves since the last capture/pawn advance
		halfmoves = Integer.parseInt(notation[4]);

		// Number of completed turns
		fullmoves = Integer.parseInt(notation[5]);

		// Set up pieces according to the position
		for (int rank = 0; rank < 8; rank++) {
			for (int square = 0, empty = 0; square < ranks[rank].length(); square++) {
				switch (ranks[rank].charAt(square)) {
				// White pieces
				case 'R':
					board[rank][square + empty].setPiece(new Rook(Alliance.WHITE));
					break;
				case 'N':
					board[rank][square + empty].setPiece(new Knight(Alliance.WHITE));
					break;
				case 'B':
					board[rank][square + empty].setPiece(new Bishop(Alliance.WHITE));
					break;
				case 'Q':
					board[rank][square + empty].setPiece(new Queen(Alliance.WHITE));
					break;
				case 'K':
					board[rank][square + empty].setPiece(new King(Alliance.WHITE));
					break;
				case 'P':
					board[rank][square + empty].setPiece(new Pawn(Alliance.WHITE));
					break;

				// Black pieces
				case 'r':
					board[rank][square + empty].setPiece(new Rook(Alliance.BLACK));
					break;
				case 'n':
					board[rank][square + empty].setPiece(new Knight(Alliance.BLACK));
					break;
				case 'b':
					board[rank][square + empty].setPiece(new Bishop(Alliance.BLACK));
					break;
				case 'q':
					board[rank][square + empty].setPiece(new Queen(Alliance.BLACK));
					break;
				case 'k':
					board[rank][square + empty].setPiece(new King(Alliance.BLACK));
					break;
				case 'p':
					board[rank][square + empty].setPiece(new Pawn(Alliance.BLACK));
					break;

				// Empty square (a number in the notation)
				default:
					empty += (ranks[rank].charAt(square) - '0' - 1);
				}
			}
		}
	}
	
	// Returns a board after a move
	public static Board testMove(Piece movingPiece, Square targetSquare) {
		Board testBoard = new Board(movingPiece.getBoard().getCurrentFEN());
		
		Piece testPiece = testBoard.getSquare(movingPiece.getX(), movingPiece.getY()).getPiece();
    	Square testTargetSquare = testBoard.getSquare(targetSquare.getX(), targetSquare.getY());
    	
    	testBoard.makeMove(testPiece, testTargetSquare);
    	
    	return testBoard;
	}
	
	// Move current active piece to a target square and return the move type
	public void makeMove(Piece movingPiece, Square targetSquare) {
		halfmoves++;
		if (turn == Alliance.BLACK)
			fullmoves++;
		turn = turn.getOpponent();
		enPassantTargetSquare = null;
		castlingAvailability = "";

		if (!movingPiece.hasMoved()) {
			if (movingPiece.getType() == PieceType.PAWN && movingPiece.getY() == targetSquare.getY() && Math.abs(movingPiece.getX() - targetSquare.getX()) == 2)
				enPassantTargetSquare = board[movingPiece.getColor() == Alliance.WHITE ? movingPiece.getX() - 1 : movingPiece.getX() + 1][movingPiece.getY()];

			movingPiece.setHasMoved();
		}
		
		switch (Move.getMoveType(movingPiece, targetSquare)) {
			case ENPASSANT:
				processEnPassant(movingPiece, targetSquare);
				break;
	
			case CASTLING:
				processCastling(movingPiece, targetSquare);
				break;
	
			case PROMOTION:
				processPromotion(movingPiece, targetSquare);
				break;
	
			case CAPTURE:
				processCapture(movingPiece, targetSquare);
				break;
				
			case REGULAR:
				processRegularMove(movingPiece, targetSquare);
				break;
		}
		
		if (getKing(Alliance.WHITE).canCastle()){
			if (getSquare(7, 7).isOccupied() && getSquare(7, 7).getPiece().getType() == PieceType.ROOK && getSquare(7, 7).getPiece().getColor() == Alliance.WHITE && !getSquare(7, 7).getPiece().hasMoved()) {
				castlingAvailability += "K";
			}
			
			if (getSquare(7, 0).isOccupied() && getSquare(7, 0).getPiece().getType() == PieceType.ROOK && getSquare(7, 0).getPiece().getColor() == Alliance.WHITE && !getSquare(7, 0).getPiece().hasMoved()) {
				castlingAvailability += "Q";
			}
		} 
		
		if (getKing(Alliance.BLACK).canCastle()) {
			if (getSquare(0, 7).isOccupied() && getSquare(0, 7).getPiece().getType() == PieceType.ROOK && getSquare(0, 7).getPiece().getColor() == Alliance.BLACK && !getSquare(0, 7).getPiece().hasMoved()) {
				castlingAvailability += "k";
			}
			
			if (getSquare(0, 0).isOccupied() && getSquare(0, 0).getPiece().getType() == PieceType.ROOK && getSquare(0, 0).getPiece().getColor() == Alliance.BLACK && !getSquare(0, 0).getPiece().hasMoved()) {
				castlingAvailability += "q";
			}
		}
	}
	
	private void processEnPassant(Piece movingPiece, Square targetSquare) {
		board[movingPiece.getX()][targetSquare.getY()].removePiece();
		processCapture(movingPiece, targetSquare);
	}
	
	private void processCastling(Piece movingPiece, Square targetSquare) {
		Square rookSquare = board[movingPiece.getX()][targetSquare.getY() == 2 ? 0 : 7];
		rookSquare.getPiece().setHasMoved();
		board[movingPiece.getX()][targetSquare.getY() == 2 ? 3 : 5].setPiece(rookSquare.getPiece());
		rookSquare.removePiece();
		processRegularMove(movingPiece, targetSquare);
	}
	
	private void processPromotion(Piece movingPiece, Square targetSquare) {
		movingPiece = ((Pawn) (movingPiece)).getPromotionPiece();
		processRegularMove(movingPiece, targetSquare);
	}
	
	private void processCapture(Piece movingPiece, Square targetSquare) {
		processRegularMove(movingPiece, targetSquare);
		halfmoves = 0;
	}
	
	private void processRegularMove(Piece movingPiece, Square targetSquare) {
		board[movingPiece.getX()][movingPiece.getY()].removePiece();
		targetSquare.setPiece(movingPiece);
		
		if (movingPiece instanceof Pawn) halfmoves = 0;
	}
	
	// Getters
	public King getKing(Alliance color) {
		for (Piece piece : getTeam(color)) {
			if (piece.getType() == PieceType.KING) {
				return (King) (piece);
			}
		}

		return null;
	}
	
	public List<Piece> getTeam(Alliance color) {
		List<Piece> team = new ArrayList<>();
		
		for (Square[] row : board) {
			for (Square square : row) {
				if (square.isOccupied() && square.getPiece().getColor() == color) {
					team.add(square.getPiece());
				}
			}
		}
		
		return team;
	}
	
	public String getCurrentFEN() {
		String fen = "";

		for (Square[] row : board) {
			int spaces = 0;
			
			for (Square square : row) {
				if (square.isOccupied()) {
					fen = fen + (spaces == 0 ? "" : spaces) + square.getPiece();
					spaces = 0;
				} else {
					spaces++;
				}
			}
			
			if (spaces != 0) fen += spaces;
				
			fen += "/";
		}
		
		fen = fen.substring(0, fen.length() - 1);

		fen += " " + (turn == Alliance.WHITE ? "w" : "b") + 
			   " " + (castlingAvailability.length() == 0 ? "-" : castlingAvailability) + 
			   " " + (enPassantTargetSquare == null ? "-" : enPassantTargetSquare) + 
			   " " + halfmoves + 
			   " " + fullmoves;

		return fen;
	}
	
	public Alliance getCurrentTurn() {
		return turn;
	}
	
	public String getCastlingAvailability() {
		return castlingAvailability;
	}
	
	public Square getEnPassantTargetSquare() {
		return enPassantTargetSquare;
	}
	
	public int getHalfmoves() {
		return halfmoves;
	}
	
	public int getFullmoves() {
		return fullmoves;
	}

	public int getTotalMaterialValue(Alliance color) {
		int total = 0;

		for (Piece piece : getTeam(color)) {
			total += piece.getType().getValue();
		}

		return total;
	}

	public boolean isGameOver() {
		return getKing(Alliance.WHITE).isInStalemate() ||
				getKing(Alliance.WHITE).isCheckmated() ||
				getKing(Alliance.BLACK).isInStalemate() ||
				getKing(Alliance.BLACK).isCheckmated();
	}
	
	public Square[][] getAllSquares() {
		return board;
	}
	
	public Square getSquare(int x, int y) {
		return x >= 0 && x <= 7 && y >= 0 && y <= 7 ? board[x][y] : null;
	}

	public Square getSquare(String notation) {
		return notation.length() == 2 && Character.isLetter(notation.charAt(0)) && Character.isDigit(notation.charAt(1))
				? getSquare(Math.abs(notation.charAt(1) - '0' - 8), Character.toLowerCase(notation.charAt(0)) - 97)
				: null;
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		
		for (Square[] row : board) {
			for (Square square : row) {
				toReturn += (square.isOccupied() ? square.getPiece() : "-") + " ";
			}
			
			toReturn += "\n";
		}
		
		return toReturn;
	}
}