package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.Obstacle;

public class PowerUps extends Entity {
	
	private final String POWERUPS = "/powerups.png"; 
	private final static int w = 16;
	private final static int h = 16;
		
	private int type;
	
	public PowerUps(int type, ArrayList<Obstacle> obs) {
		
		this.type = type;
		this.position.width = w;
		this.position.height = h;
		
		this.sl = new SpriteLoader();	    
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(POWERUPS), scale));
		
		BufferedImage[] powerup = new BufferedImage[]{ss.obtenerSprite(type*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(type*w*scale, h*scale, w*scale, h*scale)};
		
		while (!located) findStartPos(obs);
		this.animation = new Animation(powerup, 10, Direction.DOWN);
		this.animation.start();
		
	}
	
	public void tick() {
		this.animation.tick();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
//		g.setColor(Color.ORANGE);
//		g.fillRect(position.x, position.y, w*scale, h*scale);

	}
	
	public int getType() {
		return type;
	}
	
	private boolean findStartPos(ArrayList<Obstacle> obs) {
		for (int i = 0; i < obs.size(); i++) {
			if (obs.get(i).isSolid() && random.nextDouble() >= 0.95) {
				this.position.x = obs.get(i).position.x;
				this.position.y = obs.get(i).position.y;
				located = true;
				break;
			}
		}
		return true;
		
	}

}
