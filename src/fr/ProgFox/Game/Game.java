package fr.ProgFox.Game;

import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.World.World;
import fr.ProgFox.World.Buffer.VBO;
import fr.ProgFox.newMath.Vector3f;
import fr.ProgFox.renderer.Camera;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	private Camera cam;
	private World world;
	private VBO crosser;
	private Shader shader;

	public Game(Camera cam, World world) {
		this.cam = cam;
		this.world = world;
		this.shader = new ColorShader();
		crosser = new VBO();
	}

	public void update() {
		cam.input();
		world.update();

	}

	public void render() {
		world.render(cam.player);

	}

	public void renderGUI() {
		
		float x = (float) (-cam.player.position.x + cam.player.getDirection().x);
		float y = (float) (-cam.player.position.y - cam.player.getDirection().y);
		float z = (float) (-cam.player.position.z + cam.player.getDirection().z);
		crosser.init(4, shader);
		float size = 0.01f;
		crosser.addVertex(x, y, z, new Vector3f(1, 1, 1));
		crosser.addVertex(x + size, y, z, new Vector3f(1, 1, 1));
		crosser.addVertex(x + size, y + size, z, new Vector3f(1, 1, 1));
		crosser.addVertex(x, y + size, z, new Vector3f(1, 1, 1));
		crosser.end();
		crosser.render(cam.player, GL_QUADS);
	}
}
