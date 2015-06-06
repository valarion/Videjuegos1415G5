package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.Obstacle;

public class Exit extends Entity {
	
	private final String EXIT = "/exit.png"; 
	private final static int w = 16;
	private final static int h = 16;
	
	private boolean activated;
	private int scale;	
	private SpriteLoader sl;
	private SpriteSheet ss;
	
	private ArrayList<Enemy> enemies;
	
	public Exit(ArrayList<Obstacle> obs, ArrayList<Enemy> enemies) {
		this.scale = Main.ESCALA;
		this.enemies = enemies;
		this.activated = false;
		
		this.position.width = w;
		this.position.height = h;
		
		this.sl = new SpriteLoader();	    
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(EXIT), scale));
		
		BufferedImage[] exit = new BufferedImage[]{ss.obtenerSprite(0, 0, w*scale, h*scale),
					ss.obtenerSprite(w*scale, 0, w*scale, h*scale)};
		
		while (!located) findStartPos(obs);
		this.animation = new Animation(exit, 10, Direction.DOWN);
		
	}
	
	public void tick() {
		this.animation.tick();
		if (enemies.size() <= 0) {
			activated = true;
			if (!animation.finalFrame()) animation.start();
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
	}
	
	private boolean findStartPos(ArrayList<Obstacle> obs) {
		for (int i = 0; i < obs.size(); i++) {
			if (obs.get(i).isSolid() && random.nextDouble() >= 0.9) {
				this.position.x = obs.get(i).position.x;
				this.position.y = obs.get(i).position.y;
				located = true;
				break;
			}
		}
		return true;
		
	}
	
	public boolean isActive() {
		return activated;
	}
}
