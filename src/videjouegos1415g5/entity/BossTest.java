package videjouegos1415g5.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import videjouegos1415g5.InputHandler;


public class BossTest extends Mob {

    private final int DOT_SIZE = 24;
    private final int DOTS = 6;

    private final int x[] = new int[DOTS*DOT_SIZE+1];
    private final int y[] = new int[DOTS*DOT_SIZE+1];
    
    private int speed = 2;
    private int xs, ys;
    
    private InputHandler input;


    public BossTest(InputHandler input) {
    	this.input = input;
        initGame();
    }

    private void initGame() {

        for (int z = 0; z < DOTS*DOT_SIZE; z++) {
            x[z] = 50 - z;
            y[z] = 50;
        }

    }
    
    public void tick() {

        if (input.left.clicked) {
        	move();
            x[0] -= speed;
            xs = -speed + 4; ys = 0;
        }

        if (input.right.clicked) {
        	move();
            x[0] += speed;
            xs = speed - 4; ys = 0;
        }

        if (input.up.clicked) {
        	move();
            y[0] -= speed;    
            xs = 0; ys = -speed;
        }

        if (input.down.clicked) {
        	move();
            y[0] += speed;
            xs = 0; ys = speed;
        }
    }
    
    private void move() {
        for (int z = DOT_SIZE*DOTS; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
            //System.out.println(xs + " " + ys);
        }
    }

    public void render(Graphics2D g) {
        doDrawing(g);
    }
    
    private void doDrawing(Graphics2D g) {
    
		for (int z = 0; z < DOTS; z++) {
			if (z == 0) {
				g.setColor(Color.ORANGE);
				g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
			} else {
				g.setColor(Color.RED);
				//if (x[z]/z > DOT_SIZE*speed || y[z]/z > DOT_SIZE)
					//g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);	
				g.fillRect(x[z*DOT_SIZE], y[z*DOT_SIZE], DOT_SIZE, DOT_SIZE);				
			}	
		}
    }
}