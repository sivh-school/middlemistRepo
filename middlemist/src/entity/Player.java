package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	private static boolean playerExists = false;
	private int walkTimer = 0, idleTimer = 0, legCount = 0;
	private boolean walkFrame = false, idleFrame = true, left = true, walking = false;
	GamePanel gp;
	KeyHandler keyH;

	public Player(String name, int x, int y, GamePanel gp) {
		super(name, x, y);
		if (!(playerExists)) {
			playerExists = true;
			this.gp = gp;
			this.keyH = gp.keyH;
			this.entCollide.setDimensions(28, 62);
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
	    nextPlayer.entCollide.collisionUpdate();
	    nextPlayer.entCollide.canCollide = false;
	    if (nextPlayer.entCollide.colliding) {
	    	gp.entLoader.removeEntity(nextPlayer);
		    entCollide.canCollide = true;
	    	return false;
	    }
	    else {
	    	gp.entLoader.removeEntity(nextPlayer);
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
		if (keyH.escKey) {
			if (!(gp.paused)) {
				gp.pause();
			}
		}
		else {
			if (gp.paused) {
				gp.resume();
			}
			switch (keyH.lastKeyPressed) {
				case "w":
					if (keyH.wKey) {
						if (verifyPos("w")) {
							gp.world.y += speed;
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
							gp.world.y -= speed;
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
							gp.world.x += speed;
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
							gp.world.x -= speed;
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
}
