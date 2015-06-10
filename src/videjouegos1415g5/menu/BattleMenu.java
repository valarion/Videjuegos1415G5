package videjouegos1415g5.menu;

import java.awt.Graphics2D;

public class BattleMenu extends Menu {
	
	private TitleMenu menu;

	public BattleMenu(TitleMenu menu) {
		this.menu = menu;
	}
	
	public void tick() {
		if (input.exit.clicked) {
			game.setMenu(menu);
		}
	}
	
	public void render (Graphics2D g) {
		
	}

}
