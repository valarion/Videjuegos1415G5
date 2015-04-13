package videjouegos1415g5;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Font {
	
	final private int TILE_SIZE = 8;
	final private String FONT_SPRITE = "/font1.png";
	
	private Tile[][] tiles;
	private SpriteSheet tileset;
	private static String chars = "" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"!.<#|/>0123456789 =-------";
	
	public Font() {
		SpriteLoader loader = new SpriteLoader();
		BufferedImage spriteSheet = loader.cargarImagen(FONT_SPRITE);
		tileset = new SpriteSheet(spriteSheet);
		loadTiles();
	}
	
	private void loadTiles() {
		try {
			int numTilesAcross = (tileset.getWidth() + 1) / TILE_SIZE;
			tiles = new Tile[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.obtenerSprite(col * TILE_SIZE, 0, TILE_SIZE,
						TILE_SIZE);
				tiles[0][col] = new Tile(subimage, false);
				subimage = tileset.obtenerSprite(col * TILE_SIZE, TILE_SIZE,
						TILE_SIZE, TILE_SIZE);
				tiles[1][col] = new Tile(subimage, true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g, String msg, int x, int y) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int ix = chars.indexOf(msg.charAt(i));
			if (ix >= 0) {
				int r = ix / tiles[0].length;
				int c = ix % tiles[0].length;
				g.drawImage(tiles[r][c].getImage(), x + i * 8, y, null);
			}
		}
	}
}