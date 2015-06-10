package videjouegos1415g5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	
	public KeyInput(Game3D game) {
		game.addKeyListener(this);
	}

	public void keyPressed(KeyEvent arg0) {
//		int keyCode = arg0.getKeyCode();
//		if (keyCode == KeyEvent.VK_W) Game.y -= 5;
//		if (keyCode == KeyEvent.VK_S) Game.y += 5;
//		if (keyCode == KeyEvent.VK_A) Game.x -= 5;
//		if (keyCode == KeyEvent.VK_D) Game.x += 5;
//		if (keyCode == KeyEvent.VK_UP) Game.y -= 5;
//		if (keyCode == KeyEvent.VK_DOWN) Game.y += 5;
//		if (keyCode == KeyEvent.VK_LEFT) Game.x -= 5;
//		if (keyCode == KeyEvent.VK_RIGHT) Game.x += 5;		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
