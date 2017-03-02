package fr.ProgFox.Game.Inputs;

import org.lwjgl.glfw.GLFWCursorPosCallback;

class MousePosition extends GLFWCursorPosCallback {
	float x, y;

	public void invoke(long window, double xpos, double ypos) {
		x = (float) Math.floor(xpos);
		y = (float) Math.floor(ypos);
	}
}