package fr.ProgFox;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Game.*;
import fr.ProgFox.Game.Entities.*;
import fr.ProgFox.Inputs.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.Shader.*;

public class Main {

	private static Main main;
	private final int FRAME_CAP = 600000000;

	public static int width = 1200, height = 600;
	public boolean pointButton = false;

	private boolean running = false;
	private Shader shader;
	private Game game;
	private Input input;

	public static void main(String[] args) {
		main = new Main();
		main.start();
	}

	public Main() {
		input = new Input();
		Display.create(width, height, "Fox's Wars", input);
		shader = new ColorShader();
	}

	public void update() {
		Display.update();
		if (Display.wasResized()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			game.updateWhenResized();
			getPlayer().getInventory().updateWhenResized();
		}

		game.update();

		input.getKeyboardCallback().update();
		input.getMouse().update();
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
		int frames = 0;
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
				Display.setTitle("Fox's Wars | FPS : " + frames + " TPS : " + ticks);
				game.oneSecond();
				timer += 1000;
				ticks = 0;
				frames = 0;
			}
		}
		exit();
	}

	public static Main getMain() {
		return main;
	}

	public Game getGame() {
		return game;
	}

	public Camera getCamera() {
		return game.getCamera();
	}

	public LocalPlayer getPlayer() {
		return game.getPlayer();
	}

	public Shader getShader() {
		return shader;
	}

	public Input getInput() {
		return input;
	}
}
