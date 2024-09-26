package logic.engine;

import logic.board.Board;
import logic.board.Move;
import logic.board.Square;
import logic.pieces.Alliance;
import logic.pieces.Piece;
import logic.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    // Bonuses for piece placement
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final int[][] PAWN_TABLE = {
            { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
            { 100, 10 }, { 100, 20 }, { 100, 20 }, { 100, -40 }, { 100, -40 }, { 100, 20 }, { 100, 20 }, { 100, 10 },
            { 20, 10 }, { 20, -10 }, { 40, -20 }, { 60, 0 }, { 60, 0 }, { 40, -20 }, { 20, -10 }, { 20, 10 },
            { 10, 0 }, { 10, 0 }, { 20, 0 }, { 50, 40 }, { 50, 40 }, { 20, 0 }, { 10, 0 }, { 10, 0 },
            { 0, 10 }, { 0, 10 }, { 0, 20 }, { 40, 50 }, { 40, 50 }, { 0, 20 }, { 0, 10 }, { 0, 10 },
            { 10, 20 }, { -10, 20 }, { -20, 40 }, { 0, 60 }, { 0, 60 }, { -20, 40 }, { -10, 20 }, { 10, 20 },
            { 10, 100 }, { 20, 100 }, { 20, 100 }, { -40, 100 }, { -40, 100 }, { 20, 100 }, { 20, 100 }, { 10, 100 },
            { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }
    };

    public static final int[][] KNIGHT_TABLE = {
            { -100, -100 }, { -80, -80 }, { -60, -60 }, { -60, -60 }, { -60, -60 }, { -60, -60 }, { -80, -80 }, { -100, -100 },
            { -80, -80 }, { -40, -40 }, { 0, 0 }, { 0, 10 }, { 0, 10 }, { 0, 0 }, { -40, -40 }, { -80, -80 },
            { -60, -60 }, { 0, 10 }, { 20, 20 }, { 30, 30 }, { 30, 30 }, { 20, 20 }, { 0, 10 }, { -60, -60 },
            { -60, -60 }, { 10, 0 }, { 30, 30 }, { 40, 40 }, { 40, 40 }, { 30, 30 }, { 10, 0 }, { -60, -60 },
            { -60, -60 }, { 0, 10 }, { 30, 30 }, { 40, 40 }, { 40, 40 }, { 30, 30 }, { 0, 10 }, { -60, -60 },
            { -60, -60 }, { 10, 0 }, { 20, 20 }, { 30, 30 }, { 30, 30 }, { 20, 20 }, { 10, 0 }, { -60, -60 },
            { -80, -80 }, { -40, -40 }, { 0, 0 }, { 10, 0 }, { 10, 0 }, { 0, 0 }, { -40, -40 }, { -80, -80 },
            { -100, -100 }, { -80, -80 }, { -60, -60 }, { -60, -60 }, { -60, -60 }, { -60, -60 }, { -80, -80 }, { -100, -100 }
    };

    public static final int[][] BISHOP_TABLE = {
            { -40, -40 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -40, -40 },
            { -20, -20 }, { 0, 10 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 10 }, { -20, -20 },
            { -20, -20 }, { 0, 20 }, { 10, 20 }, { 20, 20 }, { 20, 20 }, { 10, 20 }, { 0, 20 }, { -20, -20 },
            { -20, -20 }, { 10, 0 }, { 10, 20 }, { 20, 20 }, { 20, 20 }, { 10, 20 }, { 10, 0 }, { -20, -20 },
            { -20, -20 }, { 0, 10 }, { 20, 10 }, { 20, 20 }, { 20, 20 }, { 20, 10 }, { 0, 10 }, { -20, -20 },
            { -20, -20 }, { 20, 0 }, { 20, 10 }, { 20, 20 }, { 20, 20 }, { 20, 10 }, { 20, 0 }, { -20, -20 },
            { -20, -20 }, { 10, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 10, 0 }, { -20, -20 },
            { -40, -40 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -20, -20 }, { -40, -40 }
    };

    public static final int[][] ROOK_TABLE = {
            { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 10 }, { 0, 10 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
            { 10, -10 }, { 20, 0 }, { 20, 0 }, { 20, 0 }, { 20, 0 }, { 20, 0 }, { 20, 0 }, { 10, -10 },
            { -10, -10 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { -10, -10 },
            { -10, -10 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { -10, -10 },
            { -10, -10 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { -10, -10 },
            { -10, -10 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { -10, -10 },
            { -10, 10 }, { 0, 20 }, { 0, 20 }, { 0, 20 }, { 0, 20 }, { 0, 20 }, { 0, 20 }, { -10, 10 },
            { 0, 0 }, { 0, 0 }, { 0, 0 }, { 10, 0 }, { 10, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }
    };

    public static final int[][] QUEEN_TABLE = {
            { -40, -40 }, { -20, -20 }, { -20, -20 }, { -10, -10 }, { -10, -10 }, { -20, -20 }, { -20, -20 }, { -40, -40 },
            { -20, -20 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 10 }, { 0, 0 }, { -20, -20 },
            { -20, -20 }, { 0, 0 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 0, 10 }, { -20, -20 },
            { -10, -10 }, { 0, 0 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 0, 0 }, { -10, 0 },
            { 0, -10 }, { 0, 0 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 0, 0 }, { -10, -10 },
            { -20, -20 }, { 10, 0 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 0, 0 }, { -20, -20 },
            { -20, -20 }, { 0, 0 }, { 10, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { -20, -20 },
            { -40, -40 }, { -20, -20 }, { -20, -20 }, { -10, -10 }, { -10, -10 }, { -20, -20 }, { -20, -20 }, { -40, -40 }
    };

    public static final int[][] KING_TABLE = {
            { -60, 40 }, { -80, 60 }, { -80, 20 }, { -100, 0 }, { -100, 0 }, { -80, 20 }, { -80, 60 }, { -60, 40 },
            { -60, 40 }, { -80, 40 }, { -80, 0 }, { -100, 0 }, { -100, 0 }, { -80, 0 }, { -80, 40 }, { -60, 40 },
            { -60, -20 }, { -80, -40 }, { -80, -40 }, { -100, -40 }, { -100, -40 }, { -80, -40 }, { -80, -40 }, { -60, -20 },
            { -60, -40 }, { -80, -60 }, { -80, -60 }, { -100, -80 }, { -100, -80 }, { -80, -60 }, { -80, -60 }, { -60, -40 },
            { -40, -60 }, { -60, -80 }, { -60, -80 }, { -80, -100 }, { -80, -100 }, { -60, -80 }, { -60, -80 }, { -40, -60 },
            { -20, -60 }, { -40, -80 }, { -40, -80 }, { -40, -100 }, { -40, -100 }, { -40, -80 }, { -40, -80 }, { -20, -60 },
            { 40, -60 }, { 40, -80 }, { 0, -80 }, { 0, -100 }, { 0, -100 }, { 0, -80 }, { 40, -80 }, { 40, -60 },
            { 40, -60 }, { 60, -80 }, { 20, -80 }, { 0, -100 }, { 0, -100 }, { 20, -80 }, { 60, -80 }, { 40, -60 }
    };

    public static final int[][] KING_TABLE_EG = {
            { -100, -100 }, { -80, -60 }, { -60, -60 }, { -40, -60 }, { -40, -60 }, { -60, -60 }, { -80, -60 }, { -100, -100 },
            { -60, -60 }, { -40, -60 }, { -20, 0 }, { 0, 0 }, { 0, 0 }, { -20, 0 }, { -40, -60 }, { -60, -60 },
            { -60, -60 }, { -20, -20 }, { 40, 40 }, { 60, 60 }, { 60, 60 }, { 40, 40 }, { -20, -20 }, { -60, -60 },
            { -60, -60 }, { -20, -20 }, { 60, 60 }, { 80, 80 }, { 80, 80 }, { 60, 60 }, { -20, -20 }, { -60, -60 },
            { -60, -60 }, { -20, -20 }, { 60, 60 }, { 80, 80 }, { 80, 80 }, { 60, 60 }, { -20, -20 }, { -60, -60 },
            { -60, -60 }, { -20, -20 }, { 40, 40 }, { 60, 60 }, { 60, 60 }, { 40, 40 }, { -20, -20 }, { -60, -60 },
            { -60, -60 }, { -60, -40 }, { 0, -20 }, { 0, 0 }, { 0, 0 }, { 0, -20 }, { -60, -40 }, { -60, -60 },
            { -100, -100 }, { -60, -80 }, { -60, -60 }, { -60, -40 }, { -60, -40 }, { -60, -60 }, { -60, -80 }, { -100, -100 }
    };

    public static class BestMove {
        public Piece piece;
        public Square square;
        public int evaluation;
    }

    public static BestMove search(Board board, int depth) {
        return search(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentTurn().isWhite());
    }

    private static BestMove search(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        BestMove bestMove = new BestMove();
        bestMove.piece = null;
        bestMove.square = null;
        bestMove.evaluation = evaluate(board);

        if (depth <= 0 || board.isGameOver())
            return bestMove;

        if (isMaximizing) {
            bestMove.evaluation = Integer.MIN_VALUE;

            for (Piece piece : board.getTeam(Alliance.WHITE)) {
                for (Square targetSquare : piece.getAllLegalMoves()) {
                    int eval = search(Board.testMove(piece, targetSquare), depth - 1, alpha, beta, false).evaluation;

                    if (eval > bestMove.evaluation) {
                        bestMove.piece = piece;
                        bestMove.square = targetSquare;
                        bestMove.evaluation = eval;
                    }

                    if (bestMove.evaluation > beta) break;

                    alpha = Math.max(alpha, bestMove.evaluation);
                }
            }
        } else {
            bestMove.evaluation = Integer.MAX_VALUE;

            for (Piece piece : board.getTeam(Alliance.BLACK)) {
                for (Square targetSquare : piece.getAllLegalMoves()) {
                    int eval = search(Board.testMove(piece, targetSquare), depth - 1, alpha, beta, true).evaluation;

                    if (eval < bestMove.evaluation) {
                        bestMove.piece = piece;
                        bestMove.square = targetSquare;
                        bestMove.evaluation = eval;
                    }

                    if (bestMove.evaluation < alpha) break;

                    beta = Math.min(beta, bestMove.evaluation);
                }
            }
        }

        return bestMove;
    }

    public static long perft(Board board, int depth) {
        if (depth < 1) {
            return 1;
        }

        long nodes = 0;

        for (Piece piece : board.getTeam(board.getCurrentTurn())) {
            for (Square targetSquare : piece.getAllLegalMoves()) {
                nodes += perft(Board.testMove(piece, targetSquare), depth - 1);
            }
        }

        return nodes;
    }

    public static int evaluate(Board board) {
        int whiteScore = evaluateMaterialValue(board, Alliance.WHITE) + evaluatePieceMobility(board, Alliance.WHITE);
        int blackScore = evaluateMaterialValue(board, Alliance.BLACK) + evaluatePieceMobility(board, Alliance.BLACK);

        if (board.getKing(Alliance.WHITE).isInStalemate() || board.getKing(Alliance.BLACK).isInStalemate())
            return 0;

        if (board.getKing(Alliance.WHITE).isCheckmated())
            return Integer.MIN_VALUE;

        if (board.getKing(Alliance.BLACK).isCheckmated())
            return Integer.MAX_VALUE;

        return whiteScore - blackScore;
    }

    public static int evaluateMaterialValue(Board board, Alliance alliance) {
        int total = board.getTotalMaterialValue(alliance);

        for (Piece piece : board.getTeam(alliance)) {
            int square = 8 * piece.getX() + piece.getY();
            int color = piece.getColor().isWhite() ? 1 : 0;

            switch (piece.getType()) {
                case PAWN:
                    total += PAWN_TABLE[square][color];
                    break;

                case KNIGHT:
                    total += KNIGHT_TABLE[square][color];
                    break;

                case BISHOP:
                    total += BISHOP_TABLE[square][color];
                    break;

                case ROOK:
                    total += ROOK_TABLE[square][color];
                    break;

                case QUEEN:
                    total += QUEEN_TABLE[square][color];
                    break;

                case KING:
                    if (isEndGame(board)) total += KING_TABLE_EG[square][color];
                    else total += KING_TABLE[square][color];
                    break;
            }
        }

        return total;
    }

    public static int evaluateKingSafety(Board board, Alliance alliance) {
        //  3900 max diff in material value without bonuses
        //https://www.chessprogramming.org/King_Safety
        return 0;
    }

    public static int evaluatePieceMobility(Board board, Alliance alliance) {
        // mobility bonuses
        // opening: knights, bishops
        // middle game: rooks, queen
        // end game: king, pawns

        // max target squares for:
        // pawn = 4
        // knight = 8
        // bishop = 13
        // rook = 14
        // queen = 27
        // king = 8

        // p = x7
        // n = x3
        // b = x2
        // r = x2
        // q = x1
        // k = x3

        int total = 0;

        for (Piece piece : board.getTeam(alliance)) {
            int bonusMultiplier = 1;

            if (isEndGame(board)) {
                if (piece.getType() == PieceType.KING) bonusMultiplier = 3;
                else if (piece.getType() == PieceType.PAWN) bonusMultiplier = 7;
            } else if (isMiddleGame(board)) {
                if (piece.getType() == PieceType.ROOK)  bonusMultiplier = 2;
            } else {
                if (piece.getType() == PieceType.KNIGHT) bonusMultiplier = 3;
                else if (piece.getType() == PieceType.BISHOP) bonusMultiplier = 2;
            }

            total += piece.getAllLegalMoves().size() * bonusMultiplier;
        }

        return total * 10;
    }

    public static boolean isMiddleGame(Board board) {
        return !isEndGame(board) && (isBackrankSparse(board) || countMajorsAndMinors(board) <= 10 || getPositionMixedness(board) > 85.0);
    }

    public static boolean isEndGame(Board board) {
        return countMajorsAndMinors(board) <= 6;
    }

    public static boolean isBackrankSparse(Board board) {
        int whiteCount = 0;
        int blackCount = 0;

        for (Piece piece : board.getTeam(Alliance.WHITE)) {
            if (piece.getX() == 7) {
                whiteCount++;
            }
        }

        for (Piece piece : board.getTeam(Alliance.BLACK)) {
            if (piece.getX() == 0) {
                blackCount++;
            }
        }

        return whiteCount < 4 || blackCount < 4;
    }

    public static int countMajorsAndMinors(Board board) {
        int count = 0;

        for (Piece piece : board.getTeam(Alliance.WHITE)) {
            if (piece.getType().isMajor() || piece.getType().isMinor()) {
                count++;
            }
        }

        for (Piece piece : board.getTeam(Alliance.BLACK)) {
            if (piece.getType().isMajor() || piece.getType().isMinor()) {
                count++;
            }
        }

        return count;
    }

    public static double getPositionMixedness(Board board) {
        List<Integer> whiteUniqueX = new ArrayList<>();
        List<Integer> whiteUniqueY = new ArrayList<>();
        List<Integer> blackUniqueX = new ArrayList<>();
        List<Integer> blackUniqueY = new ArrayList<>();

        for (Piece piece : board.getTeam(Alliance.WHITE)) {
            if (!whiteUniqueX.contains(piece.getX())) {
                whiteUniqueX.add(piece.getX());
            }

            if (!whiteUniqueY.contains(piece.getY())) {
                whiteUniqueY.add(piece.getY());
            }
        }

        for (Piece piece : board.getTeam(Alliance.BLACK)) {
            if (!blackUniqueX.contains(piece.getX())) {
                blackUniqueX.add(piece.getX());
            }

            if (!blackUniqueY.contains(piece.getY())) {
                blackUniqueY.add(piece.getY());
            }
        }

        int totalUniqueCoordinates = whiteUniqueX.size() + whiteUniqueY.size() + blackUniqueX.size() + blackUniqueY.size();
        int maximumUniqueCoordinates = 2 * (board.getTeam(Alliance.WHITE).size() + board.getTeam(Alliance.BLACK).size());

        if (maximumUniqueCoordinates > 32) maximumUniqueCoordinates = 32;

        return 100.0 * totalUniqueCoordinates / maximumUniqueCoordinates;
    }
}

