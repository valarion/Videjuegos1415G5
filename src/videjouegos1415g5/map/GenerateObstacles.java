package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.util.Random;

import videjouegos1415g5.gfx.SpriteSheet;

public class GenerateObstacles {

	//private final int MAX_OBSTACLES = 300;
	private final double LIMIT = 0.8;

	private Obstacle[] obstacles;
	private int num_obstacles;
	private int currentX;
	private int currentY;
	private int offsetX = 2;
	private int offsetY = 1;
	private int tileSize = 16;
	private boolean canPlace = false;

	public GenerateObstacles(SpriteSheet ss, int finX, int finY, int scale) {
		this.num_obstacles = 0;
		this.tileSize *= scale;
		this.obstacles = new Obstacle[finX*finY];

		Random rn = new Random();
		for (int i = 0; i < obstacles.length; i++) {
			if (currentX >= (finX - offsetX * 2)) {
				currentY++;
				currentX = 0;
			}
			if (currentY >= (finY - offsetY * 2))
				break;

			if (rn.nextDouble() >= LIMIT) {

				// Posiciones protegidas
				if (currentX == 0 && currentY == 0 || 
					currentX == 1 && currentY == 0 || 
					currentX == 0 && currentY == 1) continue;

				if (currentY % 2 == 0) { // Si la fila es par puedo colocar
					canPlace = true;
				} else {
					if (currentX % 2 == 0) { // Si la columna es par puedo colocar
						canPlace = true;
					}
				}

				if (canPlace) {
					obstacles[num_obstacles] = new Obstacle(
							currentX * tileSize + offsetX * tileSize, 
							currentY * tileSize + offsetY * tileSize, 
							tileSize, null, ss, scale);

					canPlace = false;
					num_obstacles++;
				}
			}
			currentX++;
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < obstacles.length; i++) {
			if (obstacles[i] != null) {
				obstacles[i].draw(g);
			}
		}
	}
}
