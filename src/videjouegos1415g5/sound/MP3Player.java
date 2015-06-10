package videjouegos1415g5.sound;

import javax.sound.sampled.*;

public class MP3Player {
	
	public static final MP3Player title = new MP3Player("/music/title.mp3");
	public static final MP3Player intro = new MP3Player("/music/intro.mp3");
	public static final MP3Player level_start = new MP3Player("/music/level_start.mp3");
	public static final MP3Player map_start = new MP3Player("/music/map_start.mp3");
	public static final MP3Player invincible = new MP3Player("/music/invincible.mp3");


	
	private Clip clip;
	private int frameposition;
	
	public MP3Player(String s) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(
					getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if (clip == null) return;
		if (!clip.isRunning()) {
			stop();
			if (frameposition > 0) clip.setFramePosition(frameposition);
			else clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			frameposition = 0;
		}
	}
	
	public void pause() {
		if (clip != null && clip.isRunning()) {
			frameposition = clip.getFramePosition();
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
}














