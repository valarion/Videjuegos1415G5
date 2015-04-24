package videjouegos1415g5.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ScaleImg {
	
	public static BufferedImage scale(BufferedImage img, int scale) {
	    BufferedImage resized = new BufferedImage(img.getWidth() * scale, img.getHeight() * scale, img.getType());
	    Graphics2D g = resized.createGraphics();
	    g.drawImage(img, 0, 0, resized.getWidth(), resized.getHeight(), null);
	    g.dispose();
	    return resized;
	}

}
