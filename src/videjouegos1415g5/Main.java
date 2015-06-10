package videjouegos1415g5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {
	
	public static final int ALTURA = 232;
	public static final int ANCHURA = 256;
	public static int ESCALA = 2;
	public static final String TITULO = "+";
	private static JFrame frame;
	
	public static void main(String[] args) {
		Game3D game = new Game3D(640,480);
	
	
	

		frame = new JFrame(TITULO);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		//frame.pack();
		frame.setSize(640, 480);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		game.requestFocus();
		game.start();
		//frame.pack();
	}
	
	public static void pack() {
		frame.pack();
	}
}
