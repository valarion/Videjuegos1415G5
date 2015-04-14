package videjouegos1415g5.animation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Animation {
	protected NavigableSet<Frame> frames;
	protected long animationlength;
	
	public Animation() {
		frames = new TreeSet<Frame>();
		animationlength = 0;
	}
	
	public void addFrame(Image img, long time) {
		frames.add(new Frame(img,time));
		animationlength += time;
	}

	public void draw(Graphics2D g, Rectangle position, long state) {
		Image sprite = frames.floor(new Frame(state%animationlength)).getImg();
		g.drawImage(sprite, 0, 0, null); // TODO draw in position
	}
}
