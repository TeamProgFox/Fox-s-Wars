package fr.ProgFox;

import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Renderer.Display;
import static org.lwjgl.opengl.GL11.*;

public class MainMenu {

	private boolean isRunning = false;

	private Input input;

	public MainMenu(Input input) {
		isRunning = true;
		this.input = input;
		loop();
	}

	public void update() {
		Display.update();

	}

	public void render() {
		Display.clearBuffers();
	}

	public void loop() {
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), - 1, 1000.0f);
		while (isRunning) {
			if (input.getKey(Input.KEY_ESCAPE)) {
				isRunning = false;
			}

			update();
			render();

		}
	}

}
