package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.util.Random;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.Obstacle;

public class Entity extends GameObject {
	
	private final int colisionlimit = 5;
	
	protected boolean located = false;
	protected int scale;
	protected SpriteLoader sl;
	protected SpriteSheet ss;
	
	
	public Entity() {
		super(0, 0, 0, 0, null);
		this.scale = Main.ESCALA;
	}

	protected final Random random = new Random();
	//public int x, y;
	public int xr = 6;
	public int yr = 6;
	
	public void tick() {
		animation.tick();
	}
	
	public void render(Graphics2D g) {
	}

	public void remove() {
		removed = true;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return super.intersects(new GameObject(x0,y0,x1-x0,y1-y0,null));
		//return !(position.x + xr < x0 || position.y + yr < y0 || position.x - xr > x1 || position.y - yr > y1);
	}

	public boolean blocks(Entity e) {
		return false;
	}

	public void hurt(Entity mob, int dmg) {
	}

	public void touchedBy(Entity entity) {
	}

	public boolean isBlockableBy(Mob mob) {
		return true;
	}
	
	public void collide(GameObject obs) {
		//System.out.println("collision!" + animation.getAnimationDirection());
		switch(animation.getAnimationDirection()) {
		case UP:
		case DOWN:
			if(Math.abs(this.position.x-(obs.position.x+obs.position.width)) <= colisionlimit)
				this.position.x = (obs.position.x+obs.position.width) ;
			else if(Math.abs((this.position.x+this.position.width)-obs.position.x) <= colisionlimit)
				this.position.x = (obs.position.x-this.position.width) ;
			else if(Math.abs(this.position.y-(obs.position.y+obs.position.height)) <= colisionlimit)
				this.position.y = (obs.position.y+obs.position.height) ;
			else if(Math.abs((this.position.y+this.position.height)-obs.position.y) <= colisionlimit)
				this.position.y = (obs.position.y-this.position.height) ;
			break;
		case LEFT:
		case RIGHT:
			if(Math.abs(this.position.y-(obs.position.y+obs.position.height)) <= colisionlimit)
				this.position.y = (obs.position.y+obs.position.height) ;
			else if(Math.abs((this.position.y+this.position.height)-obs.position.y) <= colisionlimit)
				this.position.y = (obs.position.y-this.position.height) ;
			if(Math.abs(this.position.x-(obs.position.x+obs.position.width)) <= colisionlimit)
				this.position.x = (obs.position.x+obs.position.width) ;
			else if(Math.abs((this.position.x+this.position.width)-obs.position.x) <= colisionlimit)
				this.position.x = (obs.position.x-this.position.width) ;
			break;
			
		}
	}

	public int getType() {
		return 0;
	}

	public int getScore() {
		return 0;
	}
}