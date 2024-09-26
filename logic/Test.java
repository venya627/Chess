package logic;

import java.io.FileNotFoundException;
import java.util.*;

import logic.board.bitboard.*;

import static logic.board.bitboard.Bitboard.*;

public class Test {

    public static Bitboard b = new Bitboard();

    public static void main(String[] args) throws FileNotFoundException {
        Engine.perftFancy(b, 6);
    }

    public static void move(int move) {
        b.makeMove(move);
        System.out.println(b.getFEN());
    }

    public static void printMoves() {
        System.out.println(movesToString(b.generateAllMoves1()));
    }

    public static void start() {
        Bitboard bitboard = new Bitboard();
        Scanner console = new Scanner(System.in);

        while (true) {
            String command = console.nextLine();

            String[] tokens = command.split(" ", 2);

            if (command.equalsIgnoreCase("quit")) {
                break;
            } else if (command.equalsIgnoreCase("fen")) {
                System.out.println(bitboard.getFEN());
            } else if (tokens[0].equalsIgnoreCase("print")) {
                if (tokens[1].equalsIgnoreCase("moves"))
                    System.out.println(movesToString(bitboard.generateAllMoves()));
                else if (tokens[1].equalsIgnoreCase("board"))
                    System.out.println(bitboard);
            } else if (tokens[0].equalsIgnoreCase("position")) {
                bitboard.loadPosition(tokens[1]);
            } else if (tokens[0].equalsIgnoreCase("search")) {

            } else if (tokens[0].equalsIgnoreCase("play")) {

            } else if (tokens[0].equalsIgnoreCase("test")) {

            } else if (tokens[0].equalsIgnoreCase("move")) {
                bitboard.makeMove(Integer.parseInt(tokens[1]));
            }
        }
    }

    private static void searchBestSeed(long bestTime) {
        java.util.Random r = new Random();

        while (bestTime > 100000000) {
            int seed = 0;

            while (seed == 0) seed = r.nextInt();

            //Magic.setSeed(seed);

            long start = System.nanoTime();

            for (int square = 0; square < 64; square++) {
                Magic.findMagicNumber(square, true);
                Magic.findMagicNumber(square, false);
            }

            long time = System.nanoTime() - start;

            if (time < bestTime) {
                bestTime = time;

                System.out.println("Time: " + bestTime);
                System.out.println("Seed: " + seed + "\n");
            }
        }
    }

    /*private static void play(Board board) {
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println(board);

            int xFrom = s.nextInt();
            int yFrom = s.nextInt();

            int xTo = s.nextInt();
            int yTo = s.nextInt();

            if (xFrom > 7 || xFrom < 0 || yFrom > 7 || yFrom < 0 || xTo > 7 || xTo < 0 || yTo > 7 || yTo < 0) break;

            board.makeMove(board.getSquare(xFrom, yFrom).getPiece(), board.getSquare(xTo, yTo));
        }
    }*/

    // Text file input format:
    //  <FEN> bm <best moves> am <avoid moves>\n ...
    // Output format:
    //  Test <number>: <result>, <engine best move>, <engine evaluation of a position after move>,
    //  <execution time in milliseconds>\n ...
    /*private static void runEngineTests(Scanner reader, int searchDepth) {
        int test = 1;

        double time = 0;
        int total = 0;
        int passed = 0;

        while (reader.hasNextLine()) {
            try {
                String output = "";

                List<String> input = Arrays.asList(reader.nextLine().split(" "));

                String position = input.get(0);
                List<String> bestMoves = new ArrayList<>();
                List<String> avoidMoves = new ArrayList<>();

                for (int s = 1; s < 6; s++) {
                    position += " " + input.get(s);
                }

                for (int bm = input.indexOf("bm") + 1; bm != 0 && (bm < input.indexOf("am") || bm < input.size()); bm++) {
                    bestMoves.add(input.get(bm));
                }

                for (int am = input.indexOf("am") + 1; am != 0 && (am < input.indexOf("bm") || am < input.size()); am++) {
                    avoidMoves.add(input.get(am));
                }

                long start = System.currentTimeMillis();

                Engine.BestMove evalMove = Engine.search(new Board(position), searchDepth);

                long end = System.currentTimeMillis() - start;
                time += end;

                String moveNotation = Move.toAlgebraicNotation(evalMove.piece, evalMove.square);

                output += "Test " + test + ": ";

                if (bestMoves.contains(moveNotation) && !avoidMoves.contains(moveNotation)) {
                    output += "pass";
                    passed++;
                } else {
                    output += "fail";
                }

                output += ", " + moveNotation + ", " + evalMove.evaluation + ", " + end;
                total++;

                System.out.println(output);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal input!");
            }

            test++;
        }

        System.out.println("Score: " + passed +  "/" + total);
        System.out.println("Time spent: " + time / 1000 + " seconds total, " + time / total + " milliseconds per test on average");
    }*/
}







