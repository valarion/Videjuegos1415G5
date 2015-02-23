package videjouegos1415g5;

import java.awt.Rectangle;

public class GameObject {
	
	protected Rectangle position;
	
	public GameObject(int x, int y, int size) {
		position = new Rectangle(x,y,size,size);
	}
	
	public boolean interstects(GameObject o) {
		return position.intersects(o.position);
	}
}
