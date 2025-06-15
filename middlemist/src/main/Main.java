package main;

import javax.swing.*;
import javax.swing.WindowConstants;
import item.InventoryMenu;

public class Main {

	public static final String userDir = System.getProperty("user.home");

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		frame.setContentPane(gamePanel);

		frame.pack();
		PausePanel pausePanel = new PausePanel();
		PausePanel.setPause(pausePanel);
		InventoryMenu inventoryMenu = new InventoryMenu();
		InventoryMenu.setInv(inventoryMenu);
		frame.add(pausePanel);
		frame.add(inventoryMenu);
		pausePanel.setVisible(false);
		inventoryMenu.setVisible(false);

		frame.setTitle("middlemist");
		frame.setVisible(true);

		gamePanel.startGameThread();
	}
}
