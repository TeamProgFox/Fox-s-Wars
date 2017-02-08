package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Game.Entity.EntityManager;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;
import fr.ProgFox.World.World;

public class Game {
	public Camera cam;
	public World world;
	private Cube cube;
	private EntityManager entityManager;
	private SkyBox skybox;

	public Game() {

		world = new World(-6956537684988609768L);
		int pos = World.SIZE * 16;
		cam = new Camera(new Vec3(-pos / 2, -30, -pos / 2), world);
		entityManager = new EntityManager();
		cube = new Cube(new Vec3(1, 1, 1));

		cam.setPerspectiveProjection(70.0f, 0.1f, 1000.0f);
		entityManager.add(cam.player);
		skybox = new SkyBox(new Vec3(1, 1, 1));

		cube.add(0, 0, 0, 0.002f, true);
		skybox.add(0, 0, 0, 100);

	}

	public void update() {
		entityManager.update();
		world.update();
	}

	public void render() {
		entityManager.render();
		world.render(cam.player, cam);

	}

	public void renderGUI() {

		float x = (float) (-cam.player.position.x + cam.player.getForward().x);
		float y = (float) (-cam.player.position.y - cam.player.getForward().y);
		float z = (float) (-cam.player.position.z + cam.player.getForward().z);

		cube.update(x, y, z, 0.002f, true);
		skybox.update(x, y, z, 100);
		
		skybox.render(cam.player, GL_QUADS, cam);
		cube.render(cam.player, GL_QUADS, cam);
	}
}
