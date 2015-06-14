package videjouegos1415g5.menu;

import java.awt.Graphics2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.sound.MP3Player;

public class CreditsMenu extends Menu {
	
	private final String DAVID = "DAVID ORERA";
	private final String RAFA = "RAFAEL MARCEN";
	private final String RUBEN = "RUBEN TOMAS";
	
	private Font font;
	private MP3Player music;
	private Thread loadmusic;
	private String[] credits = {"DYNABLASTER", "CREDITS", "", 
			"VIDEOJUEGOS 2014-2015   GRUPO 5", "", 
			"PRODUCTION", DAVID, RAFA, RUBEN, "", 
			"DESIGN", DAVID, RAFA, RUBEN, "", 
			"CODING", DAVID, RAFA, RUBEN, "", 
			"GRAPHICS", DAVID, RAFA, RUBEN, "", 
	};
	
	private String[] credits2 = {"MUSIC", DAVID, RAFA, RUBEN, "", 
			"SOUND FX", DAVID, RAFA, RUBEN, "", 
			"3D", DAVID, RAFA, RUBEN, "", 
			"GAMETESTING", DAVID, RAFA, RUBEN, "", 
			"", "ORIGINAL GAME", "1991", "HUDSON SOFT ", "", 
			};
	private float y;

	public CreditsMenu() {
		this.font = new Font(null, false);
		this.y = 0;
		this.loadmusic = new Thread(new Runnable() {
			public void run() {
				try {
					music = new MP3Player("/music/credits.mp3");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		loadmusic.start();
	}
	
	public void tick() {
		if (music !=null) music.play();
		if (input.exit.clicked || (music != null && !music.isPlaying())) {
			if (music != null) music.stop();
			loadmusic.interrupt();
			game.setMenu(new TitleMenu());
		}

		if (game.getHeight() + y + (credits.length-1)*font.getTilesize()*2*scale > game.getHeight()/2) {
			y -= 0.5;
		}

	}
	
	public void render (Graphics2D g) {
		for (int i = 0; i<credits.length; i++) {
			font.render(g, credits[i], 
					game.getWidth()/2 - (credits[i].length()/2)*font.getTilesize()*scale, 
					(int) (game.getHeight() + y + i*font.getTilesize()*2*scale));
		}

	}
	
	public void render3D (GL2 gl, GLU glu) {
		gl.glTranslated(-250,200,0);
		for (int i = 0; i<credits.length; i++) {
			
			this.pintarfrase(gl, glu, 3f, credits[i]);
			gl.glPushMatrix();
			gl.glTranslated(250,0,0);
			this.pintarfrase(gl, glu, 3f, credits2[i]);
			gl.glPopMatrix();
			gl.glTranslated(0,-15,0);
			}

	}

}
