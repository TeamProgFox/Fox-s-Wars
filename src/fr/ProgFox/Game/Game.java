package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Game.Entity.EntityManager;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Shader.SkyBoxShader;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;
import fr.ProgFox.World.World;
import fr.ProgFox.renderer.Camera;

public class Game {
	private Camera cam;
	private World world;
	private Cube cube;
	private EntityManager entityManager;
	private SkyBox skybox;

	public Game(Camera cam, World world) {
		this.cam = cam;
		this.world = world;
		entityManager = new EntityManager();
		entityManager.add(cam.player);
		this.cube = new Cube(new Vec3(1, 1, 1));
		skybox = new SkyBox(new Vec3(1, 1, 1));
		
		float x = (float) (-cam.player.position.x + cam.player.getDirection().x);
		float y = (float) (-cam.player.position.y - cam.player.getDirection().y);
		float z = (float) (-cam.player.position.z + cam.player.getDirection().z);

		cube.add(x, y, z, 0.002f, true);
		skybox.add(0, 0, 0, 100000);
	}

	public void update() {
		entityManager.update();
		world.update();
	}

	public void input() {
		entityManager.input();
	}

	public void render() {
		entityManager.render();
		world.render(cam.player);

	}

	public void renderGUI() {

		float x = (float) (-cam.player.position.x + cam.player.getDirection().x);
		float y = (float) (-cam.player.position.y - cam.player.getDirection().y);
		float z = (float) (-cam.player.position.z + cam.player.getDirection().z);

		cube.update(x, y, z, 0.002f, true);
		skybox.render(cam.player, GL_QUADS);
		cube.render(cam.player, GL_QUADS);
	}
}
