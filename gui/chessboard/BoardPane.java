package gui.chessboard;

import logic.board.*;
import logic.pieces.*;

import gui.ChessGame;
import gui.MoveHistory;
import gui.theme.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BoardPane extends GridPane {

	private final Board board;
	private final MoveHistory moveLog;
	private final SquarePane[][] squareComponents;
	private final Text[][] coordinates;

	private Piece currentPiece;

	private PieceTheme pieceTheme;
	private SoundTheme soundTheme;

	private boolean promoteToQueen;
	private boolean playSounds;

	public BoardPane(Board board, BoardTheme boardTheme, PieceTheme pieceTheme, SoundTheme soundTheme) {
		this(board, boardTheme, pieceTheme, soundTheme, false, true, true, true, true, true);
	}
	
	public BoardPane(Board board, BoardTheme boardTheme, PieceTheme pieceTheme, SoundTheme soundTheme, boolean promoteToQueen, boolean playSounds, boolean showLegalMoves, boolean showCoordinates, boolean highlightMoves, boolean whiteOnBottom) {
		super();
		
		setMinSize(ChessGame.BOARD_PANE_MIN_SIZE, ChessGame.BOARD_PANE_MIN_SIZE);
		setMaxSize(ChessGame.BOARD_PANE_MAX_SIZE, ChessGame.BOARD_PANE_MAX_SIZE);
		
		this.board = board;
		moveLog = new MoveHistory();
		squareComponents = new SquarePane[Board.BOARD_DIMENSIONS][Board.BOARD_DIMENSIONS];
		coordinates = new Text[2][Board.BOARD_DIMENSIONS];

		currentPiece = null;

		this.pieceTheme = pieceTheme;
		this.soundTheme = soundTheme;

		this.promoteToQueen = promoteToQueen;
		this.playSounds = playSounds;

		for (int i = 0; i < Board.BOARD_DIMENSIONS; i++) {
			for (int j = 0; j < Board.BOARD_DIMENSIONS; j++) {
				squareComponents[i][j] = new SquarePane(board.getSquare(i, j), boardTheme, pieceTheme, showLegalMoves, highlightMoves);
				squareComponents[i][j].getRectangle().widthProperty().bind(widthProperty().multiply(0.12125));
			}
			
			Text rank = new Text(8 - i + "");
			rank.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
			//rank.setFill(Color.rgb(248,231,187));
			
			Text file = new Text(String.valueOf((char) (97 + i)));
			file.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
			//file.setFill(Color.rgb(248,231,187));
			
			coordinates[0][i] = rank;
			coordinates[1][i] = file;	
		}
		
		whiteOnBottom(whiteOnBottom);
		showCoordinates(showCoordinates);
	}

	// Handle mouse click on one of the squares
	public void handleMouseClick(MouseEvent event) {
		SquarePane selectedSquareComponent = (SquarePane) (event.getSource());

		if (event.getButton() == MouseButton.PRIMARY) {
			clearAllHighlights();

			if (currentPiece == null) {
				if (selectedSquareComponent.getSquare().isOccupied() && board.getCurrentTurn() == selectedSquareComponent.getSquare().getPiece().getColor()) {
					selectedSquareComponent.setSelected(true);
					currentPiece = selectedSquareComponent.getSquare().getPiece();
					handleDisplayingPossibleMoves(true);
				}
			} else {				
				handleDisplayingPossibleMoves(false);

				if (Move.isLegalMove(currentPiece, selectedSquareComponent.getSquare())) {
					Move moveType = Move.getMoveType(currentPiece, selectedSquareComponent.getSquare());
					
					clearAllSelections();
					squareComponents[currentPiece.getX()][currentPiece.getY()].setSelected(true);
					selectedSquareComponent.setSelected(true);

					if (moveType == Move.PROMOTION && !promoteToQueen)
						createPromotionWindow();
					
					moveLog.addMove(Move.toAlgebraicNotation(currentPiece, selectedSquareComponent.getSquare()));

					board.makeMove(currentPiece, selectedSquareComponent.getSquare());

					updateBoard();

					if (playSounds) {
						AudioClip moveSound = soundTheme.getCheck();
						
						if (!board.getKing(currentPiece.getColor().getOpponent()).isInCheck()) {
							switch(moveType) {
								case REGULAR:
									moveSound = soundTheme.getRegular();
									break;
									
								case ENPASSANT:
									
								case CAPTURE:
									moveSound = soundTheme.getCapture();
									break;
						
								case CASTLING:
									moveSound = soundTheme.getCastling();
									break;
						
								case PROMOTION:
									moveSound = soundTheme.getPromotion();
									break;								
							}
						}
						
						moveSound.play();
					}
					
					currentPiece = null;
				} else {
					if (playSounds && Move.isPseudoLegalMove(currentPiece, selectedSquareComponent.getSquare()))
						soundTheme.getIllegal().play();
					
					squareComponents[currentPiece.getX()][currentPiece.getY()].setSelected(false);
					currentPiece = null;
					handleMouseClick(event);
				}
			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
			if (currentPiece != null) {
				handleDisplayingPossibleMoves(false);
				squareComponents[currentPiece.getX()][currentPiece.getY()].setSelected(false);
				currentPiece = null;
			}

			if (selectedSquareComponent.isHighlighted()) {
				selectedSquareComponent.setHighlighted(false);
			} else {
				selectedSquareComponent.setHighlighted(true);
			}
		}
	}

	// Update board
	public void updateBoard() {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				component.updatePieceIcon();
			}
		}
	}
	
	private void createPromotionWindow() {
		Stage dialogWindow = new Stage();
		
		ImageView queen = new ImageView(pieceTheme.getPieceImage(PieceType.QUEEN, currentPiece.getColor()));
		queen.setOnMouseClicked(event -> {
			((Pawn) (currentPiece)).setPromotionPiece(new Queen(currentPiece.getColor()));
			dialogWindow.close();
		});

		ImageView knight = new ImageView(pieceTheme.getPieceImage(PieceType.KNIGHT, currentPiece.getColor()));
		knight.setOnMouseClicked(event -> {
			((Pawn) (currentPiece)).setPromotionPiece(new Knight(currentPiece.getColor()));
			dialogWindow.close();
		});

		ImageView rook = new ImageView(pieceTheme.getPieceImage(PieceType.ROOK, currentPiece.getColor()));
		rook.setOnMouseClicked(event -> {
			((Pawn) (currentPiece)).setPromotionPiece(new Rook(currentPiece.getColor()));
			dialogWindow.close();
		});

		ImageView bishop = new ImageView(pieceTheme.getPieceImage(PieceType.BISHOP, currentPiece.getColor()));
		bishop.setOnMouseClicked(event -> {
			((Pawn) (currentPiece)).setPromotionPiece(new Bishop(currentPiece.getColor()));
			dialogWindow.close();
		});

		VBox vbox = new VBox(queen, knight, rook, bishop);
		vbox.setAlignment(Pos.CENTER);

		for (Node node : vbox.getChildren()) {
			((ImageView) (node)).setPreserveRatio(true);
			((ImageView) (node)).setFitHeight(ChessGame.PIECE_IMAGEVIEW_MAX_SIZE);
			((ImageView) (node)).setFitWidth(ChessGame.PIECE_IMAGEVIEW_MAX_SIZE);
		}

		dialogWindow.setScene(new Scene(vbox));
		dialogWindow.initModality(Modality.APPLICATION_MODAL);
		dialogWindow.initStyle(StageStyle.UNDECORATED);
		dialogWindow.setResizable(false);
		dialogWindow.showAndWait();
	}

	public void setBoardTheme(BoardTheme boardTheme) {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				component.applyBoardTheme(boardTheme);
			}
		}
	}
	
	public void setPieceTheme(PieceTheme pieceTheme) {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				component.applyPieceTheme(pieceTheme);
			}
		}
		
		this.pieceTheme = pieceTheme;
	}
	
	public void setSoundTheme(SoundTheme soundTheme) {
		this.soundTheme = soundTheme;
	}
	
	public void autoPromoteToQueen(boolean promote) {
		promoteToQueen = promote;
	}
	
	public void playSounds(boolean playing) {
		playSounds = playing;
	}
	
	public void showLegalMoves(boolean showing) {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				component.setShowingLegalMoves(showing);
			}
		}
	}
	
	public void showCoordinates(boolean showing) {
		for (Text[] coords : coordinates) {
			for (Text coord : coords) {
				coord.setVisible(showing);
			}
		}
	}
	
	public void highlightMoves(boolean highlighting) {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				component.setHighlightingMoves(highlighting);
			}
		}
	}	
	
	public void whiteOnBottom(boolean whiteOnBottom) {
		getChildren().clear();
		
		for (int row = 0; row < Board.BOARD_DIMENSIONS + 1; row++) {
			for (int col = 0; col < Board.BOARD_DIMENSIONS + 1; col++) {
				if (col == 0 && row < 8) {
					add(new StackPane(coordinates[0][whiteOnBottom ? row : 7 - row]), col, row);
				} else if (col > 0 && row == 8) {
					add(new StackPane(coordinates[1][whiteOnBottom ? col - 1 : 8 - col]), col, row);
				} else if (col > 0 && row < 8){
					add(whiteOnBottom ? squareComponents[row][col - 1] : squareComponents[7 - row][8 - col], col, row);
				}
			}
		}
	}

	public void handleDisplayingPossibleMoves(boolean showing) {
		if (currentPiece != null) {
			for (Square square : currentPiece.getAllLegalMoves())
				squareComponents[square.getX()][square.getY()].setPossibleMove(showing);
		}
	}

	public void clearAllHighlights() {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				if (component.isHighlighted())
					component.setHighlighted(false);
			}
		}
	}

	public void clearAllSelections() {
		for (SquarePane[] components : squareComponents) {
			for (SquarePane component : components) {
				if (component.isSelected())
					component.setSelected(false);
			}
		}
	}
	
	public MoveHistory getMoveLog() {
		return moveLog;
	}
}
