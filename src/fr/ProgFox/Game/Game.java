package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import fr.ProgFox.Game.Entity.EntityManager;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;
import fr.ProgFox.Utils.VertexBuffer.VBO;
import fr.ProgFox.World.World;

public class Game {
	public Camera cam;
	public World world;
	private Cube cube;
	private EntityManager entityManager;
	private SkyBox skybox;
	public Game() {

		world = new World(-6956537684988609768L);
		int pos = World.sizeX * 16;
		cam = new Camera(new Vec3(pos / 2, 30, pos / 2), world);
		entityManager = new EntityManager();
		cube = new Cube(new Vec3(1, 1, 1));
		
		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(cam.player);
		skybox = new SkyBox(new Vec3(1, 1, 1));

		cube.add(0, 0, 0, 0.002f, true);
		skybox.add(0, 0, 0, 100);

	}

	public void update() {
		entityManager.update();
		world.update();
		cam.update();
		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				Var.flyMode = !Var.flyMode;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				Var.debugMode = !Var.debugMode;
			}
		}
	}

	public void render() {
		entityManager.render();
		world.render(cam);
		
	}

	public void renderGUI() {

		float x = (float) (-cam.position.x + cam.player.getForward().x);
		float y = (float) (-cam.position.y - cam.player.getForward().y);
		float z = (float) (-cam.position.z + cam.player.getForward().z);

		cube.update(x, y, z, 0.002f, true);
		skybox.update(x, y, z, 100);
		
		skybox.render(GL_QUADS, cam);
		cube.render(GL_QUADS, cam);
	}
}
