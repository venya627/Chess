package gui.chessboard;

import logic.board.Square;

import gui.theme.BoardTheme;
import gui.theme.PieceTheme;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

// % * square size (12.5% of board size (coords in); 12.125% of board size (coords out))

// rectangle stroke width -> 3.75%
// pieceview -> 87.50% fit size
// poss move:
// 		occupied
//			37.50% circle radius
//			7.50% stroke width			
// 		not occupied
//			mentered
//				25.00% circle radius
//			not mentered
//				18.75% circle radius

public class SquarePane extends StackPane {

	private final Square square;
	
	private BoardTheme boardTheme;
	private PieceTheme pieceTheme;
	
	private boolean showLegalMoves;
	private boolean highlightMoves;
	
	private boolean isSelected;
	private boolean isHighlighted;
	private boolean isPossibleMove;

	public SquarePane(Square square, BoardTheme boardTheme, PieceTheme pieceTheme, boolean showLegalMoves, boolean highlightMoves) {
		super();
	
		this.square = square;
		
		this.boardTheme = boardTheme;
		this.pieceTheme = pieceTheme;
		
		this.showLegalMoves = showLegalMoves;
		this.highlightMoves = highlightMoves;
		
		isPossibleMove = false;
		isHighlighted = false;
		isSelected = false;
		
		getChildren().add(new Rectangle());
		getRectangle().setFill(boardTheme.getSquareColor(square.getX(), square.getY()));
		getRectangle().setStrokeType(StrokeType.INSIDE);		
		getRectangle().heightProperty().bind(getRectangle().widthProperty());
		getRectangle().strokeWidthProperty().bind(getRectangle().widthProperty().multiply(0.0375));;
		
		updatePieceIcon();
		
		setOnMouseEntered(event -> {
			if (isPossibleMove) {
				if (square.isOccupied()) ((Circle) (getChildren().get(1))).setFill(Color.rgb(0, 0, 0, 0.1));
				else ((Circle) (getChildren().get(1))).radiusProperty().bind(getRectangle().widthProperty().multiply(0.2500));;
			}
			
			if (square.isOccupied()) getRectangle().setStroke(Color.WHITE);
		});
		
		setOnMouseExited(event -> {
			if (isPossibleMove) {
				if (square.isOccupied()) ((Circle) (getChildren().get(1))).setFill(Color.TRANSPARENT);
				else ((Circle) (getChildren().get(1))).radiusProperty().bind(getRectangle().widthProperty().multiply(0.1875));;
			}
			
			getRectangle().setStroke(null);
		});

		setOnMouseClicked(event -> ((BoardPane) (getParent())).handleMouseClick(event));
	}

	// Update piece icon
	public void updatePieceIcon() {
		if (getChildren().get(getChildren().size() - 1) instanceof ImageView)
			getChildren().remove(getChildren().size() - 1);

		if (square.isOccupied()) {
			ImageView pieceView = new ImageView(pieceTheme.getPieceImage(square.getPiece()));
			pieceView.setPreserveRatio(true);
			pieceView.fitWidthProperty().bind(getRectangle().widthProperty().multiply(0.8750));
			pieceView.fitHeightProperty().bind(getRectangle().heightProperty().multiply(0.8750));

			getChildren().add(pieceView);
		}
	}
	
	public void applyBoardTheme(BoardTheme boardTheme) {
		this.boardTheme = boardTheme;
		setHighlighted(isHighlighted);
		
	}
	
	public void applyPieceTheme(PieceTheme pieceTheme) {
		this.pieceTheme = pieceTheme;
		updatePieceIcon();
	}
	
	public void setShowingLegalMoves(boolean showLegalMoves) {
		this.showLegalMoves = showLegalMoves;
		if (isPossibleMove) getChildren().get(1).setVisible(showLegalMoves); 
	}
	
	public void setHighlightingMoves(boolean highlightMoves) {
		this.highlightMoves = highlightMoves;
		setSelected(isSelected);
	}

	public void setSelected(boolean isSelected) {
		if (isSelected && highlightMoves) {
			getRectangle().setFill(boardTheme.getSelectedSquareColor(square.getX(), square.getY()));
		} else {
			getRectangle().setFill(boardTheme.getSquareColor(square.getX(), square.getY()));
		}

		this.isSelected = isSelected;
	}

	public void setHighlighted(boolean isHighlighted) {
		if (isHighlighted) {
			getRectangle().setFill(boardTheme.getHighlightedSquareColor(square.getX(), square.getY()));
		} else {
			setSelected(isSelected);
		}

		this.isHighlighted = isHighlighted;
	}

	// Handle setting this square as a possible move
	public void setPossibleMove(boolean isPossibleMove) {
		if (isPossibleMove) {
			Circle circle = new Circle();
			
			if (square.isOccupied()) {
				circle.setFill(Color.TRANSPARENT);
				circle.setStrokeType(StrokeType.OUTSIDE);
				circle.setStroke(Color.rgb(0, 0, 0, 0.1));
				circle.radiusProperty().bind(getRectangle().widthProperty().multiply(0.3750));
				circle.strokeWidthProperty().bind(getRectangle().widthProperty().multiply(0.0750));;				
			} else {
				circle.setFill(Color.rgb(0, 0, 0, 0.1));
				circle.radiusProperty().bind(getRectangle().widthProperty().multiply(0.1875));
			}
			
			circle.setVisible(showLegalMoves);
			getChildren().add(1, circle);
		} else {
			getChildren().remove(1);
		}

		this.isPossibleMove = isPossibleMove;
	}
	
	public Rectangle getRectangle() {
		return (Rectangle) (getChildren().get(0));
	}
	
	public Square getSquare() {
		return square;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public boolean isPossibleMove() {
		return isPossibleMove;
	}
}
