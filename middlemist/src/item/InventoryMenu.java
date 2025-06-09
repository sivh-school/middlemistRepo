package item;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import entity.Player;
import main.GamePanel;

public class InventoryMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	Player player;
	static InventoryMenu im;
	final int inW = 128, inH = 128;
	
	public InventoryMenu() {
		player = GamePanel.player;
		this.setPreferredSize(new Dimension(inW, inH));
		this.setBackground(Color.red);
	}
	
	public void updateInventory() {
		for (Item item : player.inventory) {
			System.out.println(item);
		}
	}
	
	public static void setInv(InventoryMenu im) {
		InventoryMenu.im = im;
	}
	
	public static void toggleVisibility() {
		System.out.println("Toggling inventory visibility");
		im.setVisible(!im.isVisible());
	}

}
