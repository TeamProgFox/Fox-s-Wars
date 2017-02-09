package fr.ProgFox.Utils.VertexBuffer;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;

public class CubeLine {
	public Vec3 color;
	private Shader shader;
	private VBO cube;
	public CubeLine(Vec3 color) {
		this.color = color;
		this.shader = new ColorShader();
		this.cube = new VBO();

	}

	public void add(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean isInCenter) {
		if (!isInCenter) {

			cube.init(24, shader);
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
			cube.init(24, shader);
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

			cube.init(24, shader);
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
			cube.init(24, shader);
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

	public void render(int mode, Camera cam, int wl) {
		glLineWidth(wl);
		cube.render(mode, cam);
		
	}
}
