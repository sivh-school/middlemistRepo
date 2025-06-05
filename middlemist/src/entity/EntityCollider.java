package entity;

import java.util.ArrayList;

import main.GamePanel;

public class EntityCollider {
	
	private static ArrayList<EntityCollider> colliders = new ArrayList<>();
	public boolean colliding;
	Entity ent;
	public int x, y, width, height;
	
	public EntityCollider(Entity ent, int w, int h) {
		this.ent = ent;
		x = ent.x;
		y = ent.y;
		width = w;
		height = h;
		colliders.add(this);
	}
	
	public boolean collidesWith(EntityCollider other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }
}
