package fr.ProgFox.Math;


public class Vec3 {

	public static final Vec3 UP = new Vec3(0.0F, 1.0F, 0.0F);
	public static final Vec3 FRONT = new Vec3(0.0F, 0.0F, 1.0F);
	public static final Vec3 RIGHT = new Vec3(1.0F, 0.0F, 0.0F);
	public float x;
	public float y;
	public float z;

	public Vec3() {
		this(0.0F, 0.0F, 0.0F);
	}

	public Vec3(float v) {
		this(v, v, v);
	}

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public Vec3(Vec3 v) {
		this(v.x, v.y, v.z);
	}

	public Vec3 add(float v) {
		x += v;
		y += v;
		z += v;
		return this;
	}

	public Vec3 add(float x, float y, float z) {
		x += x;
		y += y;
		z += z;
		return this;
	}

	public Vec3 add(Vec3 vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void addZ(float z) {
		this.z += z;
	}

	public Vec3 check() {
		float max = Math.max(Math.max(x, y), z);
		float min = Math.min(Math.min(x, y), z);
		float absMax = Math.abs(max - 1.0F);
		float absMin = Math.abs(min);
		float v = 0.0F;
		if (absMax > absMin)
			v = min;
		else
			v = max;
		int rv = 1;
		if (v < 0.5F)
			rv = -1;
		return new Vec3(v != x ? 0 : rv, v != y ? 0 : rv, v != z ? 0 : rv);
	}

	public Vec3 copy() {
		return new Vec3(x, y, z);
	}

	public Vec3 cross(Vec3 r) {
		float nx = y * r.z - z * r.y;
		float ny = z * r.x - x * r.z;
		float nz = x * r.y - y * r.x;
		return new Vec3(nx, ny, nz);
	}

	public float distance(Vec3 v) {
		return (float) Math.abs(Math.sqrt(squaredDistance(v)));
	}

	public Vec3 div(float v) {
		x /= v;
		y /= v;
		z /= v;
		return this;
	}

	public Vec3 div(float x, float y, float z) {
		x /= x;
		y /= y;
		z /= z;
		return this;
	}

	public Vec3 div(Vec3 vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	public float dot(Vec3 r) {
		return x * r.x + y * r.y + z * r.z;
	}

	public boolean equals(Vec3 v) {
		return x == v.x && y == v.y && z == v.z;
	}

	public float magnitude() {
		return (float) Math.sqrt(toScalar());
	}

	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	public float min() {
		return Math.min(x, Math.min(y, z));
	}

	public Vec3 mul(float v) {
		x *= v;
		y *= v;
		z *= v;
		return this;
	}

	public Vec3 mul(float x, float y, float z) {
		x *= x;
		y *= y;
		z *= z;
		return this;
	}

	public Vec3 mul(Vec3 vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public Vec3 negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public Vec3 normalize() {
		float mag = magnitude();
		return new Vec3(x / mag, y / mag, z / mag);
	}

	public void print() {
	}

	public Vec3 rotate(Quat rot) {
		Quat w = rot.mul(this).mul(rot.conjugate());
		return new Vec3(w.x, w.y, w.z);
	}

	public Vec3 reflect(Vec3 normal) {
		return sub(normal.copy().mul(dot(normal) * 2.0F));
	}

	public Vec3 refract(Vec3 normal, float eta) {
		float dot = normal.dot(this);
		float k = 1.0F - eta * eta * (1.0F - dot * dot);
		Vec3 result = normal.mul(mul(eta).sub((float) ((double) (eta * dot) + Math.sqrt(k))));
		if (k < 0.0F)
			return new Vec3();
		else
			return result;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vec3 vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}

	public float squaredDistance(Vec3 v) {
		return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y) + (z - v.z) * (z - v.z);
	}

	public Vec3 sub(float v) {
		x -= v;
		y -= v;
		z -= v;
		return this;
	}

	public Vec3 sub(float x, float y, float z) {
		x -= x;
		y -= y;
		z -= z;
		return this;
	}

	public Vec3 sub(Vec3 vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	public float toScalar() {
		return Mathf.pow(x, 2.0F) + Mathf.pow(y, 2.0F) + Mathf.pow(z, 2.0F);
	}

	public String toString() {
		return (new StringBuilder(String.valueOf(x))).append(" ").append(y).append(" ").append(z).toString();
	}

	public String toStringInt() {
		return (new StringBuilder(String.valueOf((int) x))).append(" ").append((int) y).append(" ").append((int) z)
				.toString();
	}

	public Vec2 xy() {
		return new Vec2(x, y);
	}

	public Vec2 xz() {
		return new Vec2(x, z);
	}

	public Vec2 yz() {
		return new Vec2(y, z);
	}
	
	public float[] toFloat() {
		return new float[]{x, y, z};
	}

	public String toStringComp() {
		return "x: " + x + ", y: " + y + ", z: " + z;
	}

}