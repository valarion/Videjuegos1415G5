package videjouegos1415g5.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static final Sound title = new Sound("");
	public static final Sound intro = new Sound("");
	
	private boolean running;
	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
			running = false;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (!running) {
			try {
				new Thread() {
					public void run() {
						clip.play();
					}
				}.start();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			running = true;
		}
	}
	
	public void stop() {
		clip.stop();
		running = false;
	}
}