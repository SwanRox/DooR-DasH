package game.engine.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundController {

	
	private Media menumusic = new Media(this.getClass().getResource("menumusic.mp3").toExternalForm());
	private Media pickmusic = new Media(this.getClass().getResource("pickmusic.mp3").toExternalForm());
	private Media gamemusic = new Media(this.getClass().getResource("gamemusic.mp3").toExternalForm());

	private MediaPlayer mediaPlayer = new MediaPlayer(menumusic);
	
	public void playMenuMusic(){
		mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(menumusic);
		mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
		mediaPlayer.play();
	}
	public void playPickMusic(){
		mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(pickmusic);
		mediaPlayer.play();
	}
	public void playGameMusic(){
		mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(gamemusic);
		mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
		mediaPlayer.play();
	}
}
