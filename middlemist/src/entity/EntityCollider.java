package entity;

import java.util.ArrayList;

import main.GamePanel;

public class EntityCollider {
	
	public static ArrayList<EntityCollider> colliders = new ArrayList<>();
	public boolean colliding, canCollide = true;
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
		if (other != this && canCollide && other.canCollide) {
	        return x < other.x + other.width &&
	               x + width > other.x &&
	               y < other.y + other.height &&
	               y + height > other.y;
	    } else {
	        return false;
	    }
    }
	
	public void collisionUpdate() {
		for (EntityCollider other : colliders) {
			if (other != this && collidesWith(other)) {
				colliding = true;
				other.colliding = true;
				break;
			}
			else {
				colliding = false;
				other.colliding = false;
			}
		}
	}
	
	public void collisionUpdate(EntityCollider other) {
		if (other != this && collidesWith(other)) {
			colliding = true;
			other.colliding = true;
		}
		else {
			colliding = false;
			other.colliding = false;
		}
	}
	
	public void setDimensions(int w, int h) {
		width = w;
		height = h;
		x = ent.x + (ent.width - width)/2;
		y = ent.y + (ent.height - height)/2;
	}
}
