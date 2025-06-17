package entity;

import interaction.DialogInteraction;
import interaction.Interaction;
import main.GamePanel;

public class Npc extends Entity {
	
	public int distance;
	public boolean isInteractive = true;
	private Interaction dialogInteraction;

	public Npc(String name, int x, int y, boolean isInteractive) {
		super(name, x, y);
		this.isInteractive = isInteractive;
	}
	
	public void newDialog(String dialog, int d) {
		distance = d;
		this.dialogInteraction = new DialogInteraction(dialog);
	}
	
	public Interaction getDialogInteraction() {
		return dialogInteraction;
	}
	
	public void checkDialog() {
	    int dx = (this.x + this.width/2) - (GamePanel.player.x + GamePanel.player.width/2);
	    int dy = (this.y + this.height/2) - (GamePanel.player.y + GamePanel.player.height/2);
	    double dist = Math.sqrt(dx * dx + dy * dy);

	    if (dist <= this.distance) {
	        System.out.println("Player is within dialog range: " + dist);
	        if (isInteractive && dialogInteraction != null) {
	            dialogInteraction.interact(this, GamePanel.player);
	        }
	    }
	}
	
}
