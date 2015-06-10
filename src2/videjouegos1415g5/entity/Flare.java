package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;



public class Flare extends Entity {
	
	public final String topflare = "/bombs/topflare.png"; 
	public final String botflare = "/bombs/botflare.png"; 
	public final String rightflare = "/bombs/rightflare.png"; 
	public final String leftflare = "/bombs/leftflare.png"; 
	public final String verticalflare = "/bombs/verticalflare.png"; 
	public final String horizontalflare = "/bombs/horizontalflare.png"; 
	public final String midflare = "/bombs/midflare.png"; 
	private final static int w = 16;
	private final static int h = 16;
	
	private Set<Entity> out = new HashSet<Entity>();
	
	private String ANIMATION;
	private String FINALANIMATION;
	
	private boolean draw;
	
	//private Animation animation;

	public Flare(Bomb bomb, int xdif, int ydif) {
		//super(obs, map, player);
		
		this.position.x = -(w*scale)/2 + ((int)Math.round(((float)(bomb.position.x+w*scale/2) / (w*scale)))+xdif) * (w*scale);
		this.position.y = -(h*scale)/2 + ((int)Math.round(((float)(bomb.position.y+h*scale/2) / (h*scale)))+ydif) * (h*scale);

		this.position.width = 12*scale;
		this.position.height = 14*scale;
		
		this.sl = new SpriteLoader();	
		
		this.draw = true;
		
		
		if(xdif == 0 && ydif == 0) {
			ANIMATION = midflare;
		}
		else if(xdif == 0) {
			if(ydif > 0)
				FINALANIMATION = botflare;
			else
				FINALANIMATION = topflare;
			if(Math.abs(ydif) == bomb.getPotency()) {
				if(ydif > 0)
					ANIMATION = botflare;
				else
					ANIMATION = topflare;
			}
			else
				ANIMATION = verticalflare;
		}
		else {
			if(xdif > 0)
				FINALANIMATION = rightflare;
			else
				FINALANIMATION = leftflare;
			if(Math.abs(xdif) == bomb.getPotency()) {
				if(xdif > 0)
					ANIMATION = rightflare;
				else
					ANIMATION = leftflare;
			}
			else
				ANIMATION = horizontalflare;
		}
		
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		BufferedImage[] anim;
		if(ANIMATION == midflare) {
			anim = new BufferedImage[]{ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
					ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
					ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(4*w*scale, 0, w*scale, h*scale)};
		}
		else {
			anim = new BufferedImage[]{ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
					ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
					ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
					ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale)};
		}
		
		// Animacion inicial
		animation = new Animation(anim, 3, Direction.DOWN);
		animation.start();

	}

	public void tick() {
		super.tick();
		
		if(animation.finalFrame()) removed = true;
		//else if (!removed) animation.tick();
	}

	public void render(Graphics2D g) {
		if(!removed && draw) {
			BufferedImage f = animation.getSprite();
			g.drawImage(animation.getSprite(), 
					position.x+position.width/2 - (f.getWidth()-2*scale)/2, 
					position.y+position.height/2 - (f.getHeight()-2*scale)/2, null);
		}

	}
	
	public void setOut(Entity e) {
		out.add(e);
	}
	
	public boolean isOut(Entity e) {
		return out.contains(e);
	}
	
	public Rectangle getBounds() {
		if(!removed)
			return new Rectangle(position.x, position.y, w, h);
		else
			return new Rectangle(0, 0, 0, 0);
	}
	
	public void setAsFinal() {
		draw = false;
		ANIMATION = FINALANIMATION;
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		BufferedImage[] anim;
		anim = new BufferedImage[]{ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(3*w*scale, 0, w*scale, h*scale)};

		
		// Animacion inicial
		animation = new Animation(anim, 3, Direction.DOWN);
		animation.start();
	}
	
	public boolean isFinal() {
		return ANIMATION == FINALANIMATION;
	}
	
	public boolean isMid() {
		return ANIMATION == midflare;
	}
	
	public void render3d(GL2 gl, GLU glu){
gl.glPushMatrix();
		
		gl.glTranslated(position.x-5, -position.y+5, 0);
		
		  GLUquadric earth = glu.gluNewQuadric();
	        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
	        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
	        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
	        
	        final int slices = 16;
	        final int stacks = 16;
	        gl.glColor3d(1, 0.2, 0.2);
	       // glu.gluSphere(earth, radius, slices, stacks);
	        gl.glColor3d(0.1,0.0,1);
	        glu.gluDisk(earth, 3, 4, slices, 5);
	        glu.gluDisk(earth, 7, 8, slices, 5);
	        gl.glColor3d(0.1,1,0.0);
	        glu.gluDisk(earth, 5, 6, slices, 5);
	        glu.gluDisk(earth, 9, 10, slices, 5);
		gl.glTranslated(0.0,-0.5,8);
		gl.glColor3d(1, 0.2, 0.2);
	       // glu.gluSphere(earth, radius, slices, stacks);
		//glu.gluSphere(earth,1, slices, stacks);
		glu.gluDeleteQuadric(earth);
		gl.glPopMatrix();
	}
}