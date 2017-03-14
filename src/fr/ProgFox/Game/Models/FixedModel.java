package fr.ProgFox.Game.Models;

import static org.lwjgl.opengl.GL11.*;

import java.nio.*;

import fr.ProgFox.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.VertexBuffer.*;
import fr.ProgFox.Utils.*;

public class FixedModel {
	private VBO3D model;
	public int size;
	int divSize;
	private String name;
	private Vec3 pos = new Vec3(), rot = new Vec3();
	private Vec3 currentPosition = new Vec3();

	public FixedModel(String name, int divSize, Vec3 pos) {
		model = new VBO3D();
		this.name = name;
		this.divSize = divSize;
		this.currentPosition = pos;
		initSize();
		model.init(size * 24);

		loadObjects(name);

		model.end();
	}

	public FixedModel(String name, int divSize) {
		model = new VBO3D();
		this.name = name;
		this.divSize = divSize;
		initSize();
		model.init(size * 24);

		loadObjects(name);

		model.end();
	}

	public void loadObjects(String name) {
		String e[];
		String line[];
		if (Loader.loadFile("saves/objects/" + name + ".tpf") != null) {
			line = Loader.loadFile("saves/objects/" + name + ".tpf").split("\n");

			for (String a : line) {
				e = a.split(";");
				float x = Float.parseFloat(e[0]);
				float y = Float.parseFloat(e[1]);
				float z = Float.parseFloat(e[2]);

				float r = Float.parseFloat(e[3]);
				float g = Float.parseFloat(e[4]);
				float b = Float.parseFloat(e[5]);

				model.addQuads(currentPosition.x + x / divSize, currentPosition.y + y / divSize,
						currentPosition.z + z / divSize, new Vec3(r, g, b), 1f / divSize);

			}
		}
	}

	public void setModelViewMatrix(Vec3 pos, Vec3 rot) {
		this.pos = pos;
		this.rot = rot;
	}

	public void initSize() {
		String line[];
		if (Loader.loadFile("saves/objects/" + name + ".tpf") != null) {
			line = Loader.loadFile("saves/objects/" + name + ".tpf").split("\n");
			for (String a : line) {
				size++;
			}
		}
	}

	float x, xx, y, yy, z, zz;
	float rx, ry, rxx, ryy;

	public void render(Vec3 pos, Vec3 rot) {
		x = Main.getMain().getPlayer().position.x;
		y = Main.getMain().getPlayer().position.y;
		z = Main.getMain().getPlayer().position.z;

		xx = pos.x;
		yy = pos.y;
		zz = pos.z;

		rx = Main.getMain().getPlayer().rotation.x;
		ry = Main.getMain().getPlayer().rotation.y;

		rxx = rot.x;
		ryy = rot.y;

		model.render(
				GL_QUADS, Main.getMain().getCamera().getProjectionMatrix(), Main.getMain().getCamera()
						.getModelViewMatrix(new Vec3(x - xx, y - yy, z - zz), new Vec3(rx + rxx, ry + ryy, 0)),
				Main.getMain().getShader());
	}

	public void render() {
		model.render(GL_QUADS,
				Main.getMain().getCamera().getProjectionMatrix(), Main.getMain().getCamera()
						.getModelViewMatrix(Main.getMain().getPlayer().position, Main.getMain().getPlayer().rotation),
				Main.getMain().getShader());
	}

	public FloatBuffer getBuffer(Vec3 pos) {
		model.init(size * 24);
		String e[];
		String line[];
		if (Loader.loadFile("saves/objects/" + name + ".tpf") != null) {
			line = Loader.loadFile("saves/objects/" + name + ".tpf").split("\n");

			for (String a : line) {
				e = a.split(";");
				float x = Float.parseFloat(e[0]);
				float y = Float.parseFloat(e[1]);
				float z = Float.parseFloat(e[2]);

				float r = Float.parseFloat(e[3]);
				float g = Float.parseFloat(e[4]);
				float b = Float.parseFloat(e[5]);

				model.addQuads(pos.x + x / divSize, pos.y + y / divSize, pos.z + z / divSize, new Vec3(r, g, b),
						1f / divSize);
			}
		}
		model.buffer.flip();

		return model.buffer;
	}

	public void clear() {
		if (model.buffer != null) {

			model.buffer.clear();
			model.buffer = null;
		}
	}
}
