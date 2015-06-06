package videjouegos1415g5.entity;

import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;

public class Enemy extends Mob {

	private GenerateObstacles obs;
	protected int score;
	Bomberman player;

	public Enemy(GenerateObstacles obs, Map map, Bomberman player) {
		this.obs = obs;
		this.player = player;
		while (!located) findStartPos(map);
	}
	
	public void tick() {
		// IA muy rudimentaria
		if (player.position.x > this.position.x) {
			//animation.setAnimationDirection(Direction.RIGHT	);
			this.position.x++;
//			animation = right;
//			animation.start();
		} else {
			//animation.setAnimationDirection(Direction.LEFT);
			this.position.x--;
//			animation = left;
//			animation.start();
		}
		
		if (player.position.y > this.position.y) {
			//animation.setAnimationDirection(Direction.DOWN);
			this.position.y++;
//			animation = down;
//			animation.start();
		} else {
			//animation.setAnimationDirection(Direction.UP);
			this.position.y--;
//			animation = up;
//			animation.start();
		}
	}
	
	public void touchedBy(Entity entity) {
		if (entity instanceof Bomberman) {
			entity.hurt(this, 10); // BIEN
		}
	}

	public boolean findStartPos(Map map) {
		located: for (int y = 0; y < map.getmapHeight() - obs.getOffsetY()*2; y++) {
			for (int x = 0; x < map.getmapWidth() - obs.getOffsetX()*2; x++) {
				if (x != 0 && y != 0 || x != 1 && y != 0 || x != 0 && y != 1) {
					if (!obs.obstacleAt(x, y, scale) && random.nextDouble() >= 0.99) {
						this.position.x = x*obs.tileSize() + obs.getOffsetX()*obs.tileSize() - 8*scale;
						this.position.y = y*obs.tileSize() + obs.getOffsetY()*obs.tileSize() + 24*scale;
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

}
