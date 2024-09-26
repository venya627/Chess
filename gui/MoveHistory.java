package gui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MoveHistory extends ScrollPane{
	
	private final GridPane contentPane;

	private Font font;
	
	private int currentRow;
	private int currentCol;
	
	public enum NotationType {
		TEXT("Noto Sans"), FIGURINE("Figurine Chess Notation Font");
		
		private final String fontName;
		
		private NotationType(String fontName) {
			this.fontName = fontName;
		}
	
		private Font getFont() {
			return Font.font(fontName, 14);
		}
	}
	
	public MoveHistory() {
		super();
		
		// 70-75% width
		// 50% height
		setPrefSize(400.0, 200.0);
		
		setFitToWidth(true);
		
		contentPane = new GridPane();

		font = NotationType.TEXT.getFont();
		
		currentRow = 0;
		currentCol = 0;
		
		ColumnConstraints fullmovesCol = new ColumnConstraints();
		fullmovesCol.setPercentWidth(20.0);
		
		ColumnConstraints whiteMovesCol = new ColumnConstraints();
		whiteMovesCol.setPercentWidth(40.0);
		
		ColumnConstraints blackMovesCol = new ColumnConstraints();
		blackMovesCol.setPercentWidth(40.0);
		
		contentPane.getColumnConstraints().addAll(fullmovesCol, whiteMovesCol, blackMovesCol);
		
		setContent(contentPane);
	}
	
	public void addMove(String move) {
		if (currentCol == 0) {
			contentPane.add(new Text(String.valueOf(currentRow + 1) + "."), currentCol, currentRow);
			currentCol++;
		}
		
		Text moveText = new Text(move);
		moveText.setFont(font);
		contentPane.add(moveText, currentCol, currentRow);
		
		currentCol++;
		
		if (currentCol == 3) {
			currentRow++;
			currentCol = 0;
		}
	}
	
	public void setNotation(NotationType type) {
		font = type.getFont();
		
		for (Node text : contentPane.getChildren()) {
			((Text) (text)).setFont(font);
		}
	}
}
