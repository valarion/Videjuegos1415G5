package videjouegos1415g5.cutscenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.menu.LevelMenu;
import videjouegos1415g5.menu.Menu;
import videjouegos1415g5.sound.MP3Player;
import videjouegos1415g5.sound.Sound;

public class InitScene extends Menu {
	
	// Imagenes
	private static final String background = "/scenes/bg_scene1.png";
	private static final String glass = "/scenes/glass.png";

	
	private String[] images = {background, glass};
	private BufferedImage[] bi;
	private Image[] img;
	
	private boolean glass_broken = false;

	
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
		
		MP3Player.intro.play();

	}
	
	public void tick() {
		if (input.fire.clicked) {
			MP3Player.intro.stop();
			game.setMenu(new LevelMenu(5));
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(img[0], 0, 32 * scale, null); // Fondo
		if (!glass_broken)
			g.drawImage(img[1], game.getWidth() / 2 - img[1].getWidth(null) / 2 + 4*scale,
					game.getHeight() /2 + 51 * scale, null); // Cupula sin romper
		
	}

}


