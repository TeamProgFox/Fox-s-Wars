package fr.ProgFox.Inputs;

import org.lwjgl.glfw.GLFWKeyCallback; 
import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {

	private static boolean[] keys = new boolean[65535];

	public void invoke(long window, int key, int scancode, int action, int mods) {

		keys[key] = action != GLFW_FALSE;
	}

	public static boolean getKey(int key) {
		if (keys[key] != false) {
			return true;
		}

		return false;
	}
	public static boolean getKeyDown(int key) {
		if (keys[key] != false) {
			return true;
		}

		return false;
	}
	
	public static void update(){
		Mouse.update();
	}

	public static float getDX(){
		return Mouse.getDX();
	}

	public static float getDY(){
		return Mouse.getDY();
	}
	
	public static boolean getMouseDown(int key){
		if(MouseButton.buttons[key]){
			return true;
		}
		return false;
	}
	
}
