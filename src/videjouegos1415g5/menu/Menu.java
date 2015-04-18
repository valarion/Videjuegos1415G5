package videjouegos1415g5.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import videjouegos1415g5.Game;
import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;

public class Menu {
	protected Game game;
	protected InputHandler input;
	protected int scale;
	protected Color bgColor;

	public void init(Game game, InputHandler input) {
		this.game = game;
		this.input = input;
		this.scale = Main.ESCALA;
		this.bgColor = new Color(73, 102, 192);
	}
	
	public void tick() {
	}
	
	public void render(Graphics2D g) {
	}

}
