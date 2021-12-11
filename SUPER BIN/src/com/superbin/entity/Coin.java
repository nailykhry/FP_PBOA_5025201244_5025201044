package com.superbin.entity;

import java.awt.Graphics;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;

public class Coin extends Entity 
{

	

	public Coin(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.coin.getBufferedImage(), x, y, width, height, null);
		
	}

	@Override
	public void tick() 
	{
	
	}


}