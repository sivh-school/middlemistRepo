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
		int spriteX = 0, spriteY = 0, colCount = 1, rowCount = 1, sheetW = ent.spriteSheet.getWidth(), sheetH = ent.spriteSheet.getHeight();
		int maxCols = sheetW / gp.tileSize;
		int maxRows = sheetH / gp.tileSize;
		for (int i = 0; i < ent.spriteIndex; i++) {
			colCount++;
			if (colCount > maxCols) {
				rowCount++;
				if (rowCount > maxRows) {
					spriteX = 0;
					spriteY = 0;
					colCount = 0;
					rowCount = 0;
				}
				else {
					spriteY += gp.tileSize;
					spriteX = 0;
					colCount = 0;
				}
			}
			else {
				spriteX += gp.tileSize;
			}
		}
		if (spriteX < sheetW && spriteY < sheetH) {
			sprite = ent.spriteSheet.getSubimage(spriteX, spriteY, gp.tileSize, gp.tileSize);
		}
		else {
			ent.spriteIndex = 0; // Reset to first sprite if out of bounds
			try {
				sprite = ent.spriteSheet.getSubimage(0, 0, gp.tileSize, gp.tileSize);
			}
			catch (Exception e) {
				System.err.println("Err. Sprite sheet too small, formatted improperly or does not exist");
			}
		}
		System.out.println("Sprite X: " + spriteX + ", Y: " + spriteY + ", Index: " + ent.spriteIndex);
		return sprite;
	}

}
