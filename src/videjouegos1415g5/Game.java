package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imagen;
	private BufferedImage spriteSheet = null;
	private SpriteSheet ss;
	
	// test
	private BufferedImage bomberman;
	private BufferedImage bomberman1;
	private Animation test;
	static int pixel = 21;
	static int x = 100;
	static int y = 100;
	
	private boolean running = true;
	private KeyInput input = new KeyInput(this);

	private void init() {
		setFocusable(true);		
		
		imagen = new BufferedImage(Main.ANCHURA, Main.ALTURA, BufferedImage.TYPE_INT_RGB);
		
		SpriteLoader loader = new SpriteLoader();
		spriteSheet = loader.cargarImagen("/big_dyna.png");
		
		// mas test
		ss = new SpriteSheet(spriteSheet);
		bomberman = ss.obtenerSprite(1, 1, pixel, pixel);
		bomberman1 = ss.obtenerSprite(2, 1, pixel, pixel);
		//test = new Animation();
		//test.addFrame(bomberman, 50);
		//test.addFrame(bomberman1, 50);
	}

	public void run() {

		init();

		while (running) {
			render();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
		
		for (int i = 1; i < Main.ALTURA/pixel; i++) {
			for (int z = 1; z < Main.ANCHURA/pixel; z++) {
				BufferedImage tile = ss.obtenerSprite(z, i, pixel, pixel);
				g.drawImage(tile, z*pixel - pixel, i*pixel - pixel, this);
			}
		}
		
		int escalaAnchura = getWidth() / Main.ANCHURA;
		int escalaAltura = getHeight() / Main.ALTURA;
		g.drawImage(bomberman, x, y, Main.ANCHURA / escalaAnchura, Main.ALTURA / escalaAltura, this);
		//g.drawImage(bomberman, x, y, this);
		g.drawImage(bomberman1, 100, 100, Main.ANCHURA / escalaAnchura, Main.ALTURA / escalaAltura, this);

		//test.draw(g, new Rectangle(pixel, pixel), 1);
		
		g.dispose();
		bs.show();
	}
}
