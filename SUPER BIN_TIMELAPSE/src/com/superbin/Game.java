package com.superbin;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Game extends Canvas
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
	}
}
