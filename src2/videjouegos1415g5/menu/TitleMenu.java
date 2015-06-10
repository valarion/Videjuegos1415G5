package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.Main;
import videjouegos1415g5.cutscenes.InitScene;
import videjouegos1415g5.entity.Bomberman;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.gfx.ScaleImg;
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
	private static final String[] options = { "GAME START", "BATTLE", "SETUP", "PASSWORD" };
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
		// Transicion vertical
		if (game.getHeight() - ybg >= 87 * scale) {
			ybg = ybg + 1 * scale;
			if (input.fire.clicked) {
				ybg = game.getHeight()-87*scale;
			}
		}
		else {
			if (input.up.clicked)
				selected--;
			if (input.down.clicked)
				selected++;

			int len = options.length;
			if (selected < 0)
				selected += len;
			if (selected >= len)
				selected -= len;

			if (input.fire.clicked) {
				if (selected == 0) {
					MP3Player.title.stop();
					game.startLevel(1, 1);
				}
				if (selected == 1)
					game.setMenu(new BattleMenu(this));
				if (selected == 2)
					game.setMenu(new SetupMenu(this));
				if (selected == 3)
					game.setMenu(new PasswordMenu(this));
			}

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
	
	public void render3D(GL2 gl, GLU glu) {	
	
		gl.glPushMatrix();
		gl.glTranslated(-250, 150, 0);
		gl.glColor3f(1.0f, 1.0f, 0.1f);
		this.pintarfrase(gl, glu, 18f, "DYNA");
		gl.glTranslated(170, -100, 0);
		this.pintarfrase(gl, glu, 15f, "BLASTER");
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
				gl.glPushMatrix();
				gl.glTranslated(-100, -50, 0.0);
				gl.glScaled(7,7,7);
	//this.prueba(gl, glu);
	gl.glPopMatrix();
gl.glPopMatrix();
gl.glPushMatrix();
//gl.glTranslated(-100, -50, 0.0);
gl.glScaled(7,7,7);
//this.prueba(gl, glu);
gl.glPopMatrix();
}
	
	
	
	
	public void prueba(GL2 gl, GLU glu){

		float r=8.5f;
		gl.glPushMatrix();
		 gl.glColor3d(1, 0, 1);
		//gl.glTranslated(position.x-3, -position.y+5, 0);
		gl.glScaled(1.4,1,1);
		gl.glPushMatrix();
		gl.glScaled(1,1,0.2);
		  GLUquadric earth = glu.gluNewQuadric();
	        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
	        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
	        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
	        
	        final int slices = 16;
	        final int stacks = 16;
	        gl.glColor3d(1, 0, 1);
	       
	     
		glu.gluSphere(earth,r, slices, stacks);
		gl.glPopMatrix();
		
		
		//alas
		gl.glPushMatrix();
		gl.glTranslated(r*0.85,r,r/20);
		float tamaño=r/3f;
	
		 gl.glBegin(GL2.GL_TRIANGLE_FAN);
	       gl.glNormal3f(0, 0, 1);
	       gl.glVertex3f(-tamaño*1.5f, -tamaño*1.5f, 0);
	       gl.glVertex3f(+tamaño*1.8f, tamaño*1.5f, 0);
	       gl.glVertex3f(-tamaño*1.5f, tamaño*1.5f, 0);
	       gl.glEnd();
	      gl.glTranslated(-r*1.8,0,0);
	      gl.glRotated(180,0,1,0);
	       gl.glBegin(GL2.GL_TRIANGLE_FAN);
	   
	       gl.glNormal3f(0, 0, -1);
	       gl.glVertex3f(-tamaño*1.5f, -tamaño*1.5f, 0);
	       gl.glVertex3f(+tamaño*1.8f, tamaño*1.5f, 0);
	       gl.glVertex3f(-tamaño*1.5f, tamaño*1.5f, 0);
	       gl.glEnd();
		
		
		gl.glPopMatrix();
		
		
	
	//ojos	
		 gl.glColor3d(0,0,0);
		 gl.glTranslated(-r/2, 0, r/5);
		 glu.gluDisk(earth, 0, r/8, slices, stacks);
		 gl.glTranslated(r,0,0);
		 glu.gluDisk(earth, 0, r/8, slices, stacks);
		 gl.glColor3d(1,1,1);
		 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
		 gl.glTranslated(-r,0,0);
		 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
		 //boca
		 gl.glTranslated(r/2,-r/2,0);
		 tamaño=r/10;
		 gl.glTranslated(-r/4,0,0);
		 gl.glBegin(GL2.GL_TRIANGLE_FAN);
	       gl.glNormal3f(0, 0, 1);
	       gl.glVertex3f(0, -tamaño*1.5f, 0);
	       gl.glVertex3f(+tamaño*1.5f, tamaño*1.5f, 0);
	       gl.glVertex3f(-tamaño*1.5f, tamaño*1.5f, 0);
	       gl.glEnd();
	       gl.glTranslated(r/2,0,0);
			 gl.glBegin(GL2.GL_TRIANGLE_FAN);
		       gl.glNormal3f(0, 0, 1);
		       gl.glVertex3f(0, -tamaño*1.5f, 0);
		       gl.glVertex3f(+tamaño*1.5f, tamaño*1.5f, 0);
		       gl.glVertex3f(-tamaño*1.5f, tamaño*1.5f, 0);
		       gl.glEnd();
		gl.glPopMatrix();
		System.out.println();
	}
}