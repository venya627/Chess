package logic.player;

import logic.board.Board;
import logic.pieces.Alliance;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Board board, Alliance alliance, int rating) {
        super(name, board, alliance, DEFAULT_AVATAR_SOURCE, rating);
    }

    public HumanPlayer(String name, Board board, Alliance alliance, String iconSource, int rating) {
        super(name, board, alliance, iconSource, rating);
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }
}
