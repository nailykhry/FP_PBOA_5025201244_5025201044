package com.superbin.gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	
	public SpriteSheet sheet;
	public BufferedImage image;
	
	public Sprite(SpriteSheet sheet, int x, int y) {
		image = sheet.getSprites(x, y);
	}
	
	public BufferedImage getBufferedImage() {
		return image;
	}
}