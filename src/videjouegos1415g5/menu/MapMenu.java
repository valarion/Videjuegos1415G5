package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.sound.MP3Player;

public class MapMenu extends Menu {
	
	private static final String sprites = "/menu/map_menu.png";
	
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage stage, game_start, level, map;
	private int count = 0;
	private int nivel;
	private int mapa;
	
	public MapMenu (int level, int map) {
		this.nivel=level;
		this.mapa=map;
		this.scale = Main.ESCALA;
		
		this.sl = new SpriteLoader();
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(sprites), scale));
		
		this.stage = ss.obtenerSprite(0, 0, 79*scale, 13*scale);
		this.game_start = ss.obtenerSprite(0, 14*scale, 107*scale, 13*scale);
		this.level = ss.obtenerSprite(8*level*scale, 28*scale, 6*scale, 13*scale);
		this.map = ss.obtenerSprite(8*map*scale, 28*scale, 6*scale, 13*scale);	
		MP3Player.map_start.play();
	}
	
	public void tick() {
		count++;
		if (count > 60 * 3 || input.fire.clicked) { // 3 segundos
			game.setMenu(null);
			MP3Player.map_start.stop();
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
public void render3D(GL2 gl, GLU glu) {	
		
		gl.glPushMatrix();
		gl.glTranslated(-250, 150, 0);
		gl.glColor3f(1.0f, 1.0f, 0.1f);
		this.pintarfrase(gl, glu, 18f, "MAP");
		gl.glTranslated(170, -100, 0);
		this.pintarfrase(gl, glu, 15f, String.valueOf(nivel)+"-"+String.valueOf(mapa));
		float size=8f;
		gl.glTranslated(0, -90, 0);
		
		
				
							
gl.glPopMatrix();	
}
}
