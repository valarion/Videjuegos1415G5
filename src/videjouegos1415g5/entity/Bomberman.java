package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class Bomberman extends Mob {
	
	private final String ANIMATION = "/bomberman.png"; 
	private final static int w = 24;
	private final static int h = 22;
	//private static Animation animation;
	
	private InputHandler input;
	private int scale;	
	private Animation walkR, walkL, walkU, walkD, death;
	private SpriteLoader sl;
	private SpriteSheet ss;

	
	public Bomberman(InputHandler input) {
		super();
		this.input = input;
		this.scale = Main.ESCALA;
		this.position.x = 30; 
		this.position.y = 40;
		this.position.width = 18*scale;
		this.position.height = h*scale;
		
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

		this.walkL = new Animation(walkingLeft, 10, Direction.LEFT);
		this.walkR = new Animation(walkingRight, 10, Direction.RIGHT);
		this.walkU = new Animation(walkingUp, 10, Direction.UP);
		this.walkD = new Animation(walkingDown, 10, Direction.DOWN);
		this.death = new Animation(die, 10, Direction.DOWN);
		
		// Animacion inicial
		this.animation = walkD;
	}
	
	public void tick() {

		if (input.left.down) {
			position.x--; 
			animation = walkL; 
			animation.start();
		}
		
		else if (input.right.down) {
			position.x++;  
			animation = walkR; 
			animation.start();
		}

		else if (input.up.down) {
			position.y--; 
			animation = walkU; 
			animation.start();
		}

		else if (input.down.down) {
			position.y++; 
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
		BufferedImage f = animation.getSprite();
		//g.fillRect(position.x+position.width/2-f.getWidth()/2, position.y+position.height/2-f.getHeight()/2, w*scale, h*scale);
		g.drawImage(animation.getSprite(), position.x+position.width/2-f.getWidth()/2, position.y+position.height/2-f.getHeight()/2, null);

	}
	
	protected void die() {
		super.die();
		animation = death;
		animation.start();
		//Sound.playerDeath.play();
	}
	
	public void touchedBy(Entity go) {
//		if (!(go instanceof Bomberman)) {
//			go.touchedBy(this);
//		}
		if (go instanceof Balloon) {
			go.hurt(this, 10); // PARA PROBAR
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x+5*scale, position.y+10*scale, 14*scale, 11*scale);
	}
}
