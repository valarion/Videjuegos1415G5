package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class Bomberman extends Mob {
	
	private final String ANIMATION = "/bomberman.png"; 
	private static Animation animation;
	
	private InputHandler input;
	private int scale;
	private final static int w = 24;
	private final static int h = 22;
	public static int x;
	public static int y;
	
	private Animation walkR, walkL, walkU, walkD, death;
	private SpriteLoader sl;
	private SpriteSheet ss;

	
	public Bomberman(InputHandler input) {
		//super(x, y, w, h);
		this.input = input;
		this.scale = Main.ESCALA;
		this.x = 100; 
		this.y = 100;
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		BufferedImage[] walkingLeft = {ss.obtenerSprite(6*w* scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(7*w* scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(8*w* scale, 0, w*scale, h*scale)};
		BufferedImage[] walkingRight = {ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(4*w* scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(5*w* scale, 0, w*scale, h*scale)};
		BufferedImage[] walkingUp = {ss.obtenerSprite(9*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(10*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(11*w*scale, 0, w*scale, h*scale)};
		BufferedImage[] walkingDown = {ss.obtenerSprite(0, 0, w * scale, h * scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale)};
		BufferedImage[] die = {ss.obtenerSprite(0, h*scale, w * scale, h * scale),
				ss.obtenerSprite(1*w*scale, h*scale, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, h*scale, w*scale, h*scale),
				ss.obtenerSprite(3*w*scale, h*scale, w*scale, h*scale), 
				ss.obtenerSprite(4*w*scale, h*scale, w*scale, h*scale),
				ss.obtenerSprite(5*w*scale, h*scale, w*scale, h*scale), 
				ss.obtenerSprite(6*w*scale, h*scale, w*scale, h*scale),
				ss.obtenerSprite(7*w*scale, h*scale, w*scale, h*scale), 
				ss.obtenerSprite(8*w*scale, h*scale, w*scale, h*scale),
				ss.obtenerSprite(9*w*scale, h*scale, w*scale, h*scale)};

		this.walkL = new Animation(walkingLeft, 10);
		this.walkR = new Animation(walkingRight, 10);
		this.walkU = new Animation(walkingUp, 10);
		this.walkD = new Animation(walkingDown, 10);
		this.death = new Animation(die, 10);
		
		// Animacion inicial
		Bomberman.animation = walkD;
	}
	
	public void tick() {

		if (input.left.down) {
			x--; 
			animation = walkL; 
			animation.start();
		}
		
		else if (input.right.down) {
			x++;  
			animation = walkR; 
			animation.start();
		}

		else if (input.up.down) {
			y--; 
			animation = walkU; 
			animation.start();
		}

		else if (input.down.down) {
			y++; 
			animation = walkD; 
			animation.start();
		}
		
		else {
			animation.stop();
			animation.reset();
		}
		
		animation.tick();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), x, y, null);
	}
	
	protected void die() {
		super.die();
		animation = death;
		animation.start();
		//Sound.playerDeath.play();
	}
	
	public void touchedBy(GameObject go) {
//		if (!(go instanceof Bomberman)) {
//			go.touchedBy(this);
//		}
		if (go instanceof Balloon) {
			go.hurt(this, 10); // PARA PROBAR
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
}
