package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	private static boolean playerExists = false;
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
		idleTimer++;
		if (idleTimer > 12) {
			idleTimer = 0;
			spriteIndex++;
		}
		System.out.println(keyH.lastKeyPressed);
		if (keyH.escKey) {
			if (!(gp.paused)) {
				gp.pause();
			}
		}
		else {
			if (gp.paused) {
				gp.resume();
			}
			else if (keyH.wKey) {
				y -= speed;
			}
			else if (keyH.sKey) {
				y += speed;
			}
			else if (keyH.aKey) {
				x -= speed;
			}
			else if (keyH.dKey) {
				x += speed;
			}
		}
	}

}
