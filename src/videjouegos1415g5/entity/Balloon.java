package videjouegos1415g5.entity;

import java.awt.Color;
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
	
	private final String ANIMATION = "/balloon.png"; 
	private final static int w = 16;
	private final static int h = 18;
	private static Animation animation;
	
	private int xa, ya;
	private int randomWalkTime = 0;
	private SpriteLoader sl;
	private SpriteSheet ss;
	private Animation move, death;
	private int score = 100;


	public Balloon(GenerateObstacles obs, Map map) {
		super(obs);
		this.position.x = random.nextInt(64);
		this.position.y = random.nextInt(64);
		this.position.width = 12*scale;
		this.position.height = 14*scale;
		health = maxHealth = 10;
		
		findStartPos(map);
		
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
		
		this.move = new Animation(mov, 10, Direction.DOWN);
		this.death = new Animation(die, 14, Direction.DOWN);
		
		// Animacion inicial
		animation = move;
		animation.start();

	}

	public void tick() {
		super.tick();
		int speed = tickTime & 1;
//		if (position.x < 0) position.x++;
//		if (position.x > 200) position.x--;
		if (health <= 0) die(); 
		//if (tickTime > 60*5) die();
		if (randomWalkTime > 0) randomWalkTime--;
		animation.tick();
	}

	public void render(Graphics2D g) {
		g.setColor(Color.CYAN);
		//g.drawImage(animation.getSprite(), position.x, position.y, null);
		BufferedImage f = animation.getSprite();
		g.drawImage(animation.getSprite(), 
				position.x+position.width/2 - (f.getWidth()-2*scale)/2, 
				position.y+position.height/2 - (f.getHeight()-2*scale)/2, null);
		//g.fillRect(position.x, position.y, 12*scale, 14*scale);


	}

	protected void die() {
		animation = death;
		animation.start();
		if (animation.finalFrame())
			super.die();
	}
	
	public void touchedBy(Entity entity) {
		if (entity instanceof Bomberman) {
			entity.hurt(this, 10); // BIEN
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x, position.y, w, h);
	}
}