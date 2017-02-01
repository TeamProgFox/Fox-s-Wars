package fr.ProgFox.Math;

public class Vec2 {

	public static final Vec2 UP = new Vec2(0.0F, 1.0F);
	public static final Vec2 RIGHT = new Vec2(1.0F, 0.0F);
	public float x;
	public float y;

	public Vec2() {
		this(0.0F, 0.0F);
	}

	public Vec2(float v) {
		this(v, v);
	}

	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2(Vec2 v) {
		this(v.x, v.y);
	}

	public Vec2 add(float v) {
		x += v;
		y += v;
		return this;
	}

	public Vec2 add(Vec2 vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Vec2 copy() {
		return new Vec2(x, y);
	}

	public Vec2 cross(Vec2 r) {
		return new Vec2(r.x, -r.y);
	}

	public Vec2 div(float v) {
		x /= v;
		y /= v;
		return this;
	}

	public Vec2 div(Vec2 vec) {
		x /= vec.x;
		y /= vec.y;
		return this;
	}

	public float dot(Vec2 r) {
		return x * r.x + y * r.y;
	}

	public boolean equals(Vec2 v) {
		return x == v.x && y == v.y;
	}

	public float magnitude() {
		return (float) Math.sqrt(sqrt());
	}

	public float max() {
		return Math.max(x, y);
	}

	public float min() {
		return Math.min(x, y);
	}

	public Vec2 mul(float v) {
		x *= v;
		y *= v;
		return this;
	}

	public Vec2 mul(Vec2 vec) {
		x *= vec.x;
		y *= vec.y;
		return this;
	}

	public Vec2 negate() {
		x = -x;
		y = -y;
		return this;
	}

	public Vec2 normalize() {
		return new Vec2(x / magnitude(), y / magnitude());
	}

	public Vec2 reflect(Vec2 normal) {
		return sub(normal.mul(dot(normal) * 2.0F));
	}

	public Vec2 refract(Vec2 normal, float eta) {
		float dot = normal.dot(this);
		float k = 1.0F - eta * eta * (1.0F - dot * dot);
		Vec2 result = normal.mul(mul(eta).sub((float) ((double) (eta * dot) + Math.sqrt(k))));
		if (k < 0.0F)
			return new Vec2();
		else
			return result;
	}

	public float sqrt() {
		return x * x + y * y;
	}

	public Vec2 sub(float v) {
		x -= v;
		y -= v;
		return this;
	}

	public Vec2 sub(Vec2 vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	public String toString() {
		return (new StringBuilder(String.valueOf(x))).append(" ").append(y).toString();
	}
	
	public float[] toFloat() {
		return new float[]{x, y};
	}

	public String toStringComp() {
		return "x: " + x + ", y: " + y;
	}

}