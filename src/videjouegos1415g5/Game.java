package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import videjouegos1415g5.entity.Balloon;
import videjouegos1415g5.entity.Bomberman;
import videjouegos1415g5.entity.Boss;
import videjouegos1415g5.entity.Entity;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.map.Obstacle;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.menu.TitleMenu;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imagen;
	private int tickCount;
	int offsetX = 0;
	int offsetY = 0;
	
	private boolean running = true;
	//private KeyInput input = new KeyInput(this);
	private InputHandler input = new InputHandler(this);
	
	private Map map;
	private GenerateObstacles obstacles;
	private Menu menu;
	private Bomberman player;
	private ArrayList<Entity> enemies = new ArrayList<Entity>();
	private ArrayList<Entity> flares = new ArrayList<Entity>();
	private ArrayList<Entity> bombs = new ArrayList<Entity>();
	
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
		
		// Mapa de prueba
		map = Map.map8_5;
		obstacles = new GenerateObstacles(map);
		player = new Bomberman(input);
		enemies.add(new Balloon());
		enemies.add(new Boss(input));
		
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
		} else {
			player.tick();
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).tick();
			}
			for (int i = 0; i < obstacles.getList().size(); i++) {
				obstacles.getList().get(i).tick();
				if (obstacles.getList().get(i).removed) 
					obstacles.getList().remove(obstacles.getList().get(i));
			}
			checkCollisions();
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
			scroll();
			g.translate(offsetX, offsetY);

			
			map.renderMap(g);
			obstacles.draw(g);
			player.render(g);
			for (Entity e : enemies) {
				e.render(g);
			}
			for (Entity e : flares) {
				e.render(g);
			}
			for (Entity e : bombs) {
				e.render(g);
			}	
		}
		
		if (menu == null) {
			//checkCollisions();
		}

		//g.drawImage(bomberman, x, y, this);

		g.dispose();
		bs.show();
	}
	
		public void checkCollisions() {
		// los enemigos con las llamas
		for(Entity enemy : enemies) {
			for(Entity flare : flares) {
				if(flare.intersects(enemy)) {
					enemy.touchedBy(flare);
					break;
				}
			}
		}
		
		// el jugador con las llamas
		for(Entity flare : flares) {
			if(flare.intersects(player)) {
				player.touchedBy(flare);
				break;
			}
		}
		
		// las bombas con las llamas
				for(Entity bomb : bombs) {
					for(Entity flare : flares) {
						if(flare.intersects(bomb)) {
							bomb.touchedBy(flare);
							break;
						}
					}
				}
		
		// los enemigos con los obstaculos
		for (Entity enemy : enemies) {
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(enemy)) {
					enemy.collide(obs);
					break;
				}
			}
		}
		
		// el jugador con los obstaculos
		for (Obstacle obs : obstacles.getList()) {
			if (obs != null && obs.intersects(player)) {
				if (obs.isSolid()) obs.die();
				player.collide(obs);
				break;
			}
		}
	}
		
		private void scroll() {
			if (player.position.x > getWidth()/2 && 
					player.position.x < map.getmapWidth()*map.getTileSize()*Main.ESCALA 
					- (getWidth()/2 + map.getTileSize()*Main.ESCALA)) {
				offsetX = -player.position.x + getWidth()/2;
			}
			if (player.position.y > getHeight()/2 && 
					player.position.y < map.getmapHeight()*map.getTileSize()*Main.ESCALA
					- getHeight()/2) {
				offsetY = -player.position.y + getHeight()/2;
			}
		}
}
