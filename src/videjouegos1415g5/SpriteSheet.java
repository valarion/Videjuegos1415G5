package videjouegos1415g5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

public class SpriteSheet {

	private BufferedImage imagen;

	public SpriteSheet(BufferedImage bi) {
		this.imagen = bi;
	}

	public BufferedImage obtenerSprite(int col, int fil, int alto, int ancho) {
		BufferedImage aux = imagen.getSubimage(col, fil, alto, ancho);
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

//	private Image TransformColorToTransparency(BufferedImage image, Color c1) {
//		final int r1 = c1.getRed();
//		final int g1 = c1.getGreen();
//		final int b1 = c1.getBlue();
//		ImageFilter filter = new RGBImageFilter() {
//			public final int filterRGB(int x, int y, int rgb) {
//				int r = (rgb & 0xFF0000) >> 16;
//				int g = (rgb & 0xFF00) >> 8;
//				int b = rgb & 0xFF;
//				if (r >= r1 && g >= g1 && b >= b1) {
//					return rgb & 0xFFFFFF;
//				}
//				return rgb;
//			}
//		};
//
//		ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
//		return Toolkit.getDefaultToolkit().createImage(ip);
//	}
//
//	private BufferedImage ImageToBufferedImage(Image image, int width, int height) {
//		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2 = dest.createGraphics();
//		g2.drawImage(image, 0, 0, null);
//		g2.dispose();
//		return dest;
//	}
}
