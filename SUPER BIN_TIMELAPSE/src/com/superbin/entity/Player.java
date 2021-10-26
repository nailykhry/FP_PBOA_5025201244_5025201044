package com.superbin.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.superbin.Handler;
import com.superbin.Id;

public class Player extends Entity
{

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) 
	{
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.BLUE); 
		g.fillRect(x, y, width, height); 
		//g.dispose(); //must dispose before show
		//bs.show();
	}

	@Override
	public void tick() 
	{
		/* make the player move from its position, depent on it setVel(X or Y) */
		x += velX;
		y += velY;
		
		/* make it stop at the border and not disappear 
		 * this calculation based on width and height on resolution @game.java
		 * */
		if(x <= 0) x = 0;
		if(y <= 0) y = 0;
		if(x + width >= 1080) x = 1080 - width ; 
		if(y + height >= 700) y = 700 - height;
	}
	

}
