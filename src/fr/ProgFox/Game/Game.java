package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.glfw.GLFW;

import fr.ProgFox.Main;
import fr.ProgFox.Game.Entities.ClientPlayer;
import fr.ProgFox.Game.Entities.EntityManager;
import fr.ProgFox.Game.Entities.SavePlayersConfiguration;
import fr.ProgFox.Game.Inputs.Input;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Chunk;
import fr.ProgFox.Game.World.GestionBlock;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.GrassBlock;
import fr.ProgFox.Game.World.Blocks.LeafBlock;
import fr.ProgFox.Game.World.Blocks.StoneBlock;
import fr.ProgFox.Game.World.Blocks.WoodBlock;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.Loader;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.SkyBox;

public class Game implements Runnable {

	public float posX, posY, posZ;
	public float rotX, rotY;

	public boolean teste = false;
	private boolean addCLientPlayerRequest = false;
	public boolean addBlock = false;
	public boolean removeBlock = false;
	public boolean isConnected = false;
	public boolean removeBlockRequest = false;
	public boolean addBlockRequest = false;
	public boolean canContinue = false;

	public List<String> players = new ArrayList<>();
	public List<Vec3> removePos = new ArrayList<>();
	public List<Vec3> addPos = new ArrayList<>();

	public List<Block> add = new ArrayList<>();

	public List<GestionBlock> gestionBlock = new ArrayList<>();

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

	String pseudo;

	public Game() {

		Loader.read("saves/Player/Player.tpf", this);

		if (Var.isInThirdPerson == false)
			Var.isInFirstPerson = true;
		do {

			pseudo = JOptionPane.showInputDialog("Pseudo : ");
		} while (pseudo.contains(";"));

		Block.add(new GrassBlock());
		Block.add(new LeafBlock());
		Block.add(new StoneBlock());
		Block.add(new WoodBlock());
		Block.add(Block.TESTE);

		world = new World(-6956537684988609768L, posX, posZ);
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

		removeBlockRequest = true;
		gestionBlock.add(new GestionBlock(pos.x, pos.y, pos.z, "", true));

	}

	public void addBlock(Vec3 pos, String block) {
		addBlockRequest = true;
		gestionBlock.add(new GestionBlock(pos.x, pos.y, pos.z, block, false));

	}

	public void addBlock2(Vec3 pos, String block) {
		addPos.add(pos);
		add.add(Block.getBlock(block));
		addBlock = true;
	}

	public void removeBlock2(Vec3 pos) {
		removePos.add(pos);
		removeBlock = true;
	}

	public void controlePlayer(String name, float x, float y, float z) {
		ClientPlayer e = entityManager.getPlayer(name);
		if (e != null) {
			e.position = new Vec3(x, y, z);
		}

	}

	public void update() {
		world.updateWorld();

		if (!canContinue)
			return;

		if ((addBlockRequest || removeBlockRequest) && !isConnected) {
			for (GestionBlock a : gestionBlock) {
				int xx = (int) (a.getX() / Chunk.SIZE);
				int zz = (int) (a.getZ() / Chunk.SIZE);
				if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
					return;

				if (world.getChunk(xx, zz) == null) {
					world.chunks[xx][zz] = new Chunk(xx, 0, zz, world.noise, world.noiseColor, world.random, world);
					world.chunks[xx][zz].createChunk();
				}
				if (a.getAction()) {
					world.removeBlock(a.getX(), a.getY(), a.getZ(), true);
				} else {
					world.addBlock(a.getX(), a.getY(), a.getZ(), Block.getBlock(a.getBlock()), true);
				}

			}
			gestionBlock.clear();

			addBlockRequest = false;
			removeBlockRequest = false;
			isConnected = true;
		}

		if (Var.isInMenu)
			spc.save();

		entityManager.update();
		entityManager.updateClientPlayer();
		World.cycleToDay();
		cam.update();
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

	}

	public void multiplayerManager() {
		new Thread("MultiPlayer") {
			public void run() {

				net.send("controle;" + pseudo + ";" + getCamera().getPlayer().position.x + ";"
						+ getCamera().getPlayer().position.y + ";" + getCamera().getPlayer().position.z);

			}
		}.start();
	}

	public void oneSecond() {
		net.send("player;" + pseudo + ";" + getCamera().getPlayer().position.x + ";"
				+ getCamera().getPlayer().position.y + ";" + getCamera().getPlayer().position.z);
	}

	public void run() {
		while (true) {
			world.update(cam);

			if (addBlock) {

				for (int i = 0; i < addPos.size(); i++) {
					int xx = (int) (addPos.get(i).x / Chunk.SIZE);
					int zz = (int) (addPos.get(i).z / Chunk.SIZE);

					if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
						return;
					if (world.getChunk(xx, zz) == null) {
						world.chunks[xx][zz] = new Chunk(xx, 0, zz, world.noise, world.noiseColor, world.random, world);
						world.chunks[xx][zz].createChunk();
					}
					world.addBlock = new Vec3(addPos.get(i).x, addPos.get(i).y, addPos.get(i).z);
					world.block = add.get(i);
					world.addBlockRequest = true;
				}
				addPos.clear();
				add.clear();
				addBlock = false;
			}

			if (removeBlock) {
				for (int i = 0; i < removePos.size(); i++) {
					int xx = (int) (removePos.get(i).x / Chunk.SIZE);
					int zz = (int) (removePos.get(i).z / Chunk.SIZE);
					if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
						return;
					if (world.getChunk(xx, zz) == null) {
						world.chunks[xx][zz] = new Chunk(xx, 0, zz, world.noise, world.noiseColor, world.random, world);
						world.chunks[xx][zz].createChunk();
					}
					world.removeBlock = new Vec3(removePos.get(i).x, removePos.get(i).y, removePos.get(i).z);
					world.removeBlockRequest = true;
				}
				removePos.clear();
				removeBlock = false;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void keyboardGestion() {
		if (Main.getMain().input.getKeyDown(Input.KEY_F)) {
			Var.flyMode = !Var.flyMode;
		}
		if (Main.getMain().input.getKeyDown(Input.KEY_F3)) {
			Var.debugMode = !Var.debugMode;
		}
		if (Main.getMain().input.getKeyDown(Input.KEY_F5)) {
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
