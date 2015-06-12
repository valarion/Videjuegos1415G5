package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;
import videjouegos1415g5.sound.Sound;

public class SnakeBody extends SnakeHead {
	
	private final String ANIMATION = "/enemies/blue_snake.png"; 
	private final static int w = 16;
	private final static int h = 18;

	protected SnakeHead parent;

	public SnakeBody(GenerateObstacles obs, Map map, Bomberman player, SnakeHead parent) {
		super(obs, map, player);
		this.parent = parent;
		this.move = false;
		this.health = 80;
		this.score = 100;
		
		this.position = new Rectangle(parent.position);
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		up = new Animation[4];
		down = new Animation[4];
		left = new Animation[4];
		right = new Animation[4];
		for(int i=0; i < 4; i++) {
			BufferedImage[] u = {ss.obtenerSprite(12*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale), 
					ss.obtenerSprite(13*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(14*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(13*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale)};
			
			BufferedImage[] d = {ss.obtenerSprite(0*w*scale, (2*i+2)*h*scale+(i+1)*scale, w*scale, h*scale), 
					ss.obtenerSprite(19*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(18*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(19*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale)};
			
			BufferedImage[] l = {ss.obtenerSprite(3*w*scale, (2*i+2)*h*scale+(i+1)*scale, w*scale, h*scale), 
					ss.obtenerSprite(2*w*scale, (2*i+2)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(1*w*scale, (2*i+2)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(2*w*scale, (2*i+2)*h*scale+(i+1)*scale, w*scale, h*scale)};
			
			BufferedImage[] r = {ss.obtenerSprite(15*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale), 
					ss.obtenerSprite(16*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(17*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale),
					ss.obtenerSprite(16*w*scale, (2*i+1)*h*scale+(i+1)*scale, w*scale, h*scale)};
			
			up[i] = new Animation(u, 20, Direction.UP);
			down[i] = new Animation(d, 20, Direction.DOWN);
			left[i] = new Animation(l, 20, Direction.LEFT);
			right[i] = new Animation(r, 20, Direction.RIGHT);
		}
		
		BufferedImage[] die = {ss.obtenerSprite(8*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(9*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(10*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(11*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(12*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(13*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(14*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(15*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(16*w*scale, 0, w*scale, h*scale)};
		
		this.death = new Animation(die, 14, Direction.DOWN);
		animation = down[0];
		animation.start();

	}

	public void tick() {
		if(child == null || child.removed)
			animation.start();
		else if(animation == death)
			animation.stop();
		if(animation == death && animation.finalFrame())
			remove();
		if (!removed)
			animation.tick();
		if (health <= 0)
			die();
		else {
			if (invincible > 0)
				invincible--;
			if (health <= 20)
				state = 3;
			else if (health <= 40)
				state = 2;
			else if (health <= 60)
				state = 1;
			else
				state = 0;
			if (animation != death) {
				if (parent.positions.size() > Map.TILESIZE * Main.ESCALA) {
					PosDir pd = parent.positions.getFirst();
					parent.positions.removeFirst();
					position = pd.position;
					xdir = pd.dirx;
					ydir = pd.diry;
					if(child != null)
						positions.addLast(pd);
				}
			}
			// super.tick();
			animation.tick();
			if (animation != death) {
				if (xdir < 0)
					animation = left[state];
				else if (xdir > 0)
					animation = right[state];
				else if (ydir < 0)
					animation = up[state];
				else
					animation = down[state];
			}

			animation.start();
		}
	}

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x, position.y, w, h);
	}
	
	public boolean canPassWalls() {
		return true;
	}
	
	public boolean canPassBombs() {
		return true;
	}
	
	public void die() {
		if(animation != death) {
			animation = death;
			parent.notifyChildDeath();
			if(child != null)
				child.notifyParentDeath();
		}
		if (animation.finalFrame())
			remove();
	}
	
	public void notifyChildDeath() {
		die();
		parent.notifyChildDeath();
	}
	
	public void notifyParentDeath() {
		die();
		if(child != null)
			child.notifyParentDeath();
	}
public void render3d( GL2 gl, GLU glu ) {
		
	float r=8.5f;
	gl.glPushMatrix();
	switch(state){
	case 0 :
	gl.glColor3d(1, 0, 1);
		break;
	case 1: gl.glColor3d(0, 0.3, 1); 
		break;
	case 2:gl.glColor3d(1, 0.95, 0);
		break;
	case 3:gl.glColor3d(1, 0, 0); 
		break;
	}
	 
	gl.glTranslated(position.x-3, -position.y+5, 0);
	gl.glScaled(1.4,1,1);
	if(animation==right[state]){
		gl.glRotated(-90,0,0,1);
		
	}
	if(animation==left[state]){
		gl.glRotated(90,0,0,1);
		
	}
	if(animation==down[state]){
		gl.glRotated(180,0,0,1);
		
	}
	
	
	gl.glPushMatrix();
	gl.glScaled(1,1,0.2);
	  GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        
        final int slices = 16;
        final int stacks = 16;
       
       
     
	
	gl.glPushMatrix();
	gl.glTranslated(0,-r/2,0);
	gl.glRotated(-90,1,0,0);
	glu.gluCylinder(earth, r, r, r, slices, stacks);
	gl.glPopMatrix();
	gl.glTranslated(0,-5,0);
	   
	glu.gluSphere(earth,r, slices, stacks);
	gl.glTranslated(0,10,0);
	  
	glu.gluSphere(earth,r, slices, stacks);
	gl.glPopMatrix();
	
	
	//alas
	gl.glPushMatrix();
	gl.glColor3d(1,1,1);
	gl.glTranslated(-r*0.9,r/3.5,r/7);
	float size=r/3f;

	 gl.glBegin(GL2.GL_TRIANGLE_FAN);
       gl.glNormal3f(0, 0, 1);
       gl.glVertex3f(-size*1.5f, -size*1.5f, 0);
       gl.glVertex3f(+size*1.8f, size*1.5f, 0);
       gl.glVertex3f(-size*1.5f, size*1.5f, 0);
       gl.glEnd();
      gl.glTranslated(r*1.8,0,0);
      gl.glRotated(180,0,1,0);
       gl.glBegin(GL2.GL_TRIANGLE_FAN);
   
       gl.glNormal3f(0, 0, -1);
       gl.glVertex3f(-size*1.5f, -size*1.5f, 0);
       gl.glVertex3f(+size*1.8f, size*1.5f, 0);
       gl.glVertex3f(-size*1.5f, size*1.5f, 0);
       gl.glEnd();
	
	
	gl.glPopMatrix();
	
	

////ojos	
//	 gl.glColor3d(0,0,0);
//	 gl.glTranslated(-r/2, 0, r/5);
//	 glu.gluDisk(earth, 0, r/8, slices, stacks);
//	 gl.glTranslated(r,0,0);
//	 glu.gluDisk(earth, 0, r/8, slices, stacks);
//	 gl.glColor3d(1,1,1);
//	 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
//	 gl.glTranslated(-r,0,0);
//	 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
//	 //boca
//	 gl.glTranslated(r/2,-r/2,0);
//	 size=r/10;
//	 gl.glTranslated(-r/4,0,0);
//	 gl.glBegin(GL2.GL_TRIANGLE_FAN);
//       gl.glNormal3f(0, 0, 1);
//       gl.glVertex3f(0, -size*1.5f, 0);
//       gl.glVertex3f(+size*1.5f, size*1.5f, 0);
//       gl.glVertex3f(-size*1.5f, size*1.5f, 0);
//       gl.glEnd();
//       gl.glTranslated(r/2,0,0);
//		 gl.glBegin(GL2.GL_TRIANGLE_FAN);
//	       gl.glNormal3f(0, 0, 1);
//	       gl.glVertex3f(0, -size*1.5f, 0);
//	       gl.glVertex3f(+size*1.5f, size*1.5f, 0);
//	       gl.glVertex3f(-size*1.5f, size*1.5f, 0);
//	       gl.glEnd();
	gl.glPopMatrix();
	System.out.println();
	}
	
	
}
