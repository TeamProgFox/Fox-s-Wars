package fr.ProgFox.Math;

public class Vec4 {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vec4() {
		this(0.0F, 0.0F, 0.0F, 0.0F);
	}

	public Vec4(float v) {
		this(v, v, v, v);
	}

	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec4(Vec4 v) {
		this(v.x, v.y, v.z, v.w);
	}

	public Vec4 sub(float v) {
		x -= v;
		y -= v;
		z -= v;
		w -= v;
		return this;
	}

	public String toString() {
		return (new StringBuilder(String.valueOf(x))).append(" ").append(y).append(" ").append(z).toString();
	}
}
