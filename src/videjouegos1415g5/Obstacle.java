package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Image;

public class Obstacle extends GameObject {
	
	private Tile obstacle;
	private int scale;
	private int tileSize;

	public Obstacle(int x, int y, int size, Animation animation, SpriteSheet ss, int tileSize, int scale) {
		super(x, y, size, animation);
		this.obstacle = new Tile(ss.obtenerSprite(2*(tileSize/scale), 0, tileSize/scale, tileSize/scale), true);
		this.position.x = x;
		this.position.y = y;
		this.tileSize = tileSize;
		this.scale = scale;
	}
	
	public boolean intersects(GameObject o) {
		return position.intersects(o.position);
	}
	
	public void update(long ms) {
		animation.update(ms);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(obstacle.getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH), position.x, position.y, null);
	}

}
