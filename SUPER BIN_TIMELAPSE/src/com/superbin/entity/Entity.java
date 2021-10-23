package com.superbin.entity;

import java.awt.Graphics;

import com.superbin.Handler;
import com.superbin.Id;

public class Entity 
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
	public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler)
	{
		this.x = x; 
		this.y = y; 
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	
	public void render(Graphics g)
	{
		
	}
	
	/* update */
	public void tick() 
	{
		x += velX;
		y += velY;
	}
	
	/**/
	public void die()
	{
		handler.removeEntity(this);
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

	public boolean isSolid() 
	{
		return solid;
	}
	
	public Id getId()
	{
		return id;
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
