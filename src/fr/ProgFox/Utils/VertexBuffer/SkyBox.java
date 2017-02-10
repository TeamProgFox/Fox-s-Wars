package fr.ProgFox.Utils.VertexBuffer;

import fr.ProgFox.Game.Entities.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Shader.ColorShader;
import fr.ProgFox.Renderer.Shader.Shader;

public class SkyBox {
	private Vec3 color;
	private Shader shader;
	private VBO skybox;

	public SkyBox(Vec3 color) {
		this.color = color;
		this.shader = new ColorShader();
		this.skybox = new VBO();
	}

	public void add(float x, float y, float z, float size) {
		skybox.init(48, shader);
		float blanc = 0.7f, bleu = 0.3f, bleu2 = 0.3f;

		skybox.addVertex(x - size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x - size, y, z + size, new Vec3(blanc, blanc, blanc));

		skybox.addVertex(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x - size, y + size, z + size, new Vec3(bleu, bleu, blanc));

		skybox.addVertex(x - size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x - size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.addVertex(x - size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y + size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x - size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.addVertex(x - size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x - size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x - size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.addVertex(x - size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x - size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x - size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.addVertex(x + size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.addVertex(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.addVertex(x + size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.addVertex(x + size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.addVertex(x - size, y - size, z - size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x - size, y - size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x + size, y - size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x + size, y - size, z - size, new Vec3(bleu2, bleu2, blanc));

		skybox.addVertex(x - size, y + size, z - size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x - size, y + size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x + size, y + size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.addVertex(x + size, y + size, z - size, new Vec3(bleu2, bleu2, blanc));

		skybox.end();


	}

	public void update(float x, float y, float z, float size) {
		skybox.clearBuffer();
		skybox.init(48, shader);
		float blanc = 0.7f, bleu = 0.3f, bleu2 = 0.3f;

		skybox.update(x - size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x - size, y, z + size, new Vec3(blanc, blanc, blanc));

		skybox.update(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x - size, y + size, z + size, new Vec3(bleu, bleu, blanc));

		skybox.update(x - size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.update(x - size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.update(x - size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y + size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.update(x - size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.update(x - size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.update(x - size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x - size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.update(x - size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.update(x - size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x - size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x - size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.update(x + size, y - size, z - size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y - size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y, z - size, new Vec3(blanc, blanc, blanc));

		skybox.update(x + size, y, z - size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y, z + size, new Vec3(blanc, blanc, blanc));
		skybox.update(x + size, y + size, z + size, new Vec3(bleu, bleu, blanc));
		skybox.update(x + size, y + size, z - size, new Vec3(bleu, bleu, blanc));

		skybox.update(x - size, y - size, z - size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x - size, y - size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x + size, y - size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x + size, y - size, z - size, new Vec3(bleu2, bleu2, blanc));

		skybox.update(x - size, y + size, z - size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x - size, y + size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x + size, y + size, z + size, new Vec3(bleu2, bleu2, blanc));
		skybox.update(x + size, y + size, z - size, new Vec3(bleu2, bleu2, blanc));

		skybox.updateEnd();

	}

	public void render(int mode, Camera cam) {
		skybox.render(mode, cam);
	}
}
