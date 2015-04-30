package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.cutscenes.InitScene;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.sound.MP3Player;

public class TitleMenu extends Menu {
	private int selected = 0;
	private int ybg;
	private Font font1, font2;
	private BufferedImage bi1, bi2, bi3, bi4;
	private Image cu, bg, dy, bl;

	private static final String cursor = "/menu/flecharoja.png";
	private static final String background = "/menu/bg1.png";
	private static final String dyna = "/menu/dyna.png";
	private static final String blaster = "/menu/blaster.png";
	private static final String[] options = { "Game start", "Battle", "Setup", "Password" };
	private static final String push = "Push fire button !";
	private static final String copyright = "COPYRIGHT 2015.2015";
	private static final String company = "VidejuegosG5 SA";

	public TitleMenu() {
		scale = Main.ESCALA;
		font1 = new Font(null, true);
		font2 = new Font(new Color(255, 255, 0), true);
		
		try {
			bi1 = ImageIO.read(getClass().getResource(background));
			bi2 = ImageIO.read(getClass().getResource(cursor));
			bi3 = ImageIO.read(getClass().getResource(dyna));
			bi4 = ImageIO.read(getClass().getResource(blaster));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bg = bi1.getScaledInstance(bi1.getWidth() * scale, bi1.getHeight() * scale, Image.SCALE_SMOOTH);
		cu = bi2.getScaledInstance(bi2.getWidth() * scale, bi2.getHeight() * scale, Image.SCALE_SMOOTH);
		dy = bi3.getScaledInstance(bi3.getWidth() * scale, bi3.getHeight() * scale, Image.SCALE_SMOOTH);
		bl = bi4.getScaledInstance(bi4.getWidth() * scale, bi4.getHeight() * scale, Image.SCALE_SMOOTH);
		MP3Player.title.play();

	}

	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.fire.clicked) {
			if (selected == 0) {
				//game.resetGame();
				//game.setMenu(new TransitionMenu(null));
				MP3Player.title.stop();;
				game.setMenu(new InitScene());
			}
			if (selected == 1) game.setMenu(new BattleMenu(this));
			if (selected == 2) game.setMenu(new SetupMenu(this));
			if (selected == 3) game.setMenu(new PasswordMenu(this));
		}
		
		// Transicion vertical
		if (game.getHeight() - ybg >= 87 * scale) {
			ybg = ybg + 1 * scale;
		}
	}

	public void render(Graphics2D g) {	
		
		// Color de fondo
		g.setColor(bgColor);
	    g.fillRect(0, 0, game.getWidth(), game.getHeight());
	    
	    // Transicion vertical
		if (game.getHeight() - ybg >= 87*scale) {
			g.drawImage(bg, 0, game.getHeight() - ybg, null);
			//ybg = ybg + 1 * scale;
			return;
		}
		g.drawImage(bg, 0, game.getHeight() - 146*scale, null); // Fondo
		g.drawImage(dy, 25 * scale, 15 * scale, null); // Dyna
		g.drawImage(bl, 80 * scale, 45 * scale, null); // Blaster
		
		for (int i = 0; i < options.length; i++) {
			String msg = options[i]; 
			if (i == selected) {
				g.drawImage(cu, 
						game.getWidth() / 2 - ((options[0].length() / 2) * font1.getTilesize() * scale + cu.getWidth(null) + 5),
						game.getHeight() / 2 + (font1.getTilesize() * i) * 3/2 *  scale + font1.getTilesize() * scale * 33 / 10, null);
				font1.render(g, msg,
						game.getWidth() / 2 - (options[0].length() / 2) * font1.getTilesize() * scale, 
						game.getHeight() / 2 + (font1.getTilesize() * i) * 3/2 * scale + font1.getTilesize() * scale * 33/10);
			} else {
				font1.render(g, msg, 
						game.getWidth() / 2 - (options[0].length() / 2) * font1.getTilesize() * scale, 
						game.getHeight() / 2 + (font1.getTilesize() * i) * 3/2 * scale + font1.getTilesize() * scale * 33/10);
			}
		}
		
		font2.render(g, push, 
				game.getWidth() / 2 - (push.length() / 2) * font1.getTilesize() * scale, 
				game.getHeight() / 2 - font2.getTilesize() * scale);
		font2.render(g, copyright, 
				game.getWidth() / 2 - (copyright.length() / 2) * font1.getTilesize() * scale,
				game.getHeight() - font2.getTilesize() * 7/2 * scale);
		font2.render(g, company, 
				game.getWidth() / 2 - (company.length() / 2) * font1.getTilesize() * scale,
				game.getHeight() - font2.getTilesize() * 2 * scale);
	}
	
	public void resize(int scale) {
		bg = bi1.getScaledInstance(bi1.getWidth() * scale, bi1.getHeight() * scale, Image.SCALE_SMOOTH);
		cu = bi2.getScaledInstance(bi2.getWidth() * scale, bi2.getHeight() * scale, Image.SCALE_SMOOTH);
		dy = bi3.getScaledInstance(bi3.getWidth() * scale, bi3.getHeight() * scale, Image.SCALE_SMOOTH);
		bl = bi4.getScaledInstance(bi4.getWidth() * scale, bi4.getHeight() * scale, Image.SCALE_SMOOTH);
		font1 = new Font(null, true);
		font2 = new Font(new Color(255, 255, 0), true);
	}
}