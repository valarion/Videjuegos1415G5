package videjouegos1415g5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;

import videjouegos1415g5.cutscenes.FinalScene;
import videjouegos1415g5.cutscenes.InitScene;
import videjouegos1415g5.entity.Bomb;
import videjouegos1415g5.entity.Bomberman;
import videjouegos1415g5.entity.Enemy;
import videjouegos1415g5.entity.Entity;
import videjouegos1415g5.entity.Exit;
import videjouegos1415g5.entity.Flare;
import videjouegos1415g5.entity.PowerUps;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.map.Obstacle;
import videjouegos1415g5.menu.GameOverMenu;
import videjouegos1415g5.menu.LevelMenu;
import videjouegos1415g5.menu.MapMenu;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.menu.TitleMenu;
import videjouegos1415g5.sound.MP3Player;
import videjouegos1415g5.sound.Sound;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private BufferedImage imagen;
	private int tickCount;
	private int offsetX = 0;
	private int offsetY = 0;
	private int time; // 4 minutos
	
	private int level = 1;
	private int levelmap = 1;
	private int continues = 2;

	private boolean running = true;
	private boolean playing = false;
	private boolean pause = false;
	// private KeyInput input = new KeyInput(this);
	private InputHandler input = new InputHandler(this);
	private Font font;
	private BufferedImage gui = null;

	private Map map;
	private GenerateObstacles obstacles;
	private Menu menu;
	private Bomberman player;
	private Entity exit;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Flare> flares = new ArrayList<Flare>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private ArrayList<PowerUps> powerups = new ArrayList<PowerUps>();
	private HashMap<String, MP3Player> music = new HashMap<String, MP3Player>();
	private String keymusic = "";


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

		new Thread(new Runnable() {
			public void run() {
				try {
					music.put("bgm_01", new MP3Player("/music/bgm_01.mp3"));
					music.put("bgm_02", new MP3Player("/music/bgm_02.mp3"));
					music.put("bgm_03", new MP3Player("/music/bgm_03.mp3"));
					music.put("bgm_boss", new MP3Player("/music/bgm_boss.mp3"));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();


		setMenu(new TitleMenu());
	}
	
	public void initLevel() {
		boolean dead = player.removed;
		int powerupssize = powerups.size();
		clear();
		
		Scanner in = new Scanner(getClass().getResourceAsStream("/maps/definitions/"+level+"/"+level+"_"+levelmap+".txt"));
		map = new Map(in.nextLine(), Map.TILESIZE);
		//obstacles = new GenerateObstacles(map, true);
		
		PowerUps powerup = null;
		if(levelmap != 8) {
			obstacles = new GenerateObstacles(map, true);
			powerup = new PowerUps(in.nextInt(), obstacles.getList());
		}
		else {
			obstacles = new GenerateObstacles(map, false);
		}
		
		if (levelmap != 8) { 
			if (level == 4 || level == 7) keymusic = "bgm_03";
			else if (level == 6 || level == 8) keymusic = "bgm_02";
			else keymusic = "bgm_01";	
		} else keymusic = "bgm_boss";
		
		while(in.hasNext()) {
			int type = in.nextInt();
			int count = in.nextInt();
			for(int i = 0; i< count; i++)
				enemies.add(Enemy.createEnemy(type, obstacles, map, player));
		}
		//in.close();
		
		if(powerup != null && levelmap != 8 && !dead){
			powerups.add(powerup);
		} 
		else if(powerup != null && levelmap != 8 && dead && powerupssize > 0) {
			powerups.add(powerup);
		}
		
		if (levelmap != 8) {
			do {
				exit = new Exit(obstacles.getList(), enemies);
			} while(exit.intersects(powerup));
		} else exit = new Exit(null, enemies);
		
		in.close();
	}
 	
	private void clear() {
		if (obstacles != null) obstacles.getList().clear();
		if (enemies != null) enemies.clear();
		if (powerups != null) powerups.clear();
		if (bombs != null) bombs.clear();
		if (flares != null) flares.clear();
		if (player != null) player.reset();
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
		//initLevel();

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
			setMenu(new GameOverMenu(player.getLives(), level, levelmap));
			time = 240;
		}

		if (menu != null) {
			menu.tick();
			playing = false;
		} else {
			if(((Exit) exit).isActive() && powerups.size() > 0) {
				for(Obstacle obs : obstacles.getList()) {
					if(obs.intersects(powerups.get(0))) {
						obs.blink();
						break;
					}
				}
			}
			if (input.exit.clicked) {
				music.get(keymusic).stop();
				if (player.isInvincible()) MP3Player.invincible.stop();
				playing = false;
				pause = false;
				initLevel();
				level = 1;
				levelmap = 1;
				setMenu(new TitleMenu());
			}
			if (input.pause.clicked) {
				//MP3Player.stage.pause();
				music.get(keymusic).pause();
				if (player.isInvincible()) MP3Player.invincible.pause();
				pause = !pause;
			}
			if (!pause) {
				if (!player.isInvincible()) {
					if (playing) music.get(keymusic).play();
					MP3Player.invincible.stop();
				}
				else  {
					MP3Player.invincible.play();
					music.get(keymusic).pause();
				}
				playing = true;
				player.tick();
				if (player.endLvl()) {
					levelmap++;
					if (levelmap > 8) {
						levelmap = 0;
						level++;
						if (level > 8) {
							setMenu(new FinalScene());
						}
						else
							setMenu(new LevelMenu(level));
					}
					else
						setMenu(new MapMenu(level,levelmap));
					initLevel();
					return;
				}
				if(player.hasRemoteDetonator() && input.remote.clicked && bombs.size() > 0) {
					bombs.get(0).removed = true;
					Sound.bomb.play();
				}
				if(input.fire.clicked && bombs.size() < player.getBombs()) {
					Bomb bomb = new Bomb(player);
					boolean found = false;
					for(Bomb b : bombs) {
						if(bomb.intersects(b)){
							found = true;
							break;
						}
					}
					if(!found)
						for (Obstacle obs : obstacles.getList()) {
							if (obs != null && obs.intersects(bomb)) {
								found = true;
								break;
							}
						}
					if(!found && !player.isTeleporting() && !player.isDying())
						bombs.add(bomb);
				}
				exit.tick();
				// Comprobar si el jugador ha muerto
				if (player.removed) {
					music.get(keymusic).stop();
					player.setLives(player.getLives() - 1);
					if (player.getLives() < 0) {
						initLevel();
						player.setLives(2);
						setMenu(new GameOverMenu(continues, level, levelmap));
						continues--;
					}
					else {
						initLevel();
						setMenu(new MapMenu(level, levelmap));
					}
				}
				// Comprobar si los enemigos han muerto
				for (Entity enemy : enemies) {
					if (enemy.removed) {
						player.setScore(player.getScore() + enemy.getScore());
						enemies.remove(enemy);
						break;
					}
					enemy.tick();
				}
				for (Entity e : powerups) {
					e.tick();
				}
				for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();) {
					Bomb bomb = it.next();
					bomb.tick();
					if(bomb.removed) {
						it.remove();
						addFlares(bomb);
					}
				}
				for(Iterator<Flare> it = flares.iterator(); it.hasNext();) {
					Flare flare = it.next();
					if(flare.removed) 
						it.remove();
					else
						flare.tick();
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
			
			// Pintar salida
			//if (levelmap != 8 || ((Exit) exit).isActive())
				exit.render(g);
			
			// Pintar bombas
			for (Entity e : bombs) {
				e.render(g);
			}
			
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

			// Pintar llamas finales
			for (Flare e : flares) {
				if(e.isFinal())
					e.render(g);
			}
			// Pintar llamas intermedias
			for (Flare e : flares) {
				if(!e.isFinal() && !e.isMid())
					e.render(g);
			}
			// Pintar llamas iniciales
			for (Flare e : flares) {
				if(e.isMid())
					e.render(g);
			}
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
		// el jugador con los obstaculos
		for (Obstacle obs : obstacles.getList()) {
			if (obs != null && obs.intersects(player)) {
				if(!(player.canPassWalls() && obs.isSolid()))
					player.collide(obs);
			}
		}
		// los enemigos con los obstaculos
		for (Enemy enemy : enemies) {
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(enemy)) {
					if(!(enemy.canPassWalls() && obs.isSolid()))
						enemy.collide(obs);
				}
			}
		}
		// los enemigos con las llamas
		for (Enemy enemy : enemies) {
			for (Entity flare : flares) {
				if (flare.intersects(enemy)) {
					enemy.hurt(flare, 10);
				}
			}
		}

		// el jugador con las llamas
		for (Entity flare : flares) {
			if (flare.intersects(player)) {
				if(!player.isInvincible())
					player.touchedBy(flare);
				//break;
			}
		}

		// las bombas con las llamas
		for (Entity bomb : bombs) {
			for (Entity flare : flares) {
				if (flare.intersects(bomb)) {
					bomb.touchedBy(flare);
					//break;
				}
			}
		}

		
		
		// los enemigos con las bombas
		for (Enemy enemy : enemies) {
			for (Bomb bomb : bombs) {
				if (bomb != null) {
					if (bomb.intersects(enemy)) {
						if (bomb.isOut(enemy) && !enemy.canPassBombs()) {
							enemy.collide(bomb);
						}
					}
					else {
						bomb.setOut(enemy);
					}
				}
			}
		}

		// el jugador con las bombas
		for (Bomb bomb : bombs) {
			if (bomb != null) {
				if (bomb.intersects(player)) {
					if (!bomb.isOut(player) && ! player.canPassBombs()) {
						player.collide(bomb);
					}
					else {
						bomb.setOut(player);
					}
				}
			}
		}

		// el jugador con los enemigos
		for (Enemy enemy : enemies) {
			if (enemy != null && enemy.intersects(player)) {
				enemy.touchedBy(player); // Normal
				//player.touchedBy(enemy); // Bomberman se carga a todos
				//break;
			}
		}

		// el jugador con los power ups
		for (Iterator<PowerUps> it = powerups.iterator(); it.hasNext();) {
			PowerUps powerup = it.next();
			if (powerup != null && powerup.intersects(player)) {
				player.addPowerUp(powerup);
				if (powerup.removed)
					it.remove();
			}
		}
		
		// el jugador con la salida
		if (player.intersects(exit) && ((Exit) exit).isActive() /*&& exit.getBounds().contains(player.getBounds())*/) {
			bombs.clear();
			player.touchedBy(exit);
			playing = false;
			music.get(keymusic).stop();
			//System.out.println("Level Complete");
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
	
	private void addFlares(Bomb bomb) {
		boolean obstaclefound;
		flares.add(new Flare(bomb,0,0));
		
		obstaclefound = false;
		flaresloop: for(int i=1; i <= bomb.getPotency(); i++) {
			Flare flare = new Flare(bomb,i,0);
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(flare)) {
					if (obs.isSolid()) {
						flare.setAsFinal();
						obs.die();
						obstaclefound = true;
						break;
					}
					else
						break flaresloop;
				}
			}
			
			if(!obstaclefound) {
				for (Iterator<PowerUps> it = powerups.iterator(); it.hasNext();) {
					PowerUps pu = it.next();
					if (pu != null && pu.intersects(flare)) {
						flare.setAsFinal();
						pu.remove();
						it.remove();
						break;
					}
				}
			}
			flares.add(flare);
			if(flare.isFinal())
				break;
		}
		
		obstaclefound = false;
		flaresloop: for(int i=-1; i >= -bomb.getPotency(); i--) {
			
			Flare flare = new Flare(bomb,i,0);
			
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(flare)) {
					if (obs.isSolid()) {
						flare.setAsFinal();
						obs.die();
						obstaclefound = true;
						break;
					}
					else
						break flaresloop;
				}
			}
			
			if(!obstaclefound) {
				for (Iterator<PowerUps> it = powerups.iterator(); it.hasNext();) {
					PowerUps pu = it.next();
					if (pu != null && pu.intersects(flare)) {
						flare.setAsFinal();
						pu.remove();
						it.remove();
						break;
					}
				}
			}
			flares.add(flare);
			if(flare.isFinal())
				break;
		}
		
		obstaclefound = false;
		flaresloop: for(int i=1; i <= bomb.getPotency(); i++) {
			Flare flare = new Flare(bomb,0,i);
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(flare)) {
					if (obs.isSolid()) {
						flare.setAsFinal();
						obs.die();
						obstaclefound = true;
						break;
					}
					else
						break flaresloop;
				}
			}
			
			if(!obstaclefound) {
				for (Iterator<PowerUps> it = powerups.iterator(); it.hasNext();) {
					PowerUps pu = it.next();
					if (pu != null && pu.intersects(flare)) {
						flare.setAsFinal();
						pu.remove();
						it.remove();
						break;
					}
				}
				
			}
			flares.add(flare);
			if(flare.isFinal())
				break;
		}
		
		obstaclefound = false;
		flaresloop: for(int i=-1; i >= -bomb.getPotency(); i--) {
			Flare flare = new Flare(bomb,0,i);
			for (Obstacle obs : obstacles.getList()) {
				if (obs != null && obs.intersects(flare)) {
					if (obs.isSolid()) {
						flare.setAsFinal();
						obs.die();
						obstaclefound = true;
						break;
					}
					else
						break flaresloop;
				}
			}
			
			if(!obstaclefound) {
				for (Iterator<PowerUps> it = powerups.iterator(); it.hasNext();) {
					PowerUps pu = it.next();
					if (pu != null && pu.intersects(flare)) {
						flare.setAsFinal();
						pu.remove();
						it.remove();
						break;
					}
				}
				
			}
			flares.add(flare);
			if(flare.isFinal())
				break;
		}
	}
	
	public void startLevel(int level, int map) {
		this.level = level;
		this.levelmap = map;
		player = new Bomberman(input);
		initLevel();
		font = new Font(Color.WHITE, false);
		try {
			gui = ScaleImg.scale(ImageIO.read(this.getClass().getResource("/hud.png")),Main.ESCALA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (level == 1 && map == 1) setMenu(new InitScene());
		else if (level > 1 && map == 1) setMenu(new LevelMenu(level));
		else setMenu(new MapMenu(level, map));
	}
}
