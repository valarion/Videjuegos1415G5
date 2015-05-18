package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.AnimationInstance;
import videjouegos1415g5.entity.Entity;
import videjouegos1415g5.entity.Mob;

public class GameObject {
	
	protected final Random random = new Random();
	public Rectangle position;
	protected AnimationInstance animation;
	public boolean removed;
	public int xr = 6;
	public int yr = 6;
	
	public GameObject(int x, int y, int width, int height, Animation animation) {
		position = new Rectangle(x, y, width, height);
		this.animation = new AnimationInstance(animation);
	}
	
	public boolean intersects(GameObject o) {
		return position.intersects(o.position);
	}
	
	public void remove() {
		removed = true;
	}
	
	public void hurt(Mob mob, int dmg) {
	}

	protected void touchedBy(GameObject go) {
	}
	
	public Rectangle getBounds() {
		return position;
	}
	
	public void tick() {
	}
	
	public void render(Graphics2D g) {
	}
	
	public void tick(long ms) {
		animation.update(ms);
	}
	
	public void draw(Graphics2D g) {
		animation.draw(g,position);
	}
}
