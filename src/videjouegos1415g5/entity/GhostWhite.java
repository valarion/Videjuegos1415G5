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

public class GhostWhite extends Enemy {

	private final String ANIMATION = "/enemies/ghost_white.png";
	private final static int w = 16;
	private final static int h = 18;

	public GhostWhite(GenerateObstacles obs, Map map, Bomberman player) {
		super(obs, map, player);

		this.position.width = 12 * scale;
		this.position.height = 14 * scale;
		this.health = 10;
		this.score = 400;

		this.sl = new SpriteLoader();
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION),
				scale));

		BufferedImage[] movDown = {
				ss.obtenerSprite(9 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(10 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(11 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(10 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] movUp = {
				ss.obtenerSprite(3 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(4 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(5 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(4 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] movLeft = {
				ss.obtenerSprite(0 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(1 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(2 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(0 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] movRight = {
				ss.obtenerSprite(6 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(7 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(8 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(7 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] die = {
				ss.obtenerSprite(12 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(13 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(14 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(15 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(16 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(17 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(18 * w * scale, 0, w * scale, h * scale),
				new Score(score, w, h).getImage() };

		this.down = new Animation(movDown, 8, Direction.DOWN);
		this.up = new Animation(movUp, 8, Direction.UP);
		this.left = new Animation(movLeft, 8, Direction.LEFT);
		this.right = new Animation(movRight, 8, Direction.RIGHT);
		this.death = new Animation(die, 14, Direction.DOWN);

		// Animacion inicial
		animation = down;
		animation.start();

	}

	public void tick() {
		super.tick();
		if (health > 0) {
			if (xdir > 0) {
				animation = right;
				animation.start();
			} else if (xdir < 0) {
				animation = left;
				animation.start();
			} else if (ydir < 0) {
				animation = up;
				animation.start();
			} else if (ydir > 0) {
				animation = down;
				animation.start();
			}
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
public void render3d( GL2 gl, GLU glu ) {
		
	float r=8.5f;
	gl.glPushMatrix();
	 
	gl.glTranslated(position.x-3, -position.y+5, 0);
	gl.glScaled(1.4,1,1);
	gl.glPushMatrix();
	gl.glScaled(1,1,0.2);
	  GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        
        final int slices = 16;
        final int stacks = 16;
        gl.glColor3d(1, 1, 1);
       
     
	glu.gluSphere(earth,r, slices, stacks);
	gl.glPopMatrix();
	gl.glPushMatrix();
	gl.glRotated(90,1,0,0);
	gl.glScaled(1,0.2,1);
	 glu.gluCylinder(earth, r, r, 2*r, slices, stacks);
	 gl.glPopMatrix();
	gl.glPushMatrix();
	gl.glColor3d(0,0,1);
	gl.glTranslated(r/10,r/1.4,0);
	gl.glRotated(-90,1,0,0);
	gl.glRotated(10,0,1,0);
	glu.gluCylinder(earth, r/2, 0, r, slices, stacks);
	gl.glPopMatrix();
	 gl.glColor3d(0,0,0);
	 gl.glTranslated(-r/2, 0, r/5);
	 glu.gluDisk(earth, 0, r/8, slices, stacks);
	 gl.glTranslated(r,0,0);
	 glu.gluDisk(earth, 0, r/8, slices, stacks);
	 gl.glColor3d(1,1,0.4);
	 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
	 gl.glTranslated(-r,0,0);
	 glu.gluDisk(earth, r/8.2, r/3.8, slices, stacks);
	 
	 gl.glTranslated(r/2,-r/2,0);
	 gl.glScaled(3.5,0.5,0);
	 gl.glColor3d(1,0.6,0);
	 glu.gluDisk(earth, r/8.2, r/4, slices, stacks);
	 gl.glColor3d(1,0.4,0.1);
	 glu.gluDisk(earth, 0, r/8, slices, stacks);
	gl.glPopMatrix();
	}
	
	
}