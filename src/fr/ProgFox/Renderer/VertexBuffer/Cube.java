package fr.ProgFox.Renderer.VertexBuffer;

import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.ColorShader;
import fr.ProgFox.Renderer.Shader.Shader;

public class Cube {

	private Vec3 color;
	private Shader shader;
	private VBO3D cube;

	public Cube(Vec3 color) {
		this.color = color;
		this.shader = new ColorShader();
		this.cube = new VBO3D();
	}

	public void add(float x, float y, float z, float size, boolean isInCenter) {
		cube.init(24);
		if (!isInCenter) {

			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y, z + size, new Vec3(color.x, color.y, color.z));

		} else {
			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.addVertex(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.addVertex(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
		}
		cube.end();

	}

	public void update(float x, float y, float z, float size, boolean isInCenter) {
		cube.init(24);
		cube.clearBuffer();
		if (!isInCenter) {

			cube.update(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z, new Vec3(color.x, color.y, color.z));

			cube.update(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x, y, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x, y, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x + size, y, z, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y, z + size, new Vec3(color.x, color.y, color.z));

		} else {
			cube.update(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));

			cube.update(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x - size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y - size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x - size, y + size, z + size, new Vec3(color.x, color.y, color.z));

			cube.update(x + size, y - size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z - size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y + size, z + size, new Vec3(color.x, color.y, color.z));
			cube.update(x + size, y - size, z + size, new Vec3(color.x, color.y, color.z));
		}
		cube.updateEnd();

	}

	public void render(int mode, Mat4 perspective, Mat4 transform) {
		cube.render(mode, perspective, transform, shader);
	}
}
