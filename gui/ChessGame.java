package gui;

import logic.board.Board;
import gui.MoveHistory.NotationType;
import gui.chessboard.BoardPane;
import gui.theme.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.pieces.Alliance;

public class ChessGame extends Stage {
	
	// Dimensions
	public final static double BOARD_PANE_MAX_SIZE = 742.0 + 26.0 / 97.0;
	public final static double BOARD_PANE_MIN_SIZE = 412.0 + 36.0 / 97.0;
	public final static double PIECE_IMAGEVIEW_MAX_SIZE = 78.75; // TODO remove these
	public final static double PIECE_IMAGEVIEW_MIN_SIZE = 43.75; // these
	
	private final List<BoardTheme> boardThemes;
	private final List<PieceTheme> pieceThemes;
	private final List<SoundTheme> soundThemes;

	private final BoardPane boardPane;
	private final Stage settingsWindow;
	
	private double oldVal;
	
	public ChessGame() {
		this(new Board());
	}
	
	public ChessGame(String startingPosition) {
		this(new Board(startingPosition));
	}
	
	private ChessGame(Board board) {
		super();
		
		boardThemes = new ArrayList<>(Arrays.asList(
				new BoardTheme("Green", Color.rgb(235, 236, 208), Color.rgb(115, 149, 82), Color.rgb(246, 246, 130), Color.rgb(186, 203, 67), Color.rgb(255, 182, 193), Color.rgb(240, 128, 128)),
				new BoardTheme("Brown", Color.rgb(255, 228, 196), Color.rgb(205, 133, 63), Color.rgb(255, 255, 77), Color.rgb(255, 227, 77), Color.rgb(255, 182, 193), Color.rgb(240, 128, 128)),
				new BoardTheme("Red", Color.rgb(245, 219, 195), Color.rgb(187, 87, 70), Color.rgb(247, 234, 172), Color.rgb(218, 168, 109), Color.rgb(255, 182, 193), Color.rgb(240, 128, 128)),
				new BoardTheme("Purple", Color.rgb(240, 241, 240), Color.rgb(132, 118, 186), Color.rgb(183, 207, 221), Color.rgb(129, 145, 194), Color.rgb(255, 182, 193), Color.rgb(240, 128, 128)),
				new BoardTheme("Dark Blue", Color.rgb(109, 122, 132), Color.rgb(40, 49, 60), Color.rgb(101, 135, 157), Color.rgb(67, 98, 121), Color.rgb(255, 182, 193), Color.rgb(240, 128, 128))
		));
		
		pieceThemes = new ArrayList<>(Arrays.asList(
				new PieceTheme("Classic", "C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/classic/"),
				new PieceTheme("Medieval", "C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/medieval/"),
				new PieceTheme("Minimalistic", "C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/minimalistic/"),
				new PieceTheme("Wood", "C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/wood/"),
				new PieceTheme("Tic Tac Toe", "C:/Users/user/Desktop/Projects/Chess/src/resources/images/pieces/tic-tac-toe/")
		));
		
		soundThemes = new ArrayList<>(Arrays.asList(
				new SoundTheme("Default", "C:/Users/user/Desktop/Projects/Chess/src/resources/sounds/default/"),
				new SoundTheme("Beat", "C:/Users/user/Desktop/Projects/Chess/src/resources/sounds/beat/"),
				new SoundTheme("Cat", "C:/Users/user/Desktop/Projects/Chess/src/resources/sounds/cat/"),
				new SoundTheme("Marble", "C:/Users/user/Desktop/Projects/Chess/src/resources/sounds/marble/"),
				new SoundTheme("Silly", "C:/Users/user/Desktop/Projects/Chess/src/resources/sounds/silly/")
		));

		boardPane = new BoardPane(board, boardThemes.get(0), pieceThemes.get(0), soundThemes.get(0));
		settingsWindow = createSettingsWindow();
		
		ImageView settingsButton = new ImageView(new Image(new File("C:/Users/user/Desktop/Projects/Chess/src/resources/images/settings_icon.png").toURI().toString()));
		settingsButton.setOnMouseClicked(event -> settingsWindow.show());
		
		ImageView resizeButton = new ImageView(new Image(new File("C:/Users/user/Desktop/Projects/Chess/src/resources/images/resize_icon.png").toURI().toString()));
		oldVal = getX() + resizeButton.getLayoutX();
		resizeButton.setOnMouseDragged(event -> {
			if (event.isPrimaryButtonDown()) {
				boardPane.setPrefSize(boardPane.getWidth() - oldVal + event.getScreenX(), boardPane.getHeight() - oldVal + event.getScreenX());
				
				oldVal = event.getScreenX();
			}
		});	
	
		VBox controlsBox = new VBox(settingsButton, resizeButton);
		controlsBox.setSpacing(BOARD_PANE_MAX_SIZE - settingsButton.getImage().getHeight() - resizeButton.getImage().getHeight());
		
		HBox mainContainer = new HBox(boardPane, controlsBox, new VBox(new PlayerView("Black", Alliance.BLACK, 1000), boardPane.getMoveLog(), new PlayerView("White", Alliance.WHITE, 1000)));
		//mainContainer.setBackground(new Background(new BackgroundFill(Color.rgb(242,202,92), null, null)));
		mainContainer.setAlignment(Pos.CENTER_LEFT);
		mainContainer.setSpacing(5.0);

		Scene scene = new Scene(mainContainer, 1200.0, 750.0);
		
        setTitle("Chess Game");
        getIcons().add(new Image(new File("C:/Users/user/Desktop/Projects/Chess/src/resources/images/window_icon.png").toURI().toString()));
        setResizable(false);
        setScene(scene);
        centerOnScreen();
	}
	
