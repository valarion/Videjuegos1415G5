package videjouegos1415g5.entity;

import java.awt.Rectangle;

import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.map.Obstacle;

public class Boss extends Enemy {

	public Boss(GenerateObstacles obs, Map map, Bomberman player) {
		super(obs, map, player);
		this.speed = 1*scale/2;
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
			
			int xsign = sign(player.position.x - position.x);
			int ysign = sign(player.position.y - position.y);

			if (isinsquare) {
				switch (r.nextInt(2)) {
				case 0:
					xdir = xsign*speed;
					ydir = 0;
					break;
				case 1:
					xdir = 0;
					ydir = ysign*speed;
					break;
				}
			}
		}
	}
	
	protected int sign(int in) {
		if(in >= 0)
			return 1;
		return -1;
	}
}
