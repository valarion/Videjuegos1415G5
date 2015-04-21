package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class Bomberman {
	
	private final String ANIMATION = "/bomberman.png"; 
	private final int w = 24;
	private final int h = 22;
	
	private InputHandler input;
	private int x, y, scale;
	private Animation walkR, walkL, walkU, walkD;
	private Animation animation;
	private SpriteLoader sl;
	private SpriteSheet ss;

	
	public Bomberman(InputHandler input) {
		this.input = input;
		this.scale = Main.ESCALA;
		this.x = 100; 
		this.y = 100;
		
		this.sl = new SpriteLoader();
		BufferedImage img = sl.cargarImagen(ANIMATION);	    
	    
		// Escalamos la secuencia de sprites
	    BufferedImage resized = new BufferedImage(img.getWidth() * scale, img.getHeight() * scale, img.getType());
	    Graphics2D g = resized.createGraphics();
	    g.drawImage(img, 0, 0, resized.getWidth(), resized.getHeight(), null);
	    g.dispose();
		
		this.ss = new SpriteSheet(resized);
		
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

		this.walkL = new Animation(walkingLeft, 10);
		this.walkR = new Animation(walkingRight, 10);
		this.walkU = new Animation(walkingUp, 10);
		this.walkD = new Animation(walkingDown, 10);
		
		// Animacion inicial
		this.animation = walkD;
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
}
