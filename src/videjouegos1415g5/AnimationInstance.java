package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class AnimationInstance {
	protected Animation animation;
	protected long state;
	
	public AnimationInstance(Animation animation) {
		this.animation = animation;
		this.state = 0;
	}

	public void update(long ms) {
		state+=ms;
	}

	public void draw(Graphics2D g, Rectangle position) {
		animation.draw(g,position,state);
	}
}