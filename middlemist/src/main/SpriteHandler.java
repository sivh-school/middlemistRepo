package main;

import java.awt.image.BufferedImage;

import entity.Entity;

public class SpriteHandler {

	GamePanel gp;

	public SpriteHandler(GamePanel gp) {
		this.gp = gp;
	}

	public BufferedImage getSprite(Entity ent) {
		BufferedImage sprite = null;
		int spriteX = 0, spriteY = 0, colCount = 0, sheetW = ent.spriteSheet.getWidth(), sheetH = ent.spriteSheet.getHeight();
		for (int i = 1; i < ent.spriteIndex; i++) {
			if (colCount > gp.maxCols) {
				spriteY += gp.tileSize;
				spriteX = 0;
				colCount = 0;
			}
			else {
				colCount++;
				spriteX += gp.tileSize;
			}
		}
		if (spriteX < sheetW && spriteY < sheetH) {
			sprite = ent.spriteSheet.getSubimage(spriteX, spriteY, gp.tileSize, gp.tileSize);
		}
		else {
			try {
				sprite = ent.spriteSheet.getSubimage(0, 0, gp.tileSize, gp.tileSize);
			}
			catch (Exception e) {
				System.err.println("Err. Sprite sheet too small, formatted improperly or does not exist");
			}
		}
		return sprite;
	}

}
