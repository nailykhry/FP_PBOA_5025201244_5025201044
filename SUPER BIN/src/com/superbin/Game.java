package com.superbin;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import com.superbin.entity.Entity;
import com.superbin.gfx.Sprite;
import com.superbin.gfx.SpriteSheet;
import com.superbin.gfx.gui.Launcher;
import com.superbin.input.KeyInput;
import com.superbin.input.MouseInput;
import com.superbin.tile.Wall;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Resolutions */
	public static final int WIDTH = 320;
	public static final int HEIGHT = 180;
	public static final int SCALE = 4;

	/* Window */
	public static final String TITLE = "SUPERBIN";
	public static JFrame frame = new JFrame(TITLE);

	private Thread thread;
	private boolean running = false;
	private static BufferedImage[] levels;

	private static BufferedImage background;

	public static int level = 0;

	public static int coins = 0;
	public static int lives = 2;
	public static int deathScreenTime = 0;
	public static int winScreenTime = 0;
	public static int gameOverScreenTime = 0;

	public static boolean showDeathScreen = true;
	public static boolean showWinScreen = false;

	public static boolean gameOver = false;
	public static boolean playing = false;

	public static Handler handler;
	public static SpriteSheet sheet;
	public static Camera cam;
	public static Launcher launcher;
	public static MouseInput mouseInput;

	public static Sprite grass;
	public static Sprite powerUp;
	public static Sprite usedPowerUp;
	public static Sprite flower;

	public static Sprite plastic;
	public static Sprite lifeBanana;
	public static Sprite coin;

	public static Sprite[] player;
	public static Sprite[] goomba;
	public static Sprite[] pipe;
	public static Sprite[] flag;

	public static int playerX, playerY;

	public static Sound jump;
	public static Sound goombastomp;
	public static Sound levelcomplete;
	public static Sound losealife;
	public static Sound themesong;
	public static Sound damage;
	public static Sound coinSound;

	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}

	private void init() {
		handler = new Handler();
		sheet = new SpriteSheet("/SpriteSheet2.png");
		cam = new Camera();
		launcher = new Launcher();
		mouseInput = new MouseInput();

		addKeyListener(new KeyInput());
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);

		player = new Sprite[10];
		goomba = new Sprite[10];
		pipe = new Sprite[2];
		flag = new Sprite[3];
		levels = new BufferedImage[5];

		grass = new Sprite(sheet, 1, 1);
		powerUp = new Sprite(sheet, 3, 1);
		usedPowerUp = new Sprite(sheet, 4, 1);
		plastic = new Sprite(sheet, 6, 1);
		lifeBanana = new Sprite(sheet, 2, 1);
		coin = new Sprite(sheet, 5, 1);
		flower = new Sprite(sheet, 8, 1);

		for (int i = 0; i < player.length; i++) {
			player[i] = new Sprite(sheet, i + 1, 16);
		}

		for (int i = 0; i < goomba.length; i++) {
			goomba[i] = new Sprite(sheet, i + 1, 15);
		}

		for (int i = 0; i < pipe.length; i++) {
			pipe[i] = new Sprite(sheet, i + 4, 2);
		}

		for (int i = 0; i < flag.length; i++) {
			flag[i] = new Sprite(sheet, i + 1, 2);
		}

		// handler.addEntity(new Player(300,512,64,64,true,Id.player,handler));
		handler.addTile(new Wall(200, 200, 64, 64, true, Id.wall, handler));

		try {
			levels[0] = ImageIO.read(getClass().getResource("/level.png"));
			levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
			levels[2] = ImageIO.read(getClass().getResource("/level3.png"));
			levels[3] = ImageIO.read(getClass().getResource("/level4.png"));
			levels[4] = ImageIO.read(getClass().getResource("/level5.png"));
			background = ImageIO.read(getClass().getResource("/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// handler.createLevel(image);

		jump = new Sound("/audio/jump.wav");
		goombastomp = new Sound("/audio/goombastomp.wav");
		levelcomplete = new Sound("/audio/levelcomplete.wav");
		losealife = new Sound("/audio/losealife.wav");
		themesong = new Sound("/audio/themesong.wav");
		damage = new Sound("/audio/damage.wav");
		coinSound = new Sound("/audio/coin.wav");

	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " Frame Per Second " + ticks + " Updates Per Second ");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		if (!showDeathScreen) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
			// g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75, null);
			g.setColor(Color.white);
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.drawString("x" + coins, 100, 95);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (showDeathScreen) {
			// g.setColor(Color.BLACK);
			// g.fillRect(0, 0, getWidth(), getHeight());//can not forget it
			if (!gameOver) {
				g.drawImage(player[0].getBufferedImage(), 500, 200, 100, 100, null);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Courier", Font.BOLD, 50));
				g.drawString("x" + lives, 610, 300);
				g.drawString("Use 'W' to jump", 400, 400);
				g.drawString("Use 'A' and 'D' to move", 310, 500);
			} else {
				g.drawImage(player[3].getBufferedImage(), 500, 200, 100, 100, null);
				g.setColor(Color.RED);
				g.setFont(new Font("Courier", Font.BOLD, 60));
				g.drawString("Game Over :(", 350, 400);
				g.drawString("Your Coins : " + coins, 350, 500);
				// Game.score();
			}

		}

		if (showWinScreen) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(player[0].getBufferedImage(), 500, 200, 100, 100, null);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Courier", Font.BOLD, 50));
			g.drawString("You Win :D", 350, 400);
			g.drawString("Your Coins : " + coins, 350, 500);
		}

		if (playing)
			g.translate(cam.getX(), cam.getY());// make it move

		if (!showDeathScreen && playing)
			handler.render(g);
		else if (!playing)
			launcher.render(g);

		g.dispose();
		bs.show();
	}

	public void tick() {
		if (playing)
			handler.tick();

		for (Entity e : handler.entity) {
			if (e.getId() == Id.player) {
				if (!e.goingDownPipe)
					cam.tick(e);
			}
		}

		if (showDeathScreen && !gameOver && playing)
			deathScreenTime++;

		if (showDeathScreen && gameOver && playing)
			gameOverScreenTime++;

		if (gameOverScreenTime >= 180) {
			level = 0;
			coins = 0;
			lives = 2;
			deathScreenTime = 0;
			winScreenTime = 0;
			gameOverScreenTime = 0;
			showDeathScreen = true;
			showWinScreen = false;
			gameOver = false;
			playing = false;
			render();
		}

		if (deathScreenTime >= 180) {
			if (!gameOver) {
				showDeathScreen = false;
				deathScreenTime = 0;
				handler.clearLevel();
				handler.createLevel(levels[level]);
				themesong.play();
			}
		}

		if (showWinScreen)
			winScreenTime++;

		if (winScreenTime >= 180) {
			level = 0;
			coins = 0;
			lives = 2;
			deathScreenTime = 0;
			winScreenTime = 0;
			gameOverScreenTime = 0;
			showDeathScreen = true;
			showWinScreen = false;
			gameOver = false;
			playing = false;
			render();
		}
	}

	public static int getFrameWidth() {
		return WIDTH * SCALE;
	}

	public static int getFrameHeight() {
		return HEIGHT * SCALE;
	}

	public static void switchLevel() {

		if (Game.level == 4) {
			Game.level = 0;
			showWinScreen = true;
			handler.clearLevel();
			themesong.stop();
			return;
		} else
			Game.level++;

		handler.clearLevel();
		try {
			handler.createLevel(levels[level]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// themesong.stop();
		Game.levelcomplete.play();
	}

	public static Rectangle getVisibleArea() {
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity e = handler.entity.get(i);
			if (e.getId() == Id.player) {

				if (!e.goingDownPipe) {
					playerX = e.getX();
					playerY = e.getY();
					return new Rectangle(playerX - (getFrameWidth() / 2 - 5), playerY - (getFrameHeight() / 2 - 5),
							getFrameWidth() + 360, getFrameHeight() + 360);
				}
			}
		}

		return new Rectangle(playerX - (getFrameWidth() / 2 - 5), playerY - (getFrameHeight() / 2 - 5),
				getFrameWidth() + 360, getFrameHeight() + 360);
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);

		frame.add(game);
		frame.pack();// pack the game
		frame.setResizable(false);// avoid resize the frame
		frame.setLocationRelativeTo(null);// set the frame in the middle
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
		// System.exit(0);
	}

}
