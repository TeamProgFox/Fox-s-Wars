package fr.ProgFox.Game;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.lwjgl.opengl.*;

import fr.ProgFox.*;
import fr.ProgFox.Game.Entities.*;
import fr.ProgFox.Game.Items.*;
import fr.ProgFox.Game.Models.*;
import fr.ProgFox.Game.Variables.*;
import fr.ProgFox.Game.World.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.GUI.*;
import fr.ProgFox.Renderer.VertexBuffer.*;
import fr.ProgFox.Renderer.VertexBuffer2D.*;
import fr.ProgFox.Utils.*;

public class Game implements Runnable {

	public float posX, posY, posZ;
	public float rotX, rotY;

	public List<String> players = new ArrayList<>();

	public static Scanner sc = new Scanner(System.in);

	private Camera cam;
	private World world;
	private EntityManager entityManager;
	private SavePlayersConfiguration spc;
	private SkyBox skybox;
	private VBO2D crosser;
	String pseudo;
	private GUIButton buttonQuit;
	private GUIButton buttonResume;
	private LocalPlayer player;
	private Cube cube;
	private GUISlider slider;

	public Game() {

		Loader.read("saves/Player/Player.tpf", this);

		do {

			pseudo = JOptionPane.showInputDialog("Pseudo : ");
		} while (pseudo.equals("") || pseudo.contains(";"));

		Block.add(new GrassBlock());
		Block.add(new LeafBlock());
		Block.add(new StoneBlock());
		Block.add(new WoodBlock());
		Block.add(Block.TESTE);
		
		world = new World(-6956537684988609768L, posX, posZ);
		cam = new Camera();
		entityManager = new EntityManager();
		skybox = new SkyBox(new Vec3(1, 1, 1));
		crosser = new VBO2D();
		player = new LocalPlayer(world, pseudo, new Vec3(posX, posY, posZ), new Vec3(rotX, rotY, 0));
		cube = new Cube(new Vec3(1, 1, 1));

		cam.setPerspectiveProjection(70.0f, 0.01f, 10000.0f);
		entityManager.add(getPlayer());
		skybox.add(0, 0, 0, 1000);

		getPlayer().setWorld(world);

		new Thread(this).start();

	}

	public void init() {

		buttonQuit = new GUIButton(0, 0, 200, 100, new Vec3(1, 0, 0), "Quit game", new Vec3(1, 1, 1));
		buttonResume = new GUIButton(Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 50, 200, 100,
				new Vec3(192f / 255f, 192f / 255f, 192f / 255f), "Resume Game", new Vec3(1, 1, 1));
		slider = new GUISlider(200, 0, 200, 100, new Vec3(1, 1, 0), new Vec3(1, 1, 1));
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
		buttonQuit = new GUIButton(0, 0, 200, 100, new Vec3(1, 0, 0), "Quit game", new Vec3(1, 1, 1));
		buttonResume = new GUIButton(Display.getWidth() / 2 - 100, Display.getHeight() / 2 - 50, 200, 100,
				new Vec3(192f / 255f, 192f / 255f, 192f / 255f), "Resume Game", new Vec3(1, 1, 1));

		getPlayer().getInventory().updateGUIWhenResized();

	}

	public void log(String msg) {
		System.out.println(msg);
	}

	public void update() {
		if (Var.isInMenu)
			spc.save();

		entityManager.update();
		entityManager.updateClientPlayer();
		World.cycleToDay();
		cam.update();
		keyboardGestion();
		ClickedGestion();

	}

	public void oneSecond() {
	}

	public void run() {

		while (true) {
			world.update(player);
		}
	}

	public void keyboardGestion() {

		if (Main.getMain().getInput().getKeyDown(Input.KEY_F)) {
			Var.flyMode = !Var.flyMode;
		}

		if (Main.getMain().getInput().getKeyDown(Input.KEY_F3)) {
			Var.debugMode = !Var.debugMode;
		}

	}

	public void render() {
		entityManager.render();
		entityManager.renderClientPlayer();
		world.render(player);

		skybox.update(Main.getMain().getPlayer().position.x, Main.getMain().getPlayer().position.y,
				Main.getMain().getPlayer().position.z, 2000);
		skybox.render(GL_QUADS, cam.getProjectionMatrix(),
				getCamera().getModelViewMatrix(Main.getMain().getPlayer().position, new Vec3(0, 0, 0)));

		cube.render(GL_QUADS, getCamera().getProjectionMatrix(),
				getCamera().getModelViewMatrix(new Vec3(0, 0, 0), new Vec3()));


	}

	public void renderGUI() {

		if (Var.isInMenu) {
			buttonQuit.renderGUI();
			buttonResume.renderGUI();
			//slider.renderGUI();
		}
		crosser.render(GL_QUADS, Main.getMain().getShader(),
				Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
				cam.getModelViewMatrix(new Vec3(), new Vec3()));

	}

	public void ClickedGestion() {
		if (buttonQuit.isClicked) {
			Main.getMain().stop();
		}

		if (buttonResume.isClicked) {
			Var.isInGame = true;
			Var.isInMenu = false;
			Main.getMain().getInput().getMouse().setGrabbed(true);
		}

		if (Main.getMain().getInput().getKey(Input.KEY_ESCAPE)) {
			Var.isInMenu = true;
			Var.isInGame = false;
			Main.getMain().getInput().getMouse().setGrabbed(false);
		}

	}

	public World getWorld() {
		return world;
	}

	public Camera getCamera() {
		return cam;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

}
