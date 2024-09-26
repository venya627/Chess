package logic.board.bitboard;

public class Engine {

    public static long perft(Bitboard bitboard, int depth) {
        int[] moves = bitboard.generateAllMoves();

        if (depth == 0) {
            return 1;
        }

        if (depth == 1) {
            return moves.length;
        }

        long nodes = 0;

        for (int move : moves) {
            nodes += perft(Bitboard.makeMove(bitboard.getFEN(), move), depth - 1);
        }

        return nodes;
    }

    public static void perftFancy(Bitboard bitboard, int depth) {
        long totalNodes = 0;
        depth--;

        long totalTime = System.nanoTime();
        int[] moves = bitboard.generateAllMoves();
        totalTime = System.nanoTime() - totalTime;

        bitboard.copy();

        for (int move : moves) {
            bitboard.makeMove(move);

            long time = System.nanoTime();
            long nodes = perft(bitboard, depth);
            totalTime += System.nanoTime() - time;
            totalNodes += nodes;

            System.out.printf("%s: %d\n", Bitboard.moveToString(move), nodes);

            bitboard.restore();
        }

        System.out.printf("\nNodes: %d\nTime(sec): %.6f\n", totalNodes, totalTime / 1000000000.0);
    }

    public static int minimax(Bitboard bitboard, int depth, int alpha, int beta, boolean isMaximazing) {
        if (depth < 1 /*&& bitboard.isGameOver()*/) {
            return evaluate(bitboard);
        }

        if (isMaximazing) {

        } else {

        }

        return 0;
    }

    public static int evaluate(Bitboard bitboard) {
        return 0;
    }
}
