package entity;

import java.util.ArrayList;

public class EntityLoader {
	
	public static ArrayList<Entity> initializedEnts = new ArrayList<>();
	public ArrayList<Entity> loadedEnts = new ArrayList<>();
	public ArrayList<Entity> unloadedEnts = new ArrayList<>();
	
	public void loadEntity(Entity ent) {
		if (initializedEnts.contains(ent)) {
			initializedEnts.remove(ent);
		}
		if (loadedEnts.contains(ent)) {
			System.err.println("Entity " + ent.entName + " already exists in the list.");
			return;
		}
		else {
			if (unloadedEnts.contains(ent)) {
				unloadedEnts.remove(ent);
			}
			loadedEnts.add(ent);
			System.out.println("Entity " + ent.entName + " appended successfully.");
		}
	}
	
	public void reloadEntity(Entity ent) {
		if (loadedEnts.contains(ent)) {
			unloadedEnts.remove(ent);
			loadedEnts.add(ent);
		} else {
			System.err.println("Entity " + ent.entName + " is not loaded. Cannot reload.");
		}
	}
	
	public void unloadEntity(Entity ent) {
		if (initializedEnts.contains(ent)) {
			initializedEnts.remove(ent);
		}
		if (loadedEnts.contains(ent)) {
			loadedEnts.remove(ent);
		}
		unloadedEnts.add(ent);
	}
}
