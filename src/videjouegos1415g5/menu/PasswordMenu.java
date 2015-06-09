package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import videjouegos1415g5.Game;
import videjouegos1415g5.Main;
import videjouegos1415g5.animation.Animation;
import videjouegos1415g5.gfx.Font;
import videjouegos1415g5.gfx.ScaleImg;
import videjouegos1415g5.gfx.SpriteLoader;
import videjouegos1415g5.gfx.SpriteSheet;

public class PasswordMenu extends Menu {
	
	private static final String cursor = "/menu/bomb_cursor.png";
	private int selected = 0;
	private String title = "Enter password ......";
	private String[] chars = 
		{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S" , "T",
			"U", "V", "W", "X", "Y", "Z", "<", ">", "#/" };
	private String[] password;
	private int index = 0;;
	private Font font1;
	private int x, y;
	private Animation bomb;
	private SpriteLoader sl;
	private SpriteSheet ss;
	
	private Menu menu;
	private Game game;
	
	private String[][] passwords;

	public PasswordMenu(TitleMenu titleMenu, Game game) {
		this.menu = titleMenu;
		this.game = game;
		this.scale = Main.ESCALA;
		this.font1 = new Font(null, false);
		this.password = new String[8];
		
		this.sl = new SpriteLoader();	    
		this.ss = new SpriteSheet(ScaleImg.scale(sl.cargarImagen(cursor), scale));
		
		BufferedImage[] bomb = {ss.obtenerSprite(0, 0, 20*scale, 23*scale), 
				ss.obtenerSprite(20*scale, 0, 20*scale, 23*scale), 
				ss.obtenerSprite(40*scale, 0, 22*scale, 23*scale)};
		this.bomb = new Animation(bomb, 10);
		
		Scanner in = new Scanner(getClass().getResourceAsStream("/maps/definitions/passwords.txt"));
		
		passwords = new String[8][8];
		
		for(int i=0; i<8;i++) {
			for(int j=0; j<8; j++) {
				in.next();
				in.next();
				passwords[i][j] = in.next();
			}
		}
		
		this.bomb.start();
	}
	
	public void tick() {
		if (input.left.clicked) selected--;
		if (input.right.clicked) selected++;
		if (input.up.clicked) selected -= 10;
		if (input.down.clicked) selected += 10;

		int len = chars.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.fire.clicked) {
			
			if (chars[selected].equals(chars[chars.length - 3])) index--; // Caracter <
			else if (chars[selected].equals(chars[chars.length - 2])) index++; // Caracter >
			else if (chars[selected].equals(chars[chars.length - 1])) { // Caracter END
				// Comprobar password
				String pass = String.join("", password);
				int i = 0, j = 0;
				for(i=0; i<8;i++) {
					for(j=0; j<8; j++) {
						if(passwords[i][j].equals(pass))
							System.out.println("Found!");
					}
				}
				i++;
				j++;
				// TODO crear nivel i j
				/*for (int i = 0; i<password.length; i++) {
					System.out.print(password[i]);
				}
				password = new String[password.length];
				index = 0;*/
			}
			else password[index++] = chars[selected]; // Cualquiera de los otros caracteres
			
			if (index < 0) index++;
			if (index >= password.length) index--;
		}
		
		if (input.exit.clicked) {
			bomb.stop();
			game.setMenu(menu);
		}
		bomb.tick();
	}
	
	public void render(Graphics2D g) {
		
		// Color de fondo
		g.setColor(bgColor);
	    g.fillRect(0, 0, game.getWidth(), game.getHeight());
	    
	    // Titulo del menu
	    font1.render(g, title, 
	    		game.getWidth() / 2 - (title.length() / 2)*font1.getTilesize()*scale,
	    		game.getHeight() / 2 - font1.getTilesize()*8*scale);
	    
		this.x = game.getWidth() / 2 - ((19/2)*font1.getTilesize())*scale;
		this.y = game.getHeight() / 2 - font1.getTilesize()*3*scale;
		
	    // Cursor de todas los caracteres
	    g.drawImage(bomb.getSprite(), x + selected%10 * font1.getTilesize()*2*scale - 6*scale, 
	    		y + selected/10 * font1.getTilesize()*2*scale - 10*scale, null);
		// Caracteres
	    for (int i = 0; i < chars.length; i++) {
	    	font1.render(g, chars[i],
	    			x + i%10 * font1.getTilesize()*2*scale, 
	    			y + i/10 * font1.getTilesize()*2*scale);
	    }
	    
	    this.x = game.getWidth() / 2 - password.length/2*font1.getTilesize()*scale;
	    this.y = game.getHeight() - font1.getTilesize()*5*scale;
	    
		for (int i = 0; i < password.length; i++) {
			if (password[i] != null)
				font1.render(g, password[i],  x + i*font1.getTilesize()*scale, y);
			
			// Guiones
			font1.render(g, "-", 
					x + i*font1.getTilesize()*scale, y + font1.getTilesize() * scale);
			// Cursor de la letra seleccionada
			font1.render(g, "=", x+ index*font1.getTilesize()*scale, y + font1.getTilesize() * scale);
			
		}
	}
}
