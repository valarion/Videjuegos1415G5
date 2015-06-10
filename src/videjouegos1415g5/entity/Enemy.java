package videjouegos1415g5.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import videjouegos1415g5.Main;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.map.Obstacle;

public class Enemy extends Mob {

	int xdir, ydir, speed;

	protected GenerateObstacles obs;
	protected Bomberman player;
	protected int score;

	protected static Random r = new Random();

	protected Rectangle lastpos;

	public Enemy(GenerateObstacles obs, Map map, Bomberman player) {
		this.obs = obs;
		this.player = player;
		this.speed = 1*Main.ESCALA/2;
		while (!located)
			findStartPos(map);

		switch (r.nextInt(3)) {
		case 0:
			xdir = speed;
			ydir = 0;
			break;
		case 1:
			xdir = -speed;
			ydir = 0;
			break;
		case 2:
			xdir = 0;
			ydir = speed;
			break;
		case 3:
			xdir = 0;
			ydir = -speed;
			break;
		}
	}

	public void tick() {
		if (!removed)
			animation.tick();
		if (health <= 0)
			die();
		else {
			this.position.x += xdir;
			this.position.y += ydir;

			boolean isinsquare = position.equals(lastpos);
			lastpos = new Rectangle(position);
			if (!isinsquare)
				for (Obstacle rect : obs.getPath()) {
					if (rect.getBounds().contains(position)) {
						isinsquare = true;
						break;
					}
				}

			if (isinsquare) {
				switch (r.nextInt(30)) {
				case 0:
					xdir = speed;
					ydir = 0;
					break;
				case 1:
					xdir = -speed;
					ydir = 0;
					break;
				case 2:
					xdir = 0;
					ydir = speed;
					break;
				case 3:
					xdir = 0;
					ydir = -speed;
					break;
				}
			}
		}
		/*
		 * else { // IA muy rudimentaria if (player.position.x >
		 * this.position.x) { this.position.x++; } else { this.position.x--; }
		 * 
		 * if (player.position.y > this.position.y) { this.position.y++; } else
		 * { this.position.y--; } }
		 */
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillRect(position.x, position.y, 12*scale, 14*scale);
		BufferedImage f = animation.getSprite();
		g.drawImage(animation.getSprite(), 
				position.x+position.width/2 - (f.getWidth())/2, 
				position.y+position.height/2 - (f.getHeight()+3*scale)/2, null);
	}

	public void touchedBy(Entity entity) {
		if (health > 0 && entity instanceof Bomberman) {
			if(!entity.isInvincible())
				entity.hurt(this, 10); // BIEN
		}
	}

	public boolean findStartPos(Map map) {
		located: for (int y = 0; y < map.getmapHeight() - obs.getOffsetY() * 2; y++) {
			for (int x = 0; x < map.getmapWidth() - obs.getOffsetX() * 2; x++) {
				if (x == 0 && y == 0 || x == 1 && y == 0 || x == 0 && y == 1) {
					break;
				}
				else {
					if (!obs.obstacleAt(x, y, scale)
							&& random.nextDouble() >= 0.99) {
						this.position.x = x * obs.tileSize() + obs.getOffsetX()
								* obs.tileSize() - 8 * scale;
						this.position.y = y * obs.tileSize() + obs.getOffsetY()
								* obs.tileSize() + 24 * scale;
						located = true;
						break located;
					}
				}
			}
		}
		return true;
	}

	public int getScore() {
		return score;
	}

	public static Enemy createEnemy(int type, GenerateObstacles obs, Map map,
			Bomberman player) {
		switch (type) {
		case 0:
			return new Balloon(obs, map, player);
		case 1:
			return new BalloonPurple(obs, map, player);
		case 2:
			return new BalloonBlue(obs, map, player);
		case 3:
			return new BalloonRed(obs, map, player);
		case 4:
			return new DragonPurple(obs, map, player);
		case 5:
			return new GhostYellow(obs, map, player);
		case 6:
			return new SnakeHead(obs, map, player);
		case 7:
			return new CrocodileGreen(obs, map, player);
		case 8:
			return new GarlicBlue(obs, map, player);
		case 9:
			return new FishGreen(obs, map, player);
		case 10:
			return new GhostBlue(obs, map, player);
		case 11:
			return new CoinYellow(obs, map, player);
		case 12:
			return new GhostPink(obs, map, player);
		}
		return null;
	}
}
