package fr.ProgFox.Inputs;

import org.lwjgl.glfw.GLFWScrollCallback;

class MouseScroll extends GLFWScrollCallback {
	float xOffs, yOffs;

	public void invoke(long window, double xoffset, double yoffset) {
		xOffs = (float) xoffset;
		yOffs = (float) yoffset;
	}
}