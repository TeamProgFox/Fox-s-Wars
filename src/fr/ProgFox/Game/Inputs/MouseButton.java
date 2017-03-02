package fr.ProgFox.Game.Inputs;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButton extends GLFWMouseButtonCallback {
	boolean[] buttons = new boolean[5];

	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = (action == GLFW.GLFW_PRESS);
	}
}