package logic.board.bitboard;

import java.util.Arrays;
import static logic.board.bitboard.Bitboard.*;

public class Magic {

    // Best seed I was able to find so far
    // Time to compute all 128 magic numbers is 253694100 nanoseconds
    private static int seed = -1694677673;

    public static long findMagicNumber(int square, boolean isBishop) {
        long[] occupancies = new long[4096];
        long[] attacks = new long[4096];
        long[] usedAttacks = new long[4096];

        long attackMask = isBishop ? BISHOP_MASK[square] : ROOK_MASK[square];
        int relevantBits = isBishop ? BISHOP_RELEVANT_BITS[square] : ROOK_RELEVANT_BITS[square];
        int indexes = 1 << relevantBits;

        for (int i = 0; i < indexes; i++) {
            occupancies[i] = setOccupancy(i, relevantBits, attackMask);
            attacks[i] = isBishop ? calculateBishopAttacks(square, occupancies[i]) : calculateRookAttacks(square, occupancies[i]);
        }

        while (true) {
            long magicNumberCandidate = generateMagicNumberCandidate();

            if (Long.bitCount((attackMask * magicNumberCandidate) & 0xFF00000000000000L) < 6) continue;

            Arrays.fill(usedAttacks, 0L);

            boolean fail = false;

            for (int j = 0; j < indexes && !fail; j++) {
                int magicIndex = (int) ((occupancies[j] * magicNumberCandidate) >>> (64 - relevantBits));

                if (usedAttacks[magicIndex] == 0L)
                    usedAttacks[magicIndex] = attacks[j];
                else if (usedAttacks[magicIndex] != attacks[j])
                    fail = true;
            }

            if (!fail) return magicNumberCandidate;
        }
    }

    private static long generateMagicNumberCandidate() {
        return randomLong() & randomLong() & randomLong();
    }

    private static long randomLong() {
        long n1, n2, n3, n4;

        n1 = (long) (randomInt()) & 0xFFFF;
        n2 = (long) (randomInt()) & 0xFFFF;
        n3 = (long) (randomInt()) & 0xFFFF;
        n4 = (long) (randomInt()) & 0xFFFF;

        return n1 | (n2 << 16) | (n3 << 32) | (n4 << 48);
    }

    private static int randomInt() {
        seed ^= seed << 13;
        seed ^= seed >>> 17;
        seed ^= seed << 5;

        return seed;
    }
}
