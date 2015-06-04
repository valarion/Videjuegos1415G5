package videjouegos1415g5.entity;

import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;

public class Enemy extends Mob {

	private GenerateObstacles obs;

	public Enemy(GenerateObstacles obs) {
		this.obs = obs;

	}

	public boolean findStartPos(Map map) {
		int currentX = 0;
		int currentY = 0;
		for (int x = 0; x < map.getmapHeight() - obs.getOffsetY() * 2; x++) {
			for (int y = 0; y < map.getmapWidth() - obs.getOffsetX() * 2; y++) {
				if (currentX != 0 && currentY != 0 || currentX != 1
						&& currentY != 0 || currentX != 0 && currentY != 1) {
					if (obs.obstacleAt(this, currentX, currentY) && random.nextDouble() >= 0.8) {
						this.position.x = currentX*obs.tileSize() + obs.getOffsetX()*obs.tileSize() - obs.tileSize()/scale;
						this.position.y = currentY*obs.tileSize() + obs.getOffsetY()*obs.tileSize() - obs.tileSize()/scale;
						break;
					}
				}
				currentX++;
			}
			currentY++;
			currentX = 0;
		}
		return true;
	}

}
