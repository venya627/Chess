package logic.player;

import logic.pieces.Piece;
import logic.pieces.PieceType;
import logic.board.Board;
import logic.pieces.Alliance;

public abstract class Player {

    public static final String SUPERMAN_AVATAR_SOURCE = "C:/Users/user/Desktop/Projects/Chess/src/resources/images/superman_avatar.png";
    public static final String BATMAN_AVATAR_SOURCE = "C:/Users/user/Desktop/Projects/Chess/src/resources/images/batman_avatar.png";
    public static final String DEFAULT_AVATAR_SOURCE = "C:/Users/user/Desktop/Projects/Chess/src/resources/images/default_avatar.png";

    protected final String name;
    protected final Board board;
    protected final Alliance alliance;
    protected final String iconSource;
    protected final int rating;

    public Player(String name, Board board, Alliance alliance, String iconSource, int rating) {
        this.name = name;
        this.board = board;
        this.alliance = alliance;
        this.iconSource = iconSource;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public String getIconSource() {
        return iconSource;
    }

    public int getRating() {
        return rating;
    }

    public abstract boolean isHumanPlayer();
}
