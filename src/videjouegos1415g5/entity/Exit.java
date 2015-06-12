package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.Obstacle;

public class Exit extends Entity {
	
	private final String EXIT = "/exit.png"; 
	private final static int w = 16;
	private final static int h = 16;
	
	private boolean activated;
	
	private ArrayList<Enemy> enemies;
	
	public Exit(ArrayList<Obstacle> obs, ArrayList<Enemy> enemies) {
		this.enemies = enemies;
		this.activated = false;
		
		this.position.width = w;
		this.position.height = h;
		
		this.sl = new SpriteLoader();	    
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(EXIT), scale));
		
		BufferedImage[] exit = new BufferedImage[]{ss.obtenerSprite(0, 0, w*scale, h*scale),
					ss.obtenerSprite(w*scale, 0, w*scale, h*scale)};
		
		if (obs != null) while (!located) findStartPos(obs);
		else {
			this.position.x = 40*scale;
			this.position.y = 72*scale;
		}
		this.animation = new Animation(exit, 10, Direction.DOWN);
		
	}
	
	public void tick() {
		this.animation.tick();
		if (enemies.size() <= 0) {
			activated = true;
			if (!animation.finalFrame()) animation.start();
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
	}
	
	private boolean findStartPos(ArrayList<Obstacle> obs) {
		for (int i = 0; i < obs.size(); i++) {
			if (obs.get(i).isSolid() && random.nextDouble() >= 0.9) {
				this.position.x = obs.get(i).position.x;
				this.position.y = obs.get(i).position.y;
				located = true;
				break;
			}
		}
		return true;
		
	}
	
	public boolean isActive() {
		return activated;
	}
	
	public void render3d(GL2 gl, GLU glu){

		gl.glPushMatrix();
		gl.glTranslated(position.x, -position.y, 0);
		gl.glScaled(20,20,1);
		gl.glColor3d(1, 1, 0.2);
		if (activated){
			gl.glColor3d(0,0.3,1);
			gl.glScaled(1.55,1.55,1);}
	    gl.glTranslated(-0.5,-0.5,-0.5);
	      /* draws the sides of a unit cube (0,0,0)-(1,1,1) */
	      gl.glBegin(GL2.GL_POLYGON);/* f1: front */
	        gl.glNormal3f(-1.0f,0.0f,0.0f);
	        gl.glVertex3f(0.0f,0.0f,0.0f);
	        gl.glVertex3f(0.0f,0.0f,1.0f);
	        gl.glVertex3f(1.0f,0.0f,1.0f);
	        gl.glVertex3f(1.0f,0.0f,0.0f);
	      gl.glEnd();
	      gl.glBegin(GL2.GL_POLYGON);/* f2: bottom */
	        gl.glNormal3f(0.0f,0.0f,-1.0f);
	        gl.glVertex3f(0.0f,0.0f,0.0f);
	        gl.glVertex3f(1.0f,0.0f,0.0f);
	        gl.glVertex3f(1.0f,1.0f,0.0f);
	        gl.glVertex3f(0.0f,1.0f,0.0f);
	      gl.glEnd();
	      gl.glBegin(GL2.GL_POLYGON);/* f3:back */
	        gl.glNormal3f(1.0f,0.0f,0.0f);
	        gl.glVertex3f(1.0f,1.0f,0.0f);
	        gl.glVertex3f(1.0f,1.0f,1.0f);
	        gl.glVertex3f(0.0f,1.0f,1.0f);
	        gl.glVertex3f(0.0f,1.0f,0.0f);
	      gl.glEnd();
	      gl.glBegin(GL2.GL_POLYGON);/* f4: top */
	        gl.glNormal3f(0.0f,0.0f,1.0f);
	        gl.glVertex3f(1.0f,1.0f,1.0f);
	        gl.glVertex3f(1.0f,0.0f,1.0f);
	        gl.glVertex3f(0.0f,0.0f,1.0f);
	        gl.glVertex3f(0.0f,1.0f,1.0f);
	      gl.glEnd();
	      gl.glBegin(GL2.GL_POLYGON);/* f5: left */
	        gl.glNormal3f(0.0f,1.0f,0.0f);
	        gl.glVertex3f(0.0f,0.0f,0.0f);
	        gl.glVertex3f(0.0f,1.0f,0.0f);
	        gl.glVertex3f(0.0f,1.0f,1.0f);
	        gl.glVertex3f(0.0f,0.0f,1.0f);
	      gl.glEnd();
	      gl.glBegin(GL2.GL_POLYGON);/* f6: right */
	        gl.glNormal3f(0.0f,-1.0f,0.0f);
	        gl.glVertex3f(1.0f,0.0f,0.0f);
	        gl.glVertex3f(1.0f,0.0f,1.0f);
	        gl.glVertex3f(1.0f,1.0f,1.0f);
	        gl.glVertex3f(1.0f,1.0f,0.0f);
	      gl.glEnd();
	      gl.glPopMatrix();
	}
	
}
