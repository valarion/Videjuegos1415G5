package videjouegos1415g5.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.Colors;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;
import videjouegos1415g5.sound.MP3Player;
import videjouegos1415g5.sound.Sound;

public class Bomberman extends Mob {

	private final String ANIMATION = "/bomberman.png";
	private final static int w = 24;
	private final static int h = 22;

	private InputHandler input;

	private int score;
	private int lives;
	private int velocity;
	private int max_velocity;
	private int bombs;
	private int max_bombs;
	private int potency;
	private int max_potency;
	
	private boolean canPassWalls, canPassBombs, remoteDetonator;
	
	private int invincible;
	
	private Animation teleport, sparks;

	public Bomberman(InputHandler input) {
		
		this.input = input;
		this.position.x = 25 * scale;
		this.position.y = 45 * scale;
		this.position.width = 14 * scale;
		this.position.height = 11 * scale;

		this.score = 0;
		this.lives = 1;
		this.velocity = 0;
		this.max_velocity = 1;
		this.bombs = 1;
		this.max_bombs = 8;
		this.potency = 1;
		this.max_potency = 8;
		this.canPassWalls = false;
		this.canPassBombs = false;
		this.remoteDetonator = false;
		this.invincible = 0;

		this.sl = new SpriteLoader();
		// Escalamos la secuencia de sprites
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(ANIMATION),
				scale));
		
		

		BufferedImage[] walkingLeft = {
				ss.obtenerSprite(6 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(7 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(8 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] walkingRight = {
				ss.obtenerSprite(3 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(4 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(5 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] walkingUp = {
				ss.obtenerSprite(9 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(10 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(11 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] walkingDown = {
				ss.obtenerSprite(0, 0, w * scale, h * scale),
				ss.obtenerSprite(1 * w * scale, 0, w * scale, h * scale),
				ss.obtenerSprite(2 * w * scale, 0, w * scale, h * scale) };
		BufferedImage[] die = {
				ss.obtenerSprite(0, h * scale, w * scale, h * scale),
				ss.obtenerSprite(1 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(2 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(3 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(4 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(5 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(6 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(7 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(8 * w * scale, h * scale, w * scale, h * scale),
				ss.obtenerSprite(9 * w * scale, h * scale, w * scale, h * scale) };
		
		BufferedImage[] tp = new BufferedImage[h*scale];
		for(int i=0; i<h*scale/4; i++) {
			tp[i*4] = ss.obtenerSprite(6 * w * scale, i*4, w * scale, h * scale-i*4);
			tp[i*4+1] = ss.obtenerSprite(9 * w * scale, i*4, w * scale, h * scale-i*4);
			tp[i*4+2] = ss.obtenerSprite(3 * w * scale, i*4, w * scale, h * scale-i*4);
			tp[i*4+3] = ss.obtenerSprite(0 * w * scale, i*4, w * scale, h * scale-i*4);
		}

		BufferedImage[] sp = new BufferedImage[h * scale];

		for (int i = 0; i < h * scale / 4; i++) {
			sp[i*4] = ss.obtenerSprite(8 * w * scale, h * scale, w * scale, h * scale);
			sp[i*4+1] = ss.obtenerSprite(9 * w * scale, h * scale, w * scale, h * scale);
			sp[i*4+2] = ss.obtenerSprite(10 * w * scale, h * scale, w * scale, h * scale);
			sp[i*4+3] = ss.obtenerSprite(11 * w * scale, h * scale, w * scale, h * scale);
		}

		this.left = new Animation(walkingLeft, 10, Direction.LEFT);
		this.right = new Animation(walkingRight, 10, Direction.RIGHT);
		this.up = new Animation(walkingUp, 10, Direction.UP);
		this.down = new Animation(walkingDown, 10, Direction.DOWN);
		this.death = new Animation(die, 10, Direction.DOWN);
		
		this.teleport = new Animation(tp,4,Direction.DOWN);
		this.sparks = new Animation(sp,15,Direction.DOWN);

		// Animacion inicial
		this.animation = down;
	}

	public void tick() {
		if(isInvincible())
			invincible--;
		if(animation != teleport) {
			if (health <= 0) {
				if (health == 0) Sound.death.play(); // Para que solo se reproduzca una vez
				die();
			} else {
	
				if (input.left.down) {
					position.x -= (scale + velocity);
					animation = left;
					animation.start();
				}
	
				else if (input.right.down) {
					position.x += (scale + velocity);
					animation = right;
					animation.start();
				}
	
				else if (input.up.down) {
					position.y -= (scale + velocity);
					animation = up;
					animation.start();
				}
	
				else if (input.down.down) {
					position.y += (scale + velocity);
					animation = down;
					animation.start();
				}
	
				else {
					animation.stop();
					animation.reset();
				}
			}
			animation.tick();
		}
		else {
			teleport.tick();
			sparks.tick();
		}
	}

	public void render(Graphics2D g) {
		if(animation != teleport) {
			if(invincible / 5 % 2 == 0) {
				// g.fillRect(position.x, position.y, 14 * scale, 11 * scale);
				BufferedImage f = animation.getSprite();
				// g.fillRect(position.x+position.width/2-f.getWidth()/2,
				// position.y+position.height/2-f.getHeight()/2, w*scale, h*scale);
				g.drawImage(animation.getSprite(),
						position.x + position.width / 2 - (f.getWidth() - 1 * scale)
								/ 2, position.y + position.height / 2
								- (f.getHeight() + 11 * scale) / 2, null);
			}
			else {
				BufferedImage f = animation.getSprite();
				g.drawImage(Colors.convertColor(f, Color.WHITE),
						position.x + position.width / 2 - (f.getWidth() - 1 * scale)
								/ 2, position.y + position.height / 2
								- (f.getHeight() + 11 * scale) / 2, null);
			}
		}
		else {
			BufferedImage f = teleport.getSprite();
			g.drawImage(f,
					position.x + position.width / 2 - (f.getWidth() - 1 * scale)
							/ 2, position.y + position.height / 2
							- (f.getHeight() + 11 * scale) / 2 + teleport.getCurrentFrame()/scale, null);
			f = sparks.getSprite();
			g.drawImage(f,
					position.x + position.width / 2 - (f.getWidth() - 1 * scale)
							/ 2, position.y + position.height / 2
							- (f.getHeight() + 11 * scale) / 2, null);
		}

	}

	public void touchedBy(Entity go) {
		if (go instanceof Flare) {
			if(!isInvincible())
				this.hurt(go, 10);
		}
		if(go instanceof Exit) {
			this.position.x = go.position.x;
			this.position.y = go.position.y;
			this.animation = teleport;
			teleport.start();
			sparks.start();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(position.x + 5 * scale, position.y + 10 * scale,
				14 * scale, 11 * scale);
	}

	public void addPowerUp(Entity powerup) {
		// Añadir mejoras del power up
		Sound.powerup.play();
		switch (powerup.getType()) {
		case 0:
			if(potency < max_potency)
				potency += 1;
			break;
		case 1:
			if(bombs < max_bombs)
				bombs += 1;
			break;
		case 2:
			remoteDetonator = true;
			break;
		case 3:
			if (velocity < max_velocity)
				velocity += 1;
			break;
		case 4:
			canPassBombs = true;
			break;
		case 5:
			canPassWalls = true;
			break;
		case 6:
			invincible = 600;
			break;
		case 7:
			lives += 1;
			break;
		case 8:
			break;
		}
		powerup.remove();
	}

	public int getLives() {
		return this.lives;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getPotency() {
		return potency;
	}

	public int getBombs() {
		return bombs;
	}
	
	public boolean isTeleporting() {
		return animation == teleport;
	}
	
	public boolean endLvl() {
		return animation == teleport && animation.finalFrame();
	}
	
	public void reset() {
		if(removed) {
	   		this.canPassBombs = false;
	   		this.canPassWalls = false;
	   		this.remoteDetonator = false;
   		}
		this.position.x = 25 * scale;
		this.position.y = 45 * scale;
		this.removed = false;
		this.health = 10;
   		this.animation = down;
   		this.invincible = 999999990;
	}
	
	public void resetAnim() {
   		this.animation = down;
	}
	
	public boolean canPassWalls() {
		return canPassWalls;
	}
	
	public boolean canPassBombs() {
		return canPassBombs;
	}
	
	public boolean hasRemoteDetonator() {
		return remoteDetonator;
	}
	
	public boolean isInvincible() {
		return invincible > 0;
	}

	public boolean isDying() {
		return animation == death;
	}

	
}
