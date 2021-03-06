package fr.ProgFox.Inputs;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

	public static boolean AZERTY = true;

	public static final int NUM_KEYCODES = 1000;

	public static final int RELEASE = 0x0;
	public static final int PRESS = 0x1;
	public static final int REPEAT = 0x2;

	public boolean[] keys = new boolean[65536];

	public ArrayList<Integer> currentKeys = new ArrayList<>();
	private ArrayList<Integer> downKeys = new ArrayList<>();
	private ArrayList<Integer> upKeys = new ArrayList<>();

	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key >= 0) {
			if (AZERTY) {
				if (key == Input.KEY_Q) {
					keys[Input.KEY_A] = action != RELEASE;
					return;
				}
				if (key == Input.KEY_A) {
					keys[Input.KEY_Q] = action != RELEASE;
					return;
				}

				if (key == Input.KEY_Z) {
					keys[Input.KEY_W] = action != RELEASE;
					return;
				}
				if (key == Input.KEY_W) {
					keys[Input.KEY_Z] = action != RELEASE;
					return;
				}

				if (key == Input.KEY_Z) {
					keys[Input.KEY_W] = action != RELEASE;
					return;
				}
				if (key == Input.KEY_W) {
					keys[Input.KEY_Z] = action != RELEASE;
					return;
				}

				if (key == Input.KEY_SEMICOLON) {
					keys[Input.KEY_M] = action != RELEASE;
					return;
				}
				if (key == Input.KEY_M) {
					keys[Input.KEY_COMMA] = action != RELEASE;
					return;
				}

				if (key == Input.KEY_COMMA) {
					keys[Input.KEY_PERIOD] = action != RELEASE;
					return;
				}
				if (key == Input.KEY_PERIOD) {
					keys[Input.KEY_KP_DIVIDE] = action != RELEASE;
					return;
				}
				// if (key == KEY_M) {keys[KEY_COMMA] = action != RELEASE;
				// return;}
			}
			keys[key] = action != RELEASE;
		}
	}

	public void update() {
		upKeys.clear();

		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (!getKey(i) && currentKeys.contains(i)) {
				upKeys.add(i);
			}
		}

		downKeys.clear();

		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i) && !currentKeys.contains(i)) {
				downKeys.add(i);
			}
		}

		currentKeys.clear();

		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i)) {
				currentKeys.add(i);
			}
		}
	}

	public boolean getKey(int key) {
		return keys[key];
	}

	public boolean getKeyDown(int key) {
		return downKeys.contains(key);
	}

	public boolean getKeyUp(int key) {
		return upKeys.contains(key);
	}
}