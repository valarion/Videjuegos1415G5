package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteSheet;

public class Obstacle extends GameObject {

	private boolean solid;
	private Animation a1, a2, a3;

	public Obstacle(int x, int y, int size, Animation animation,
			SpriteSheet ss, int scale, boolean solid) {
		super(x - 8*scale, y + 24*scale, size, size, animation);
		//this.position.x = this.position.x - 8 * scale;
		//this.position.y = this.position.y + 24 * scale;
		this.solid = solid;
		
		ss = new SpriteSheet(ScaleImg.scale(ss.getImage(), scale));
		
		BufferedImage[] obstacle = {ss.obtenerSprite(2*size, 0, size, size)};
		a1 = new Animation(obstacle, 1, Direction.DOWN);
		
		BufferedImage[] explosion = {ss.obtenerSprite(size*7, size, size, size),
				ss.obtenerSprite(size*8, size, size, size),
				ss.obtenerSprite(size*9, size, size, size),
				ss.obtenerSprite(size*10, size, size, size),
				ss.obtenerSprite(size*11, size, size, size),
				ss.obtenerSprite(size*12, size, size, size),
				ss.obtenerSprite(size*13, size, size, size)};
		
		a2 = new Animation(explosion, 6, Direction.DOWN);
		
		BufferedImage[] blink = {ss.obtenerSprite(2*size, 0, size, size),
				ss.obtenerSprite(size*7, size, size, size)};
		
		a3 = new Animation(blink, 20, Direction.DOWN);

		this.animation = a1;
	}

//	public boolean intersects(GameObject o) {
//		return position.intersects(o.position);
//	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void die() {
		if (!removed) {
			if (!(animation == a2)) {
				animation = a2;
				animation.start();
			}
		}
	}

	public void update(long ms) {
		//animation.update(ms);
	}
	
	public void tick() {
		animation.tick();
		if (animation.equals(a2) && animation.finalFrame()) {
			remove();
		}
	}

	public void draw(Graphics2D g) {
		if (solid) {
			if(animation == a3)
				g.drawImage(a1.getSprite(), position.x, position.y, null);
			g.drawImage(animation.getSprite(), position.x, position.y, null);
//			g.setColor(Color.BLACK);
//		 	g.fillRect(position.x, position.y, position.width, position.height);
		}
//		else {
//			g.setColor(Color.ORANGE);
//			g.fillRect(position.x, position.y, position.width, position.height);
//		}
	}
	
	public void blink() {
		if(animation == a1) {
			animation = a3;
			a3.start();
		}
	}
}
