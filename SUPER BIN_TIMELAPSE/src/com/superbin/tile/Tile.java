package com.superbin.tile;

import java.awt.Graphics;

import com.superbin.Handler;
import com.superbin.Id;

public abstract class Tile 
{
	public int x; //position horizontal
	public int y; //position vertical
	public int width;
	public int height;
	public int velX;
	public int velY;
	public boolean solid;
	
	/* import from Enum Id */
	public Id id;
	
	/* from Handler */
	public Handler handler;
	
	/* constructor */
	public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler)
	{
		this.x = x; 
		this.y = y; 
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	public abstract void render(Graphics g);
	
	/* update */
	public abstract void tick();
	
	/**/
	public void die()
	{
		handler.removeTile(this);
	}

	/* getter and setter */
	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public Id getId()
	{
		return id;
	}
	
	public boolean isSolid() 
	{
		return solid;
	}

	public int getVelX() 
	{
		return velX;
	}


	public void setVelX(int velX) 
	{
		this.velX = velX;
	}


	public int getVelY() 
	{
		return velY;
	}


	public void setVelY(int velY) 
	{
		this.velY = velY;
	}
	
}
