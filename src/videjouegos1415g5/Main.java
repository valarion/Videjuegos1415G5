package videjouegos1415g5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {
	
	public static final int ALTURA = 446;
	public static final int ANCHURA = 544;
	private static final int ESCALA = 1;
	public static final String TITULO = "+";
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setMinimumSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));
		game.setMaximumSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));
		game.setPreferredSize(new Dimension(ANCHURA * ESCALA, ALTURA * ESCALA));

		JFrame frame = new JFrame(TITULO);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.run();
	}
}
