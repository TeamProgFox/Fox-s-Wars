package fr.ProgFox;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.glfw.GLFW;
import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Renderer.Display;

public class Main {

	public static boolean running = false;
	public static final int FRAME_CAP = 600000000;
	public static int frames = 0;
	public static int teste = 1;
	public static int width = 1200, height = 600;
	
	private static Main main;
	private Game game;


	public static void main(String[] args) {
		main = new Main();
		main.start();
	}

	public Main() {
		Display.create(width, height, "Fox's Wars");
	}

	public void update() {
		Display.update();
		if (Display.wasResized())
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		if (Input.getKey(GLFW.GLFW_KEY_ESCAPE)) {
			GLFW.glfwSetInputMode(Display.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			Var.isInMenu = true;
			Var.isInGame = false;
		}
		if (Input.getMouseDown(0) && !Var.isInGame) {
			Var.isInGame = true;
			Var.isInMenu = false;
			GLFW.glfwSetInputMode(Display.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		}

		game.update();
		Input.update();
	}

	public void render() {
		Display.clearBuffers();
		game.render();
	}

	public void renderGUI() {
		game.renderGUI();
	}

	// ----
	public void start() {
		game = new Game();
		game.init();
		running = true;
		loop();
	}

	public void stop() {
		running = false;
	}

	public void exit() {
		Display.dispose();
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

			if (Display.isClosed())
				stop();
			if (System.nanoTime() - lastTickTime > tickTime) {
				update();
				ticks++;
				lastTickTime += tickTime;
			} else if (System.nanoTime() - lastRenderTime > RenderTime) {
				render();
				renderGUI();
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
				// System.out.println("FPS : " + frames + " TPS : " + ticks);
				timer += 1000;
				ticks = 0;
				frames = 0;
			}
		}
		exit();
	}

	public void log() {
		System.out.println("lol");
	}

	public static Main getMain() {
		return main;
	}

	public Game getGame() {
		return game;
	}

}
