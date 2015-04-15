package videjouegos1415g5.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Font {
	
	final private int TILE_SIZE = 8;
	final private String FONT_SPRITE = "/font.png";
	
	private Image[][] font;
	private Image[][] shad;
	private SpriteSheet tileset;
	private int scale;
	private Color color;
	private boolean shadows;
	private static String chars = "" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"!.<#/>0123456789 =------- ";
	
	public Font(Color color, boolean shadows) {
		this.scale = 2;
		if (color != null)
			this.color = color;
		this.shadows = shadows;
		SpriteLoader loader = new SpriteLoader();
		BufferedImage spriteSheet = loader.cargarImagen(FONT_SPRITE);
		tileset = new SpriteSheet(spriteSheet);
		loadTiles();
	}
	
	private void loadTiles() {
		try {
			int numTilesAcross = (tileset.getWidth() + 1) / TILE_SIZE;
			font = new Image[2][numTilesAcross];
			shad = new Image[2][numTilesAcross];

			BufferedImage subimage, shadowImage;
			
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.obtenerSprite(col * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
				font[0][col] = Colors.convertColor(subimage, color).getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
				
				subimage = tileset.obtenerSprite(col * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
				font[1][col] = Colors.convertColor(subimage, color).getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
				
				if (shadows) {
					shadowImage = tileset.obtenerSprite(col * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
					shad[0][col] = Colors.convertColor(shadowImage, new Color(0,0,0)).getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
					
					shadowImage = tileset.obtenerSprite(col * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
					shad[1][col] = Colors.convertColor(shadowImage, new Color(0,0,0)).getScaledInstance(TILE_SIZE*scale, TILE_SIZE*scale, Image.SCALE_SMOOTH);
				}
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
				if (shadows) {
					g.drawImage(shad[r][c], x + i * + TILE_SIZE * scale + 2, y * scale + 2, null);
				}
				g.drawImage(font[r][c], x + i * + TILE_SIZE * scale, y * scale, null);
			}
		}
	}
}