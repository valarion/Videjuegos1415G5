package videjouegos1415g5;

import java.awt.Graphics2D;
import java.util.Random;

public class GenerateObstacles {
	
	private final int MAX_OBSTACLES = 300;
	private final double LIMIT = 0.6;
	private int TILESIZE = 16;
	
	private int num_obstacles;
	private Obstacle[] obstacles;
	private int currentX;
	private int currentY;
	private int offsetX = 2;
	private int offsetY = 1;
	private boolean canPlace = false;

	public GenerateObstacles(SpriteSheet ss, int finX, int finY, int scale) {
		this.num_obstacles = 0;
		this.TILESIZE *= scale;
		this.obstacles = new Obstacle[MAX_OBSTACLES];
		
		Random rn = new Random();
		for (int i = 0; i < obstacles.length; i++) {
			if (currentX >= (finX - offsetX*2)) {
				currentY++;
				currentX = 0;
			}
			if (currentY >= (finY - offsetY*2)) break;
			
			if (rn.nextDouble() >= LIMIT) {
				
				// Posiciones protegidas
				if (currentX == 0 && currentY == 0 ||
					currentX == 1 && currentY == 0 ||
					currentX == 0 && currentY == 1) continue;
				
				if (currentY%2 == 0) { // Si la fila es par puedo colocar
					canPlace = true;
				} 
				else {
					if (currentX%2 == 0) { // Si la columna es par puedo colocar
						canPlace = true;
					}
				}
				
				if (canPlace) {
					obstacles[num_obstacles] = new Obstacle(currentX*TILESIZE + offsetX*TILESIZE, 
							currentY*TILESIZE + offsetY*TILESIZE, 
							TILESIZE, 
							null, 
							ss, 
							TILESIZE,
							scale);
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
