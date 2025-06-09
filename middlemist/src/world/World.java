package world;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.border.Border;

import entity.Entity;
import entity.EntityCollider;
import main.GamePanel;

public class World{
	
	private static final long serialVersionUID = 1L;
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
		if (entCol.canCollide) {
	        return !(x <= entCol.x + entCol.width &&
	               x + worldW >= entCol.x &&
	               y <= entCol.y + entCol.height &&
	               y + worldH >= entCol.y);
	    } else {
	        return false;
	    }
	}
}
