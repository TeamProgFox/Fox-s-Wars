package fr.ProgFox.World;

import java.util.Random;

import fr.ProgFox.Math.*;

public class Noise {
	private long seed;
	private Random rand;
	private int octave;
	private float amplitude;

	public Noise(long seed, int octave, float amplitude) {
		this.seed = seed;
		this.octave = octave;
		this.amplitude = amplitude;

		rand = new Random();
	}

	public float getNoise(float x, float z) {
		int xmin = (int) (double) x / octave;
		int xmax = (int) xmin + 1;
		int zmin = (int) (double) z / octave;
		int zmax = (int) zmin + 1;

		Vec2 a = new Vec2(xmin, zmin);
		Vec2 b = new Vec2(xmax, zmin);
		Vec2 c = new Vec2(xmax, zmax);
		Vec2 d = new Vec2(xmin, zmax);

		float ra = (float) noise(a);
		float rb = (float) noise(b);
		float rc = (float) noise(c);
		float rd = (float) noise(d);

		float t1 = (x - xmin * octave) / octave;
		float t2 = (z - zmin * octave) / octave;
		float i1 = interpolate(ra, rb, t1);
		float i2 = interpolate(rd, rc, t1);

		float h = interpolate(i1, i2, t2);

		return h * amplitude;
	}

	private float interpolate(float a, float b, float t) {
		
		float ft = (float) (t * Math.PI);
		float f = (float) ((1f - Math.cos(ft)) * 0.5f);
		float ret = a * (1f - f) + b * f;

		return ret;
	}

	private double noise(Vec2 coord) {
		double var = 10000 * (Math.sin(coord.x + Math.cos(coord.y)) + Math.tan(seed));
		rand.setSeed((long) var);
		double ret = rand.nextDouble();

		return ret;
	}
}
