package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import videjouegos1415g5.entity.Balloon;
import videjouegos1415g5.entity.Bomberman;
import videjouegos1415g5.entity.Enemy;
import videjouegos1415g5.entity.Entity;
import videjouegos1415g5.entity.Exit;
import videjouegos1415g5.entity.PowerUps;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.map.Obstacle;
import videjouegos1415g5.menu.GameOverMenu;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.menu.TitleMenu;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private BufferedImage imagen;
	private int tickCount;
	int offsetX = 0;
	int offsetY = 0;
	int time; // 4 minutos
	
	private int level = 1;

	private boolean running = true;
	private boolean playing = false;
	private boolean pause = false;
	// private KeyInput input = new KeyInput(this);
	private InputHandler input = new InputHandler(this);
	private Font font;
	BufferedImage gui = null;

	private Map map;
	private GenerateObstacles obstacles;
	private Menu menu;
	private Bomberman player;
	private Entity exit;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Entity> flares = new ArrayList<Entity>();
	private ArrayList<Entity> bombs = new ArrayList<Entity>();
	private ArrayList<Entity> powerups = new ArrayList<Entity>();


	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null)
			menu.init(this, input);
	}

	private void init() {
		setFocusable(true);

		imagen = new BufferedImage(Main.ANCHURA, Main.ALTURA,BufferedImage.TYPE_INT_RGB);
		font = new Font(Color.WHITE, false);
		try {
			gui = ScaleImg.scale(ImageIO.read(this.getClass().getResource("/hud.png")),Main.ESCALA);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setMenu(new TitleMenu());
	}
	
	private void initLevel() {
		clear();
		player = new Bomberman(input);
		
		int enemiesCount = 5;
		int powerUpCount = 0;
		switch (level) {
		case 1:
			map = Map.map8_5;
			level = 2;
			break;
		case 2:
			map = Map.map1_5;
			level = 1;
			break;
		}
		obstacles = new GenerateObstacles(map);
		for (int i = 0; i < enemiesCount; i++) {
			enemies.add(new Balloon(obstacles, map, player));
		}
		powerups.add(new PowerUps(1, obstacles.getList()));
		exit = new Exit(obstacles.getList(), enemies);
	}
 	
	private void clear() {
		if (obstacles != null) obstacles.getList().clear();
		if (enemies != null) enemies.clear();
		if (powerups != null) powerups.clear();
		player = null;
		time = 240;
		offsetX = 0;
		offsetY = 0;
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
		initLevel();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastLoopTime) / nsPerTick;
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
				if (playing && !pause)
					time -= 1;
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

		if (time <= 0 && playing) {
			// Tiempo acabado, Game Over
			player.setLives(player.getLives() - 1);
			setMenu(new GameOverMenu(player.getLives()));
			time = 240;
		}

		if (menu != null) {
			menu.tick();
			playing = false;
		} else {
			if (input.pause.clicked) {
				pause = !pause;
			}
			if (!pause) {
				playing = true;
				player.tick();
				exit.tick();
				// Comprobar si el jugador ha muerto
				if (player.removed) {
					this.setMenu(new GameOverMenu(player.getLives()));
					initLevel();
				}
				for (Entity enemy : enemies) {
					if (enemy.removed) {
						enemies.remove(enemy);
						break;
					}
					enemy.tick();
				}
				for (Entity e : powerups) {
					e.tick();
				}
				for (int i = 0; i < obstacles.getList().size(); i++) {
					obstacles.getList().get(i).tick();
					if (obstacles.getList().get(i).removed)
						obstacles.getList().remove(obstacles.getList().get(i));
				}
				checkCollisions();
			}
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
		} else {
			scroll();
			g.translate(offsetX, offsetY);

			// Pintar mapa
			map.renderMap(g);
			
			// Pintar PowerUps
			for (Entity e : powerups) {
				e.render(g);
			}
			
			// Pintar obstaculos
			obstacles.draw(g);
			
			// Pintar enemigos
			for (Entity e : enemies) {
				e.render(g);
			}
			
			// Pintar llamas
			for (Entity e : flares) {
				e.render(g);
			}
			
			// Pintar bombas
			for (Entity e : bombs) {
				e.render(g);
			}

			// Pintar salida
			exit.render(g);
			
			// Pintar bomberman
			player.render(g);
			
			// Pintar gui
			renderGui(g);
		}

		if (menu == null) {
			// checkCollisions();
		}

		// g.drawImage(bomberman, x, y, this);

		g.dispose();
		bs.show();
	}

	public void checkCollisions() {
		// los enemigos con las llamas
		for (Entity enemy : enemies) {
			for (Entity flare : flares) {
				if (flare.intersects(enemy)) {
					enemy.touchedBy(flare);
					break;
				}
			}
		}

		// el jugador con las llamas
		for (Entity flare : flares) {
			if (flare.intersects(player)) {
				player.touchedBy(flare);
				break;
			}
		}

		// las bombas con las llamas
		for (Entity bomb : bombs) {
			for (Entity flare : flares) {
				if (flare.intersects(bomb)) {
					bomb.touchedBy(flare);
					break;
				}
			}
		}

		// los enemigos con los obstaculos
		for (Enemy enemy : enemies) {
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
				if (obs.isSolid()) {
					obs.die();
				}
				player.collide(obs);

				break;
			}
		}

		// el jugador con los enemigos
		for (Enemy enemy : enemies) {
			if (enemy != null && enemy.intersects(player)) {
				//enemy.touchedBy(player); // Normal
				player.touchedBy(enemy); // Bomberman se carga a todos
				player.setScore(player.getScore() + enemy.getScore());
				break;
			}
		}

		// el jugador con los power ups
		for (Entity powerup : powerups) {
			if (powerup != null && powerup.intersects(player)) {
				player.addPowerUp(powerup);
				if (powerup.removed)
					powerups.remove(powerup);
				break;
			}
		}
	}

	private void scroll() {
		if (player.position.x > getWidth() / 2
				&& player.position.x < map.getmapWidth() * map.getTileSize()
						* Main.ESCALA
						- (getWidth() / 2 + map.getTileSize() * Main.ESCALA)) {
			offsetX = -player.position.x + getWidth() / 2;
		}
		if (player.position.y > getHeight() / 2
				&& player.position.y < map.getmapHeight() * map.getTileSize()
						* Main.ESCALA - getHeight() / 2) {
			offsetY = -player.position.y + getHeight() / 2;
		}
	}

	private void renderGui(Graphics2D g) {

		if (pause) {
			String p = "Pause";
			g.fillRect(-offsetX, -offsetY, getWidth(), gui.getHeight());
			font.render(g, "Pause", -offsetX+ getWidth()/2 
					- (p.length()*font.getTilesize()*Main.ESCALA)/2, 
					-offsetY + gui.getHeight()/2 - (font.getTilesize()*Main.ESCALA)/2);
		} else {
			
			int x = 0;
			int y = 0;
			
			g.drawImage(gui, -offsetX, -offsetY, null);

			// Score
			x = 80 - 8 * (new Integer(player.getScore()).toString().length() - 1);
			y = 8;
			font.render(g, String.valueOf(player.getScore()), -offsetX + x
					* Main.ESCALA, -offsetY + y * Main.ESCALA);
			x = 241 - 8 * (new Integer(player.getScore()).toString().length() - 1);
			font.render(g, String.valueOf(player.getScore()), -offsetX + x
					* Main.ESCALA, -offsetY + y * Main.ESCALA);

			// Lives
			x = 153;
			font.render(g, String.valueOf(player.getLives()), -offsetX + x
					* Main.ESCALA, -offsetY + y * Main.ESCALA);

			// Time
			int minutes = time / 60;
			int seconds = time - minutes * 60;
			String seg = String.valueOf(seconds);
			if (seconds < 10)
				seg = "0" + seconds;

			x = 106;
			font.render(g, String.valueOf(minutes), -offsetX + x * Main.ESCALA,
					-offsetY + y * Main.ESCALA);
			x = 120;
			font.render(g, seg, -offsetX + x * Main.ESCALA, -offsetY + y
					* Main.ESCALA);
		}
	}
}
