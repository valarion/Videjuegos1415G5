package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.Score;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.map.GenerateObstacles;
import videjouegos1415g5.map.Map;

public class CoinYellow extends Enemy {
	
	private final String ANIMATION = "/enemies/coin_yellow.png"; 
	private final static int w = 16;
	private final static int h = 18;

	public CoinYellow(GenerateObstacles obs, Map map, Bomberman player) {
		super(obs, map, player);

		this.position.width = 12*scale;
		this.position.height = 14*scale;
		this.health = 10;
		this.score = 1000;
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		BufferedImage[] mov = {ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale)};
		BufferedImage[] die = {ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(4*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(5*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(6*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(7*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(8*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(9*w*scale, 0, w*scale, h*scale),
				new Score(score, w, h).getImage()};
		
		this.down = new Animation(mov, 8, Direction.DOWN);
		this.death = new Animation(die, 14, Direction.DOWN);
		
		// Animacion inicial
		animation = down;
		animation.start();

	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics2D g) {
		super.render(g);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(position.x, position.y, w, h);
	}
public void render3d( GL2 gl, GLU glu ) {
		
		float r=8;
gl.glPushMatrix();

gl.glTranslated(position.x-5, -position.y+5, 0);
		gl.glScaled(1.5,1,1);
		
		
		  GLUquadric earth = glu.gluNewQuadric();
	        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
	        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
	        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
	        
	        final int slices = 16;
	        final int stacks = 16;
	        gl.glColor3d(1, 0.25, 0.0);
	       // glu.gluSphere(earth, radius, slices, stacks);
		glu.gluSphere(earth,r, slices, stacks);
		
	
		gl.glTranslated(0.0, -2*r*3/4, 0.0);
		gl.glRotated(-90, 1, 0, 0);
		
		glu.gluCylinder(earth, r/10, r/1.2, r, slices, stacks);
	 glu.gluDeleteQuadric(earth);
		gl.glPopMatrix();
	}
	
	
}