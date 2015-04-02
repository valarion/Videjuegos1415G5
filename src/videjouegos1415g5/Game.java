package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imagen;
	private BufferedImage spriteSheet = null;
	private SpriteSheet ss;
	
	// test
	private BufferedImage bomberman;
	//private BufferedImage bomberman1;
	static int pixel = 21;
	static int x = 100;
	static int y = 100;
	
	private boolean running = true;
	private KeyInput input = new KeyInput(this);

	private Map map;
	private GenerateObstacles obstacles;

	private void init() {
		setFocusable(true);		
		
		imagen = new BufferedImage(Main.ANCHURA, Main.ALTURA, BufferedImage.TYPE_INT_RGB);
		
		SpriteLoader loader = new SpriteLoader();
		spriteSheet = loader.cargarImagen("/big_dyna.png");
		
		// mas test
		ss = new SpriteSheet(spriteSheet);
		bomberman = ss.obtenerSprite(1, 1, pixel, pixel);
		//bomberman1 = ss.obtenerSprite(2, 1, pixel, pixel);

		
		// Mapa de prueba
		map = new Map("res/maps/map1_2_square.txt", 16);
		spriteSheet = loader.cargarImagen("/maps/map1.png");
		ss = new SpriteSheet(spriteSheet);
		
		int scale = 2;
		map.loadTiles(ss);
		map.saveImagetoFile(false, scale);
		obstacles = new GenerateObstacles(ss, map.getmapWidth(), map.getmapHeight(), scale);
	}

	public void run() {

		init();
		
		long fps = 0;
		long fpscounter = 0;
		long lastLoopTime = System.currentTimeMillis();
		while (running) {
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			fpscounter += delta;
			fps++;	
			
			render(delta);
			
			if (fpscounter > 1000) {
				System.out.println("FPS: " + fps);
				fps = 0;
				fpscounter = 0;
			}

			try { 
				Thread.sleep(10); // Como a Mena le gusta
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void render(long delta) {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
		g.setColor(Color.BLACK);
		
		//map.render(g);
		//g.drawImage(final_map, 0, 0, this);
		map.renderMap(g);
		obstacles.draw(g);
		
		//int escalaAnchura = getWidth() / Main.ANCHURA;
		//int escalaAltura = getHeight() / Main.ALTURA;
		//g.drawImage(bomberman, x, y, Main.ANCHURA / escalaAnchura, Main.ALTURA / escalaAltura, this);
		g.drawImage(bomberman, x, y, this);

		g.dispose();
		bs.show();
	}
}
