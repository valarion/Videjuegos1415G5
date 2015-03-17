package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class Map {

	private String mapPath;

	private int x;
	private int y;

	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;

	private SpriteSheet tileset;
	private Tile[][] tiles;

	private int minx;
	private int miny;
	private int maxx = 0;
	private int maxy = 0;

	public Map(String s, int tileSize) {
		if (s != null) {
			this.mapPath = s;
			this.tileSize = tileSize;

			try {

				BufferedReader br = new BufferedReader(new FileReader(mapPath));

				mapWidth = Integer.parseInt(br.readLine());
				mapHeight = Integer.parseInt(br.readLine());
				map = new int[mapHeight][mapWidth];

				// minx = WIDTH - mapWidth * tileSize;
				// miny = HEIGHT - mapHeight * tileSize;

				String delimiters = "\\s+";
				for (int row = 0; row < mapHeight; row++) {
					String line = br.readLine();
					String[] tokens = line.split(delimiters);
					for (int col = 0; col < mapWidth; col++) {
						map[row][col] = Integer.parseInt(tokens[col]);
					}
				}
				br.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void loadTiles(SpriteSheet s) {
		try {
			tileset = s;

			int numTilesAcross = 0;
			int h = (tileset.getHeight() + 1) / tileSize;
			for (int i = 0; i < h; i++) {
				numTilesAcross += (tileset.getWidth() + 1) / tileSize;
			}
			tiles = new Tile[2][numTilesAcross / h];

			BufferedImage subimage;
			for (int col = 0; col < (numTilesAcross / h) - 1; col++) {
				subimage = tileset.obtenerSprite(col * tileSize, 0, tileSize,
						tileSize);
				tiles[0][col] = new Tile(subimage, false);
				subimage = tileset.obtenerSprite(col * tileSize, tileSize,
						tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public int getTile(int row, int col) {
		return map[row][col];
	}

	public int getTileSize() {
		return tileSize;
	}

	public boolean isBlocked(int row, int col) {
		int rc = map[row][col];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		return tiles[r][c].isBlocked();
	}

	public void setx(int i) {
		x = i;
		if (x < minx) {
			x = minx;
		}
		if (x > maxx) {
			x = maxx;
		}
	}

	public void sety(int i) {
		y = i;
		if (y < miny) {
			y = miny;
		}
		if (y > maxy) {
			y = maxy;
		}
	}

	public void render(Graphics2D g) {

		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {

				int rc = map[row][col];

				int r = rc / tiles[0].length;
				int c = rc % tiles[0].length;

				g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row
						* tileSize, null);

			}
		}

	}
}