package fr.ProgFox.Utils.VertexBuffer;

import java.nio.FloatBuffer; 

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Math.Vec4;
import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;

public class Cube {
	private Vec3 color;
	private Shader shader;
	private VBO cube;

	public Cube(Vec3 color) {
		this.color = color;
		this.shader = new ColorShader();
		this.cube = new VBO();
	}

	public void add(float x, float y, float z, float size, boolean isInCenter) {
		cube.init(24, shader);
		if (!isInCenter) {

			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));

		} else {
			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z - size + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
		}
		cube.end();

	}

	public void update(float x, float y, float z, float size) {
		cube.init(24, shader);
		cube.clearBuffer();
		cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));

		cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

		cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));

		cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

		cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));

		cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
		cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));

		cube.end();

	}

	public void render(Player player, int mode) {
		cube.render(player, mode);
	}
}
