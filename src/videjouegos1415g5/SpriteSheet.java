package videjouegos1415g5;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private final int PIXEL = 21;
	private BufferedImage imagen;
	
	public SpriteSheet(BufferedImage bi) {
		this.imagen = bi;
	}
	
	public BufferedImage obtenerSprite(int col, int fil, int alto, int ancho) {
		return imagen.getSubimage(col*PIXEL - PIXEL, fil*PIXEL - PIXEL, alto, ancho);
	}
}
