package videjouegos1415g5.gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Font {
	
	final private int TILE_SIZE = 8;
	final private String FONT_SPRITE = "/font.png";
	
	private Image[][] font;
	private SpriteSheet tileset;
	private int scale;
	private static String chars = "" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"!.<#/>0123456789 =------- ";
	
	public Font() {
		this.scale = 2;
		SpriteLoader loader = new SpriteLoader();
		BufferedImage spriteSheet = loader.cargarImagen(FONT_SPRITE);
		tileset = new SpriteSheet(spriteSheet);
		loadTiles();
	}
	
	private void loadTiles() {
		try {
			int numTilesAcross = (tileset.getWidth() + 1) / TILE_SIZE;
			font = new Image[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.obtenerSprite(col * TILE_SIZE, 0, TILE_SIZE,
						TILE_SIZE);
				font[0][col] = subimage.getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
				subimage = tileset.obtenerSprite(col * TILE_SIZE, TILE_SIZE,
						TILE_SIZE, TILE_SIZE);
				font[1][col] = subimage.getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g, String msg, int x, int y) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int ix = chars.indexOf(msg.charAt(i));
			if (ix >= 0) {
				int r = ix / font[0].length;
				int c = ix % font[0].length;
				g.drawImage(font[r][c], x + i * + TILE_SIZE * scale, y * scale, null);
			}
		}
	}
}