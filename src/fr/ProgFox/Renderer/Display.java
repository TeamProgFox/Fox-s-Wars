package fr.ProgFox.Renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Inputs.MouseButton;

public class Display {
	public static int w, h;

	public static long window;
	public static GLFWKeyCallback keyCallback;
	public static GLFWMouseButtonCallbackI mouseCallback;

	public static void create(int width, int height, String title) {

		w = width;
		h = height;
		if (!glfwInit()) {
			System.err.println("lol");
			System.exit(1);
		}
		window = glfwCreateWindow(width, height, title, 0, 0);

		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1);
		glfwShowWindow(window);
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		glfwSetMouseButtonCallback(window, mouseCallback = new MouseButton());

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
