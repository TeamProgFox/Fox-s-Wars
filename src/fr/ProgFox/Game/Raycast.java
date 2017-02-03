package fr.ProgFox.Game;

import java.util.ArrayList; 
import java.util.List;
import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.World;

public class Raycast {
	private List<Vec3> points;
	private Player player;
	public static int teste = 0;

	public Raycast(Player player) {
		this.player = player;
		this.points = new ArrayList<Vec3>();
		for (int i = 0; i < 2 * 32; i++) {
			points.add(new Vec3());
		}
	}

	public void update() {
		Vec3 pos2 = new Vec3();
		pos2.x = Math.abs(player.position.x);
		pos2.y = Math.abs(player.position.y);
		pos2.z = Math.abs(player.position.z);

		Vec3 dir2 = new Vec3();
		dir2.x = player.getForward().x;
		dir2.y = -player.getForward().y;
		dir2.z = player.getForward().z;
		int i = 0;
		for (Vec3 v : points) {
			Vec3 pos = pos2.copy().add(dir2.copy().mul(i / 16.0f));
			// pos.y += 1f;
			v.set(pos);
			i++;
		}
	}

	public Vec3 getBlock(World world) {
		for (Vec3 v : points) {

			boolean block = world.getBlock(v.x, v.y, v.z) != null;

			if (block) {
				// AMETTRE ICI
				teste = 1;
				return new Vec3(v.x, v.y, v.z);
			}

			

		}

		return null;

	}

}
