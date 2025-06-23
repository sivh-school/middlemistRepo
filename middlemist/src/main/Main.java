package main;

import javax.swing.*;
import javax.swing.WindowConstants;

import interaction.DialogPanel;
import item.InventoryMenu;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		frame.setContentPane(gamePanel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		
		PausePanel pausePanel = new PausePanel();
		PausePanel.setPause(pausePanel);
		InventoryMenu inventoryMenu = new InventoryMenu();
		InventoryMenu.setInv(inventoryMenu);
		DialogPanel dialogPanel = new DialogPanel();
		DialogPanel.setDialog(dialogPanel);
		frame.add(pausePanel);
		frame.add(inventoryMenu);
		frame.add(dialogPanel, "South");
		pausePanel.setVisible(false);
		inventoryMenu.setVisible(false);

		frame.setTitle("middlemist");
		frame.setVisible(true);

		gamePanel.startGameThread();
	}
}
