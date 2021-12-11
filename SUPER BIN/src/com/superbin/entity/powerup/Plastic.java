package com.superbin.entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;
import com.superbin.entity.Entity;
import com.superbin.tile.Tile;

public class Plastic extends Entity
{
	public Random random = new Random();
	
	public Plastic(int x, int y, int width, int height, Id id, Handler handler, int type) 
	{
		super(x, y, width, height, id, handler);
		
		this.type = type;
		
		int dir = random.nextInt(2);
		
		switch(dir)
		{
		case 0:
			setVelX(-1);
			break;
		case 1:
			setVelX(1);
			break;
		}
	}

	public void render(Graphics g)
	{
		switch(getType())
		{
			case 0 :
				g.drawImage(Game.plastic.getBufferedImage(), x, y, width, height, null);
				break;
			
			case 1 :
				g.drawImage(Game.lifeBanana.getBufferedImage(), x, y, width, height, null);
				break;
		}
	}
	
	public void tick()
	{
		x += velX;
		y += velY;
		
		for(int i = 0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid()) {

				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling) falling=false;
				}
				else 
					if(!falling&&!jumping)
				{
					falling = true;
					gravity = 0.8;
				}
					
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(1);
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(-1);
				}
			}
		}
		if ( falling ) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
	}
}
