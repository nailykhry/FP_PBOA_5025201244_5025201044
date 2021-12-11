package com.superbin.tile;

import java.awt.Graphics;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;
import com.superbin.entity.mob.Plant;

public class Pipe extends Tile
{

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) 
	{
		super(x, y, width, height, solid, id, handler);
		
		this.facing = facing;
		if(plant)
		{
			handler.addEntity(new Plant(getX(), getY()-64, getWidth(), 64, Id.plant, handler));
		}
	}

	@Override
	public void render(Graphics g) 
	{
		g.drawImage(Game.pipe[0].getBufferedImage(), x, y, width, 64, null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
