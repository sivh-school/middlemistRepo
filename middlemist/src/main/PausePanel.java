package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class PausePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	final int pauseW = 75, pauseH = 25;
	static PausePanel pp;
	JLabel pauseText;
	
	
	public PausePanel() {
		this.setPreferredSize(new Dimension(pauseW, pauseH));
		this.setBackground(Color.white);
		pauseText = new JLabel();
		pauseText.setText("PAUSED");
		this.add(pauseText);
	}
	
	public static void setPause(PausePanel pp) {
		PausePanel.pp = pp;
	}
	
	public static void toggleVisibility() {
		pp.setVisible(!pp.isVisible());
	}
}
