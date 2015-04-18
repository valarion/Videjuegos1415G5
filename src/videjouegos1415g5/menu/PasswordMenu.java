package videjouegos1415g5.menu;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import videjouegos1415g5.Main;
import videjouegos1415g5.gfx.Font;

public class PasswordMenu extends Menu {
	
	private static final String cursor = "/flecharoja.png";
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
	private BufferedImage bi;
	private Image cu;
	private Menu menu;

	public PasswordMenu(TitleMenu titleMenu) {
		this.menu = titleMenu;
		this.scale = Main.ESCALA;
		this.font1 = new Font(null, false);
		this.password = new String[8];
		
		try {
			bi = ImageIO.read(getClass().getResource(cursor));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cu = bi.getScaledInstance(bi.getWidth() * scale, bi.getHeight() * scale, Image.SCALE_SMOOTH);
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
			
			if (chars[selected].equals(chars[chars.length -3])) index--;
			else if (chars[selected].equals(chars[chars.length - 2])) index++;
			else if (chars[selected].equals(chars[chars.length - 1])) {
				// Comprobar password
				for (int i = 0; i<password.length; i++) {
					System.out.print(password[i]);
				}
				password = new String[password.length];
				index = 0;
			}
			else password[index++] = chars[selected];
			
			if (index < 0) index++;
			if (index >= password.length) index--;
		}
		
		if (input.exit.clicked) game.setMenu(menu);
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
		
	    // Cursor de todas las letras
	    g.drawImage(cu, x + selected%10 * font1.getTilesize()*2*scale, 
	    		y + selected/10 * font1.getTilesize()*2*scale, null);
		// Letras
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
			// Cursos de la letra seleccionada
			font1.render(g, "=", x+ index*font1.getTilesize()*scale, y + font1.getTilesize() * scale);
			
		}
	}
}
