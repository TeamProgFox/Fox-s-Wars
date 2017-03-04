package fr.ProgFox.Inputs;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import fr.ProgFox.Math.Vec2;
import fr.ProgFox.Renderer.Display;

public class Mouse {
	public static final int NUM_MOUSEBUTTONS = 5;

	private MousePosition position = new MousePosition();
	private MouseButton buttons = new MouseButton();
	private MouseScroll scroll = new MouseScroll();

	private boolean grabbed = false;

	private float lx, ly;
	private float cx, cy;
	private float dx, dy;

	private float dsx, dsy;

	private ArrayList<Integer> currentMouse = new ArrayList<>();
	private ArrayList<Integer> downMouse = new ArrayList<>();
	private ArrayList<Integer> upMouse = new ArrayList<>();
	
	public Mouse() {

	}

	public void update() {
		if (dsx != 0)
			dsx = 0;
		if (dsy != 0)
			dsy = 0;

		dsx += scroll.xOffs;
		dsy += scroll.yOffs;

		if (scroll.xOffs != 0)
			scroll.xOffs = 0;
		if (scroll.yOffs != 0)
			scroll.yOffs = 0;

		cx = position.x - lx;
		dx = cx;
		lx = position.x;

		cy = position.y - ly;
		dy = cy;
		ly = position.y;

		if (grabbed) {
			GLFW.glfwSetInputMode(Display.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		} else {
			GLFW.glfwSetInputMode(Display.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		}

		upMouse.clear();

		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (!getButton(i) && currentMouse.contains(i)) {
				upMouse.add(i);
			}
		}

		downMouse.clear();

		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (getButton(i) && !currentMouse.contains(i)) {
				downMouse.add(i);
			}
		}

		currentMouse.clear();

		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (getButton(i)) {
				currentMouse.add(i);
			}
		}
	}

	public int getX() {
		return (int) position.x;
	}

	public int getY() {
		return (int) position.y;
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return dy;
	}

	public float getDWheel() {
		return (dsx + dsy) / 2.0f;
	}

	public void setMousePosition(Vec2 position) {
		glfwSetCursorPos(Display.getWindow(), position.x, position.y);
	}

	public boolean getButton(int button) {
		return buttons.buttons[button];
	}

	public boolean getButtonDown(int button) {
		return downMouse.contains(button);
	}

	public boolean getButtonUp(int button) {
		return upMouse.contains(button);
	}

	public boolean isGrabbed() {
		return grabbed;
	}

	public void setGrabbed(boolean grabbed) {
		this.grabbed = grabbed;
	}

	public MousePosition getCursorPosCallback() {
		return position;
	}

	public MouseButton getMouseButtonCallback() {
		return buttons;
	}

	public MouseScroll getScrollCallback() {
		return scroll;
	}
}