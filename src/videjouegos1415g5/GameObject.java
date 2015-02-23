package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameObject {
	
	protected Rectangle position;
	protected AnimationInstance animation;
	
	public GameObject(int x, int y, int size, Animation animation) {
		position = new Rectangle(x,y,size,size);
		this.animation = new AnimationInstance(animation);
	}
	
	public boolean intersects(GameObject o) {
		return position.intersects(o.position);
	}
	
	public void update(long ms) {
		animation.update(ms);
	}
	
	public void draw(Graphics2D g) {
		animation.draw(g,position);
	}
}
