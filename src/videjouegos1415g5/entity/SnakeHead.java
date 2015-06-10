package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.sound.Sound;

public class SnakeHead extends Boss {
	
	private final String ANIMATION = "/enemies/blue_snake.png"; 
	private final static int w = 16;
	private final static int h = 18;
	
	protected int state = 0;
	
	protected Animation[] up, down, left, right;
	
	protected LinkedList<PosDir> positions = new LinkedList<PosDir>();
	protected SnakeBody child;
	
	protected class PosDir {
		public PosDir(Rectangle position, int dirx, int diry) {
			this.position = new Rectangle(position);
			this.dirx = dirx;
			this.diry = diry;
		}
		public Rectangle position;
		public int dirx;
		public int diry;
	}

	public SnakeHead(GenerateObstacles obs, Map map, Bomberman player) {
		super(obs, map, player);

		this.position.width = 12*scale;
		this.position.height = 14*scale;
		this.health = 10;
		this.score = 100;
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		up = new Animation[4];
		down = new Animation[4];
		left = new Animation[4];
		right = new Animation[4];
		for(int i=0; i < 4; i++) {
			BufferedImage[] u = {ss.obtenerSprite(0*w*scale, (i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(1*w*scale, (i+1)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] d = {ss.obtenerSprite(6*w*scale, (i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(7*w*scale, (i+1)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] l = {ss.obtenerSprite(8*w*scale, (i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(9*w*scale, (i+1)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] r = {ss.obtenerSprite(2*w*scale, (i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(3*w*scale, (i+1)*h*scale, w*scale, h*scale)};
			
			up[i] = new Animation(u, 20, Direction.UP);
			down[i] = new Animation(d, 20, Direction.DOWN);
			left[i] = new Animation(l, 20, Direction.LEFT);
			right[i] = new Animation(r, 20, Direction.RIGHT);
		}
		
		BufferedImage[] die = {ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(4*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(5*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(6*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(7*w*scale, 0, w*scale, h*scale),
				new Score(score, w, h).getImage()};
		
		this.death = new Animation(die, 14, Direction.DOWN);
		animation = down[0];
		animation.start();

	}

	public void tick() {
		super.tick();
		if(xdir < 0)
			animation = left[state];
		else if(xdir > 0)
			animation = right[state];
		else if (ydir < 0)
			animation = up[state];
		else
			animation = down[state];
		
		positions.addLast(new PosDir(position,xdir,ydir));
		
		animation.start();
	}

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x, position.y, w, h);
	}
	
	public boolean canPassWalls() {
		return true;
	}
	
	public boolean canPassBombs() {
		return true;
	}

	public void setChild(SnakeBody child) {
		this.child = child;
	}
}
