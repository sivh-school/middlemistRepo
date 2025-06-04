package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	private static boolean playerExists = false;
	private int walkTimer = 0;
	private boolean walkFrame = true, walking = false;
	GamePanel gp;
	KeyHandler keyH;
	public int speed;

	public Player(String name, int x, int y, GamePanel gp) {
		super(name, x, y);
		if (!(playerExists)) {
			playerExists = true;
			this.gp = gp;
			this.keyH = gp.keyH;
			speed = 4;
		}
		else {
			System.err.println("Player already exists. Cannot create another instance.");
		}
	}

	public void playUpdate() {
		if (walking) {
			walkTimer++;
			if (walkTimer > 30) {
				walkTimer = 0;
				walkFrame = !walkFrame;
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
						gp.world.y -= speed;
						walking = true;
						playDraw(0);
					}
					break;
				case "s":
					if (keyH.sKey) {
						gp.world.y += speed;
						walking = true;
						playDraw(0);
					}
					break;
				case "a":
					if (keyH.aKey) {
						gp.world.x -= speed;
						walking = true;
						playDraw(0);
					}
					break;
				case "d":
					if (keyH.dKey) {
						gp.world.x += speed;
						walking = true;
						playDraw(0);
					}
					break;
				default:
					walking = false;
					playDraw(0);
					break;
			}
		}
	}
	public void playDraw(int index) {
		if (walkFrame) {
			spriteIndex = index;
		}
		else {
			spriteIndex = index + 1;
		}
	}
}
