package gui.theme;

import logic.pieces.Alliance;
import logic.pieces.Piece;
import logic.pieces.PieceType;
import java.io.File;
import javafx.scene.image.Image;

public class PieceTheme {
	
	private final String themeName; 
	private final String sourceFolder;
	
	public PieceTheme(String themeName, String sourceFolder) {
		this.themeName = themeName;
		this.sourceFolder = sourceFolder;
	}
	
	public Image getPieceImage(Piece piece) {
		return getPieceImage(piece.getType(), piece.getColor());
	}

	public Image getPieceImage(PieceType pieceType, Alliance pieceColor) {
		return new Image(new File(sourceFolder + pieceColor.name().toLowerCase() + "_" + pieceType.name().toLowerCase() + ".png").toURI().toString());
	}
	
	@Override
	public String toString() {
		return themeName;
	}
}
