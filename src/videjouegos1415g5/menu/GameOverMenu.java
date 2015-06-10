package videjouegos1415g5.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class GameOverMenu extends Menu {
	
	private static final String sprites = "/menu/gameover_menu.png";
	private static final String cursor = "/menu/flecharoja.png";
	
	private String[] options = { "Continue", "End"};
	private int selected = 0;
	private SpriteLoader sl;
	private SpriteSheet ss;
	private BufferedImage bg, title, bi;
	private Image cu;
	private Font font, passfont;
	private String password;
	private int lives;
	
	public GameOverMenu(int lives) {
		this.lives = lives;
		this.options[0] += " " + lives;
		this.font = new Font(null, true);

		if (lives == -1) {
			options[0] = "Continue last";
			this.font = new Font(Color.RED, true);
		}
		
		if (lives == -2) {
			options = new String[]{"End"};
		}
		
		this.scale = Main.ESCALA;
		this.bgColor = new Color(0, 97, 146);
		this.password = "Password";
		
		this.sl = new SpriteLoader();
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(sprites), scale));
		this.bg = ss.obtenerSprite(0, 0, 193*scale, 65*scale);
		this.title = ss.obtenerSprite(0, 65*scale, 156*scale, 27*scale);
		
		try {
			bi = ImageIO.read(getClass().getResource(cursor));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cu = bi.getScaledInstance(bi.getWidth() * scale, bi.getHeight() * scale, Image.SCALE_SMOOTH);
		
		this.passfont = new Font(null, false);
	}
	
	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.fire.clicked) {
			if (selected == 0  && lives > -2) game.setMenu(null);
			else game.setMenu(new TitleMenu());
			if (selected == 1) game.setMenu(new TitleMenu());
				
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(new Color(0, 97, 146));
	    g.fillRect(0, 25*scale, game.getWidth(), game.getHeight() - 45*scale);
	    
	    g.drawImage(bg, 
	    		game.getWidth() / 2 - bg.getWidth() / 2, 
	    		game.getHeight() / 2, null);
	    g.drawImage(title, 
	    		game.getWidth() / 2 - title.getWidth() / 2, 
	    		game.getHeight() / 2 - 74*scale, null);
	    
	    
	    for (int i = 0; i < options.length; i++) {
			String msg = options[i]; 
			if (i == selected) {
				g.drawImage(cu, 
						game.getWidth()/2 - ((options[0].length()/2)*font.getTilesize()*scale + cu.getWidth(null) + 5),
						game.getHeight()/2 + (font.getTilesize()*i)*2*scale - font.getTilesize()*scale*7/2, null);
				font.render(g, msg,
						game.getWidth()/2 - (options[0].length()/2)*font.getTilesize()*scale, 
						game.getHeight()/2 + (font.getTilesize()*i)*2*scale - font.getTilesize()*scale*7/2);
			} else {
				font.render(g, msg, 
						game.getWidth()/2 - (options[0].length()/2)*font.getTilesize() * scale, 
						game.getHeight()/2 + (font.getTilesize()*i)*2*scale - font.getTilesize()*scale*7/2);
			}
		}
	    
	    passfont.render(g, password,
	    		game.getWidth() / 2 - (password.length() / 2)*font.getTilesize()*scale - 9*scale,
	    		game.getHeight() / 2 + 39*scale);
		
	}

	public void render3D(GL2 gl, GLU glu) {	
		
		gl.glPushMatrix();
		gl.glTranslated(-250, 150, 0);
		gl.glColor3f(1.0f, 1.0f, 0.1f);
		this.pintarfrase(gl, glu, 18f, "GAME");
		gl.glTranslated(170, -100, 0);
		this.pintarfrase(gl, glu, 15f, "OVER");
		float tamaño=8f;
		gl.glTranslated(0, -90, 0);
		
		
				
				for (int i = 0; i < options.length; i++) {
					
					String msg = options[i]; 
					if (i == selected) {
						gl.glPushMatrix();
						gl.glTranslatef(0.0f, -i*tamaño*5+tamaño/2, 0.0f);
						this.cursor(gl, glu, tamaño);
						gl.glTranslatef(tamaño*2, 0.0f, 0.0f);
						gl.glColor3f(0.0f, 1.0f, 0.1f);
						
						this.pintarfrase(gl, glu, tamaño, msg);
						
						gl.glPopMatrix();
						} else {
							gl.glPushMatrix();
							gl.glTranslatef(0.0f, -i*tamaño*5+tamaño/2, 0.0f);
							gl.glColor3f(0.0f, 0.0f, 1f);
							this.pintarfrase(gl, glu, tamaño, msg);
							gl.glPopMatrix();
						}
				}
				
							
gl.glPopMatrix();	
}


	
}
