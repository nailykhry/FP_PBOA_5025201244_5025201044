package com.superbin.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.superbin.Game;
import com.superbin.entity.Entity;
import com.superbin.tile.Tile;
import com.superbin.Id;

public class KeyInput implements KeyListener
{

	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		for(int i=0; i<Game.handler.entity.size();i++)
		{
			Entity en = Game.handler.entity.get(i);
			
			if(en.getId() == Id.player)
			{
				switch(key)
				{
				case KeyEvent.VK_W:
					//en.setVelY(-5);
					
					for(int q=0;q<Game.handler.tile.size();q++) 
					{
						Tile t = Game.handler.tile.get(q);
						if(t.getId()==Id.pipe) 
						{
							if (en.getBoundsTop().intersects(t.getBounds())) 
							{
								if (!en.goingDownPipe) 
									en.goingDownPipe = true;		
							}
						}
						
						if(t.isSolid()) 
						{
							if(en.getBoundsBottom().intersects(t.getBounds())) 
							{
								if (!en.jumping) 
								{
									en.jumping = true;
									en.gravity = 10.0;
								}
								Game.jump.play();
							}
						}
					}
				
					break;
				
				case KeyEvent.VK_S :
					for(int j=0; j<Game.handler.tile.size(); j++) 
					{
						Tile t = Game.handler.tile.get(j);
						if(t.getId()==Id.pipe) 
						{
							if (en.getBoundsBottom().intersects(t.getBounds())) 
							{
								if (!en.goingDownPipe) 
									en.goingDownPipe = true;
	
							}
						}
					}
					break;
					
				case KeyEvent.VK_A:
					en.setVelX(-5);
					en.facing = 0;
					break;
					
				case KeyEvent.VK_D:
					en.setVelX(5);
					en.facing = 1;
					break;
					
				case KeyEvent.VK_Q:
					en.die();
				}
			}
			
		}
	}

	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		for(int i=0; i<Game.handler.entity.size(); i++)
		{
			Entity en = Game.handler.entity.get(i);
			
			if(en.getId() == Id.player)
			{
				switch(key)
				{
				case KeyEvent.VK_W:
					en.setVelY(0);
					break;
				case KeyEvent.VK_S:
					en.setVelY(0);
					break;
				case KeyEvent.VK_A:
					en.setVelX(0);
					break;
				case KeyEvent.VK_D:
					en.setVelX(0);
					break;
				}
			}
		}
	}
	
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
