package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.tile.Tile;

public class Map {
	public final static int TILESIZE = 16;
	private final static String path1 = "/maps/";
	private final static String path2 = ".txt";

	// Level 1
	public static final Map map1_1 = new Map("map1_2_square", TILESIZE);
	public static final Map map1_2 = new Map("map1_2_square", TILESIZE);
	public static final Map map1_3 = new Map("map1_2_square", TILESIZE);
	public static final Map map1_4 = new Map("map1_2_square", TILESIZE);
	public static final Map map1_5 = new Map("map1_2_height", TILESIZE);
	public static final Map map1_6 = new Map("map1_2_height", TILESIZE);
	public static final Map map1_7 = new Map("map1_2_height", TILESIZE);
	public static final Map map1_8 = new Map("map1_2_square", TILESIZE);
	
	// Level 2
	public static final Map map2_1 = new Map("map2_square", TILESIZE);
	public static final Map map2_2 = new Map("map2_square", TILESIZE);
	public static final Map map2_3 = new Map("map2_height", TILESIZE);
	public static final Map map2_4 = new Map("map2_height", TILESIZE);
	public static final Map map2_5 = new Map("map2_square", TILESIZE);
	public static final Map map2_6 = new Map("map2_square", TILESIZE);
	public static final Map map2_7 = new Map("map2_height", TILESIZE);
	public static final Map map2_8 = new Map("map2_square", TILESIZE);
	
	// Level 3
	public static final Map map3_1 = new Map("map3_height", TILESIZE);
	public static final Map map3_2 = new Map("map3_width", TILESIZE);
	public static final Map map3_3 = new Map("map3_width", TILESIZE);
	public static final Map map3_4 = new Map("map3_height", TILESIZE);
	public static final Map map3_5 = new Map("map3_width", TILESIZE);
	public static final Map map3_6 = new Map("map3_width", TILESIZE);
	public static final Map map3_7 = new Map("map3_height", TILESIZE);
	public static final Map map3_8 = new Map("map3_width", TILESIZE);
	
	// Level 4
	public static final Map map4_1 = new Map("map4_height", TILESIZE);
	public static final Map map4_2 = new Map("map4_width", TILESIZE);
	public static final Map map4_3 = new Map("map4_width", TILESIZE);
	public static final Map map4_4 = new Map("map4_height", TILESIZE);
	public static final Map map4_5 = new Map("map4_height", TILESIZE);
	public static final Map map4_6 = new Map("map4_width", TILESIZE);
	public static final Map map4_7 = new Map("map4_width", TILESIZE);
	public static final Map map4_8 = new Map("map4_width", TILESIZE);
	
	// Level 5
	public static final Map map5_1 = new Map("map5_width", TILESIZE);
	public static final Map map5_2 = new Map("map5_width", TILESIZE);
	public static final Map map5_3 = new Map("map5_height", TILESIZE);
	public static final Map map5_4 = new Map("map5_width", TILESIZE);
	public static final Map map5_5 = new Map("map5_width", TILESIZE);
	public static final Map map5_6 = new Map("map5_height", TILESIZE);
	public static final Map map5_7 = new Map("map5_width", TILESIZE);
	public static final Map map5_8 = new Map("map5_width", TILESIZE);

	// Level 6
	public static final Map map6_1 = new Map("map6_width", TILESIZE);
	public static final Map map6_2 = new Map("map6_height", TILESIZE);
	public static final Map map6_3 = new Map("map6_width", TILESIZE);
	public static final Map map6_4 = new Map("map6_height", TILESIZE);
	public static final Map map6_5 = new Map("map6_height", TILESIZE);
	public static final Map map6_6 = new Map("map6_width", TILESIZE);
	public static final Map map6_7 = new Map("map6_height", TILESIZE);
	public static final Map map6_8 = new Map("map6_width", TILESIZE);
	
	// Level 7
	public static final Map map7_1 = new Map("map7_width", TILESIZE);
	public static final Map map7_2 = new Map("map7_height", TILESIZE);
	public static final Map map7_3 = new Map("map7_height", TILESIZE);
	public static final Map map7_4 = new Map("map7_width", TILESIZE);
	public static final Map map7_5 = new Map("map7_width", TILESIZE);
	public static final Map map7_6 = new Map("map7_width", TILESIZE);
	public static final Map map7_7 = new Map("map7_height", TILESIZE);
	public static final Map map7_8 = new Map("map7_width", TILESIZE);
	
