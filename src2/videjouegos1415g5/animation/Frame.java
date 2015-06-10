package videjouegos1415g5.animation;

import java.awt.image.BufferedImage;

public class Frame implements Comparable<Frame> {
	protected long length;
	protected BufferedImage img;

	public Frame(long length) {
		this.length = length;
		this.img = null;
	}
	
	public Frame(BufferedImage img, long time) {
		this.length = time;
		this.img = img;
	}

	@Override
	public int compareTo(Frame f) {
		return Long.compare(length, f.length);
	}
	
	public BufferedImage getImg() {
		return img;
	}
}
