package fr.ProgFox;

import static org.lwjgl.opengl.GL11.*; 

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.World;
import fr.ProgFox.renderer.Camera;
import fr.ProgFox.renderer.DisplayManager;

public class Core {
	public static final int FRAME_CAP = 60000;
	boolean running = false;
	public static int frames = 0;
	public static int teste = 1;
	Camera cam;
	World world;
	Game game;
	public static int width = 1200, height = 600;
	private Random seeds;

	// ----
	public Core() {
		DisplayManager.create(width, height, "Fox's Wars");
		seeds = new Random();
		System.out.println(seeds.nextLong());
		world = new World(-6956537684988609768L);
		int posX = World.SIZE * 16;
		int posZ = World.SIZE * 16;
		cam = new Camera(new Vec3(-posX / 2, -30, -posZ / 2), world);
		game = new Game(cam, world);
	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);
		if (Mouse.isButtonDown(0) && !Mouse.isGrabbed())
			Mouse.setGrabbed(true);
		if (!Mouse.isGrabbed())
			return;
		game.update();
		game.input();
	}

	public void render() {
		if (Display.wasResized()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		DisplayManager.clearBuffers();
		game.render();
	}

	public void renderGUI() {
		game.renderGUI();
	}

	public void start() {
		running = true;
		loop();
	}

	public void stop() {
		running = false;
	}

	public void exit() {
		DisplayManager.dispose();
		System.exit(0);
	}

	public void loop() {

		long lastTickTime = System.nanoTime();
		long lastRenderTime = System.nanoTime();

		double tickTime = 1000000000.0 / 60.0;
		double RenderTime = 1000000000.0 / FRAME_CAP;

		int ticks = 0;
		long timer = System.currentTimeMillis();

		while (running) {

			if (DisplayManager.isClosed())
				stop();
			if (System.nanoTime() - lastTickTime > tickTime) {
				update();
				ticks++;
				lastTickTime += tickTime;
			} else if (System.nanoTime() - lastRenderTime > RenderTime) {
				render();
				renderGUI();
				DisplayManager.update();
				frames++;
				lastRenderTime += RenderTime;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				Display.setTitle("Fox's Wars : FPS = " + frames + "/ TPS = " + ticks + " | coords : "
						+ cam.getPosition().getX() + "/" + cam.getPosition().getY() + "/" + cam.getPosition().getZ());
				ticks = 0;
				Raycast.teste = 0;
				frames = 0;
			}
		}
		exit();
	}

	public static void main(String[] args) {
		Core main = new Core();
		main.start();
	}
}
