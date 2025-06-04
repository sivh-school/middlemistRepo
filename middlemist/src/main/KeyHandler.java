package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean wKey, sKey, aKey, dKey, escKey = false;
	public String lastKeyPressed = "";

	@Override
	public void keyTyped(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_ESCAPE) {
			if (!escKey) {
				escKey = true;
			}
			else {
				escKey = false;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			lastKeyPressed = "w";
			wKey = true;
		}
		if (code == KeyEvent.VK_S) {
			lastKeyPressed = "s";
			sKey = true;
		}
		if (code == KeyEvent.VK_A) {
			lastKeyPressed = "a";
			aKey = true;
		}
		if (code == KeyEvent.VK_D) {
			lastKeyPressed = "d";
			dKey = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			wKey = false;
		}
		if (code == KeyEvent.VK_S) {
			sKey = false;
		}
		if (code == KeyEvent.VK_A) {
			aKey = false;
		}
		if (code == KeyEvent.VK_D) {
			dKey = false;
		}
	}

}
