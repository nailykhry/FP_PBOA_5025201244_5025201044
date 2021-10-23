package com.superbin;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable
{
	/* resolution */
	public static final int WIDTH = 270;
	public static final int HEIGHT = WIDTH/14*10;
	public static final int SCALE = 3;
	
	/* Window */
	public static final String TITLE = "Super Bin";
	
	/* constructor */
	public Game()
	{
		/* dimension 
		 * import from java.awt
		 */
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}	
	
	/* Thread --> Game Loops */
	private Thread thread;
	public boolean running = false; 
	
	private synchronized void start()
	{
		if(running) return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}
	
	private synchronized void stop()
	{
		if(!running) return;
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException error)
		{
			error.printStackTrace();
		}
		
	}
	
	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0/60.0;
		int frames = 0;
		int ticks = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta>=1)
			{
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000)
			{
				timer+=1000;
				System.out.println(frames + "Frames per Second " + ticks + " Updates per Second");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public void render() //render like graphic
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) //we havent create
		{
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLUE); 
		g.fillRect(0, 0, getWidth(), getHeight()); 
		g.setColor(Color.RED);
		g.fillRect(200, 200, getWidth()-400, getHeight()-400);
		g.dispose(); //must dispose before show
		bs.show();
	}
	
	/* update */
	public void tick() 
	{
		
	}
	
	/* main */
	public static void main(String[] args)
	{
		/* Window */
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack(); //pack everything together
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
		game.start();
	}
}
