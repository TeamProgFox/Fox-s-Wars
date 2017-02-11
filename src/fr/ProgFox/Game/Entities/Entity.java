package fr.ProgFox.Game.Entities;

import fr.ProgFox.Math.Vec3;

public abstract class Entity {
	public int id;
	public String name;
	public abstract void update();

	public abstract void render();
	
	
}
