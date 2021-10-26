package com.superbin.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;
import com.superbin.tile.Tile;

public class Player extends Entity
{

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) 
	{
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) 
	{
		g.drawImage(Game.player.getBufferedImage(), x, y, width, height, null);
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
		
		for(Tile t: handler.tile) {
			if(!t.solid) break;
			if(t.getId() == Id.WALL) {
				if(getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling) {
						falling = false;
					}
				}
				else {
					if(!falling && !jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()+t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()-t.width;
				}
			}
		}
		if(jumping) {
			gravity -= 0.1;
			setVelY((int)-gravity);
			if(gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}
		if(falling) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
	}
	

}
