package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		frame.setContentPane(gamePanel);

		frame.pack();

		frame.setTitle("middlemist");
		frame.setVisible(true);

		gamePanel.startGameThread();
	}

}
