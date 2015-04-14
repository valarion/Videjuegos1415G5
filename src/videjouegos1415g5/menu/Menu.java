package videjouegos1415g5.menu;

import java.awt.Graphics2D;

import videjouegos1415g5.Game;
import videjouegos1415g5.InputHandler;

public class Menu {
	protected Game game;
	protected InputHandler input;
	protected int scale = 2;

	public void init(Game game, InputHandler input) {
		this.game = game;
		this.input = input;
		this.scale = 2;
	}
	
	public void tick() {
	}
	
	public void render(Graphics2D g) {
	}

}
