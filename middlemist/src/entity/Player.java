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
		}
		else {
			System.err.println("Player already exists. Cannot create another instance.");
		}
	}

	public void playUpdate() {
		if (walking) {
			idleTimer = 0;
			walkTimer++;
			if (walkTimer > 30) {
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
						gp.world.y += speed;
						walking = true;
						playDrawWalk(1, 0);
					}
					else {
						walking = false;
					}
					break;
				case "s":
					if (keyH.sKey) {
						gp.world.y -= speed;
						walking = true;
						playDrawWalk(1, 1);
					}
					else {
						walking = false;
					}
					break;
				case "a":
					if (keyH.aKey) {
						gp.world.x += speed;
						walking = true;
						playDrawWalk(5, 5);
					}
					else {
						walking = false;
					}
					break;
				case "d":
					if (keyH.dKey) {
						gp.world.x -= speed;
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
