package entity;

import item.Item;
import main.GamePanel;

public class ItemEntity extends Entity {
	
	public Item itemPickup;

	public ItemEntity(String name, int x, int y, Item item) {
		super(name, x, y);
		itemPickup = item;
		spriteSheet = item.icon;
	}
	
	public void itemUpdate() {
		if (entCollide.colliding) {
			GamePanel.player.inventory.add(itemPickup);
		}
	}

}
