import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
public class Actor {
	private double xvel, yvel;
	private Rectangle hitBox;
	private Color c1;

	public Actor(int x, int y, int w, int h, double xv, double yv, Color c){
		xvel = xv;
		yvel = yv;
		c1 = c;
		hitBox = new Rectangle(x, y, w, h);
	}

	public void drawActor(Graphics2D win){
		win.setColor(c1);
		win.fill(hitBox);

	}

	public double getYVel(){
		return yvel;
	}

	public double getXVel(){
		return xvel;
	}
	public void setXVel(double value) {
		xvel = value;
	}

	public void setYVel(double value) {
		yvel = value;
	}


	public int getXPos() {
		return (int) hitBox.getX();
	}
	public int getYPos() {
		return (int) hitBox.getY();
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public Color getColor(){
		return c1;
	}
	public void setColor(Color c){
		c1 = c;
	}
	
	public int getWidth(){
		return (int) hitBox.getWidth();
	}
	public int getHeight(){
		return (int) hitBox.getHeight();
	}
}