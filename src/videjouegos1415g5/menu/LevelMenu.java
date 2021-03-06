package videjouegos1415g5.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.Colors;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.sound.MP3Player;

public class LevelMenu extends Menu {
	
	private static final String sprites = "/menu/level_menu.png";
	
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage bg, round, level;
	private Animation head, roundFlicker, levelFlicker;
	private int count = 0;
	private int x, y, lev;
	
	public LevelMenu (int level) {
		this.scale = Main.ESCALA;
		this.lev = level;
		
		this.sl = new SpriteLoader();
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(sprites), scale));
		
		this.bg = ss.obtenerSprite(0, 0, 256*scale, 160*scale);
		this.round = ss.obtenerSprite(76*scale, 161*scale, 49*scale, 14*scale);
		this.level = ss.obtenerSprite(12*(level-1)*scale + 134*scale, 160*scale, 12*scale, 14*scale);
		BufferedImage[] roundFlicker = {this.round, Colors.convertColor(this.round, Color.YELLOW)};
		BufferedImage[] levelFlicker = {this.level, Colors.convertColor(this.level, Color.YELLOW)};
		BufferedImage[] head = {ss.obtenerSprite(1, 160*scale, 23*scale, 23*scale), 
				ss.obtenerSprite(23*scale, 160*scale, 24*scale, 23*scale), 
				ss.obtenerSprite(48*scale, 160*scale, 24*scale, 23*scale)};
		this.head = new Animation(head, 12);
		this.roundFlicker = new Animation(roundFlicker, 5);
		this.levelFlicker = new Animation(levelFlicker, 5);
		
		// FALTA DE CALCULAR 
		switch (level) {
		case 1:
			this.x = 18*scale;
			this.y = 134*scale;
			break;
		case 2:
			this.x = 48*scale;
			this.y = 112*scale;
			break;
		case 3:
			this.x = 66*scale;
			this.y = 168*scale;
			break;
		case 4:
			this.x = 104*scale;
			this.y = 128*scale;
			break;
		case 5:
			this.x = 154*scale;
			this.y = 120*scale;
			break;
		case 6:
			this.x = 210*scale;
			this.y = 138*scale;
			break;
		case 7:
			this.x = 210*scale;
			this.y = 104*scale;
			break;
		case 8:
			this.x = 210*scale;
			this.y = 62*scale;
			break;
		}
		this.head.start();
		this.roundFlicker.start();
		this.levelFlicker.start();
		MP3Player.level_start.play();
	}
	
	public void tick() {
		count++;
		if (count > 60 * 5 || input.fire.clicked) { // 5 segundos
			head.stop();
			roundFlicker.stop();
			levelFlicker.stop();
			MP3Player.level_start.stop();
			game.setMenu(new MapMenu(lev, 1));
		}
		head.tick();
		roundFlicker.tick();
		levelFlicker.tick();
	}
	
	public void render(Graphics2D g) {
	    
	    g.drawImage(bg, 0, game.getHeight() / 2 - bg.getHeight() / 2, null);
	    
	    g.drawImage(roundFlicker.getSprite(), 
	    		game.getWidth() / 2 - round.getWidth() / 2 - 12*scale, 
	    		game.getHeight() - round.getHeight()*2, null);
	    
	    g.drawImage(levelFlicker.getSprite(), 
	    		game.getWidth() / 2 + round.getWidth() / 2, 
	    		game.getHeight() - round.getHeight()*2, null);
	    
	    g.drawImage(head.getSprite(), x, y, null);

	}

}
