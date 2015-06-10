package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.sound.Sound;

public class SnakeBody extends SnakeHead {
	
	private final String ANIMATION = "/enemies/blue_snake.png"; 
	private final static int w = 16;
	private final static int h = 18;

	protected SnakeHead parent;

	public SnakeBody(GenerateObstacles obs, Map map, Bomberman player, SnakeHead parent) {
		super(obs, map, player);
		this.parent = parent;
		this.move = false;
		this.health = 80;
		this.score = 100;
		
		this.position = new Rectangle(parent.position);
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		up = new Animation[4];
		down = new Animation[4];
		left = new Animation[4];
		right = new Animation[4];
		for(int i=0; i < 4; i++) {
			BufferedImage[] u = {ss.obtenerSprite(12*w*scale, (2*i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(13*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(14*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(13*w*scale, (2*i+1)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] d = {ss.obtenerSprite(0*w*scale, (2*i+2)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(19*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(18*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(19*w*scale, (2*i+1)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] l = {ss.obtenerSprite(3*w*scale, (2*i+2)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(2*w*scale, (2*i+2)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(1*w*scale, (2*i+2)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(2*w*scale, (2*i+2)*h*scale, w*scale, h*scale)};
			
			BufferedImage[] r = {ss.obtenerSprite(15*w*scale, (2*i+1)*h*scale, w*scale, h*scale), 
					ss.obtenerSprite(16*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(17*w*scale, (2*i+1)*h*scale, w*scale, h*scale),
					ss.obtenerSprite(16*w*scale, (2*i+1)*h*scale, w*scale, h*scale)};
			
			up[i] = new Animation(u, 20, Direction.UP);
			down[i] = new Animation(d, 20, Direction.DOWN);
			left[i] = new Animation(l, 20, Direction.LEFT);
			right[i] = new Animation(r, 20, Direction.RIGHT);
		}
		
		BufferedImage[] die = {ss.obtenerSprite(8*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(9*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(10*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(11*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(12*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(13*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(14*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(15*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(16*w*scale, 0, w*scale, h*scale)};
		
		this.death = new Animation(die, 14, Direction.DOWN);
		animation = down[0];
		animation.start();

	}

	public void tick() {
		if(child == null || child.removed)
			animation.start();
		else
			animation.stop();
		if(animation == death && animation.finalFrame())
			remove();
		if (!removed)
			animation.tick();
		if (health <= 0)
			die();
		else {
			if (invincible > 0)
				invincible--;
			if (health <= 20)
				state = 3;
			else if (health <= 40)
				state = 2;
			else if (health <= 60)
				state = 1;
			else
				state = 0;
			if (animation != death) {
				if (parent.positions.size() > Map.TILESIZE * Main.ESCALA) {
					PosDir pd = parent.positions.getFirst();
					parent.positions.removeFirst();
					position = pd.position;
					xdir = pd.dirx;
					ydir = pd.diry;
					positions.addLast(pd);
					if (positions.size() > Map.TILESIZE * Main.ESCALA * 2) {
						positions.clear();
					}
				}
			}
			// super.tick();
			animation.tick();
			if (animation != death) {
				if (xdir < 0)
					animation = left[state];
				else if (xdir > 0)
					animation = right[state];
				else if (ydir < 0)
					animation = up[state];
				else
					animation = down[state];
			}

			animation.start();
		}
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
	
	public void die() {
		if(animation != death) {
			animation = death;
			parent.notifyChildDeath();
			if(child != null)
				child.notifyParentDeath();
		}
		if (animation.finalFrame())
			remove();
	}
	
	public void notifyChildDeath() {
		die();
		parent.notifyChildDeath();
	}
	
	public void notifyParentDeath() {
		die();
		if(child != null)
			child.notifyParentDeath();
	}
}
