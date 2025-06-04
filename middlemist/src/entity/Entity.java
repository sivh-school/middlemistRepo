package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Entity {

	//Variables

	public static ArrayList<Entity> allEnts = new ArrayList<>();

	public String entName;
	public BufferedImage spriteSheet;
	public int spriteIndex, x, y, idleTimer = 0;

	//Constructors

	public Entity(String name, int x, int y) {
		entName = name;
		this.x = x;
		this.y = y;
		spriteIndex = 0;
	}

	//Methods

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

	public void appendEntity() {
		if (allEnts.contains(this)) {
			System.err.println("Entity " + entName + " already exists in the list.");
			return;
		}
		else if (entName == null || entName.isEmpty()) {
			System.err.println("Entity name is null or empty. Cannot append entity.");
			return;
		}
		else {
			if (spriteSheet == null) {
				System.err.println("Sprite sheet not initialized for entity: " + entName);
				return;
			}
			else {
				allEnts.add(this);
				System.out.println("Entity " + entName + " appended successfully.");
			}
		}
	}

}
