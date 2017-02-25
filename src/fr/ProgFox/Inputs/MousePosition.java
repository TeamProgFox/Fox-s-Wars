package fr.ProgFox.Inputs;

import java.nio.DoubleBuffer;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Renderer.Display;

public class MousePosition {
	public static float x, y;

	public static float getPosX() {
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Display.getWindow(), xBuffer, yBuffer);
		double x = xBuffer.get(0);
		double y = yBuffer.get(0);

		return (float) x;
	}

	public static float getPosY() {
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Display.getWindow(), xBuffer, yBuffer);
		double y = yBuffer.get(0);

		return (float) y;
	}
}
