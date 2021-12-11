package com.superbin.gfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.superbin.Game;

public class Launcher 
{
	public Button[] buttons;
	private static BufferedImage background;
	
	public Launcher() 
	{
		buttons = new Button[2];
		
		//buttons[0] = new Button(100, 100, 100, 100, "Start Game");
		//buttons[1] = new Button(200, 200, 100, 100, "Exit Game");
		buttons[0] = new Button(100, Game.getFrameHeight()/2, Game.getFrameWidth()-200, 100, "Start Game");
		buttons[1] = new Button(100, Game.getFrameHeight()/2+100, Game.getFrameWidth()-200, 100, "Exit Game");
	}
	public void render(Graphics g)
	{

		//g.setColor(Color.pink);
		//g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
		try {
			background = ImageIO.read(getClass().getResource("/bg_dashboard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(background, 0, 0, Game.getFrameWidth(),Game.getFrameHeight(), null);
		
		g.setColor(new Color(102,41,0));
		g.setFont(new Font("Century Gothic", Font.BOLD,20));
		g.drawString("TIMELAPSE", 580, 600);
		g.drawString("Naily Khairiya(5025201244) - Khariza Azmi(5025201044)", 400, 630);
		
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i].render(g);
		}
	}
}
