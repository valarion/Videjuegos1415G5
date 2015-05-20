package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.awt.Image;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.SpriteSheet;

public class Obstacle extends GameObject {

	private Image obstacle;
	private boolean solid;

	public Obstacle(int x, int y, int size, Animation animation,
			SpriteSheet ss, int scale, boolean solid) {
		super(x - 8*scale, y + 24*scale, size, size, animation);
		//this.position.x = this.position.x - 8 * scale;
		//this.position.y = this.position.y + 24 * scale;
		this.obstacle = ss.obtenerSprite(2 * (size / scale), 0, size / scale,
				size / scale).getScaledInstance(size, size, Image.SCALE_SMOOTH);
		this.solid = solid;
	}

//	public boolean intersects(GameObject o) {
//		return position.intersects(o.position);
//	}
	
	public boolean isSolid() {
		return solid;
	}

	public void update(long ms) {
		//animation.update(ms);
	}

	public void draw(Graphics2D g) {
		if (solid)
			g.drawImage(obstacle, position.x, position.y, null);
	}
}
