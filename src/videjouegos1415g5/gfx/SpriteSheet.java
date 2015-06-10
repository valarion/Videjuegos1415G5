package videjouegos1415g5.gfx;

import java.awt.image.BufferedImage;


public class SpriteSheet {

	private BufferedImage imagen;

	public SpriteSheet(BufferedImage bi) {
		this.imagen = bi;
	}

	public BufferedImage obtenerSprite(int x, int y, int width, int height) {
		BufferedImage aux = imagen.getSubimage(x, y, width, height);
		//Image im = TransformColorToTransparency(aux, new Color(128, 128, 255));
		//return ImageToBufferedImage(im, alto, ancho);
		return aux;
	}
	
	public int getWidth() {
		return imagen.getWidth();
	}
	
	public int getHeight() {
		return imagen.getHeight();
	}
	
	
	public BufferedImage getImage() {
		return this.imagen;
	}
	
	public void setImage(BufferedImage img) {
		this.imagen = img;
	}
}
