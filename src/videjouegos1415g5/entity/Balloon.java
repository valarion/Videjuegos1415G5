package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;

public class Balloon extends Enemy {
	
	private final String ANIMATION = "/enemies/balloon.png"; 
	private final static int w = 16;
	private final static int h = 18;

	public Balloon(GenerateObstacles obs, Map map, Bomberman player) {
		super(obs, map, player);

		this.position.width = 12*scale;
		this.position.height = 14*scale;
		this.health = 10;
		this.score = 100;
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		BufferedImage[] mov = {ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale)};
		BufferedImage[] die = {ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(4*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(5*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(6*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(7*w*scale, 0, w*scale, h*scale),
				new Score(score, w, h).getImage()};
		
		this.down = new Animation(mov, 10, Direction.DOWN);
		this.death = new Animation(die, 14, Direction.DOWN);
		
		// Animacion inicial
		animation = down;
		animation.start();

	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x, position.y, w, h);
	}
}