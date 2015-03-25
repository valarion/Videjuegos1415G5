package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Image;

public class Obstacle extends GameObject {

	private Image obstacle;

	public Obstacle(int x, int y, int size, Animation animation,
			SpriteSheet ss, int scale) {
		super(x, y, size, animation);
		this.obstacle = ss.obtenerSprite(2 * (size / scale), 0, size / scale,
				size / scale).getScaledInstance(size, size, Image.SCALE_SMOOTH);
	}

	public boolean intersects(GameObject o) {
		return position.intersects(o.position);
	}

	public void update(long ms) {
		animation.update(ms);
	}

	public void draw(Graphics2D g) {
		g.drawImage(obstacle, position.x, position.y, null);
	}
}
