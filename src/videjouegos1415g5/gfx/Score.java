package videjouegos1415g5.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.Main;

public class Score {
		
	private final String SCORE = "/score.png"; 
	private final static int W = 15;
	private final static int H = 5;
	
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage scoreImg;
	private BufferedImage scoreFinal;
	
	private int scale;
	
	public Score(int score, int w, int h) {
		scale = Main.ESCALA;
		int pos = 0;
		if (score%1000 == 0) {
			pos = (int) (Math.log(score/1000)/Math.log(2)) + 7;
		}
		else {
			pos = (int) (Math.log(score/100)/Math.log(2));
		}
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(SCORE), scale));
		
		scoreImg = ss.obtenerSprite(pos*W*scale, 0, W*scale, H*scale);
		
		scoreFinal = new BufferedImage(w*scale, h*scale, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = scoreFinal.createGraphics();
	    g2d.drawImage(scoreImg, w*scale/2 - W*scale/2, h*scale/2 - H*scale/2, null);
	    g2d.dispose();
	}
	
	public void render(Graphics2D g) {

		//g.drawImage(scoreImg, 0, 0, null);
	}
	
	public BufferedImage getImage() {
		return scoreFinal;
	}

}
