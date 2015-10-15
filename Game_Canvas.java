import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URISyntaxException;


public class Game_Canvas extends GameDriver {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private double bgXPos, wfXPos, score, highScore;
	private int waterfallCount;
	private int pressEnterCount;
	private double loading;
	private BufferedImage background, waterfall, endImg, title;
	private Mario joon;
	private ArrayList<Bullet> launcher;
	private Foreground fg;
	private boolean startGame;
	private boolean endGame;
	private Font defaultFont;
	private SoundDriver sd;
	private FileDriver fd;
	private int BulletTimer;
	private int spawnSpace;
	private int timer;
	private double accelRate;
	private int musicTimer;
	private boolean isEnter;
	private boolean isEnterTwo;
	private int endCounter;

	private ArrayList<Fireball> fire;
	private int fireTimer;

	private final int timeBetweenFire = 22;

	public Game_Canvas() {
		String[] stuff = {"audio/Jump.wav","audio/Shooting.wav","audio/BulletDying.wav", "audio/Dead.wav","audio/Opening.wav", "audio/BuildUp.wav", "audio/BackgroundSong.wav"};
		sd = new SoundDriver(stuff);
		fd = new FileDriver();
		background = addImage("background.png");
		waterfall = addImage("waterfall.png");
		title = addImage("Title.png");
		timer = 0;
		new Rectangle(0,0,800,600);
		joon = new Mario(120,416,60,75,0,0,Color.RED, addImage("MarioSprites.png"));
		launcher = new ArrayList<Bullet>();
		fire = new ArrayList<Fireball>();
		fireTimer = 0;
		loading = 1.1;
		accelRate = -0.001;
		spawnSpace = 250;
		fg = new Foreground(-6, addImage("LeftEdgeTile.png"), addImage("Tile.png"), addImage("RightEdgeTile.png"));
		fg.init();
		musicTimer = 0;
		startGame = false;
		endGame = false;
		isEnterTwo = false;
		isEnter = false;
		endImg = addImage("GameEnd.png");
		highScore = Integer.parseInt(fd.getStringArray("highScores.txt")[0]);
		Font ttfBase = null;
		sd.loop(4);
		try {
			InputStream myStream = new BufferedInputStream(new FileInputStream("BorisBlackBloxx.ttf"));
			ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
			defaultFont = ttfBase.deriveFont(Font.BOLD, 36);
		} catch (Exception ex) {
	        ex.printStackTrace();
	        System.err.println("CODE2000 not loaded.");
		}
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.WHITE);
		win.setFont(defaultFont);
		if(!startGame){

			drawAndMoveBG(win);
			win.drawImage(title,800/2 - title.getWidth()/2 - 12, 100, null);
			Font f1 = defaultFont.deriveFont(Font.BOLD, 20);
			int pos = 470;
			win.setFont(f1);
			win.drawString("By: Joon Hee Lee & Sean Nguyen", 396, 550);
			win.setColor(Color.white);
			win.drawString("Here's How to Play:", 10, pos);
			win.drawString("To jump, press W",20, pos + 20);
			win.drawString("To shoot, press Spacebar", 20, pos + 40);
			win.drawString("Good luck!", 20, pos + 80);
			win.drawString("Avoid: Bullets & Water", 20, pos+60);
			win.setColor(new Color(245,49,28));
			f1 = defaultFont.deriveFont(Font.BOLD, 36);
			win.setFont(f1);
			if(loading >= 100){
				if( pressEnterCount % 60 <= 29) {
					win.drawString("Press ENTER to Play", 170, 340);
				}
				pressEnterCount++;
				if(pressEnterCount >= 120){
					pressEnterCount = 0;
				}
				if(keys[10]){
				//	startGame = true;
					isEnter = true;
					pressEnterCount = 0;
					sd.stop(4);
					sd.play(5);
				}
				if(isEnter = true){
					isEnter = false;
					isEnterTwo = true;
				}
				if(isEnterTwo = true){
					if(musicTimer >= 723){
						startGame = true;
						sd.stop(4);
						sd.stop(5);
						sd.loop(6);
					}
					else {
						musicTimer++;
					}
				}
			}
			else {
				win.drawString("Loading: " + (int) loading, 275, 340);
				loading = loading * 1.01;
			}
		}
		else if(!endGame){
			drawAndMoveBG(win);
			drawAndMoveWaterfall(win,endGame);
			win.drawString("Score: " + (int) score,8,36);
			win.drawString("High Score: " + (int) highScore,8,80);
			setScore();
			fg.move();
			fg.draw(win);
			fg.accel(accelRate);
			accelRate += -0.0000001;
			shootAndDraw(win);
			joon.jump(keys,fg.checkCollision(joon),sd);
			joon.shoot(keys, fireTimer, timeBetweenFire);
			joon.move(keys);
			joon.drawActor(win);
			drawAndMoveBullet(win);
			killBullet();
			death();
			timer++;
		}
		else {
			win.drawImage(background,(int)bgXPos,0,null);
			win.drawImage(background,(int)bgXPos + background.getWidth(), 0, null);
			drawAndMoveWaterfall(win,endGame);
			win.drawString("Score: " + (int) score,8,36);
			win.drawString("High Score: " + (int) highScore,8,80);
			fg.draw(win);
			win.setColor(new Color(245, 49, 28));
			if(pressEnterCount % 60 <= 29  && endCounter > 400) {
				win.drawString("Game Over", 280, 260);
				win.drawString("Press ENTER to Continue", 120, 300);
			}
			pressEnterCount++;
			if(pressEnterCount >= 120){
				pressEnterCount = 0;
			}
		//	win.drawImage(endImg, 30, 200, null);
			if(highScore < score) {
				highScore = score;
				String[] scores = fd.getStringArray("highScores.txt");
				scores[0] = "" + (int) score;
				fd.addToFile("highScores.txt", scores);
			}
			if(keys[10] && endCounter > 400){
				reset();
				pressEnterCount = 0;
				sd.stop(3);
				sd.loop(6);
			}
			endCounter++;
		}
	}

	public void drawAndMoveBG(Graphics2D win){
		bgXPos -= 0.5;
		if(bgXPos <= -background.getWidth()){
			bgXPos = 0;
		}
		win.drawImage(background,(int)bgXPos,0, null);
		win.drawImage(background,(int)bgXPos + background.getWidth(), 0, null);
	}

	public void drawAndMoveWaterfall(Graphics2D win, boolean end) {
			wfXPos += fg.getSpeed();
			if(end) wfXPos -= fg.getSpeed();
			if(wfXPos <= -waterfall.getWidth()){
				wfXPos = 0;
			}
		int pos = waterfallCount / 15;
		BufferedImage img = waterfall.getSubimage(0,0 + 92*pos,896,92);
		win.drawImage(img,(int)wfXPos,520, null);
		win.drawImage(img,(int)wfXPos + waterfall.getWidth(), 520, null);
		waterfallCount++;
		if(waterfallCount == 45){
			waterfallCount = 0;
		}
	}

	public void shootAndDraw(Graphics2D win) {
		BufferedImage fb = addImage("Fireball.png");
		fireTimer++;
		if(keys[11] && fireTimer > timeBetweenFire){
			fireTimer = 0;
			int x = joon.getXPos() + (int) joon.getHitBox().getWidth() - 20;
			int y = joon.getYPos() + (int) (joon.getHitBox().getHeight()/2) - 8;
			Fireball f1 = new Fireball(x,y,42,42,5,0,Color.RED,fb);
			fire.add(f1);
			sd.play(1);
		}

		for(int i = 0; i < fire.size(); i++){
			fire.get(i).move();
			fire.get(i).draw(win);
			if(fire.get(i).getXPos() > 820){
				 fire.remove(i);
				 i--;
			}
		}
	}

	public void drawAndMoveBullet(Graphics2D win) {
		// spawning
		if (BulletTimer > spawnSpace) {
			BulletTimer = 0;
			if(timer >= 200000) timer = 200000;
			spawnSpace = (int) (Math.random()*(200- timer/1000)) + 70;
		//	spawnSpace = (int) (Math.random()*(200)) + 70;
			int bHeight = fg.getBulletHeight() - 32;
			Bullet bill = new Bullet(900, bHeight, 48, 24, (int)(fg.getSpeed()-5), 0, Color.BLACK, addImage("Bullet.png"));
			launcher.add(bill);
		}
		// drawing
		for (int i = 0; i < launcher.size(); i++) {

			launcher.get(i).move();
			launcher.get(i).drawActor(win);

			if(launcher.get(i).getXPos() < -50) {
				launcher.remove(i);
				i--;
			}
		}
		BulletTimer++;
	}

	public void killBullet(){
		for(int i = 0; i < launcher.size(); i++){
			Bullet m1 = launcher.get(i);
			for(int j = 0; j < fire.size(); j++){
				Fireball b1 = fire.get(j);
				if (b1.getHitBox().intersects(m1.getHitBox()) && m1.getHitBox().getX() < 800){
					fire.remove(j);
					launcher.remove(i);
					sd.play(2);
				}
			}
		}
	}

	public void death(){
		if(joon.getYPos() > 600){
			sd.stop(4);
			sd.stop(5);
			sd.stop(6);
			sd.play(3);
			endGame = true;
		}
		Rectangle hitBox = joon.getHitBox();
		for(int i = 0; i < launcher.size(); i++) {
			Rectangle m1  = launcher.get(i).getHitBox();
			if(hitBox.intersects(m1)) {
				sd.stop(4);
				sd.stop(5);
				sd.stop(6);
				sd.play(3);
				endGame = true;
			}
		}
	}

	public void setScore(){
		score += 0.1;
	}

	public void reset(){
		endGame = false;
		endCounter = 0;
		joon.getHitBox().setLocation(120,416);
		joon.reset();
		fg.clear();
		launcher.clear();
		fire.clear();
		fg.init();
		score = 0;
		timer = 0;
		accelRate = -0.001;
	}
}