package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.util.Random;

public class Entity {
	protected final Random random = new Random();
	public int x, y;
	public int xr = 6;
	public int yr = 6;
	public boolean removed;

	public void tick() {
	}
	
	public void render(Graphics2D g) {
	}

	public void remove() {
		removed = true;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(x + xr < x0 || y + yr < y0 || x - xr > x1 || y - yr > y1);
	}

	public boolean blocks(Entity e) {
		return false;
	}

	public void hurt(Mob mob, int dmg) {
	}

	protected void touchedBy(Entity entity) {
	}

	public boolean isBlockableBy(Mob mob) {
		return true;
	}
}