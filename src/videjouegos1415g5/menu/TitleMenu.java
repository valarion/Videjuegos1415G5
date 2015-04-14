package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.sound.Sound;

public class TitleMenu extends Menu {
	private int selected = 0;
	private int ybg;
	private Font font;
	private BufferedImage bi1, bi2, bi3, bi4;
	private Image cu, bg, dy, bl;
	private Color bgColor;

	private static final String cursor = "/flecharoja.png";
	private static final String background = "/bg1.png";
	private static final String dyna = "/dyna.png";
	private static final String blaster = "/blaster.png";
	private static final String[] options = { "Game start", "Battle", "Setup", "Password" };

	public TitleMenu() {
		font = new Font();
		bgColor = new Color(73, 102, 192);
		try {
			bi1 = ImageIO.read(getClass().getResource(background));
			bi2 = ImageIO.read(getClass().getResource(cursor));
			bi3 = ImageIO.read(getClass().getResource(dyna));
			bi4 = ImageIO.read(getClass().getResource(blaster));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bg = bi1.getScaledInstance(bi1.getWidth()*scale, bi1.getHeight()*scale, Image.SCALE_SMOOTH);
		cu = bi2.getScaledInstance(bi2.getWidth()*scale, bi2.getHeight()*scale, Image.SCALE_SMOOTH);
		dy = bi3.getScaledInstance(bi3.getWidth()*scale, bi3.getHeight()*scale, Image.SCALE_SMOOTH);
		bl = bi4.getScaledInstance(bi4.getWidth()*scale, bi4.getHeight()*scale, Image.SCALE_SMOOTH);

	}

	public void tick() {
		if (input.up.clicked) selected--;
		if (input.down.clicked) selected++;

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.fire.clicked) {
			if (selected == 0) {
				//Sound.test.play();
				//game.resetGame();
				game.setMenu(null);
			}
			if (selected == 1) game.setMenu(new BattleMenu(this));
			if (selected == 2) game.setMenu(new SetupMenu(this));
			if (selected == 3) game.setMenu(new PasswordMenu(this));
		}
	}

	public void render(Graphics2D g) {	
		Sound.intro.play();
		
		// Color de fondo
		g.setColor(bgColor);
	    g.fillRect(0, 0, Main.ANCHURA, Main.ALTURA);
	    
	    // Transicion vertical
		if (Main.ALTURA - ybg >= 150) {
			g.drawImage(bg, 16, Main.ALTURA - ybg, null);
			ybg++;
			return;
		}
		g.drawImage(bg, 16, Main.ALTURA - ybg, null); // Fondo
		g.drawImage(dy, 50, 30, null); // Dyna
		g.drawImage(bl, 160, 90, null); // Blaster
		
		for (int i = 0; i < 4; i++) {
			String msg = options[i]; 
			if (i == selected) {
				g.drawImage(cu, Main.ANCHURA / 2 - 50 * scale, ((8 + i) * 7 * scale + 23) * scale, null);
				font.render(g, msg, Main.ANCHURA / 2 - 38 * scale, (8 + i) * 7 * scale + 23);
			} else {
				font.render(g, msg, Main.ANCHURA / 2 - 38 * scale, (8 + i) * 7 * scale + 23);
			}
		}
		
		font.render(g, "Push fire button !", Main.ANCHURA - 200 * scale, 100);
		font.render(g, "COPYRIGHT 2015.2015", Main.ANCHURA - 200 * scale, Main.ALTURA - 63 * scale * scale);
		font.render(g, "VidejuegosG4 SA", Main.ANCHURA - 200 * scale, Main.ALTURA - 60 * scale * scale);
	}
}