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
		if (health <= 0) die();
	}

	protected void die() {
		remove();
	}

	public boolean blocks(Entity e) {
		return e.isBlockableBy(this);
	}

	public void hurt(Mob mob, int damage) {
		doHurt(damage);
	}

	protected void doHurt(int damage) {
		health -= damage;
	}

	public boolean findStartPos(Map map) {
		return false;
	}
}
