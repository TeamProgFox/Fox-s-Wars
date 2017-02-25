package fr.ProgFox.Inputs;

public class Mouse {
	private static float lx, ly;
	private static float cx, cy;
	private static float dx, dy;

	public static void update() {
		cx = MousePosition.getPosX() - lx;
		dx = cx;
		lx = MousePosition.getPosX();

		cy = MousePosition.getPosY() - ly;
		dy = cy;
		ly = MousePosition.getPosY();
		
	}
	
	public static float getDX(){
		return dx;
	}
	
	public static float getDY(){
		return dy;
	}
	
	
}
