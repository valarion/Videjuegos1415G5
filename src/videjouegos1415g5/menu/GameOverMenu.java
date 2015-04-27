package videjouegos1415g5.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public GameOverMenu(int lives) {
		this.scale = Main.ESCALA;
		this.options[0] += " " + lives;
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
		
		this.font = new Font(null, true);
		this.passfont = new Font(null, false);
	}
	
	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.fire.clicked) {
			if (selected == 0) game.setMenu(null);
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

}
