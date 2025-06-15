package entity;

import item.InventoryMenu;
import item.Item;
import main.GamePanel;

public class ItemEntity extends Entity {
	
	public Item itemPickup;

	public ItemEntity(String name, int x, int y, Item item) {
		super(name, x, y);
		itemPickup = item;
		spriteSheet = item.icon;
		entCollide.canCollide = false;
	}
	
	public void itemUpdate() {
		if (entCollide.intersectsWith(GamePanel.player.entCollide)) {
			GamePanel.player.inventory.add(itemPickup);
			GamePanel.entLoader.removeEntity(this);
			InventoryMenu.im.updateInventory();
		}
	}

}
