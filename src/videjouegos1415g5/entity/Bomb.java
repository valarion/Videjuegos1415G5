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
import videjouegos1415g5.sound.Sound;


public class Bomb extends Entity {
	
	private final String ANIMATION = "/bombs/bomb.png"; 
	private final static int w = 16;
	private final static int h = 16;
	
	private Set<Entity> out = new HashSet<Entity>();
	
	private int potency;
	private boolean explode;
	
	//private Animation animation;

	public Bomb(Bomberman player) {
		//super(obs, map, player);
		
		this.position.x = 2-(w*scale)/2 + (int)Math.round(((float)(player.position.x+(float)w*scale/2) / (w*scale))) * (w*scale);
		this.position.y = -(h*scale)/2 + (int)Math.round(((float)(player.position.y+(float)h*scale/2) / (h*scale))) * (h*scale);

		this.position.width = 12*scale;
		this.position.height = 14*scale;
		
		explode = !player.hasRemoteDetonator();
		
		this.potency = player.getPotency();
		
		this.sl = new SpriteLoader();	    
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION), scale));
		
		BufferedImage[] anim = {ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale), 
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(1*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(0*w*scale, 0, w*scale, h*scale),
				ss.obtenerSprite(2*w*scale, 0, w*scale, h*scale)};
		
		// Animacion inicial
		animation = new Animation(anim, 10, Direction.DOWN);
		animation.start();

	}

	public void tick() {
		super.tick();
		
		if(explode && animation.finalFrame()) removed = true;
		if (removed) Sound.bomb.play();

		//else if (!removed) animation.tick();
	}

	public void render(Graphics2D g) {
		if(!removed) {
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

	public int getPotency() {
		return potency;
	}
	
	public void touchedBy(Entity entity) {
		if(entity instanceof Flare) {
			removed = true;
		}
	}
	
	public void render3d(GL2 gl, GLU glu) {
gl.glPushMatrix();
		
		gl.glTranslated(position.x-5, -position.y, 0);
		
		  GLUquadric earth = glu.gluNewQuadric();
	        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
	        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
	        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
	        
	        final int slices = 16;
	        final int stacks = 16;
	        gl.glColor3d(0.2, 0.2, 0.2);
	       // glu.gluSphere(earth, radius, slices, stacks);
		glu.gluSphere(earth,8, slices, stacks);
		gl.glTranslated(0.0,-0.5,8);
		gl.glColor3d(1, 0.2, 0.2);
	       // glu.gluSphere(earth, radius, slices, stacks);
		glu.gluSphere(earth,1, slices, stacks);
		glu.gluDeleteQuadric(earth);
		gl.glPopMatrix();
	}
	
}