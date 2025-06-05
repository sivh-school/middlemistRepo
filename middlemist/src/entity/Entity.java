package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Entity {

	//Variables

	public String entName;
	public BufferedImage spriteSheet;
	public int spriteIndex, x, y, speed = 4, frameCount = 0;
	public EntityCollider entCollide;
	
	//Constructors

	public Entity(String name, int x, int y) {
		entName = name;
		this.x = x;
		this.y = y;
		spriteIndex = 0;
		entCollide = new EntityCollider(this, 64, 64);
		innitEnt();
	}

	//Methods
	
	private void innitEnt() {
		EntityLoader.initializedEnts.add(this);
	}

	public void innitSheet(String imgPath) {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(getClass().getResourceAsStream(imgPath));
		} catch (IOException e) {
			System.err.println("Error loading sprite sheet for entity: " + entName);
			e.printStackTrace();
		}
		if (sheet != null) {
			spriteSheet = sheet;
		}
		else {
			System.err.println("Failed to load sprite sheet for entity: " + entName);
		}
	}
}
