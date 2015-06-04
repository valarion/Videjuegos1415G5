package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import videjouegos1415g5.GameObject;


public class GenerateObstacles {

	//private final int MAX_OBSTACLES = 300;
	private final double LIMIT = 0.8;

	private ArrayList<Obstacle> obstacles;
	private int num_obstacles;
	private int currentX;
	private int currentY;
	private int offsetX = 2;
	private int offsetY = 1;
	private int tileSize = 16;
	private boolean canPlace = false;

	public GenerateObstacles(Map map) {
		int finX = map.getmapWidth();
		int finY = map.getmapHeight();
		this.num_obstacles = 0;
		this.tileSize *= map.getScale();
		
		if (finY > finX) finY--;
		
		obstacles = new ArrayList<Obstacle>();

		Random rn = new Random();
		while(true) {
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
					obstacles.add(new Obstacle(
							currentX*tileSize + offsetX*tileSize, 
							currentY*tileSize + offsetY*tileSize, 
							tileSize, null, map.getSpriteSheet(), map.getScale(), true));

					canPlace = false;
					num_obstacles++;
				}
			}
			// Obstaculos fijos
			if (currentY%2 != 0 && currentX%2 != 0)
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false));
			
			// Paredes laterales
			// Pared izquierda
			if (currentX == 1)
				obstacles.add(new Obstacle(
						currentX*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false));
			// Pared Derecha
			if (currentX == ((finX - offsetX*2) - 1))
				obstacles.add(new Obstacle(
						currentX*tileSize + (offsetX+1)*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false));
			// Pared superior
			if (currentY == 0)
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false));
			// Pared inferior
			if (currentY == (finY - offsetY*2) - 1)
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize + (offsetY+1)*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false));
			
			currentX++;
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).draw(g);
		}
	}
	
	public ArrayList<Obstacle> getList() {
		return obstacles;
	}
	
	public boolean obstacleAt(GameObject go, int x, int y) {
		x = x*tileSize + offsetX*tileSize;
		y = y*tileSize + offsetX*tileSize;
		for(Obstacle obs: obstacles) {
			if (new Rectangle(x, y, go.position.width, go.position.height).intersects(obs.position))
				return true;
		}
		return false;
	}
	
	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}
	
	public int tileSize() {
		return tileSize;
	}
}
