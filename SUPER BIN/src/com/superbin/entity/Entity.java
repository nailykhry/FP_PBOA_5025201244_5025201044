package com.superbin.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;

public abstract class Entity 
{
	public int x, y;
	public int width, height;
	public int facing = 0; //0 -left, 1 - right
	
	public int velX, velY;
	
	public boolean jumping = false;
	public boolean falling = false;
	public boolean goingDownPipe = false;
	public int type;
	
	
	public Id id;
	
	public double gravity = 0.0;
	
	public Handler handler;

	public Entity(int x, int y, int width, int height, Id id, Handler handler)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();

	public void die()
	{	
		handler.removeEntity(this);
		if(getId()==Id.player) 
		{
			if(Game.coins>=10) Game.coins -= 10;
				else Game.coins = 0;
			Game.lives--;
			Game.showDeathScreen=true;
			if(Game.lives<=0)
			{
				Game.gameOver=true;
				Game.themesong.stop();
			}
			Game.losealife.play();
		}
	}
	
	
	public int getType() 
	{
		return type;
	}

	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Id getId()
	{
		return id;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(getX(), getY(), width, height);
	}
	
	public Rectangle getBoundsTop()
	{
		return new Rectangle(getX()+5, getY(), width-10, 5);
	}
	
	public Rectangle getBoundsBottom()
	{
		return new Rectangle(getX()+10, getY()+height-5, width-20, 5);
	}
	
	public Rectangle getBoundsLeft()
	{
		return new Rectangle(getX(), getY()+10, 5, height-20);
	}
	
	public Rectangle getBoundsRight()
	{
		return new Rectangle(getX()+width-5, getY()+10, 5, height-20);
	}
	
}
