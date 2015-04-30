package videjouegos1415g5.cutscenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.menu.LevelMenu;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.sound.MP3Player;

public class InitScene extends Menu {
	
	// Imagenes
	private static final String background = "/scenes/bg_scene1.png";
	private static final String glass = "/scenes/glass.png";
	private static final String broken_glass = "/scenes/broken_glass.png";
	private static final String left_wall = "/scenes/left_wall.png";
	private static final String right_wall = "/scenes/right_wall.png";
	private static final String left_g1 = "/scenes/l1.png";
	private static final String left_right_g2 = "/scenes/lr2.png";
	private static final String left_g3 = "/scenes/l3.png";
	private static final String left_g4 = "/scenes/l4.png";
	private static final String right_g1 = "/scenes/r1.png";
	private static final String right_g3 = "/scenes/r3.png";
	private static final String right_g4 = "/scenes/r4.png";

	// Imagenes con animaciones
	private static final String enemy = "/scenes/enemy.png";
	private static final String bomberman = "/scenes/bomberman.png";
	private static final String viejo = "/scenes/viejo.png";


	private String[] images = {background, glass, broken_glass, left_wall, right_wall,
			left_g1, right_g1, left_right_g2, left_g3, right_g3, left_g4, right_g4};
	private BufferedImage[] bi;
	private Image[] img;
	private Animation a1, a2, a3, a4;
	private int state = 0;
	private int count;
	private SpriteLoader sl;
	private SpriteSheet ss;
	
	// Posiciones de los actores
	private int xenemy, yenemy;
	private int xbomb, ybomb;
	private int xold, yold;
	
	
	public InitScene() {
		bgColor = Color.BLACK;
		scale = Main.ESCALA;
		
		bi = new BufferedImage[images.length];
		img = new Image[images.length];
		for (int i = 0; i < images.length; i++) {
			
			try {
				bi[i] = ImageIO.read(getClass().getResource(images[i]));
			} catch (IOException e) {
				e.printStackTrace();
			}
			img[i] = bi[i].getScaledInstance(bi[i].getWidth() * scale, 
					bi[i].getHeight() * scale, Image.SCALE_SMOOTH);
		}
		
		this.sl = new SpriteLoader();
		// Enemigo
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(enemy), scale));
		BufferedImage[] bi1 = {ss.obtenerSprite(0, 0, 39*scale, 49*scale), 
				ss.obtenerSprite(40*scale, 0, 39*scale, 49*scale), 
				ss.obtenerSprite(81*scale, 0, 36*scale, 49*scale)};
		a1 = new Animation(bi1, 10);
		
		// Bomberman
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(bomberman), scale));
		BufferedImage[] walk_right = {ss.obtenerSprite(0, 0, 18*scale, 23*scale), 
				ss.obtenerSprite(18*scale, 0, 18*scale, 23*scale), 
				ss.obtenerSprite(38*scale, 0, 19*scale, 23*scale)};
		BufferedImage[] walk_left = {ss.obtenerSprite(58*scale, 0, 18*scale, 23*scale), 
				ss.obtenerSprite(79*scale, 0, 18*scale, 23*scale), 
				ss.obtenerSprite(98*scale, 0, 18*scale, 23*scale)};
		BufferedImage[] jump = {ss.obtenerSprite(117*scale, 0, 19*scale, 23*scale), 
				ss.obtenerSprite(138*scale, 0, 19*scale, 23*scale)};
		a2 = new Animation(walk_right, 8);
		a3 = new Animation(walk_left, 8);
		a4 = new Animation(jump, 8);
		
		// Viejo
		
		MP3Player.intro.play();

	}
	
	public void tick() {
		switch(state) {
		// No pasa nada (4 segundos)
		case 0:
			if (count > 60*1) {
				state = 1;
				count = 0;
				a1.start();
			}
			break;
		// Cristal roto
		case 1:
			a2.start();
			a3.start();
			a4.start();
			a1.tick();
			if (count > 60) {
				state = 2;
				count = 0;
			}
			break;
		// Enemigo sube, bomberman y viejo se desplazan
		case 2:
			a2.tick(); // Bomberman
			a3.tick();
			a4.tick();
			a1.tick(); // Enemigo
			if (yenemy < 60*scale) yenemy++;
			break;
			
		}
		if (input.fire.clicked) {
			MP3Player.intro.stop();
			game.setMenu(new LevelMenu(5));
		}
		count++;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(img[0], 0, 32 * scale, null); // Fondo

		switch (state) {
		case 0:
			g.drawImage(img[1], game.getWidth() / 2 - img[1].getWidth(null) / 2 + 4*scale,
					game.getHeight() /2 + 51*scale, null); // Cupula sin romper
			break;
		case 1:
			if (count > 8 && count < 24) {
				g.drawImage(img[5], 80*scale, 128*scale, null);
				g.drawImage(img[6], 150*scale, 128*scale, null);
			}
			if (count > 16 && count < 32) {
				g.drawImage(img[7], 60*scale, 76*scale, null);
				g.drawImage(img[7], 170*scale, 76*scale, null);
				
			}
			if (count > 24 && count < 40) {
				g.drawImage(img[8], 40*scale, 42*scale, null);
				g.drawImage(img[9], 190*scale, 42*scale, null);
				
			}
			if (count > 32 && count < 48) {
				g.drawImage(img[10], 20*scale, 8*scale, null);
				g.drawImage(img[11], 210*scale, 12*scale, null);
			}
		case 2:
			// Enemigo
			g.drawImage(a1.getSprite(), game.getWidth() / 2 - a1.getSprite().getWidth() / 2 + 4*scale,
					game.getHeight() / 2 + 49*scale - yenemy, null);
			// Cupula rota
			g.drawImage(img[2], game.getWidth()/2 - img[2].getWidth(null)/2 + 4*scale,
					game.getHeight()/2 + 53*scale, null);
			// Rectangulos para esconder al enemigo
			g.setColor(new Color(0, 17, 90));
			g.fillRect(126*scale, 207*scale, 13*scale, 1*scale);
			g.setColor(new Color(211, 239, 239));
			g.fillRect(127*scale, 208*scale, 12*scale, 6*scale);
			// Pared Izquierda
//			g.drawImage(img[3], game.getWidth()/2 - 48*scale,
//					game.getHeight() /2 + 80*scale, null);
			// Pared Derecha
			g.drawImage(img[4], game.getWidth()/2 + 21*scale,
					game.getHeight() /2 + 76*scale, null);
			
			g.drawImage(a2.getSprite(), game.getWidth()/2 -48*scale,
					game.getHeight()/2, null);
			g.drawImage(a3.getSprite(), game.getWidth()/2,
					game.getHeight()/2, null);
			g.drawImage(a4.getSprite(), game.getWidth()/2,
					game.getHeight()/2, null);
			break;
		}
	}
}

