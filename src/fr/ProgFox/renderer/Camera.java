package fr.ProgFox.renderer;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.World.World;
import fr.ProgFox.newMath.Vector3f;

public class Camera {

	public Player player;
	World world;

	public Camera(Vector3f position, World world) {
		player = new Player(world);
		player.position = position;
		player.rotation = new Vector3f(0, 0, 0);
		this.world = world;
	}

	public void update() {
		player.update();
	}

	public void input() {
		player.input();
	}

	public Vector3f getPosition() {
		return player.position;
	}

	public void setPosition(Vector3f position) {

		player.position = position;
	}

}