	// Level 8
	public static final Map map8_1 = new Map("map8_width", TILESIZE);
	public static final Map map8_2 = new Map("map8_width", TILESIZE);
	public static final Map map8_3 = new Map("map8_height", TILESIZE);
	public static final Map map8_4 = new Map("map8_width", TILESIZE);
	public static final Map map8_5 = new Map("map8_width", TILESIZE);
	public static final Map map8_6 = new Map("map8_height", TILESIZE);
	public static final Map map8_7 = new Map("map8_width", TILESIZE);
	public static final Map map8_8 = new Map("map8_width", TILESIZE);

	private String mapPath;

	private int x;
	private int y;

	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private Image finalMap;

	private SpriteSheet tileset;
	private Tile[][] tiles;

	private int minx;
	private int miny;
	private int maxx = 0;
	private int maxy = 0;

	public Map(String s, int tileSize) {
		if (s != null) {
			this.mapPath = path1 + s + path2;
			this.tileSize = tileSize;

			try {

				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.getClass().getResourceAsStream(mapPath)));

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
		
		
		String map = null;
		switch (s) {
			case "map1_2_square": case "map1_2_height":
				map = "/maps/map1.png";
				break;
			case "map2_width": case "map2_square": case "map2_height":
				map = "/maps/map2.png";
				break;
			case "map3_width": case "map3_height":
				map = "/maps/map3.png";
				break;
			case "map4_width": case "map4_height":
				map = "/maps/map4.png";
				break;
			case "map5_width": case "map5_height":
				map = "/maps/map5.png";
				break;
			case "map6_width": case "map6_height":
				map = "/maps/map6.png";
				break;
			case "map7_width": case "map7_height":
				map = "/maps/map7.png";
				break;
			case "map8_width": case "map8_height":
				map = "/maps/map8.png";
				break;
			default:
				map = "/maps/map1.png";
				
		}
		
		SpriteLoader loader = new SpriteLoader();
		BufferedImage spriteSheet = loader.cargarImagen(map);
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		loadTiles(ss);
		saveImagetoFile(false, Main.ESCALA);
	}

	public void loadTiles(SpriteSheet s) {
		try {
			tileset = s;

			int numTilesAcross = (tileset.getWidth() + 1) / tileSize;
			tiles = new Tile[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
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

	public void saveImagetoFile(boolean save, int scale) {
		
        int width = mapWidth*tileSize;
        int height = mapHeight*tileSize;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
		
		int error = 0;
		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {

				int rc = map[row][col];

				int r = rc / tiles[0].length;
				int c = rc % tiles[0].length;

				// Apaño provisional, el techo izquierdo no esta rotado en los
				// sprites
				if (rc >= 25 && rc <= 27)
					error = 8;
				else
					error = 0;
				g.drawImage(tiles[r][c].getImage(), error + x + col * tileSize,
						y + row * tileSize, null);

			}
		}
		g.dispose();
		if (save) {
			String file = "res/maps/final_map" + this.mapPath.replaceAll("\\D+","") + ".png";
			try {
			    File outputfile = new File(file);
			    ImageIO.write(newImage, "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			this.finalMap = newImage.getScaledInstance(width*scale, height*scale, Image.SCALE_SMOOTH);
		}
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}
	
	public int getmapWidth() {
		return mapWidth;
	}
	
	public int getmapHeight() {
		return mapHeight;
	}
	

	public int getTile(int row, int col) {
		return map[row][col];
	}

	public int getTileSize() {
		return tileSize;
	}
	
	public SpriteSheet getSpriteSheet() {
		return tileset;
	}
	
	public int getScale() {
		return Main.ESCALA;
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

		int error = 0;
		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {

				int rc = map[row][col];

				int r = rc / tiles[0].length;
				int c = rc % tiles[0].length;

				//System.out.println(r + " " + c);

				// Apaño provisional, el techo izquierdo no esta rotado en los
				// sprites
				if (rc >= 25 && rc <= 27)
					error = 8;
				else
					error = 0;
				g.drawImage(tiles[r][c].getImage(), error + x + col * tileSize,
						y + row * tileSize, null);

			}
		}

	}
	
	public void renderMap(Graphics2D g) {
		g.drawImage(finalMap, -8*Main.ESCALA, 24*Main.ESCALA, null);
	}
}
