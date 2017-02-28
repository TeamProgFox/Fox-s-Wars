package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.glfw.GLFW;

import fr.ProgFox.Main;
import fr.ProgFox.Game.Entities.ClientPlayer;
import fr.ProgFox.Game.Entities.Entity;
import fr.ProgFox.Game.Entities.EntityManager;
import fr.ProgFox.Game.Entities.SavePlayersConfiguration;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.SaveChunk;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.GrassBlock;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.Loader;
import fr.ProgFox.Utils.UniqueID;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;

public class Game implements Runnable {

	public float posX, posY, posZ;
	public float rotX, rotY;
	public int id;
	public boolean teste = false;
	private boolean addCLientPlayerRequest = false;
	public List<String> players = new ArrayList<>();

	public static Scanner sc = new Scanner(System.in);

	private Camera cam;
	private World world;
	private EntityManager entityManager;
	private SavePlayersConfiguration spc;
	private NetworkClient net;
	private String ClientPlayerName;
	private Vec3 cpp;
	private Cube cube;
	private SkyBox skybox;

	public Vec3 removePos;
	public boolean removeBlockRequest = false;

	String pseudo;

	public Game() {

		Loader.read("saves/Player/Player.tpf", this);

		if (Var.isInThirdPerson == false)
			Var.isInFirstPerson = true;

		pseudo = JOptionPane.showInputDialog("Pseudo : ");

		world = new World(-6956537684988609768L);
		cam = new Camera(new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0), world, pseudo);
		entityManager = new EntityManager();
		skybox = new SkyBox(new Vec3(1, 1, 1));
		cube = new Cube(new Vec3(1, 1, 1));
		net = new NetworkClient("localhost", 2222, pseudo, this);

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(getCamera().getPlayer());
		cube.add(0, 0, 0, 0.002f, true);
		skybox.add(0, 0, 0, 100);
		getCamera().getPlayer().setWorld(world);

		new Thread(this).start();

	}

	public void init() {
		spc = new SavePlayersConfiguration();
	}

	public void log(String msg) {
		System.out.println(msg);
	}

	public void addClientPlayer(String name, float x, float y, float z) {
		if (!pseudo.equals(name)) {
			this.ClientPlayerName = name;
			this.cpp = new Vec3(x, y, z);
			players.add(name);
			addCLientPlayerRequest = true;
		}
	}

	public void removeBlock(Vec3 pos) {
		removePos = pos;
		removeBlockRequest = true;
		System.out.println("REMOVE BLOCK " + removePos.x + " / " + removePos.y + " / " + removePos.z);
	}

	public void controlePlayer(String name, float x, float y, float z) {
		ClientPlayer e = entityManager.getPlayer(name);
		if (e != null) {
			e.position = new Vec3(x, y, z);
		}

	}

	public void update() {

		if (Var.isInMenu)
			spc.save();

		entityManager.update();
		entityManager.updateClientPlayer();
		World.cycleToDay();
		cam.update();
		world.addBlock(0, 2, 0, Block.TESTE, true);
		keyboardGestion();
		multiplayerManager();

		if (addCLientPlayerRequest) {
			ClientPlayer e = entityManager.getPlayer(ClientPlayerName);
			if (e == null) {
				ClientPlayer cP = new ClientPlayer(100, ClientPlayerName, cpp, new Vec3());
				entityManager.add(cP);
				addCLientPlayerRequest = false;
			}
		}
		if (removeBlockRequest) {
			world.removeBlock(removePos.x, removePos.y, removePos.z, true);
			System.out.println("REMOVE BLOCK AT " + removePos.x + " / " + removePos.y + " / " + removePos.z);
			removeBlockRequest = false;
		}

	}

	public void multiplayerManager() {
		new Thread("MultiPlayer") {
			public void run() {
				net.send("player;" + pseudo + ";" + getCamera().getPlayer().position.x + ";"
						+ getCamera().getPlayer().position.y + ";" + getCamera().getPlayer().position.z);
				net.send("controle;" + pseudo + ";" + getCamera().getPlayer().position.x + ";"
						+ getCamera().getPlayer().position.y + ";" + getCamera().getPlayer().position.z);
			}
		}.start();
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

		entityManager.renderClientPlayer();
		world.render(cam);

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

	public World getWorld() {
		return world;
	}

	public Camera getCamera() {
		return cam;
	}

	public NetworkClient getNetwork() {
		return net;
	}

}
