package gui.theme;

public class Theme {

	private BoardTheme boardTheme;
	private PieceTheme pieceTheme;
	private SoundTheme soundTheme;

	public Theme(BoardTheme boardTheme, PieceTheme pieceTheme, SoundTheme soundTheme) {
		this.boardTheme = boardTheme;
		this.pieceTheme = pieceTheme;
		this.soundTheme = soundTheme;
	}
	
	public void setBoardTheme(BoardTheme boardTheme) {
		this.boardTheme = boardTheme;
	}
	
	public void setPieceTheme(PieceTheme pieceTheme) {
		this.pieceTheme = pieceTheme;
	}
	
	public void setSoundTheme(SoundTheme soundTheme) {
		this.soundTheme = soundTheme;
	}

	public BoardTheme board() {
		return boardTheme;
	}
	
	public PieceTheme pieces() {
		return pieceTheme;
	}
	
	public SoundTheme sounds() {
		return soundTheme;
	}
}