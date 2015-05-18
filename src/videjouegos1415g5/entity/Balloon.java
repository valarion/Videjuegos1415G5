package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;



public class Balloon extends Mob {
	
	private final String ANIMATION = "/balloon.png"; 
	private final static int w = 16;
	private final static int h = 18;
	private static Animation animation;
	
	private int xa, ya;
	private int randomWalkTime = 0;
	private SpriteLoader sl;
	private SpriteSheet ss;
	private Animation move, death;

	public Balloon() {
		position.x = random.nextInt(64);
		position.y = random.nextInt(64);
		health = maxHealth = 10;
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		BufferedImage[] mov = {ss.obtenerSprite(7*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(8*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(9*w*scale, 0, w*scale, h*scale)};
		BufferedImage[] die = {ss.obtenerSprite(10*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(11*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(4*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(5*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(6*w*scale, 0, w*scale, h*scale)};
		
		this.move = new Animation(mov, 10, Direction.DOWN);
		this.death = new Animation(die, 10, Direction.DOWN);
		
		// Animacion inicial
		animation = move;
		animation.start();

	}

	public void tick() {
		super.tick();
		int speed = tickTime & 1;
		if (position.x < 0) position.x++;
		if (position.x > 200) position.x--;
		if (tickTime > 60*5) die();
		if (randomWalkTime > 0) randomWalkTime--;
		animation.tick();
	}

	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
	}

	protected void die() {
		super.die();
		animation = death;
		animation.start();	
	}
	
	public void touchedBy(Entity entity) {
		if (entity instanceof Bomberman) {
			entity.hurt(this, 10);
		}
	}
}