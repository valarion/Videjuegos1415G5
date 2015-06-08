package videjouegos1415g5.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public static final Sound boom = new Sound("/music/boom.wav");

	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		clip.stop();
	}
}