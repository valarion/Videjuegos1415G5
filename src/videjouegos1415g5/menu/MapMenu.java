package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class MapMenu extends Menu {
	
	private static final String sprites = "/menu/map_menu.png";
	
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage stage, game_start, level, map;
	private int count = 0;
	
	public MapMenu (int level, int map) {
		this.scale = Main.ESCALA;
		
		this.sl = new SpriteLoader();
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(sprites), scale));
		
		this.stage = ss.obtenerSprite(0, 0, 79*scale, 13*scale);
		this.game_start = ss.obtenerSprite(0, 14*scale, 107*scale, 13*scale);
		this.level = ss.obtenerSprite(8*level*scale, 28*scale, 6*scale, 13*scale);
		this.map = ss.obtenerSprite(8*map*scale, 28*scale, 6*scale, 13*scale);	
	}
	
	public void tick() {
		count++;
		if (count > 60 * 3 || input.fire.clicked) { // 3 segundos
			game.setMenu(new GameOverMenu(1));
			//game.setMenu(null);
		}
	}
	
	public void render(Graphics2D g) {
	    
	    g.drawImage(stage, 
	    		game.getWidth() / 2 - stage.getWidth() / 2 - 3*scale, 
	    		game.getHeight() / 2 - stage.getHeight() - 4*scale, null);
	    
	    g.drawImage(game_start, 
	    		game.getWidth() / 2 - game_start.getWidth() / 2, 
	    		game.getHeight() / 2, null);
	    
	    g.drawImage(level, 
	    		game.getWidth() / 2 + stage.getWidth() / 2 - 18*scale, 
	    		game.getHeight() / 2 - stage.getHeight() - 4*scale, null);
	    
	    g.drawImage(map, 
	    		game.getWidth() / 2 + stage.getWidth() / 2, 
	    		game.getHeight() / 2 - stage.getHeight() - 4*scale, null);

	}

}