	private Stage createSettingsWindow() {
		Stage settingsWindow = new Stage();

		ComboBox<BoardTheme> boardTheme = new ComboBox<>();
		boardTheme.setItems(FXCollections.observableArrayList(boardThemes));
		boardTheme.getSelectionModel().select(0);
		
		ComboBox<PieceTheme> pieceTheme = new ComboBox<>();
		pieceTheme.setItems(FXCollections.observableArrayList(pieceThemes));
		pieceTheme.getSelectionModel().select(0);
		
		ComboBox<SoundTheme> soundTheme = new ComboBox<>();
		soundTheme.setItems(FXCollections.observableArrayList(soundThemes));
		soundTheme.getSelectionModel().select(0);

		ToggleButton playSounds = new ToggleButton();
		playSounds.setSelected(true);

		ToggleButton showLegalMoves = new ToggleButton();
		showLegalMoves.setSelected(true);
		
		ToggleButton showCoordinates = new ToggleButton();
		showCoordinates.setSelected(true);
		
		ToggleButton highlightMoves = new ToggleButton();
		highlightMoves.setSelected(true);
		
		ToggleButton whiteOnBottom = new ToggleButton();
		whiteOnBottom.setSelected(true);
		
		GridPane boardGrid = new GridPane();
		boardGrid.setVgap(2.0);
		boardGrid.setHgap(3.0);
		AnchorPane.setTopAnchor(boardGrid, 5.0);
		AnchorPane.setLeftAnchor(boardGrid, 5.0);

		boardGrid.addColumn(0,
				new Label("Board Theme"),
				new Label("Piece Theme"),
				new Label("Sound Theme"),
				new Label("Play Sounds"),
				new Label("Show Legal Moves"),
				new Label("Show Coordinates"),
				new Label("Highlight Moves"),
				new Label("White On Bottom")
		);
		
		boardGrid.addColumn(1,
				boardTheme,
				pieceTheme,
				soundTheme,
				playSounds,
				showLegalMoves,
				showCoordinates,
				highlightMoves,
				whiteOnBottom
		);

		ComboBox<NotationType> notationType = new ComboBox<>();
		notationType.setItems(FXCollections.observableArrayList(NotationType.TEXT, NotationType.FIGURINE));
		notationType.getSelectionModel().select(0);

		ToggleButton showRatings = new ToggleButton();
		showRatings.setSelected(true);

		ToggleButton autoPromoteQueen = new ToggleButton();
		autoPromoteQueen.setSelected(false);

		ToggleButton lowTimeWarning = new ToggleButton();
		lowTimeWarning.setSelected(true);

		ToggleButton confirmResignDraw = new ToggleButton();
		confirmResignDraw.setSelected(true);

		GridPane playGrid = new GridPane();
		playGrid.setVgap(2.0);
		playGrid.setHgap(3.0);
		AnchorPane.setTopAnchor(playGrid, 5.0);
		AnchorPane.setRightAnchor(playGrid, 5.0);

		playGrid.addColumn(0,
				new Label("Piece Notation"),
				new Label("Show Ratings"),
				new Label("Auto-Promote To Queen"),
				new Label("Low-Time Warning"),
				new Label("Confirm Resignation/Draw")
		);

		playGrid.addColumn(1,
				notationType,
				showRatings,
				autoPromoteQueen,
				lowTimeWarning,
				confirmResignDraw
		);

		Button save = new Button("Save");
		AnchorPane.setBottomAnchor(save, 5.0);
		AnchorPane.setRightAnchor(save, 212.5);
		AnchorPane.setLeftAnchor(save, 212.5);
		save.setOnAction(event -> {
			boardPane.setBoardTheme(boardTheme.getValue());
			boardPane.setPieceTheme(pieceTheme.getValue());
			boardPane.setSoundTheme(soundTheme.getValue());
			boardPane.getMoveLog().setNotation(notationType.getValue());
			boardPane.autoPromoteToQueen(autoPromoteQueen.isSelected());
			boardPane.playSounds(playSounds.isSelected());
			boardPane.showLegalMoves(showLegalMoves.isSelected());
			boardPane.showCoordinates(showCoordinates.isSelected());
			boardPane.highlightMoves(highlightMoves.isSelected());
			boardPane.whiteOnBottom(whiteOnBottom.isSelected());
			// TODO showRatings
			// TODO lowTimeWarning
			// TODO confirmResign/defeat

			settingsWindow.close();
		});
		
		AnchorPane pane = new AnchorPane(boardGrid, playGrid, save);
		
		settingsWindow.initOwner(this);
		settingsWindow.setScene(new Scene(pane, 475.0, 275.0));
		settingsWindow.centerOnScreen();
		
		return settingsWindow;
	}
}
