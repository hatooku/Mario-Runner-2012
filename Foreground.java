import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Foreground {
	private ArrayList<Tile> tiles;
	private double dx;
	private BufferedImage left,mid,right;
	private final double origDX;

	public Foreground(double DX, BufferedImage leftEdge, BufferedImage middle, BufferedImage rightEdge){
		tiles = new ArrayList<Tile>();
		dx = DX;
		origDX = DX;
		left = leftEdge;
		mid = middle;
		right = rightEdge;
	}

	public void init(){
		addGroundPieces(9,0,482);
	}

	public void move(){
		for(int i = 0; i < tiles.size(); i++){
			tiles.get(i).move();
			if(tiles.get(i).getXPos() < -1* tiles.get(i).getWidth()){
				tiles.remove(i);
				i--;
			}
		}
		if (tiles.get(tiles.size()-1).getXPos() + tiles.get(tiles.size()-1).getWidth() < 800){
			int gap = (int) ((800 * 0.08) + (Math.random() * 800 * .35));
			int num = (int)(5 + Math.random() * 7);
			int yPos = (int)(382 + Math.random() * 101);
			addGroundPieces(num, 800 + gap, yPos);
		}
	}

	public void draw(Graphics2D win){
		for(Tile t : tiles){
			t.drawTile(win);
		}
	}

	public boolean checkCollision(Actor a){
		boolean value = false;
		for(Tile t:tiles){
			if(t.checkForCollision(a)) value = true;
		}
		return value;
	}

	public void accel(double y){
		for(Tile t1 : tiles){
			t1.setXVel(t1.getXVel() + y);
		}
		dx += y;
	}

	public void clear(){
		tiles.clear();
		dx = origDX;
	}


	private void addGroundPieces(int length, int x, int y){
		Tile t1 = new Tile(x, y, length, dx, 0, Color.GREEN, left, mid, right);
		tiles.add(t1);
	}

	public double getSpeed(){
		return dx;
	}
	public int getBulletHeight(){
		if(tiles.size() <= 1) return tiles.get(tiles.size()-1).getYPos();
		else
			return (int) ((tiles.get(tiles.size()-1).getYPos() + tiles.get(tiles.size() - 2).getYPos())/2);
	}
}
