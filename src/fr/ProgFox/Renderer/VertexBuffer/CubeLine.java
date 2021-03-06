package fr.ProgFox.Renderer.VertexBuffer;

import static org.lwjgl.opengl.GL11.*;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.Shader;

public class CubeLine {
	public Vec3 color;
	private VBO3D cube;

	public CubeLine(Vec3 color) {
		this.color = color;
		this.cube = new VBO3D();

	}

	public void add(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean isInCenter) {
		if (!isInCenter) {

			cube.init(24);
			cube.addVertex(x, y, z, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y, z, new Vec3(1, 1, 1));

			cube.addVertex(x, y + sizeY, z, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));

			cube.addVertex(x, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x, y, z, new Vec3(1, 1, 1));
			cube.addVertex(x, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x, y + sizeY, z, new Vec3(1, 1, 1));
			cube.addVertex(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y, z, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x, y, z, new Vec3(1, 1, 1));
			cube.addVertex(x, y + sizeY, z, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y, z, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));

			cube.addVertex(x, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.end();
		} else {
			cube.init(24);
			cube.addVertex(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.addVertex(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.addVertex(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.end();
		}
	}

	public void update(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean isInCenter) {
		if (!isInCenter) {

			cube.init(24);
			cube.clearBuffer();
			cube.update(x, y, z, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y, z, new Vec3(1, 1, 1));

			cube.update(x, y + sizeY, z, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));

			cube.update(x, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x, y, z, new Vec3(1, 1, 1));
			cube.update(x, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x, y + sizeY, z, new Vec3(1, 1, 1));
			cube.update(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y, z, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x, y, z, new Vec3(1, 1, 1));
			cube.update(x, y + sizeY, z, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y, z, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z, new Vec3(1, 1, 1));

			cube.update(x, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.updateEnd();
		} else {
			cube.init(24);
			cube.update(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x - sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y - sizeY, z - sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z - sizeZ, new Vec3(1, 1, 1));

			cube.update(x - sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x - sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.update(x + sizeX, y - sizeY, z + sizeZ, new Vec3(1, 1, 1));
			cube.update(x + sizeX, y + sizeY, z + sizeZ, new Vec3(1, 1, 1));

			cube.updateEnd();
		}
	}

	public void render(int mode, int wl, Mat4 perspective, Mat4 transform, Shader shader) {
		glLineWidth(wl);
		cube.render(mode, perspective, transform, shader);
	}
}
