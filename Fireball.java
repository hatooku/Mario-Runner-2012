import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
public class Fireball extends Actor {
	
	private int count;
	private int speed;
	BufferedImage img;
	public Fireball(int x, int y, int w, int h, int xv, int yv, Color c, BufferedImage b) {
		super(x,y,w,h,xv,yv,c);
		img = b;
		speed = 4;
		count = 0;
	}

	public void move() {
		if(count >= 8){
			getHitBox().translate((int)getXVel(),0);
		}
	}

	public void draw(Graphics2D win){
		int pos = count / speed;
		BufferedImage spriteImg = img.getSubimage(0 + getWidth()*pos,0,getWidth(),getHeight());
		win.drawImage(spriteImg, this.getXPos(), this.getYPos(), null);
		if(count < 5 * speed){
			count++;
		}
	}
}