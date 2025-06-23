package item;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Item {
	
	public static int itemCount = 0;
	
	public int id;
	public String name;
	public BufferedImage icon;
	public boolean isConsumable = false, hasUse = false, isEquippable = false;
	public int healthBoost;
	
	public Item(String name) {
		this.name = name;
		this.id = itemCount++;
	}
	
	public void setIcon(String iconPath) {
		try {
			icon = ImageIO.read(getClass().getResourceAsStream(iconPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void consume() {
		GamePanel.player.health += healthBoost;
	}

}
