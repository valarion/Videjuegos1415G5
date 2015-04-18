package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.menu.TitleMenu;

public class Game extends Canvas implements Runnable {
	
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
	private int tickCount;
	
	private boolean running = true;
	//private KeyInput input = new KeyInput(this);
	private InputHandler input = new InputHandler(this);
	
	private Map map;
	private GenerateObstacles obstacles;
	private Menu menu;
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) menu.init(this, input);
	}

	private void init() {
		setFocusable(true);		
		
		imagen = new BufferedImage(Main.ANCHURA, Main.ALTURA, BufferedImage.TYPE_INT_RGB);
		
		//SpriteLoader loader = new SpriteLoader();
		//spriteSheet = loader.cargarImagen("/big_dyna.png");
		
		// mas test
		//ss = new SpriteSheet(spriteSheet);
		//bomberman = ss.obtenerSprite(1, 1, pixel, pixel);
		//bomberman1 = ss.obtenerSprite(2, 1, pixel, pixel);

		
		// Mapa de prueba
		map = Map.map1_1;
		obstacles = new GenerateObstacles(map);
		
		setMenu(new TitleMenu());

	}

	public void run() {
		
		int frames = 0;
		int ticks = 0;
		double nsPerTick = 1000000000.0 / 60;
		double delta = 0;
		long lastLoopTime = System.nanoTime();
		long lastLoopTime1 = System.currentTimeMillis();
		long now = 0;
		boolean shouldRender;
		
		init();
		
		while (running) {
			now = System.nanoTime();
			delta += (now - lastLoopTime) / nsPerTick ;
			lastLoopTime = now;
			
			shouldRender = true;
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			if (shouldRender) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - lastLoopTime1 > 1000) {
				lastLoopTime1 += 1000;
				System.out.println("FPS: " + frames + " - Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
			
			try { 
				Thread.sleep(2); // Como a Mena le gusta
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		tickCount++;
		input.tick();
		if (menu != null) {
			menu.tick();
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
		g.setColor(Color.BLACK);
		
		if (menu != null) {
			menu.render(g);
		}
		else {
			map.renderMap(g);
			obstacles.draw(g);
		}

		//g.drawImage(bomberman, x, y, this);

		g.dispose();
		bs.show();
	}
}
