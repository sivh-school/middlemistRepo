package entity;

public class GameObject extends Entity {
	
	public boolean solid;
	
	public GameObject(String name, int x, int y, boolean solid) {
		super(name, x, y);
		this.entCollide.canCollide = solid;
		this.solid = solid;
	}
}
