package fr.ProgFox.Game.Entities;

import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Math.Vec3;

public abstract class Entity {
	public Vec3 position;
	public Vec3 rotation;

	public World world;

	public Entity(Vec3 pos, Vec3 rot) {
		this.position = pos;
		this.rotation = rot;
	}

	public String name;

	public abstract void update();

	public abstract void render();

	public void move(float xDir, float yDir, float zDir) {
		if (!isColliding(xDir, 0, 0)) {
			position.addX(xDir);
		}
		if (!isColliding(0, yDir, 0)) {
			position.addY(yDir);
		}
		if (!isColliding(0, 0, zDir)) {
			position.addZ(zDir);
		}
		if (Var.flyMode) {
			position.addX(xDir);
			position.addY(yDir);
			position.addZ(zDir);
		}
	}

	public boolean isColliding(float xDir, float yDir, float zDir) {

		float rayon = 0.3f;

		float x0 = (position.getX() + xDir) - rayon;
		float x1 = (position.getX() + xDir) + rayon;

		float y0 = (position.getY() + yDir) - rayon - 0.40f;
		float y1 = (position.getY() + yDir) + rayon;

		float z0 = (position.getZ() + zDir) - rayon;
		float z1 = (position.getZ() + zDir) + rayon;

		if (world.getBlock(x0, y0, z0) != null) {
			return true;
		}
		if (world.getBlock(x1, y0, z0) != null) {
			return true;
		}
		if (world.getBlock(x1, y1, z0) != null) {
			return true;
		}
		if (world.getBlock(x0, y1, z0) != null) {
			return true;
		}

		if (world.getBlock(x0, y0, z1) != null) {
			return true;
		}
		if (world.getBlock(x1, y0, z1) != null) {
			return true;
		}
		if (world.getBlock(x1, y1, z1) != null) {
			return true;
		}
		if (world.getBlock(x0, y1, z1) != null) {
			return true;
		}

		return false;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
