package world;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.EntityCollider;
import main.GamePanel;

public class World{
	
	public int x, y, worldW, worldH, lastX, lastY;
	public BufferedImage mapImage;
	GamePanel gp;
	
	public World(GamePanel gp) {
		this.gp = gp;
	}
	
	public void verifyPos() {
		lastX = x;
		lastY = y;
	}
	
	public void worldUpdate() {
		int xDif = x - lastX;
		int yDif = y - lastY;
		for (Entity ent : gp.entities) {
			ent.x += xDif;
			ent.y += yDif;
			ent.entCollide.x = ent.x + (ent.width - ent.entCollide.width)/2;
			ent.entCollide.y = ent.y + (ent.height - ent.entCollide.height)/2;
		}
	}
	
	public boolean checkWorldCollision(EntityCollider entCol) {
	    return !(x + entCol.width <= entCol.x + entCol.width &&
	           x + worldW >= entCol.x + entCol.width &&
	           y + entCol.height <= entCol.y + entCol.height &&
	           y + worldH >= entCol.y + entCol.height);
	}
}
