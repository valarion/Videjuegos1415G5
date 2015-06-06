package videjouegos1415g5.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import videjouegos1415g5.InputHandler;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.animation.Animation.Direction;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

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
	private int potency;

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
		this.potency = 1;

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

		this.left = new Animation(walkingLeft, 10, Direction.LEFT);
		this.right = new Animation(walkingRight, 10, Direction.RIGHT);
		this.up = new Animation(walkingUp, 10, Direction.UP);
		this.down = new Animation(walkingDown, 10, Direction.DOWN);
		this.death = new Animation(die, 10, Direction.DOWN);

		// Animacion inicial
		this.animation = down;
	}

	public void tick() {
		if (health <= 0) {
			animation = death;
			if (!animation.finalFrame()) animation.start();
			else removed = true;
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

	public void render(Graphics2D g) {
		//g.fillRect(position.x, position.y, 14 * scale, 11 * scale);
		BufferedImage f = animation.getSprite();
		// g.fillRect(position.x+position.width/2-f.getWidth()/2,
		// position.y+position.height/2-f.getHeight()/2, w*scale, h*scale);
		g.drawImage(animation.getSprite(),
				position.x + position.width / 2 - (f.getWidth() - 1 * scale)
						/ 2, position.y + position.height / 2
						- (f.getHeight() + 11 * scale) / 2, null);

	}

	protected void die() {
		super.die();
		animation = death;
		animation.start();
		// Sound.playerDeath.play();
	}

	public void touchedBy(Entity go) {
		// if (!(go instanceof Bomberman)) {
		// go.touchedBy(this);
		// }
		if (go instanceof Balloon) {
			go.hurt(this, 10); // PARA PROBAR
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(position.x + 5 * scale, position.y + 10 * scale,
				14 * scale, 11 * scale);
	}

	public void addPowerUp(Entity powerup) {
		// Añadir mejoras del power up
		System.out.println("PowerUp");
		switch (powerup.getType()) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			lives += 1;
			break;
		case 3:
			if (velocity < max_velocity) velocity += 1;
			System.out.println(velocity);
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
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
}
