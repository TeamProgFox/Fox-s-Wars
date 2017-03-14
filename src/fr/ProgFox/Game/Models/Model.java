package fr.ProgFox.Game.Models;

import fr.ProgFox.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.VertexBuffer.*;
import fr.ProgFox.Utils.*;

import static org.lwjgl.opengl.GL11.*;

import javax.xml.stream.events.*;

public class Model {
	private VBO3D model;
	int size;
	int divSize;
	private String name;
	private Vec3 pos = new Vec3(), rot = new Vec3();

	public Model(String name, int divSize) {
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

				model.addQuads(x / divSize, y / divSize, z / divSize, new Vec3(r, g, b), 1f / divSize);

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

	public void render() {
		model.render(GL_QUADS, Main.getMain().getCamera().getProjectionMatrix(),
				Main.getMain().getCamera().getModelViewMatrix(pos, rot), Main.getMain().getShader());
	}

	public void render(Vec3 pos, Vec3 rot) {
		model.render(GL_QUADS, Main.getMain().getCamera().getProjectionMatrix(),
				Main.getMain().getCamera().getModelViewMatrix(pos, rot), Main.getMain().getShader());
	}

	public void render(Mat4 projection, Vec3 pos, Vec3 rot) {
		model.render(GL_QUADS, projection,
				Main.getMain().getCamera().getModelViewMatrix(pos, rot), Main.getMain().getShader());
	}

}
