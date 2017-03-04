package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import javax.swing.*;

import fr.ProgFox.*;
import fr.ProgFox.Game.Entities.*;
import fr.ProgFox.Game.Variables.*;
import fr.ProgFox.Game.World.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Math.*;
import fr.ProgFox.Network.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.GUI.*;
import fr.ProgFox.Renderer.VertexBuffer.*;
import fr.ProgFox.Renderer.VertexBuffer2D.*;
import fr.ProgFox.Utils.*;

public class Game implements Runnable {

	public float posX, posY, posZ;
	public float rotX, rotY;

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

	private boolean addCLientPlayerRequest = false;

	private Camera cam;
	private World world;
	private EntityManager entityManager;
	private SavePlayersConfiguration spc;
	private NetworkClient net;
	private String ClientPlayerName;
	private Vec3 cpp;
	private SkyBox skybox;
	private VBO2D crosser;
	String pseudo;
	private GUIButton buttonQuit;
	private GUIButton buttonResume;

	public Game() {

		Loader.read("saves/Player/Player.tpf", this);

		if (Var.isInThirdPerson == false)
			Var.isInFirstPerson = true;
		do {

			pseudo = JOptionPane.showInputDialog("Pseudo : ");
		} while (pseudo.contains(";") || pseudo == null);

		Block.add(new GrassBlock());
		Block.add(new LeafBlock());
		Block.add(new StoneBlock());
		Block.add(new WoodBlock());
		Block.add(Block.TESTE);

		world = new World(-6956537684988609768L, posX, posZ);
		cam = new Camera(new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0), world, pseudo);
		entityManager = new EntityManager();
		skybox = new SkyBox(new Vec3(1, 1, 1));
		net = new NetworkClient("localhost", 2222, pseudo, this);
		crosser = new VBO2D();

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(getCamera().getPlayer());
		skybox.add(0, 0, 0, 100);
		getCamera().getPlayer().setWorld(world);

		new Thread(this).start();

	}

	public void init() {

		buttonQuit = new GUIButton(0, 0, 200, 100, new Vec3(1, 0, 0), "Quit game", new Vec3(1, 1, 1));
		buttonResume = new GUIButton(Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 50, 200, 100,
				new Vec3(192f / 255f, 192f / 255f, 192f / 255f), "Resume Game", new Vec3(1, 1, 1));

		spc = new SavePlayersConfiguration();

		float x, y, xx, yy;
		int size = 1;

		x = Display.getWidth() / 2 - size;
		y = Display.getHeight() / 2 - size;

		xx = Display.getWidth() / 2 + size;
		yy = Display.getHeight() / 2 + size;

		crosser.init(4);
		crosser.addVertex(x, y, new Vec3(1, 1, 1));
		crosser.addVertex(xx, y, new Vec3(1, 1, 1));
		crosser.addVertex(xx, yy, new Vec3(1, 1, 1));
		crosser.addVertex(x, yy, new Vec3(1, 1, 1));
		crosser.end();
	}

	public void updateWhenResized() {
		float x, y, xx, yy;
		int size = 1;
		System.out.println(Display.getWidth() + " / " + Display.getHeight());
		x = Display.getWidth() / 2 - size;
		y = Display.getHeight() / 2 - size;

		xx = Display.getWidth() / 2 + size;
		yy = Display.getHeight() / 2 + size;
		crosser.update(x, y, new Vec3(1, 1, 1));
		crosser.update(xx, y, new Vec3(1, 1, 1));
		crosser.update(xx, yy, new Vec3(1, 1, 1));
		crosser.update(x, yy, new Vec3(1, 1, 1));
		crosser.updateEnd();

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
		if (!isConnected) {
			MultiPlayer.rePlace(this);
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

		ClickedGestion();

	}

	public void multiplayerManager() {
		Game e = this;
		new Thread("MultiPlayer") {
			public void run() {
				MultiPlayer.sendPos(net, e);
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
			MultiPlayer.place(net, this);
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

		skybox.update(cam.position.x, cam.position.y, cam.position.z, 2000);
		skybox.render(GL_QUADS, cam.getProjectionMatrix(), getCamera().getTransform(cam.position, new Vec3(0, 0, 0)));
	}

	public void renderGUI() {

		if (Var.isInMenu) {
			buttonQuit.renderGUI();
			buttonResume.renderGUI();
		}
		crosser.render(GL_QUADS, Main.getMain().getShader(),
				Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
				cam.getTransform(new Vec3(), new Vec3()));

	}

	public void ClickedGestion() {
		if (buttonQuit.isClicked) {
			Main.getMain().stop();
		}

		if (buttonResume.isClicked) {
			Var.isInGame = true;
			Var.isInMenu = false;
			Main.getMain().input.getMouse().setGrabbed(true);
		}

		if (Main.getMain().input.getKey(Input.KEY_ESCAPE)) {
			Var.isInMenu = true;
			Var.isInGame = false;
			Main.getMain().input.getMouse().setGrabbed(false);
		}

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
