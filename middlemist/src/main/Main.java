package main;

import javax.swing.*;
import javax.swing.WindowConstants;

import interaction.DialogPanel;
import item.InventoryMenu;

public class Main {

	public static void main(String[] args) {
		//Creates the window frame that is displayed on the users device, and tells it to stop all ascociated programs when closed
		// and to not allow itself to be resized.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);

		//Create the gamePanel which displays the main game enviornment which the user plays in, and sets it to the content pane
		// of the frame.
		GamePanel gamePanel = new GamePanel();
		frame.setContentPane(gamePanel);

		//Pack the frame setting its bounds to the preffered size of the gamePanel, and sets its location to the center of the 
		// screen.
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		//Add all other menus so far, and initialize them
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

		//Set title, visibility and start game thread.
		frame.setTitle("middlemist");
		frame.setVisible(true);

		gamePanel.startGameThread();
	}
}
