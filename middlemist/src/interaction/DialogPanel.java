package interaction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.GamePanel;

public class DialogPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static DialogPanel dp;
	final int width = GamePanel.gamePanel.getWidth();
	final int height = 256;
	public JTextArea dialogText;
	String dialog;
	StringBuilder displayed = new StringBuilder();
	int[] index = {0}; // Use array for effectively final variable in lambda
	Timer timer = new Timer(50, e -> {
		if (index[0] < dialog.length()) {
			if (dialogText.getMaximumSize().height >= dialogText.getSize().height) {
				displayed.append(dialog.charAt(index[0]));
				dp.dialogText.setText(displayed.toString());
				index[0]++;
			}
			else {
				((Timer) e.getSource()).stop();
			}
		} else {
			((Timer) e.getSource()).stop(); // Stop the timer when done
		}
	});
	boolean waitingForInput = false;
	int dIndex = 0;
	
	public DialogPanel() {
		setPreferredSize(new Dimension(width, height));
		this.setDoubleBuffered(true);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.setVisible(false);
		this.setLayout(null);
		dialogText = new JTextArea();
		dialogText.setEditable(false);
		dialogText.setLineWrap(true);
		dialogText.setSelectionColor(null);
		dialogText.setWrapStyleWord(true);
		dialogText.setMaximumSize(this.getPreferredSize());
		dialogText.setBackground(this.getBackground());
		dialogText.setForeground(Color.WHITE);
		dialogText.setOpaque(false);
		dialogText.setCaretColor(Color.WHITE);
		try {
			dialogText.setFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/res/fonts/super-pixel-font/SuperPixel-m2L8j.ttf")).deriveFont(14f));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dialogText.setBounds(20, 20, width - 20, height - 20);
		dialogText.addKeyListener(new java.awt.event.KeyAdapter() {
		    @Override
		    public void keyPressed(java.awt.event.KeyEvent e) {
		        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
		            if (waitingForInput && dialogText.getText().length() == dialog.length()) {
		                waitingForInput = false;
		                dIndex++;
		                showNextDialog();
		            } else if (dialogText.getText().length() < dialog.length()) {
		                timer.stop();
		                dialogText.setText(dialog);
		            }
		        }
		    }
		});
		dialogText.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        if (waitingForInput && dialogText.getText().length() == dialog.length()) {
		            waitingForInput = false;
		            dIndex++;
		            showNextDialog();
		        } else if (dialogText.getText().length() < dialog.length()) {
		            timer.stop();
		            dialogText.setText(dialog);
		        }
		    }
		});
		this.add(dialogText);
	}
	
	public static void setDialog(DialogPanel dialogPanel) {
		DialogPanel.dp = dialogPanel;
	}
	
	public static void toggleVisibility() {
		dp.setVisible(!dp.isVisible());
	    GamePanel.gamePanel.togglePause();
	    GamePanel.gamePanel.enableInputMethods(!dp.isVisible());
	    GamePanel.gamePanel.requestFocus();
	}
	
	private String[] dialogs;
	public static void loadDialog(String dialog) {
		String[] dialogs = dialog.split("NEXT_DIALOG");
		dp.dIndex = 0;
		dp.dialogs = dialogs;
		dp.showNextDialog();
	}
	
	private void showNextDialog() {
	    if (dIndex < dialogs.length) {
	        String d = dialogs[dIndex].trim();
	        if (!d.isEmpty()) {
	        	dialog = d;
	            displayed.setLength(0);
	            index[0] = 0;
	            dialogText.setText("");
	            timer.restart();
	            waitingForInput = true;
	        } else {
	            dIndex++;
	            dialogText.setText(dialog);
	            showNextDialog();
	        }
	    } else {
	    	GamePanel.gamePanel.keyH.pauseF = false;
	        DialogPanel.toggleVisibility();
	    }
	}
	
	

}
