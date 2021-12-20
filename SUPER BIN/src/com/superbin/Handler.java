package com.superbin;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.superbin.entity.Coin;
import com.superbin.entity.Entity;
import com.superbin.entity.Player;
import com.superbin.entity.mob.Goomba;
import com.superbin.entity.powerup.Plastic;
import com.superbin.tile.Flag;
import com.superbin.tile.Pipe;
import com.superbin.tile.PowerUpBlock;
import com.superbin.tile.Tile;
import com.superbin.tile.Wall;

public class Handler 
{
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();


	
	public void render(Graphics g)
	{
		for(int i=0;i<entity.size();i++) 
		{
			Entity en = entity.get(i);
			if(Game.getVisibleArea()!=null && en.getBounds().intersects(Game.getVisibleArea())) 
				en.render(g);
		}
		for(int i=0;i<tile.size();i++) 
		{
			Tile  ti = tile.get(i);
				ti.render(g);
		}
	}
	
	public void tick()
	{
		for(int i=0; i<entity.size();i++) 
		{
			Entity e = entity.get(i);
			e.tick();
		}
		for(int i=0;i<tile.size();i++) 
		{
			Tile ti = tile.get(i);
				ti.tick();
		}
	}
	
	/*public void die()
	{
		
	}*/
	
	public void addEntity(Entity en)
	{
		entity.add(en);
	}
	
	public void removeEntity(Entity en)
	{
		entity.remove(en);
	}
	
	public void addTile(Tile ti)
	{
		tile.add(ti);
	}
	
	public void removeTile(Tile ti)
	{
		tile.remove(ti);
	}
	
/*	public void createLevel()
	{
		for(int i=0; i<Game.WIDTH*Game.SCALE/32+1;i++)
		{
			addTile(new Wall(i*32, Game.HEIGHT*Game.SCALE-32, 32, 32, true, Id.wall, this));
			if(i!=0&&i!=1&&i!=32&&i!=17) addTile(new Wall(i*32, 300, 32,32, true, Id.wall,this));
		}
	}
*/
	
	public void createLevel(BufferedImage level)
	{
		int width = level.getWidth();
		int height = level.getHeight();
		for(int y=0; y<height; y++)
		{
			for(int x=0; x<width;x++)
			{
				int pixel = level.getRGB(x, y);
				
				int red = (pixel>>16)&0xff;
				int green = (pixel>>8)&0xff;
				int blue = (pixel)&0xff;
				
				if(red==0&&blue==0&&green==0) addTile(new Wall(x*32, y*32, 64, 64, true, Id.wall, this));
				if(red==0&&blue==255&&green==0) addEntity(new Player(x*32, y*32, 48, 48, Id.player, this));//0000ff
				if(red==255&&green==0&&blue==0) addEntity(new Plastic(x*32, y*32, 64, 64, Id.plastic, this, 0));//ff0000
				if(red==255&&green==119&&blue==0) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//ff7700
				if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*32, y*32, 64, 64, true, Id.powerUp,this,Game.lifeBanana, 1));//ffff00
				if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*32, y*32, 64, 64*5,true,Id.pipe,this,128-green, true));//ff7700,008000
				if(red==255&&green==165&&blue==0) addEntity(new Coin(x*32, y*32, 64, 64,Id.coin, this));//FFA500
				if(red==105&&green==105&&blue==105) addTile(new Flag(x*32, y*32, 64, 64*5, true, Id.flag, this));//696969
				if(red==0&&green==128&&blue==128) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//008080
				if(red==169&&green==169&&blue==169) addEntity(new Plastic(x*32, y*32, 64, 64,Id.plastic,this,1));//A9A9A9 //powerup
				
			}
		}
	}
	
	public void clearLevel() 
	{
		entity.clear();
		tile.clear();
	}

}
