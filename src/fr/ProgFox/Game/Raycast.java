package fr.ProgFox.Game;

import java.util.ArrayList;
import java.util.List;
import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.World.World;
import fr.ProgFox.newMath.Vector3f;

public class Raycast {
	private List<Vector3f> points;
	private Player player;
	public static int teste = 0;

	public Raycast(Player player) {
		this.player = player;
		this.points = new ArrayList<Vector3f>();
		for (int i = 0; i < 2 * 32; i++) {
			points.add(new Vector3f());
		}
	}

	public void update() {
		Vector3f pos2 = new Vector3f();
		pos2.x = Math.abs(player.position.x);
		pos2.y = Math.abs(player.position.y);
		pos2.z = Math.abs(player.position.z);

		Vector3f dir2 = new Vector3f();
		dir2.x = player.getForward().x;
		dir2.y = -player.getForward().y;
		dir2.z = player.getForward().z;
		int i = 0;
		for (Vector3f v : points) {
			Vector3f pos = pos2.copy().add(dir2.copy().mul(i / 16.0f));
			// pos.y += 1f;
			v.set(pos);
			i++;
		}
	}

	public Vector3f getBlock(World world) {
		for (Vector3f v : points) {

			boolean block = world.getBlock(v.x, v.y, v.z) != null;

			if (block) {
				// AMETTRE ICI
				teste = 1;
				return new Vector3f(v.x, v.y, v.z);
			}

			

		}

		return null;

	}

}
