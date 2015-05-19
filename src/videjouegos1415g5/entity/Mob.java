package videjouegos1415g5.entity;

import videjouegos1415g5.Main;
import videjouegos1415g5.map.Map;


public class Mob extends Entity {
	protected int walkDist = 0;
	protected int xKnockback, yKnockback;
	public int maxHealth = 10;
	public int health = maxHealth;
	public int tickTime = 0;
	public int scale;

	public Mob() {
		position.x = position.y = 8;
		xr = 4;
		yr = 3;
		scale = Main.ESCALA;
	}

	public void tick() {
		tickTime++;

		if (health <= 0) {
			die();
		}
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
		int x = random.nextInt(map.getmapWidth());
		int y = random.nextInt(map.getmapHeight());
		int xx = x * 16 + 8;
		int yy = y * 16 + 8;

		return false;
	}
}
