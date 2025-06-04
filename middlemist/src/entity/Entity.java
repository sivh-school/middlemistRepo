package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Entity {

	//Variables

	public static ArrayList<Entity> loadedEnts = new ArrayList<>();
	public static ArrayList<Entity> unloadedEnts = new ArrayList<>();

	public String entName;
	public BufferedImage spriteSheet;
	public int spriteIndex, x, y, frameCount = 0;
	//Constructors

	public Entity(String name, int x, int y) {
		entName = name;
		this.x = x;
		this.y = y;
		spriteIndex = 0;
		innitEnt();
	}

	//Methods
	
	private void innitEnt() {
		unloadedEnts.add(this);
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

	public void loadEntity() {
		if (loadedEnts.contains(this)) {
			System.err.println("Entity " + entName + " already exists in the list.");
			return;
		}
		else {
			unloadedEnts.remove(this);
			loadedEnts.add(this);
			System.out.println("Entity " + entName + " appended successfully.");
		}
	}
	
	public void unloadEntity() {
		if (loadedEnts.contains(this)) {
			Class<? extends Entity> test = this.getClass();
			if (test.equals(Player.class)) {
				System.err.println("Cannot unload Player entity.");
				return;
			}
			else {
				loadedEnts.remove(this);
				unloadedEnts.add(this);
			}
		} else {
			System.err.println("Entity " + entName + " is not loaded.");
		}
	}

}
