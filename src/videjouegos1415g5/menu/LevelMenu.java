package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class LevelMenu extends Menu {
	
	private static final String sprites = "/menu/level_menu.png";
	
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage bg, round, level;
	private Animation head;
	private int count = 0;
	private int x, y;
	
	public LevelMenu (int level) {
		this.scale = Main.ESCALA;
		
		this.sl = new SpriteLoader();
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(sprites), scale));
		
		this.bg = ss.obtenerSprite(0, 0, 256*scale, 160*scale);
		this.round = ss.obtenerSprite(76*scale, 161*scale, 49*scale, 14*scale);
		this.level = ss.obtenerSprite(12*(level-1)*scale + 134*scale, 160*scale, 12*scale, 14*scale);
		BufferedImage[] head = {ss.obtenerSprite(1, 160*scale, 23*scale, 23*scale), 
				ss.obtenerSprite(23*scale, 160*scale, 24*scale, 23*scale), 
				ss.obtenerSprite(48*scale, 160*scale, 24*scale, 23*scale)};
		this.head = new Animation(head, 5);
		
		// FALTA DE CALCULAR 
		switch (level) {
		case 1:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 2:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 3:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 4:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 5:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 6:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 7:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		case 8:
			this.x = 10*scale;
			this.y = 150*scale;
			break;
		}
		this.head.start();
	}
	
	public void tick() {
		count++;
		if (count > 60 * 5 || input.fire.clicked) { // 5 segundos
			head.stop();
			game.setMenu(new MapMenu(1, 2));
			//game.setMenu(null);
		}
		head.tick();
	}
	
	public void render(Graphics2D g) {
	    
	    g.drawImage(bg, 0, game.getHeight() / 2 - bg.getHeight() / 2, null);
	    
	    g.drawImage(round, 
	    		game.getWidth() / 2 - round.getWidth() / 2 - 12*scale, 
	    		game.getHeight() - round.getHeight()*2, null);
	    
	    g.drawImage(level, 
	    		game.getWidth() / 2 + round.getWidth() / 2, 
	    		game.getHeight() - round.getHeight()*2, null);
	    
	    g.drawImage(head.getSprite(), x, y, null);

	}

}
