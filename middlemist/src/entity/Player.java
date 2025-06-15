package entity;

import java.util.ArrayList;

import item.InventoryMenu;
import item.Item;
import main.GamePanel;
import main.KeyHandler;
import main.PausePanel;

public class Player extends Entity{
	
	public static final int levelMultiplier = 25;
	
	public int maxHealth = 20, health = maxHealth, xp = 0, level = 1;
	public int malice = 10, melancholy = 10, joy = 10, sorrow = 10;
	private static boolean playerExists = false;
	private int walkTimer = 0, idleTimer = 0, legCount = 0;
	private boolean walkFrame = false, idleFrame = true, left = true, walking = false;
	GamePanel gp;
	KeyHandler keyH;
	
	public ArrayList<Item> inventory;

	public Player(String name, int x, int y, GamePanel gp) {
		super(name, x, y);
		if (!(playerExists)) {
			playerExists = true;
			this.gp = gp;
			this.keyH = gp.keyH;
			this.entCollide.setDimensions(28, 60);
			inventory = new ArrayList<>();
		}
		else {
			System.err.println("Player already exists. Cannot create another instance.");
		}
	}
	
	private boolean verifyPos(String key) {
	    int nextX = x;
	    int nextY = y;
	    switch (key) {
	        case "w":
	            nextY = y - speed; // Up
	            break;
	        case "s":
	            nextY = y + speed; // Down
	            break;
	        case "a":
	            nextX = x - speed; // Left
	            break;
	        case "d":
	            nextX = x + speed; // Right
	            break;
	    }
	    Entity nextPlayer = new Entity("NextPlayer", nextX, nextY);
	    nextPlayer.entCollide.setDimensions(28, 62);
	    entCollide.canCollide = false;
	    nextPlayer.entCollide.collisionUpdate(GamePanel.world);
	    nextPlayer.entCollide.canCollide = false;
	    if (nextPlayer.entCollide.colliding) {
	    	GamePanel.entLoader.removeEntity(nextPlayer);
		    entCollide.canCollide = true;
	    	return false;
	    }
	    else {
	    	GamePanel.entLoader.removeEntity(nextPlayer);
		    entCollide.canCollide = true;
	    	return true;
	    }
	}

	public void playUpdate() {
		if (walking) {
			idleTimer = 0;
			walkTimer++;
			if (walkTimer > 15) {
				legCount++;
				walkTimer = 0;
				walkFrame = !walkFrame;
				if (legCount > 1) {
					legCount = 0;
					left = !left;
				}
			}
		}
		else {
			walkTimer = 0;
			idleTimer++;
			if (idleTimer > 30) {
				idleTimer = 0;
				idleFrame = !idleFrame;
			}
		}
			switch (keyH.lastKeyPressed) {
				case "w":
					if (keyH.wKey) {
						if (verifyPos("w")) {
							GamePanel.world.y += speed;
						}
						walking = true;
						playDrawWalk(1, 0);
					}
					else {
						walking = false;
					}
					break;
				case "s":
					if (keyH.sKey) {
						if (verifyPos("s")) { 
							GamePanel.world.y -= speed;
						}
						walking = true;
						playDrawWalk(1, 1);
					}
					else {
						walking = false;
					}
					break;
				case "a":
					if (keyH.aKey) {
						if (verifyPos("a")) { 
							GamePanel.world.x += speed;
						}
						walking = true;
						playDrawWalk(5, 5);
					}
					else {
						walking = false;
					}
					break;
				case "d":
					if (keyH.dKey) {
						if (verifyPos("d")) { 
							GamePanel.world.x -= speed;
						}
						walking = true;
						playDrawWalk(9, 9);
					}
					else {
						walking = false;
					}
					break;
			}
			if (!(walking) ) {
				switch (keyH.lastKeyPressed) {
					case "w", "s":
						playDrawIdle(0);
						break;
					case "a":
						playDrawIdle(4);
						break;
					case "d":
						playDrawIdle(8);
						break;
					default:
						playDrawIdle(0);
						break;
				}
			}
		}
	private void playDrawWalk(int index, int restFrame) {
		if (walkFrame) {
			spriteIndex = restFrame;
		}
		else {
			if (left) {
				spriteIndex = index + 1;
			}
			else {
				spriteIndex = index + 2;
			}
		}
	}
	
	private void playDrawIdle(int index) {
		if (idleFrame) {
			spriteIndex = index;
		}
		else {
			spriteIndex = index + 1;
		}
	}
	
	public void updateStats() {
		if (health > maxHealth) {
			health = maxHealth;
		}
		else if (health < 0) {
			health = 0;
		}
		if (inventory.size() > InventoryMenu.inventoryMax) {
			ItemEntity droppedItem = new ItemEntity(inventory.get(InventoryMenu.inventoryMax).name, x, y + GamePanel.player.height, inventory.get(InventoryMenu.inventoryMax));
			GamePanel.entLoader.loadEntity(droppedItem);
			inventory.remove(-1);
		}
	}
	
	public String updateCharacter() {
		String iconPath = null;
		if (health >= maxHealth) {
			iconPath = "/res/sprites/player.png";
		}
		else if (health >= maxHealth / 2) {
			iconPath = "/res/sprites/player.png";
		}
		else {
			iconPath = "/res/sprite/player.png";
		}
		return iconPath;
	}
	
	public void pauseUpdate() {
		if (keyH.escKey) {
			System.out.println("Escape key pressed");
			PausePanel.toggleVisibility();
			keyH.escKey = false; // Reset the escape key to prevent repeated toggling
		}
		if (keyH.invKey) {
			System.out.println("Inventory key pressed");
			InventoryMenu.toggleVisibility();
			keyH.invKey = false; // Prevents toggling inventory multiple times
		}
	}
}
