package com.superbin.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.superbin.Handler;
import com.superbin.Id;

public abstract class Entity 
{
	public int x; //position horizontal
	public int y; //position vertical
	public int width;
	public int height;
	public int velX;
	public int velY;
	public boolean solid;
	
	public boolean jumping = false;
	public boolean falling = true;
	
	/* import from Enum Id */
	public Id id;
	
	public double gravity = 0.0;
	
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
	
	
	public abstract void render(Graphics g);
	
	/* update */
	public abstract void tick();
	
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
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), width, height);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(getX()+10, getY(), width-20, 5);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(getX()+10, getY()+height-5, width-20, 5);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY()+10, 5, height-20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(getX()+width-5, getY()+10, 5, height-20);
	}
}
