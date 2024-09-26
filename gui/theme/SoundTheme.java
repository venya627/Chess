package gui.theme;

import java.io.File;
import javafx.scene.media.AudioClip;

public class SoundTheme {
	
	private final String themeName;
	private final String sourceFolder;
	
	public SoundTheme(String themeName, String sourceFolder) {
		this.themeName = themeName;
		this.sourceFolder = sourceFolder;
	}
	
	public AudioClip getRegular() {
		return new AudioClip(new File(sourceFolder + "regular.mp3").toURI().toString());
	}
	
	public AudioClip getCapture() {
		return new AudioClip(new File(sourceFolder + "capture.mp3").toURI().toString());
	}
	
	public AudioClip getCastling() {
		return new AudioClip(new File(sourceFolder + "castling.mp3").toURI().toString());
	}
	
	public AudioClip getPromotion() {
		return new AudioClip(new File(sourceFolder + "promotion.mp3").toURI().toString());
	}
	
	public AudioClip getCheck() {
		return new AudioClip(new File(sourceFolder + "check.mp3").toURI().toString());
	}
	
	public AudioClip getIllegal() {
		return new AudioClip(new File(sourceFolder + "illegal.mp3").toURI().toString());
	}
	
	public AudioClip getGameStarted() {
		return new AudioClip(new File(sourceFolder + "game_start.mp3").toURI().toString());
	}
	
	public AudioClip getGameWon() {
		return new AudioClip(new File(sourceFolder + "game_win.mp3").toURI().toString());
	}
	
	public AudioClip getGameLost() {
		return new AudioClip(new File(sourceFolder + "game_lose.mp3").toURI().toString());
	}
	
	public AudioClip getGameDraw() {
		return new AudioClip(new File(sourceFolder + "game_draw.mp3").toURI().toString());
	}
	
	public AudioClip getDrawOffered() {
		return new AudioClip(new File(sourceFolder + "draw_offer.mp3").toURI().toString());
	}
	
	public AudioClip getDrawDeclined() {
		return new AudioClip(new File(sourceFolder + "draw_decline.mp3").toURI().toString());
	}
	
	@Override
	public String toString() {
		return themeName;
	}
}
