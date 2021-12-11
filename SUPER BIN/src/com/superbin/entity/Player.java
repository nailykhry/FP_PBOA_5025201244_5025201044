package com.superbin.entity;

import java.awt.Graphics;

import com.superbin.Game;
import com.superbin.Handler;
import com.superbin.Id;
import com.superbin.states.PlayerState;
import com.superbin.tile.Tile;

public class Player extends Entity
{
	
	private PlayerState state;
	
	private int pixelsTravelled = 0;
	
	public int frame = 0;
	private int frameDelay = 0;
	
	private boolean animate = false;
	
	public Player(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		state = PlayerState.SMALL;
	}
	
	public void render(Graphics g)
	{
		//g.setColor(Color.BLUE);
		//g.fillRect(x, y, width, height);
		if(facing==0)
		{
			g.drawImage(Game.player[frame+4].getBufferedImage(), x, y, width, height, null);
		}
		else 
		if(facing==1)
		{
			g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
		}
		
	
	}
	public void tick()
	{
		x += velX;
		y += velY;
		//if(x<=0) x=0;
		//if(y<=0) y=0;
		//if(x+width>=1280) x = 1280-width;
		
		
		if(y+height>=720) y = 720-height;
		
		if(velX!=0) animate = true;
		else animate=false;
		
		for(int i=0; i<handler.tile.size();i++)
		{
			Tile t = handler.tile.get(i);
			
			if(getBounds().intersects(t.getBounds())) 
			{
				if(t.getId()==Id.flag) 
				{
					Game.switchLevel();
				}
			}
			
			if(t.isSolid() && !goingDownPipe)
			{
				if(getBoundsTop().intersects(t.getBounds()) )
				{
					setVelY(0);
					//y = t.getY() + height;
					
					if(jumping && !goingDownPipe)
					{
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
					 
					if(t.getId()==Id.powerUp)
					{
						if(getBoundsTop().intersects(t.getBounds()))
						{
							t.activated = true;
						}
					}
				}
				
				if(getBoundsBottom().intersects(t.getBounds()))
				{
					setVelY(0);
					//y = t.getY() - height;
					
					if(falling) falling = false;
				}
				else
				{
					if(!falling&&!jumping)
					{
						gravity = 0.8;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(t.getBounds()))
				{
					setVelX(0);
					x = t.getX() + t.width;
				}
				if(getBoundsRight().intersects(t.getBounds()))
				{
					setVelX(0);
					x = t.getX() - t.width;
				}	
				
			}
		}
		
		for(int i=0; i<handler.entity.size(); i++)
		{
			Entity e = handler.entity.get(i);
			
			//plastic
			if(e.getId() == Id.plastic)
			{
				switch(e.getType())
				{
					case 0 :
						if(getBounds().intersects(e.getBounds())) 
						{
							int tpX = getX();
							int tpY = getY();
							width+=(width/3);
							height+=(height/3);
							setX(tpX-width);
							setY(tpY-height);
							if (state == PlayerState.SMALL) 
								state = PlayerState.BIG;
							e.die();
						}
						break;
					case 1 :
						if(getBounds().intersects(e.getBounds())) 
						{
							Game.lives++;
							e.die();
						}
				}
	
			}
			else 
			if(e.getId() == Id.goomba || e.getId() == Id.plant)
			{
				if(getBounds().intersects(e.getBoundsTop()))
				{
					e.die();
					Game.goombastomp.play();
				}
				else
				if(getBounds().intersects(e.getBounds()))
				{
					if (state == PlayerState.BIG)
					{
						Game.damage.play();
						state = PlayerState.SMALL;
						width/=2;
						height/=2;
						x+=width;
						y+=height;
						e.die();
					}
					else
					if (state == PlayerState.SMALL) 
					{
						die();
					}
				}
				
			}
			
			if(getBounds().intersects(e.getBounds())&&e.getId()==Id.coin) 
			{
				Game.coinSound.play();
				Game.coins++;
				e.die();
			}
		
		}
		
		if(jumping&&!goingDownPipe)
		{
			gravity -= 0.1;
			setVelY((int)-gravity);
			if(gravity<=0.0)
			{
				jumping = false;
				falling = true;
			}
		}
		if(falling&&!goingDownPipe)
		{
			gravity += 0.1;
			setVelY((int)gravity);
		}
		
		if(animate)
		{
			frameDelay++;
			if(frameDelay>=3)
			{
				frame++;
				if(frame>=5)
				{
					frame = 0;
				}
				frameDelay = 0;
			}
		}
		if(goingDownPipe)
		{
			for(int i=0; i<Game.handler.tile.size(); i++)
			{
				Tile t = Game.handler.tile.get(i);
				if(t.getId()==Id.pipe)
				{
					if(getBounds().intersects(t.getBounds()))
					{
						switch(t.facing)
						{
							case 0:
								setVelY(5);
								setVelX(0);
								pixelsTravelled-=velY;
								break;
							case 2:
								setVelY(-5);
								setVelX(0);
								pixelsTravelled+=velY;
								break;
						}
						
						if (pixelsTravelled>=t.height*2+height) 
						{
							goingDownPipe = false;
							pixelsTravelled = 0;
						}
					}

				}
			}
		}
	}
	
}
