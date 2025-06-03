package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public int speed = 4, pauseTimer = 0;

	public Player(String name, int x, int y, GamePanel gp) {
		super(name, x, y);
		this.gp = gp;
		this.keyH = gp.keyH;
	}

	public void playUpdate() {

		if (keyH.escKey) {
			if (!(gp.paused)) {
				gp.pause();
			}
		}
		else {
			if (gp.paused) {
				gp.resume();
			}
			if (keyH.wKey) {
				if (!(keyH.sKey || keyH.aKey || keyH.dKey)) {
					y -= speed;
				}
			}
			if (keyH.sKey) {
				if (!(keyH.wKey || keyH.aKey || keyH.dKey)) {
					y += speed;
				}
			}
			if (keyH.aKey) {
				if (!(keyH.sKey || keyH.wKey || keyH.dKey)) {
					x -= speed;
				}
			}
			if (keyH.dKey) {
				if (!(keyH.wKey || keyH.aKey || keyH.sKey)) {
					x += speed;
				}
			}
		}
	}

}
