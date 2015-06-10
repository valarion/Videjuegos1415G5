package videjouegos1415g5.entity;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.map.Map;


public class Mob extends Entity {
	public int health = 10;
	public int tickTime = 0;
	protected Animation up, down, left, right, death;

	public Mob() {
	}

	public void tick() {
		tickTime++;
		animation.tick();
	}
	
	public void die() {
		animation = death;
		animation.start();
		if (animation.finalFrame())
			remove();
	}

	public boolean blocks(Entity e) {
		return e.isBlockableBy(this);
	}

	public void hurt(Entity mob, int damage) {
		doHurt(damage);
	}

	protected void doHurt(int damage) {
		health -= damage;
	}

	public boolean findStartPos(Map map) {
		return false;
	}
}
