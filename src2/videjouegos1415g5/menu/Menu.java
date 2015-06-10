package videjouegos1415g5.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import videjouegos1415g5.Game3D;
import videjouegos1415g5.InputHandler;
import videjouegos1415g5.Main;

public class Menu {
	protected Game3D game;
	protected InputHandler input;
	protected int scale;
	protected Color bgColor;

	public void init(Game3D game, InputHandler input) {
		this.game = game;
		this.input = input;
		this.scale = Main.ESCALA;
		this.bgColor = new Color(73, 102, 192);
	}
	
	public void tick() {
	}
	
	public void render(Graphics2D g) {
	}

public void render3D(GL2 gl, GLU glu) {
	}

	private void pintarletra(GL2 gl, GLU glu,float tamaño, char letra){
		int[] vector;
		
		switch (letra){
		case 'A': 	int[] A={1,1,1,
				1,0,1,
				1,1,1,
				1,0,1};
		vector=A;
			break;
		case 'B':	int[] B={	1,1,1,
					1,1,1,
					1,0,1,
					1,1,1};
				vector=B;
				break;

		case 'C':	int[] C={	1,1,1,
					1,0,0,
					1,0,0,
					1,1,1};
					vector=C;
					break;
					
		case 'D': int[] D={	1,1,0,
					1,1,1,
					1,0,1,
					1,1,1};
					vector=D;
					break;

		case 'E': int[] E={	1,1,1,
					1,0,0,
					1,1,1,
					1,1,1};
					vector=E;
					break;

		case 'F': int[] F={	1,1,1,
					1,0,0,
					1,1,1,
					1,0,0};
					vector=F;
					break;

		case 'G': int[] G={	1,1,1,
					1,0,0,
					1,0,1,
					1,1,1};
					vector=G;
					break;

		case 'H': int[] H={	1,0,1,
					1,0,1,
					1,1,1,
					1,0,1};
					vector=H;
					System.out.println("pintando h");
					break;
					
		case 'I': int[] I={	1,1,1,
					0,1,0,
					0,1,0,
					1,1,1};
					vector=I;
					break;

		case 'J': int[] J={	0,1,1,
					0,0,1,
					1,0,1,
					1,1,1};
					vector=J;
					break;

		case 'K': int[] K={	1,0,1,
					1,1,1,
					1,1,0,
					1,0,1};
		vector=K;
		break;

		case 'L': int[] L={	1,0,0,
							1,0,0,
							1,0,0,
							1,1,1};
							vector=L;
							break;

		case 'M': int[] M={	1,1,1,
				1,1,1,
				1,0,1,
				1,0,1};
				vector=M;
				break;	
				
		case 'N': int[] N={	1,1,1,
				1,1,1,
				1,1,1,
				1,0,1};
				vector=N;
				break;
				
		case 'O': int[] O={	1,1,1,
				1,0,1,
				1,0,1,
				1,1,1};
				vector=O;
				break;
		case 'P': int[] P={	1,1,1,
				1,0,1,
				1,1,1,
				1,0,0};
				vector=P;
				break;
		
		case 'Q': int[] Q={	1,1,0,
				1,1,0,
				1,1,0,
				1,1,1};
				vector=Q;
				break;
				
		case 'R': int[] R={	1,1,1,
				1,0,1,
				1,1,0,
				1,0,1};
				vector=R;
				break;
				
		case 'S': int[] S={	1,1,1,
				1,1,0,
				0,1,1,
				1,1,1};
				vector=S;
				break;
		case 'T': int[] T={	1,1,1,
				1,1,1,
				0,1,0,
				0,1,0};
				vector=T;
				break;
				
		case 'U': int[] U={	1,0,1,
				1,0,1,
				1,0,1,
				1,1,1};
				vector=U;
				break;
		case 'V': int[] V={	1,0,1,
				1,0,1,
				1,1,1,
				0,1,0};
				vector=V;
				break;
		case 'W': int[] W={	1,0,1,
				1,0,1,
				1,1,1,
				1,1,1};
				vector=W;
				break;
				
		case 'X': int[] X={	1,0,1,
				0,1,0,
				0,1,0,
				1,0,1};
				vector=X;
				break;
				
		case 'Y': int[] Y={	1,0,1,
				1,1,1,
				0,1,0,
				0,1,0};
				vector=Y;
				break;
		case 'Z': int[] Z={	1,1,1,
				0,1,1,
				1,1,0,
				1,1,1};
				vector=Z;
				break;
		case ' ': int[] espacio={0,0,0,
				0,0,0,
				0,0,0,
				0,0,0};
		vector=espacio;
		break;
	
				
		case '1': int[] uno={0,1,0,0,1,0,0,1,0,0,1,0};
		vector=uno;
		break;
		case '2': int[] dos={1,1,1,0,0,1,0,1,0,1,1,1};
		vector=dos;
		break;
		case '3': int[] tres={1,1,1,0,0,1,0,1,1,1,1,1};
		vector=tres;
		break;
		
		case '4': int[] cuatro={1,0,1,1,0,1,0,1,1,0,0,1};
		vector=cuatro;
		break;
		case '5': int[] cinco={1,1,1,1,0,0,0,1,1,1,1,1};
		vector=cinco;
		break;
		case '6':
				int[] seis={1,0,0,1,1,1,1,0,1,1,1,1};
				vector =seis;
				break;
		case '7': int[] siete={1,1,1,0,0,1,0,1,0,1,0,0};
		vector=siete;
		break;
		case '8': int[] ocho={1,1,1,1,0,1,1,1,1,1,1,1};
		vector=ocho;
		break;
		case '9': int[]  nueve={1,1,1,1,0,1,1,1,1,0,0,1};
		vector=nueve;
		break;
		case '0': int[] cero={1,1,1,1,0,1,1,0,1,1,1,1};
		vector=cero;
		break;
		case '>': int[] menor={0,0,0,
				0,1,0,
				0,0,1,
				0,1,0};
		vector=menor;
		break;
		case '<': int[] mayor={0,0,0,
				0,1,0,
				1,0,0,
				0,1,0};
		vector=mayor;
		break;
		case '#': int[] hash={0,0,0,
				0,0,0,
				1,1,0,
				1,1,0};
		vector=hash;
		break;
		case '?': int[] interrogacion={
				1,1,1,
				0,0,1,
				0,1,0,
				0,1,0};
		vector=interrogacion;
		break;
				
		case '-': int[] guion={
				0,0,0,
				1,1,1,
				1,1,1,
				0,0,0};
		vector=guion;
		break;
		
	case '.': int[] punto={
			0,0,0,
			0,0,0,
			0,0,0,
			0,1,0};
	vector=punto;
	break;
	
		
				
		default: int[] todo={	1,1,1,
				1,1,1,
				1,1,1,
				1,1,1};
				vector=todo;
				break;	
				
		}
		gl.glPushMatrix();
		 float[] ambiental= {0.3f, 0.5f, 1f};
//		gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_AMBIENT,ambiental,0);
//			gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,ambiental,0);
			 
		        GLUquadric earth = glu.gluNewQuadric();
		        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
		        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		        final float radius = tamaño/2;
		        final int slices = 16;
		        final int stacks = 16;
		       // glu.gluSphere(earth, radius, slices, stacks);
		     
		
		if (vector[0]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[1]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[2]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		gl.glTranslatef(-3*tamaño,-tamaño,0);

		if (vector[3]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[4]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[5]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		gl.glTranslatef(-3*tamaño,-tamaño,0);

		if (vector[6]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[7]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[8]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		gl.glTranslatef(-3*tamaño,-tamaño,0);

		if (vector[9]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[10]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		if (vector[11]==1)
			glu.gluSphere(earth, radius, slices, stacks);
		gl.glTranslatef(tamaño,0,0);
		  glu.gluDeleteQuadric(earth);
		  gl.glPopMatrix();
		
	}

public void pintarfrase(GL2 gl, GLU glu,float tamaño, String frase){
	gl.glPushMatrix();
	
	for (int i=0; i<frase.length();i++){
	
		pintarletra(gl,glu,tamaño,frase.charAt(i));
		gl.glTranslated(tamaño*3+tamaño/2, 0, 0);
	}
	gl.glPopMatrix();
}
public void cursor(GL2 gl, GLU glu, float tamaño ){
	gl.glPushMatrix();
	gl.glTranslated(0, -tamaño*1.7, 0);
	 float[] ambiental= {0.99f, 0.2f, 0.2f};
		gl.glColor3f(0.99f, 0.2f, 0.2f);
		 
	  
       gl.glBegin(GL2.GL_TRIANGLE_FAN);
       gl.glNormal3f(0, 0, 1);
       gl.glVertex3f(-tamaño*1.5f, -tamaño*1.5f, 0);
       gl.glVertex3f(+tamaño*1.5f, 0, 0);
       gl.glVertex3f(-tamaño*1.5f, tamaño*1.5f, 0);
       gl.glEnd();
       gl.glPopMatrix();
	
}
public void panel(GL2 gl, GLU glu, float xtam, float ytam){
gl.glPushMatrix();
gl.glScaled(xtam, ytam, 0.0);
gl.glBegin(GL2.GL_POLYGON);/* f4: top */
gl.glNormal3f(0, 0, 1);
gl.glNormal3f(0.0f,0.0f,0.0f);
gl.glVertex3f(1.0f,1.0f,0.0f);
gl.glVertex3f(1.0f,0.0f,0.0f);
gl.glVertex3f(0.0f,0.0f,0.0f);
gl.glVertex3f(0.0f,1.0f,0.0f);
gl.glEnd();
gl.glPopMatrix();
}
	
}
