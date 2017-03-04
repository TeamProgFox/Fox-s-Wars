package fr.ProgFox.Renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Inputs.Keyboard;
import fr.ProgFox.Inputs.Mouse;
import fr.ProgFox.Inputs.MouseButton;

public class Display {
	public static int w, h;
	public static int lw, lh;

	public static long window;

	public static GLFWKeyCallback keyCallback;
	public static GLFWMouseButtonCallbackI mouseCallback;

	public static void create(int width, int height, String title, Input input) {

		w = width;
		h = height;
		if (!glfwInit()) {
			System.exit(1);
		}
		window = glfwCreateWindow(width, height, title, 0, 0);
		glfwShowWindow(window);
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		glfwSetKeyCallback(window, keyCallback = input.getKeyboardCallback());
		glfwSetCursorPosCallback(window, input.getMouse().getCursorPosCallback());
		glfwSetMouseButtonCallback(window, mouseCallback = input.getMouse().getMouseButtonCallback());
		glfwSetScrollCallback(window, input.getMouse().getScrollCallback());

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(0);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_FRONT);

	}

	public static void update() {
		glfwPollEvents();
		glfwSwapBuffers(window);
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		lw = w.get(0);
		lh = h.get(0);
	}

	public static boolean wasResized() {
		if (lw != w || lh != h) {
			w = lw;
			h = lh;
			return true;
		}
		return false;
	}

	public static void clearBuffers() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static boolean isClosed() {
		return glfwWindowShouldClose(window);
	}

	public static void dispose() {
		glfwTerminate();
	}

	public static void setTitle(String title) {
		glfwSetWindowTitle(window, title);
	}

	public static long getWindow() {
		return window;
	}

	public static int getWidth() {
		return w;
	}

	public static int getHeight() {
		return h;
	}

}
