package ui;

import javax.swing.JLayeredPane;
import main.GamePanel;

public class UIPanel extends JLayeredPane{

	private static final long serialVersionUID = 1L;
	public UIPanel() {
		this.setLayout(null);
		this.setOpaque(false);
		this.setFocusable(false);
		this.setVisible(true);
		this.setPreferredSize(GamePanel.gamePanel.getPreferredSize());
	}

}
