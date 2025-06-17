package interaction;

import entity.Entity;
import entity.Player;

public class DialogInteraction implements Interaction {
	
	public String dialog;
	
	public DialogInteraction(String dialog) {
		this.dialog = dialog;
	}

	@Override
	public void interact(Entity ent, Player play) {
		DialogPanel.toggleVisibility();
		DialogPanel.loadDialog(dialog);
	}
}
