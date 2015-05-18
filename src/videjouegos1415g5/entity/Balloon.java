package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;



public class Balloon extends Mob {
	
	private final String ANIMATION = "/balloon.png"; 
	private static Animation animation;
	
	private int xa, ya;
	private int randomWalkTime = 0;
	private int score = 100;
	private final static int w = 16;
	private final static int h = 18;
	public static int x;
	public static int y;
	private SpriteLoader sl;
	private SpriteSheet ss;
	private Animation move, death;

	public Balloon() {
		x = random.nextInt(64);
		y = random.nextInt(64);
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
				ss.obtenerSprite(6*w*scale, 0, w*scale, h*scale),
				new Score(score, w, h).getImage()};
		
		this.move = new Animation(mov, 10);
		this.death = new Animation(die, 14);
		
		// Animacion inicial
		animation = move;
		animation.start();

	}

	public void tick() {
		super.tick();
		int speed = tickTime & 1;
		if (x < 0) x++;
		if (x > 200) x--;
		if (health <= 0) die(); 
		//if (tickTime > 60*5) die();
		if (randomWalkTime > 0) randomWalkTime--;
		System.out.println(health);
		animation.tick();
	}

	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), x, y, null);
	}

	protected void die() {
		animation = death;
		animation.start();
		if (animation.finalFrame())
			super.die();
	}
	
	protected void touchedBy(GameObject go) {
		if (go instanceof Bomberman) {
			go.hurt(this, 10); // BIEN
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
}