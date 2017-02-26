package fr.ProgFox.Game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.WGL;
import org.lwjgl.opengl.WGLARBMakeCurrentRead;

import fr.ProgFox.Core;
import fr.ProgFox.Game.Entities.ClientPlayer;
import fr.ProgFox.Game.Entities.Entity;
import fr.ProgFox.Game.Entities.EntityManager;
import fr.ProgFox.Game.Entities.SavePlayersConfiguration;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Display;
import fr.ProgFox.Utils.Loader;
import fr.ProgFox.Utils.UniqueID;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;

public class Game implements Runnable {
	public Camera cam;
	public World world;
	private Cube cube;
	public EntityManager entityManager;
	private SkyBox skybox;
	public float posX, posY, posZ;
	public float rotX, rotY;
	public SavePlayersConfiguration spc;
	public static Scanner sc = new Scanner(System.in);
	public int id;
	private CubeLine perso;
	public NetworkClient net;
	public List<ClientPlayer> players = new ArrayList<>();
	public boolean teste = false;

	public Game() {
		spc = new SavePlayersConfiguration(this);
		Loader.read("saves/Player/Player.tpf", this);

		if (Var.isInThirdPerson == false)
			Var.isInFirstPerson = true;

		String pseudo = JOptionPane.showInputDialog("Pseudo : ");

		world = new World(-6956537684988609768L);
		cam = new Camera(new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0), world, pseudo);
		entityManager = new EntityManager();
		cube = new Cube(new Vec3(1, 1, 1));
		perso = new CubeLine(new Vec3(1, 1, 1));
		skybox = new SkyBox(new Vec3(1, 1, 1));

		cam.player.connect(this, "	", 2009);

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(cam.player);
		cube.add(0, 0, 0, 0.002f, true);
		perso.add(0, 0, 0, 1, 1, 1, false);
		skybox.add(0, 0, 0, 100);
		cam.player.setWorld(world);

		new Thread(this).start();
	}

	public void controleEntity(String name, float x, float y, float z) {
		ClientPlayer e = entityManager.getPlayer(name);
		if (e != null)
			e.position = new Vec3(x, y, z);
	}

	public void addClientPlayer(String name, float x, float y, float z) {
		ClientPlayer cp = entityManager.getPlayer(name);
		if (cp != null)
			return;

		ClientPlayer CPlayer = new ClientPlayer(UniqueID.getUniqueID(), name, new Vec3(x, y, z), new Vec3());
		System.out.println("lol");
		players.add(CPlayer);
		entityManager.add((Entity) CPlayer);
	}

	public void update() {

		for (ClientPlayer a : players)
			perso.update(a.position.x, a.position.y, a.position.z, 0.5f, 1.25f, 0.5f, true);

		if (Var.isInMenu)
			spc.save();

		entityManager.update();
		World.cycleToDay();
		cam.update();

		keyboardGestion();

	}

	public void run() {
		while (true) {
			world.update(cam);

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void keyboardGestion() {
		if (Input.getKey(GLFW.GLFW_KEY_F)) {
			Var.flyMode = !Var.flyMode;
		}
		if (Input.getKey(GLFW.GLFW_KEY_F3)) {
			Var.debugMode = !Var.debugMode;
		}
		if (Input.getKey(GLFW.GLFW_KEY_F5)) {
			if (Var.isInFirstPerson) {
				Var.isInFirstPerson = false;
				Var.isInThirdPerson = true;
			} else if (Var.isInThirdPerson) {
				Var.isInFirstPerson = true;
				Var.isInThirdPerson = false;
			}
		}
	}

	public void render() {
		entityManager.render();
		
		world.render(cam);

		for (ClientPlayer a : players) {
			perso.render(GL_LINES, 2, cam.getPerspectiveProjection(), cam.position, cam.shader);
		}
	}

	public void renderGUI() {

		float x = (float) (cam.position.x + cam.getForward().x);
		float y = (float) (cam.position.y - cam.getForward().y);
		float z = (float) (cam.position.z + cam.getForward().z);

		cube.update(x, y, z, 0.002f, true);
		skybox.update(cam.position.x, cam.position.y, cam.position.z, 2000);

		skybox.render(GL_QUADS, cam.getPerspectiveProjection(), cam.position);
		cube.render(GL_QUADS, cam.getPerspectiveProjection(), cam.position);
	}
}
