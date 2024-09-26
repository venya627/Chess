package logic.board;

import logic.pieces.Alliance;

public enum GameStatus {
	STARTED, ACTIVE, ENDED;

	public static GameStatus getGameStatus(Board board) {
		/*
		 * ACTIVE, 
	CHECKMATE, RESIGNATION, TIMEOUT, 
	STALEMATE, INSUFFICIENT_MATERIAL, FIFTY_MOVE_RULE, THREEFOLD_REPETITION, DRAW_BY_AGREEMENT;
		 */
		if (board.getFullmoves() == 1)
			return STARTED;
		
		if (board.getKing(Alliance.WHITE).isCheckmated() || board.getKing(Alliance.BLACK).isCheckmated())
			return ENDED;
		
		return ACTIVE;
	}
}
