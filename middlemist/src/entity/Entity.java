package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {

	//Variables
	
	public static int entCount = 0;
	
	public int id;
	public String entName;
	public BufferedImage spriteSheet;
	public int spriteIndex, x, y, speed = 4, frameCount = 0, width, height;
	public EntityCollider entCollide;
	
	//Constructors

	public Entity(String name, int x, int y) {
		entCount++;
		id = entCount;
		entName = name;
		this.x = x;
		this.y = y;
		spriteIndex = 0;
		width = 64;
		height = 64;
		entCollide = new EntityCollider(this, width, height);
		innitEnt();
	}

	//Methods
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		entCollide = new EntityCollider(this, width, height);
	}
	
	private void innitEnt() {
		EntityLoader.initializedEnts.add(this);
	}

	public void innitSheet(String imgPath) {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(getClass().getResourceAsStream("/res/sprites/" + imgPath));
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
