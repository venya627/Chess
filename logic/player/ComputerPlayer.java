package logic.player;

import logic.board.Board;
import logic.board.Square;
import logic.pieces.Alliance;
import logic.pieces.Piece;

import javafx.util.Pair;

public class ComputerPlayer extends Player {

    private static final int MAX_DEPTH = 5;

    private int depth;

    public ComputerPlayer(Board board, Alliance alliance, int depth) {
        super("Computer", board, alliance, alliance == Alliance.WHITE ? SUPERMAN_AVATAR_SOURCE : BATMAN_AVATAR_SOURCE, 0);
        setDepth(depth);
        // TODO figure out how depth relates to the ELO
    }

    public void setDepth(int depth) {
        if (depth > MAX_DEPTH) depth = MAX_DEPTH;
        else if (depth < 0) depth = 0;

        this.depth = depth;
    }

    public static Pair<Piece, Square> calculate(Board board, Alliance alliance, int depth) {


        return null;
    }

    public Pair<Piece, Square> calculate() {
        return calculate(board, alliance, depth);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public boolean isHumanPlayer() {
        return false;
    }
}