package videjouegos1415g5.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class GenerateObstacles {

	//private final int MAX_OBSTACLES = 300;
	private final double LIMIT = 0.8;

	private ArrayList<Obstacle> obstacles;
	private ArrayList<Obstacle> path;

	private int currentX;
	private int currentY;
	private int offsetX = 2;
	private int offsetY = 1;
	private int tileSize = 16;
	private boolean canPlace = false;

	public GenerateObstacles(Map map, boolean solidObstacles) {
		int finX = map.getmapWidth();
		int finY = map.getmapHeight();
		this.tileSize *= map.getScale();
		
		if (finY > finX) finY--;
		
		obstacles = new ArrayList<Obstacle>();
		path = new ArrayList<Obstacle>();


		Random rn = new Random();
		while(true) {
			if (currentX >= (finX - offsetX * 2)) {
				currentY++;
				currentX = 0;
			}
			if (currentY >= (finY - offsetY * 2))
				break;

			if (solidObstacles && rn.nextDouble() >= LIMIT) {

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
							tileSize, null, map.getSpriteSheet(), map.getScale(), true,1));

					canPlace = false;
				}
			}
			// Camino libre con obstaculos
			if (currentY % 2 == 0 || currentX % 2 == 0) {
				path.add(new Obstacle(
							currentX*tileSize + offsetX*tileSize, 
							currentY*tileSize + offsetY*tileSize, 
							tileSize, null, map.getSpriteSheet(), map.getScale(), true,4));
			}
			// Obstaculos fijos
			if (currentY%2 != 0 && currentX%2 != 0)
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false,2));
			
			// Paredes laterales
			// Pared izquierda
			if (currentX == 1) 
				obstacles.add(new Obstacle(
						currentX*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false,3));
			
			// Pared Derecha
			if (currentX == ((finX - offsetX*2) - 1)) 
				obstacles.add(new Obstacle(
						currentX*tileSize + (offsetX+1)*tileSize, 
						currentY*tileSize + offsetY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false,3));
			
			// Pared superior
			if (currentY == 0) 
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false,3));
			
			// Pared inferior
			if (currentY == (finY - offsetY*2) - 1) 
				obstacles.add(new Obstacle(
						currentX*tileSize + offsetX*tileSize, 
						currentY*tileSize + (offsetY+1)*tileSize, 
						tileSize, null, map.getSpriteSheet(), map.getScale(), false,3));
			

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
	
	public ArrayList<Obstacle> getPath() {
		return path;
	}
	
	public boolean obstacleAt(int x, int y, int scale) {
		x = x*tileSize + offsetX*tileSize - 8*scale;
		y = y*tileSize + offsetY*tileSize + 24*scale;
		for (Obstacle obs: obstacles) {
			if (new Rectangle(x, y, tileSize, tileSize).intersects(obs.position))
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
	public void draw3d(GL2 gl, GLU glu) {
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).draw3d(gl,glu);
		}
	}
}
