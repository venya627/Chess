package gui;

import logic.pieces.Alliance;
import logic.pieces.PieceType;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlayerView extends GridPane {

    private final HBox takenPiecesBox;
    private final Alliance alliance;
    private final Text playerName;
    private final ImageView playerIcon;
    private final Text playerRating;

    public PlayerView(String name, Alliance alliance, int rating) {
        this(name, alliance, new Image(new File("C:/Users/user/Desktop/Projects/Chess/src/resources/images/default_avatar.png").toURI().toString()), rating);
    }
    public PlayerView(String name, Alliance alliance, Image image, int rating) {
        super();

        takenPiecesBox = new HBox();
        takenPiecesBox.setSpacing(3.0);

        this.alliance = alliance;

        playerName = new Text(name);
        playerName.setFont(Font.font("Ariel", FontWeight.BOLD, 18));

        playerIcon = new ImageView(image);
        playerIcon.setPreserveRatio(true);
        playerIcon.setFitWidth(90.0);
        playerIcon.setFitHeight(90.0);

        playerRating = new Text("(" + String.valueOf(rating) + ")");
        playerName.setFont(Font.font("Ariel", FontWeight.MEDIUM, 16));

        HBox playerInfoBox = new HBox(playerName, playerRating);
        playerInfoBox.setSpacing(5.0);

        VBox box = new VBox(playerInfoBox, takenPiecesBox);
        box.setSpacing(3.0);

        add(playerIcon, 0, 0);
        add(box, 1, 0);
    }

    public void showRating(boolean show) {
        playerRating.setVisible(show);
    }

    public void addTakenPiece(PieceType pieceType) {
        ImageView piece = new ImageView(new Image(new File("C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/classic/" + alliance.name().toLowerCase() + "_" + pieceType.name().toLowerCase() + ".png").toURI().toString()));
        piece.setPreserveRatio(true);
        piece.setFitWidth(20.0);
        piece.setFitHeight(20.0);

        takenPiecesBox.getChildren().add(piece);
    }
}
