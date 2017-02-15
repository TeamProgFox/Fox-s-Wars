package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Drawable;

import fr.ProgFox.Core;
import fr.ProgFox.Game.Entities.ClientPlayer;
import fr.ProgFox.Game.Entities.EntityManager;
import fr.ProgFox.Game.Entities.SavePlayersConfiguration;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.DisplayManager;
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

	public Game() {
		spc = new SavePlayersConfiguration(this);
		int pos = World.sizeX * 16;

		Loader.read("saves/Player/Player.tpf", this);
		this.id = UniqueID.getUniqueID();
		System.out.println(id);

		if (Var.isInThirdPerson == false)
			Var.isInFirstPerson = true;
		world = new World(-6956537684988609768L);
		cam = new Camera(new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0), world);
		entityManager = new EntityManager();

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);

		cube = new Cube(new Vec3(1, 1, 1));
		perso = new CubeLine(new Vec3(1, 1, 1));

		entityManager.add(cam.player);

		cam.player.connect(this, "	", 2009);
		skybox = new SkyBox(new Vec3(1, 1, 1));

		cube.add(0, 0, 0, 0.002f, true);
		perso.add(0, 0, 0, 1, 1, 1, false);
		skybox.add(0, 0, 0, 100);
		//net = new NetworkClient(this, "localhost", 2009);
		
		new Thread(this).start();
	}

	public void save() {
		if (Var.isInMenu) {
			spc.save();
		}
	}

	public void controleEntity(String name, float x, float y, float z) {

		ClientPlayer e = entityManager.getPlayer(name);
		if (e != null) {
			e.position = new Vec3(x, y, z);
		}
	}

	public void addClientPlayer(String name, float x, float y, float z) {
		ClientPlayer cp = entityManager.getPlayer(name);
		if (cp != null)
			return;

		ClientPlayer CPlayer = new ClientPlayer(UniqueID.getUniqueID(), name, new Vec3(x, y, z), new Vec3(), cam);

		players.add(CPlayer);
		entityManager.add(CPlayer);
	}

	public void update() {

		for (ClientPlayer a : players) {
			perso.update(a.position.x, a.position.y, a.position.z, 0.5f, 1.25f, 0.5f, true);
		}
		
		// net.send(("Enzo;0;0;0").getBytes());

		entityManager.update();
		
		World.cycleToDay();

		cam.update();

		keyboardGestion();

	}

	public void run() {

		new Thread("WorldUpdate") {
			public void run() {
				try {
					Core.sd.makeCurrent();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				while (true) {
					world.update(cam);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void keyboardGestion() {
		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				Var.flyMode = !Var.flyMode;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				Var.debugMode = !Var.debugMode;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F5)) {
				if (Var.isInFirstPerson) {
					Var.isInFirstPerson = false;
					Var.isInThirdPerson = true;
				} else if (Var.isInThirdPerson) {
					Var.isInFirstPerson = true;
					Var.isInThirdPerson = false;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
				net.send((new Random().toString() + ";0;0;0").getBytes());
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				net.send("c;o;u;cou".getBytes());
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
