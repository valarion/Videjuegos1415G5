package videjouegos1415g5.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.GameObject;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteSheet;

public class Obstacle extends GameObject {

	private boolean solid;
	private Animation a1, a2, a3;
	private int type;

	public Obstacle(int x, int y, int size, Animation animation,
			SpriteSheet ss, int scale, boolean solid, int type) {
			super(x - 8*scale, y + 24*scale, size, size, animation);
		//this.position.x = this.position.x - 8 * scale;
		//this.position.y = this.position.y + 24 * scale;
		this.solid = solid;
		this.type=type;
		ss = new SpriteSheet(ScaleImg.scale(ss.getImage(), scale));
		
		BufferedImage[] obstacle = {ss.obtenerSprite(2*size, 0, size, size)};
		a1 = new Animation(obstacle, 1, Direction.DOWN);
		
		BufferedImage[] explosion = {ss.obtenerSprite(size*7, size, size, size),
				ss.obtenerSprite(size*8, size, size, size),
				ss.obtenerSprite(size*9, size, size, size),
				ss.obtenerSprite(size*10, size, size, size),
				ss.obtenerSprite(size*11, size, size, size),
				ss.obtenerSprite(size*12, size, size, size),
				ss.obtenerSprite(size*13, size, size, size)};
		
		a2 = new Animation(explosion, 6, Direction.DOWN);
		
		BufferedImage[] blink = {ss.obtenerSprite(2*size, 0, size, size),
				ss.obtenerSprite(size*7, size, size, size)};
		
		a3 = new Animation(blink, 20, Direction.DOWN);

		this.animation = a1;
	}

//	public boolean intersects(GameObject o) {
//		return position.intersects(o.position);
//	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void die() {
		if (!removed) {
			if (!(animation == a2)) {
				animation = a2;
				animation.start();
			}
		}
	}

	public void update(long ms) {
		//animation.update(ms);
	}
	
	public void tick() {
		animation.tick();
		if (animation.equals(a2) && animation.finalFrame()) {
			remove();
		}
	}

	public void draw(Graphics2D g) {
		if (solid) {
			if(animation == a3)
				g.drawImage(a1.getSprite(), position.x, position.y, null);
			g.drawImage(animation.getSprite(), position.x, position.y, null);
//			g.setColor(Color.BLACK);
//		 	g.fillRect(position.x, position.y, position.width, position.height);
		}
//		else {
//			g.setColor(Color.ORANGE);
//			g.fillRect(position.x, position.y, position.width, position.height);
//		}
	}
	
	public void blink() {
		if(animation == a1) {
			animation = a3;
			a3.start();
		}
	}
	
	public void draw3d(GL2 gl, GLU glu){
	if(animation!=a2){
		  final int slices = 16;
	        final int stacks = 16;
	  	  GLUquadric earth = glu.gluNewQuadric();
		switch(type){
			case 1: 
		gl.glPushMatrix();
		
		gl.glTranslated(position.x, -position.y, 0);
		
		gl.glColor3d(0.54, 0.26, 0.07);
		if(animation==a3)
			gl.glColor3d(0,1,1);
	        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
	        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
	        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
	        
	      
	     
	       // glu.gluSphere(earth, radius, slices, stacks);
		//glu.gluSphere(earth,14, slices, stacks);
	       gl.glScaled(29, 29, 29);
	        cubo(gl);
		gl.glPopMatrix();
		break;
			case 2:
				gl.glPushMatrix();
				
				gl.glTranslated(position.x, -position.y, 0);
			
			
			        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
			        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
			        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
			        
			        gl.glColor3d(0.8,0.8,0.8);
			        
			       // glu.gluSphere(earth, radius, slices, stacks);
				//glu.gluSphere(earth,14, slices, stacks);
			        gl.glScaled(29, 29, 29);
			        cubo(gl);
			        gl.glPopMatrix();
				break;
				
			case 3: 
				gl.glPushMatrix();
				
				gl.glTranslated(position.x, -position.y, 0);
			
			
			        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
			        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
			        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
			      
			        gl.glColor3d(1, 0.0, 0.0);
			       // glu.gluSphere(earth, radius, slices, stacks);
				//glu.gluSphere(earth,14, slices, stacks);
			        gl.glScaled(32, 32, 31);
			        cubo(gl);
			        gl.glPopMatrix();
				break;
			case 4:
				gl.glPushMatrix();
				
				gl.glTranslated(position.x, -position.y, 0);
			
			
			        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
			        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
			        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
			        
			        gl.glColor3d(0.8,1.0,1.0);
			        
			       // glu.gluSphere(earth, radius, slices, stacks);
				//glu.gluSphere(earth,14, slices, stacks);
			        gl.glScaled(29, 29, 29);
			        cubo(gl);
			        gl.glPopMatrix();
				break;
				
		
		}
		}
	}
	
	static void cubo(GL2 gl)
    {gl.glPushMatrix();
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
