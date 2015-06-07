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
	private static final String shadow = "/scenes/shadow.png";
	private static final String bomberman = "/scenes/bomberman.png";
	private static final String old = "/scenes/viejo.png";


	private String[] images = {background, glass, broken_glass, left_wall, right_wall,
			left_g1, right_g1, left_right_g2, left_g3, right_g3, left_g4, right_g4};
	private BufferedImage[] bi;
	private Image[] img;
	private Animation enemyAn, enemyGirl, shadowNear, shadowFar;
	private Animation bomberRight, bomberLeft, bomberJump, bomberCalm, bomberAn;
	private Animation oldRight, oldLeft, oldJump, oldCalm, oldAn;
	private int state = 0;
	private int count;
	private SpriteLoader sl;
	private SpriteSheet ss;
	private boolean hidewall = false;
	private boolean hideAlpha = true;
	private int bgAlpha = 255;
	
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
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(shadow), scale));
		BufferedImage[] shadow_near = {ss.obtenerSprite(0, 0, 16*scale, 16*scale), 
				ss.obtenerSprite(0, 17*scale, 16*scale, 16*scale)};
		BufferedImage[] shadow_far = {ss.obtenerSprite(0, 33*scale, 16*scale, 16*scale), 
				ss.obtenerSprite(0, 48*scale, 16*scale, 16*scale)};
		enemyGirl = new Animation(bi1, 10);
		shadowNear = new Animation(shadow_near, 10);
		shadowFar = new Animation(shadow_far, 10);
		
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
		BufferedImage[] calm = {ss.obtenerSprite(160*scale, 0, 17*scale, 23*scale)};
		bomberRight = new Animation(walk_right, 8);
		bomberLeft = new Animation(walk_left, 8);
		bomberJump = new Animation(jump, 8);
		bomberCalm = new Animation(calm, 8);
		
		// Viejo
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(old), scale));
		BufferedImage[] walk_right_old = {ss.obtenerSprite(0, 0, 23*scale, 27*scale), 
				ss.obtenerSprite(29*scale, 0, 22*scale, 27*scale), 
				ss.obtenerSprite(56*scale, 0, 23*scale, 27*scale)};
		BufferedImage[] walk_left_old = {ss.obtenerSprite(83*scale, 0, 23*scale, 27*scale), 
				ss.obtenerSprite(111*scale, 0, 22*scale, 27*scale), 
				ss.obtenerSprite(139*scale, 0, 23*scale, 27*scale)};
		BufferedImage[] jump_old = {ss.obtenerSprite(166*scale, 0, 26*scale, 27*scale), 
				ss.obtenerSprite(195*scale, 0, 26*scale, 27*scale)};
		BufferedImage[] calm_old = {ss.obtenerSprite(223*scale, 0, 24*scale, 27*scale)};
		oldRight = new Animation(walk_right_old, 8);
		oldLeft = new Animation(walk_left_old, 8);
		oldJump = new Animation(jump_old, 8);
		oldCalm = new Animation(calm_old, 8);
		
		MP3Player.intro.play();

	}
	
	public void tick() {
		switch(state) {
		// No pasa nada (4 segundos)
		case 0:
			if (count > 60*4) {
				state = 1;
				count = 0;
				enemyAn = enemyGirl;
				enemyAn.start();
			}
			break;
		// Cristal roto
		case 1:
			enemyAn.tick();
			if (count > 60) {
				state = 2;
				count = 0;
				bomberAn = bomberLeft;
				bomberAn.start();
				oldAn = oldRight;
				oldAn.start();
			}
			break;
		// Enemigo sube, bomberman y viejo se desplazan
		case 2:
			bomberAn.tick(); // Bomberman
			oldAn.tick();	// Viejo
			if (enemyAn != null) enemyAn.tick(); // Enemigo
			if (yenemy < 60*scale) yenemy++;
			// Movimiento de bomberman
			if (xbomb < 60*scale && bomberAn == bomberLeft) xbomb++;
			else {
				bomberAn = bomberRight;
				bomberAn.start();
				hidewall = true;
				if (ybomb < 10*scale) ybomb++;
				if (xbomb > -40) xbomb--;
				else {
					bomberAn = bomberJump;
					bomberAn.start();
				}
			}
			// Movimiento del viejo
			if (count > 30) {
				if (xold < 60*scale && oldAn == oldRight) xold++;
				else {
					oldAn = oldLeft;
					oldAn.start();
					//hidewall = true;
					if (yold < 10*scale) yold++;
					if (xold > -38) xold--;
					else {
						oldAn = oldJump;
						oldAn.start();
					}
				}
			}
			// Movimiento final del enemigo
			if (count > 60*6) {
				if (yenemy < 210*scale && enemyAn == enemyGirl) yenemy+=2;
				else {
					enemyAn = shadowNear;
					enemyAn.start();
					xenemy = 3*scale;
					if (yenemy > 160*scale) {
						if (count%3 == 0) yenemy--;
					}
					else {
						enemyAn = shadowFar;
						enemyAn.start();
						if (yenemy > 108*scale) {
							if (count%4 == 0) yenemy--;
							bomberAn = bomberCalm;
							bomberAn.start();
							oldAn = oldCalm;
							oldAn.start();
							hideAlpha = false;
						}
						else {
							enemyAn = null;
							bomberAn = bomberCalm;
							oldAn = oldCalm;
							if (count > 60*22) {
								MP3Player.intro.stop();
								game.setMenu(new LevelMenu(1));
							}
						}
					}
				}
			}
			break;
			
		}
		if (input.fire.clicked) {
			MP3Player.intro.stop();
			game.setMenu(new LevelMenu(1));
		}
		count++;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(img[0], 0, 32 * scale, null); // Fondo
		// Mascara para ocultar el castillo
		if (!hideAlpha && bgAlpha > 0) bgAlpha--;
		g.setColor(new Color(0, 0, 0, bgAlpha));
		g.fillRect(0, 0, game.getWidth(), 159*scale);
		g.fillRect(0, 159*scale, 16*scale, 16*scale);
		g.fillRect(184*scale, 159*scale, 72*scale, 24*scale);
		g.fillRect(176*scale, 175*scale, 8*scale, 8*scale);

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
			// Enemigo 
			g.drawImage(enemyAn.getSprite(), game.getWidth() / 2 - enemyAn.getSprite().getWidth() / 2 + 4*scale,
					game.getHeight() / 2 + 49*scale - yenemy, null);
			// Cupula rota
			g.drawImage(img[2], game.getWidth()/2 - img[2].getWidth(null)/2 + 4*scale,
					game.getHeight()/2 + 53*scale, null);
			// Rectangulos para esconder al enemigo
			g.setColor(new Color(0, 17, 90));
			g.fillRect(126*scale, 207*scale, 13*scale, 1*scale);
			g.setColor(new Color(211, 239, 239));
			g.fillRect(127*scale, 208*scale, 12*scale, 6*scale);
			break;
		case 2:
			// Enemigo
			if (enemyAn != null)
				g.drawImage(enemyAn.getSprite(), game.getWidth() / 2 - enemyAn.getSprite().getWidth() / 2 + 4*scale - xenemy,
					game.getHeight() / 2 + 49*scale - yenemy, null);
			// Cupula rota
			g.drawImage(img[2], game.getWidth()/2 - img[2].getWidth(null)/2 + 4*scale,
					game.getHeight()/2 + 53*scale, null);
			// Rectangulos para esconder al enemigo
			g.setColor(new Color(0, 17, 90));
			g.fillRect(126*scale, 207*scale, 13*scale, 1*scale);
			g.setColor(new Color(211, 239, 239));
			g.fillRect(127*scale, 208*scale, 12*scale, 6*scale);
			// Bomberman
			g.drawImage(bomberAn.getSprite(), game.getWidth()/2 - 47*scale - xbomb,
					game.getHeight()/2 + 84*scale + ybomb, null);
			// Viejo
			g.drawImage(oldAn.getSprite(), game.getWidth()/2 + 24*scale + xold,
					game.getHeight()/2 + 80*scale + yold, null);
			if (!hidewall) {
				// Pared Izquierda
				g.drawImage(img[3], game.getWidth()/2 - 48*scale,
						game.getHeight() /2 + 80*scale, null);
				// Pared Derecha
				g.drawImage(img[4], game.getWidth()/2 + 21*scale,
						game.getHeight() /2 + 76*scale, null);
			}

			break;
		}
	}
}

