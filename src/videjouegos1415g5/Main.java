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
		Game game = new Game();
		game.setMinimumSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));
		game.setMaximumSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));
		game.setPreferredSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));

		frame = new JFrame(TITULO);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public static void pack() {
		frame.pack();
	}
}
