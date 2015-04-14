package videjouegos1415g5.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {
	
	private BufferedImage imagen;
	
	public BufferedImage cargarImagen(String ruta) {
		try {
			imagen = ImageIO.read(getClass().getResource(ruta));
		} catch (IOException e) {
			System.out.println(getClass().getResource(ruta));
			e.printStackTrace();
		}
		return imagen;
	}
}
