package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.Obstacle;

public class PowerUps extends Entity {
	
	private final String POWERUPS = "/powerups.png"; 
	private final static int w = 16;
	private final static int h = 16;
		
	private int type;
	
	public PowerUps(int type, ArrayList<Obstacle> obs) {
		
		this.type = type;
		this.position.width = w;
		this.position.height = h;
		
		this.sl = new SpriteLoader();	    
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(POWERUPS), scale));
		
		BufferedImage[] powerup = new BufferedImage[]{ss.obtenerSprite(type*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(type*w*scale, h*scale, w*scale, h*scale)};
		if (type != -1) while (!located) findStartPos(obs);
		this.animation = new Animation(powerup, 10, Direction.DOWN);
		this.animation.start();
		
	}
	
	public void tick() {
		this.animation.tick();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), position.x, position.y, null);
//		g.setColor(Color.ORANGE);
//		g.fillRect(position.x, position.y, w*scale, h*scale);

	}
	
	public int getType() {
		return type;
	}
	
	private boolean findStartPos(ArrayList<Obstacle> obs) {
		for (int i = 0; i < obs.size(); i++) {
			if (obs.get(i).isSolid() && random.nextDouble() >= 0.95) {
				this.position.x = obs.get(i).position.x;
				this.position.y = obs.get(i).position.y;
				located = true;
				break;
			}
		}
		return true;
		
	}

	public void render3d( GL2 gl, GLU glu ) {
		float r=8;
		int slices = 16;
        int stacks = 16;
        GLUquadric earth = glu.gluNewQuadric();
        gl.glPushMatrix();
      
		
		switch(type){
		
		case 0:// - Fire         = it will make your explosion range longer
			gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			 
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		       
		        gl.glColor3d(1, 0.2, 0.2);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluDisk(earth, 6, 8, slices, 5);
			gl.glTranslated(0.0,-0.5,8);
			gl.glColor3d(1, 0.2, 0.2);
		       // glu.gluSphere(earth, radius, slices, stacks);
			//glu.gluSphere(earth,1, slices, stacks);
			glu.gluDeleteQuadric(earth);
			gl.glPopMatrix();
			break;
			
		case 1: //- Bomb         = it will add your bomb by 1
			gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		      
		        gl.glColor3d(0.2, 0.2, 0.5);
		       
			glu.gluSphere(earth,7, slices, stacks);
			gl.glTranslated(0.0,-0.5,8);
			gl.glColor3d(1, 0.2, 0.2);
		      
			glu.gluSphere(earth,1, slices, stacks);
			glu.gluDeleteQuadric(earth);
			gl.glPopMatrix();
			break;
		case	2:// detonator-	gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		    
		        gl.glColor3d(0.2, 0.2,0.2);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluSphere(earth,8, slices, stacks);
		
			gl.glPopMatrix();
		
			break;
		case	3: //skateboard- Detonator   
			gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		    
		        gl.glColor3d(0.0, 0.0,1);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluSphere(earth,8, slices, stacks);
		
			gl.glPopMatrix();
			break;
		case	5: //- Wall-Through = it will make you can accross the wall
	gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		    
		        gl.glColor3d(0.8,0.6,0.3);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluSphere(earth,8, slices, stacks);
		
			gl.glPopMatrix();
			break;
		case	4: //- Bomb-Through = it will make you can accross the bomb
	gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		    
		        gl.glColor3d(0.0, 0.0,1);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluSphere(earth,8, slices, stacks);
		
			gl.glPopMatrix();
			break;
		case	7: //- Dyna Blaster = it will add your life by 1
			gl.glPushMatrix();
			
			gl.glTranslated(position.x-3, -position.y+5, 0);
			gl.glScaled(1.4,1,1);
			gl.glPushMatrix();
		
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		       
		        gl.glColor3d(1, 1, 1);
		       
		     
			glu.gluSphere(earth,r, slices, stacks);
			 gl.glPushMatrix();
		        gl.glTranslated(0,0,r);
		        gl.glColor3d(1, 0.8, 0.7);
		        glu.gluSphere(earth,r/1.5, slices, stacks);
		        gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslated(0,r-r/10.0, 0);
			gl.glRotated(-90, 1, 0, 0);
			glu.gluCylinder(earth, r/5, r/5, r/1.8, slices, stacks);
			gl.glPopMatrix();
			gl.glTranslated(0, 2*r-r/4.0, 0);
			
			gl.glColor3d(1, 0.2, 0.2);
			glu.gluSphere(earth,r/2.5, slices, stacks);
		 gl.glPopMatrix();
		 gl.glColor3d(0.2, 0.2, 0.2);
		 gl.glTranslated(-r/3.2,0, 1.5*r);
		 glu.gluSphere(earth,r/6.0, slices, stacks);
		 gl.glTranslated(r*2/3.2,0, 0);
		 glu.gluSphere(earth,r/6.0, slices, stacks);
		 glu.gluDeleteQuadric(earth);
			gl.glPopMatrix();
			break;
		case	6: //- Invincible   = it will make you invincible temporarily
	gl.glPushMatrix();
			
			gl.glTranslated(position.x-5, -position.y+5, 0);
			
			  
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        
		    
		        gl.glColor3d(1,1,0.0);
		       // glu.gluSphere(earth, radius, slices, stacks);
			glu.gluSphere(earth,8, slices, stacks);
		
			gl.glPopMatrix();
			break;
		}
		gl.glPopMatrix();
	}


	
}
