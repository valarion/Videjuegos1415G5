package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import videjouegos1415g5.animation.Animation;

public class GameObject {
	
	public Rectangle position;
	protected Animation animation;
	
	public GameObject(int x, int y, int width, int height, Animation animation) {
		position = new Rectangle(x, y, width, height);
		this.animation = animation;
	}
	
	public boolean intersects(GameObject o) {
		return position.intersects(o.position);
	}
	
	public void tick(long ms) {
		animation.tick();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
	}
}
