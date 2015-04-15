package videjouegos1415g5.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;

public class Colors {
		
	public static BufferedImage convertColor(BufferedImage bufferedImage, Color color) {
		
		if (color == null) {
			return bufferedImage;
		}
		
		short[] red = new short[256];
		short[] green = new short[256];
		short[] blue = new short[256];
		short[] alpha = new short[256];

		for (short i = 0; i < 256; i++) {
			red[i] = (short) color.getRed();
			green[i] = (short) color.getBlue();
			blue [i] = (short) color.getBlue();
			alpha[i] = i;
		}
	    
		short[][] data = new short[][] {
		    red, green, blue, alpha
		};

		LookupTable lookupTable = new ShortLookupTable(0, data);
		LookupOp op = new LookupOp(lookupTable, null);
		BufferedImage destinationImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return op.filter(bufferedImage, destinationImage);

	}
}
