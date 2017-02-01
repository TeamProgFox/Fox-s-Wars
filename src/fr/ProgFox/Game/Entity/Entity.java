package fr.ProgFox.Game.Entity;

public abstract class Entity {

	public abstract void update();

	public abstract void input();

	public abstract void move(float xDir, float yDir, float zDir);

}
