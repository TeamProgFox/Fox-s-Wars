package fr.ProgFox;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.DisplayManager;
import fr.ProgFox.Utils.Loader;
import fr.ProgFox.World.World;

public class Core {
	public static final int FRAME_CAP = 600000;
	public static boolean running = false;
	public static int frames = 0;
	public static int teste = 1;
	Game game;
	public static int width = 1200, height = 600;

	public Core() {
		DisplayManager.create(width, height, "Fox's Wars");
		game = new Game();
	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
			Var.isInMenu = true;
			Var.isInGame = false;
		}
		if (Mouse.isButtonDown(0) && !Var.isInGame) {
			Var.isInGame = true;
			Var.isInMenu = false;
			Mouse.setGrabbed(true);
		}
		if (!Mouse.isGrabbed()) {
			Var.isInGame = false;
			game.save();
			return;
		}
		game.update();

	}

	public void render() {
		if (Display.wasResized())
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		DisplayManager.clearBuffers();
		game.render();
	}

	public void renderGUI() {
		game.renderGUI();
	}

	// ----
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
				Display.setTitle("Fox's Wars : FPS = " + frames + "/ TPS = " + ticks);
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
