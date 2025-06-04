package entity;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import main.GamePanel;

public class SpriteHandler {

	GamePanel gp;

	public SpriteHandler(GamePanel gp) {
		this.gp = gp;
	}

	public BufferedImage getSprite(Entity ent) {
		if (ent.spriteSheet == null) {
			ent.innitSheet("/res/sprites/exc.png");
		}
		BufferedImage sprite = null;
		int spriteX = 0, spriteY = 0, colCount = 0, rowCount = 0, sheetW = ent.spriteSheet.getWidth(), sheetH = ent.spriteSheet.getHeight();
		int maxCols = sheetW / gp.tileSize;
		int maxRows = sheetH / gp.tileSize;
		int maxSprites = maxCols * maxRows;
		for (int i = 0; i < ent.spriteIndex; i++) {
		    colCount++;
		    if (colCount >= maxCols) {
		        colCount = 0;
		        rowCount++;
		        if (rowCount >= maxRows) {
		            // Out of bounds; reset everything
		            colCount = 0;
		            rowCount = 0;
		        }
		    }
		}
		if (ent.spriteIndex >= maxSprites) {
			ent.spriteIndex = 0; // Reset to first sprite if out of bounds
			colCount = 0;
			rowCount = 0;
		}
		spriteX = colCount * gp.tileSize;
		spriteY = rowCount * gp.tileSize;
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
		
		int transCount = 0;
		Raster rast = sprite.getData();
		if (rast.getNumBands() == 4) {
			// If the sprite has an alpha channel, we can check for transparency
			for (int y = 0; y < sprite.getHeight(); y++) {
				for (int x = 0; x < sprite.getWidth(); x++) {
					if (sprite.getRGB(x, y) == 0x00000000) { // Check for fully transparent pixel
						transCount++;
					}
				}
			}
		}
		if (transCount == sprite.getWidth() * sprite.getHeight()) {
			ent.spriteIndex = 0; // Reset to first sprite if out of bounds
			ent.frameCount++;
			try {
				sprite = ent.spriteSheet.getSubimage(0, 0, gp.tileSize, gp.tileSize);
			}
			catch (Exception e) {
				System.err.println("Err. Sprite sheet too small, formatted improperly or does not exist");
			}
			return sprite;
		}
		else {
			return sprite;
		}
	}

}
