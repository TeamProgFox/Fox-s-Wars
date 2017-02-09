package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import fr.ProgFox.Game.Entities.EntityManager;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.Loader;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;
import fr.ProgFox.World.World;

public class Game {
	public Camera cam;
	public World world;
	private Cube cube;
	private EntityManager entityManager;
	private SkyBox skybox;
	public float posX, posY, posZ;
	public float rotX, rotY;
	public SavePlayersConfiguration spc;
	public Game() {
		spc = new SavePlayersConfiguration(this);
		int pos = World.sizeX * 16;
		
		Loader.read("saves/Player/Player.tpf", this);
		System.out.println(posX + " / " + posY + " / " + posZ);
		
		world = new World(-6956537684988609768L);
		cam = new Camera(new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0), world);
		entityManager = new EntityManager();
		cube = new Cube(new Vec3(1, 1, 1));

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(cam.player);
		skybox = new SkyBox(new Vec3(1, 1, 1));

		cube.add(0, 0, 0, 0.002f, true);
		skybox.add(0, 0, 0, 100);

	}
	public void save(){
		if(Var.isInMenu){
			spc.save();
		}
	}
	public void update() {
		entityManager.update();
		world.update();
		cam.update();
		keyboardGestion();
		
	}
	public void keyboardGestion(){
		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				Var.flyMode = !Var.flyMode;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				Var.debugMode = !Var.debugMode;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F5)) {
				if (Var.isInFirstPersonne) {
					Var.isInFirstPersonne = false;
					Var.isInThirdPersonne = true;
				} else if (Var.isInThirdPersonne) {
					Var.isInFirstPersonne = true;
					Var.isInThirdPersonne = false;
				}
			}
		}
		
		
	}
	public void render() {
		entityManager.render();
		world.render(cam);

	}

	public void renderGUI() {

		float x = (float) (cam.position.x + cam.getForward().x);
		float y = (float) (cam.position.y - cam.getForward().y);
		float z = (float) (cam.position.z + cam.getForward().z);

		cube.update(x, y, z, 0.002f, true);
		skybox.update(cam.position.x, cam.position.y, cam.position.z, 100);

		skybox.render(GL_QUADS, cam);
		cube.render(GL_QUADS, cam);
	}
}
