import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Bullet extends Actor{
	//fields
	private BufferedImage img;
	//constructor
	public Bullet(int x, int y, int w, int h, int xv, int yv, Color c, BufferedImage i) {
		super(x,y,w,h,xv,yv,c);
		img = i;
	}
	//methods
	public void move(){
		getHitBox().translate((int) getXVel(),(int) getYVel());
	}

	public void drawActor(Graphics2D win){
		win.drawImage(img, getXPos(), getYPos(), null);
	}

}