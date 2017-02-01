package fr.ProgFox.Math;

public class MathUtils {
	public static double getRotate(double dx, double dy, double ax, double ay) {
		return Math.toDegrees(Math.atan2(dy - ay, dx - ay));
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		double dou = (double) tmp / factor;
		return dou;
	}
	
	public double sqr(double v) {
		return v * v;
	}
	
	public double distance(double x1, double x2, double y1, double y2, double z1, double z2) {
		return Math.sqrt(sqr(y2 - y1) + sqr(x2 - x1) + sqr(z2 - z1));
	}
	
	public double sup(double... value) {
		if(value[0] < value[1]) return value[1];
		else return value[0];
	}

	public double inf(double... value) {
		if(value[0] > value[1]) return value[1];
		else return value[0];
	}
	
	public float sup(float... value) {
		if(value[0] < value[1]) return value[1];
		else return value[0];
	}

	public float inf(float... value) {
		if(value[0] > value[1]) return value[1];
		else return value[0];
	}

}
