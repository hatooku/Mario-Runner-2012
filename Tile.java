import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile extends Actor{
	private BufferedImage[] imgs;
	private int length;
	
	public Tile(int x, int y, int l, double xv, double yv, Color c, BufferedImage leftImg, BufferedImage midImg, BufferedImage rightImg){
		super(x,y,37*2 + 80*l,160,xv,yv,c);
		length = l;
		imgs = new BufferedImage[3];
		imgs[0] = leftImg;
		imgs[1] = midImg;
		imgs[2] = rightImg;
	}
	
	public void move(){
		getHitBox().translate((int) getXVel(),(int) getYVel());
	}
	
	public void drawTile(Graphics2D win){
		win.drawImage(imgs[0], this.getXPos(), this.getYPos(), null);
		for(int i = 0; i < length; i++){
			win.drawImage(imgs[1], this.getXPos() + 37 + 80 * i, this.getYPos(), null);
		}
		win.drawImage(imgs[2], this.getXPos() + 37 + 80 * length, this.getYPos(), null);
	}
	
	public boolean checkForCollision(Actor a){
		int aX = a.getXPos();
		int aY = a.getYPos();
		int aWidth = (int) a.getHitBox().getWidth();
		int aHeight = (int) a.getHitBox().getHeight();
		if(aY+aHeight <= getHitBox().getY()+28 && aY+aHeight > getHitBox().getY()){
			if(aX <= getHitBox().getX() + getHitBox().getWidth() && aX + aWidth >= getHitBox().getX()){
				return true;
			}
		}
		return false;
	}
}
