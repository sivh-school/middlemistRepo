package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Entity {

	//Variables

	public static ArrayList<Entity> allEnts = new ArrayList<>();

	public String entName;
	public BufferedImage spriteSheet;
	public int spriteIndex, x, y;

	//Constructors

	public Entity(String name, int x, int y) {
		entName = name;
		this.x = x;
		this.y = y;
	}

	//Methods

	public BufferedImage innitSheet(String path) {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}

	public void appendEntity() {
		if (this.spriteSheet != null) {
			allEnts.add(this);
		}
	}

}
