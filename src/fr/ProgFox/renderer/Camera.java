package fr.ProgFox.renderer;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.World;

public class Camera {

	public Player player;
	World world;

	public Camera(Vec3 position, World world) {
		player = new Player(world);
		player.position = position;
		player.rotation = new Vec3(0, 0, 0);
		this.world = world;
	}

	public void update() {
	}

	public void input() {
	}

	public Vec3 getPosition() {
		return player.position;
	}

	public void setPosition(Vec3 position) {

		player.position = position;
	}

}
