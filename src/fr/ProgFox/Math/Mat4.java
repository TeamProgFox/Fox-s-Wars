package fr.ProgFox.Math;

public class Mat4 {

	public static Mat4 cameraView(Vec3 forward, Vec3 up) {
		Mat4 m = identity();
		Vec3 f = (new Vec3(forward)).normalize();
		Vec3 r = (new Vec3(up)).normalize();
		r = r.cross(f);
		Vec3 u = f.cross(r);
		m.matrix[0][0] = r.x;
		m.matrix[0][1] = r.y;
		m.matrix[0][2] = r.z;
		m.matrix[1][0] = u.x;
		m.matrix[1][1] = u.y;
		m.matrix[1][2] = u.z;
		m.matrix[2][0] = f.x;
		m.matrix[2][1] = f.y;
		m.matrix[2][2] = f.z;
		return m;
	}

	private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20,
			float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
	}

	public static Mat4 identity() {
		Mat4 result = new Mat4();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++)
				result.matrix[x][y] = 0.0F;

		}

		result.matrix[0][0] = 1.0F;
		result.matrix[1][1] = 1.0F;
		result.matrix[2][2] = 1.0F;
		result.matrix[3][3] = 1.0F;
		return result;
	}

	public static Mat4 invert(Mat4 src, Mat4 dest) {
		float determinant = src.determinant();
		if (determinant != 0.0F) {
			if (dest == null)
				dest = new Mat4();
			float determinant_inv = 1.0F / determinant;
			float t00 = determinant3x3(src.matrix[1][1], src.matrix[1][2], src.matrix[1][3], src.matrix[2][1],
					src.matrix[2][2], src.matrix[2][3], src.matrix[3][1], src.matrix[3][2], src.matrix[3][3]);
			float t01 = -determinant3x3(src.matrix[1][0], src.matrix[1][2], src.matrix[1][3], src.matrix[2][0],
					src.matrix[2][2], src.matrix[2][3], src.matrix[3][0], src.matrix[3][2], src.matrix[3][3]);
			float t02 = determinant3x3(src.matrix[1][0], src.matrix[1][1], src.matrix[1][3], src.matrix[2][0],
					src.matrix[2][1], src.matrix[2][3], src.matrix[3][0], src.matrix[3][1], src.matrix[3][3]);
			float t03 = -determinant3x3(src.matrix[1][0], src.matrix[1][1], src.matrix[1][2], src.matrix[2][0],
					src.matrix[2][1], src.matrix[2][2], src.matrix[3][0], src.matrix[3][1], src.matrix[3][2]);
			float t10 = determinant3x3(src.matrix[0][1], src.matrix[0][2], src.matrix[0][3], src.matrix[2][1],
					src.matrix[2][3], src.matrix[2][3], src.matrix[3][1], src.matrix[3][2], src.matrix[3][3]);
			float t11 = -determinant3x3(src.matrix[0][0], src.matrix[0][2], src.matrix[0][3], src.matrix[2][0],
					src.matrix[2][3], src.matrix[2][3], src.matrix[3][0], src.matrix[3][2], src.matrix[3][3]);
			float t12 = determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][3], src.matrix[2][0],
					src.matrix[2][2], src.matrix[2][3], src.matrix[3][0], src.matrix[3][1], src.matrix[3][3]);
			float t13 = -determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][2], src.matrix[2][0],
					src.matrix[2][2], src.matrix[2][2], src.matrix[3][0], src.matrix[3][1], src.matrix[3][2]);
			float t20 = determinant3x3(src.matrix[0][1], src.matrix[0][2], src.matrix[0][3], src.matrix[1][1],
					src.matrix[1][2], src.matrix[1][3], src.matrix[3][1], src.matrix[3][2], src.matrix[3][3]);
			float t21 = -determinant3x3(src.matrix[0][0], src.matrix[0][2], src.matrix[0][3], src.matrix[1][0],
					src.matrix[1][2], src.matrix[1][3], src.matrix[3][0], src.matrix[3][2], src.matrix[3][3]);
			float t22 = determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][3], src.matrix[1][0],
					src.matrix[1][1], src.matrix[1][3], src.matrix[3][0], src.matrix[3][1], src.matrix[3][3]);
			float t23 = -determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][2], src.matrix[1][0],
					src.matrix[1][1], src.matrix[1][2], src.matrix[3][0], src.matrix[3][1], src.matrix[3][2]);
			float t30 = determinant3x3(src.matrix[0][1], src.matrix[0][2], src.matrix[0][3], src.matrix[1][1],
					src.matrix[1][2], src.matrix[1][3], src.matrix[2][1], src.matrix[2][2], src.matrix[2][3]);
			float t31 = -determinant3x3(src.matrix[0][0], src.matrix[0][2], src.matrix[0][3], src.matrix[1][0],
					src.matrix[1][2], src.matrix[1][3], src.matrix[2][0], src.matrix[2][2], src.matrix[2][3]);
			float t32 = determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][3], src.matrix[1][0],
					src.matrix[1][1], src.matrix[1][3], src.matrix[2][0], src.matrix[2][1], src.matrix[2][3]);
			float t33 = -determinant3x3(src.matrix[0][0], src.matrix[0][1], src.matrix[0][2], src.matrix[1][0],
					src.matrix[1][1], src.matrix[1][2], src.matrix[2][0], src.matrix[2][1], src.matrix[2][2]);
			dest.matrix[0][0] = t00 * determinant_inv;
			dest.matrix[1][1] = t11 * determinant_inv;
			dest.matrix[2][2] = t22 * determinant_inv;
			dest.matrix[3][3] = t33 * determinant_inv;
			dest.matrix[0][1] = t10 * determinant_inv;
			dest.matrix[1][0] = t01 * determinant_inv;
			dest.matrix[2][0] = t02 * determinant_inv;
			dest.matrix[0][2] = t20 * determinant_inv;
			dest.matrix[1][2] = t21 * determinant_inv;
			dest.matrix[2][1] = t12 * determinant_inv;
			dest.matrix[0][3] = t30 * determinant_inv;
			dest.matrix[3][0] = t03 * determinant_inv;
			dest.matrix[1][3] = t31 * determinant_inv;
			dest.matrix[3][1] = t13 * determinant_inv;
			dest.matrix[3][2] = t23 * determinant_inv;
			dest.matrix[2][3] = t32 * determinant_inv;
			return dest;
		} else {
			return null;
		}
	}

	public static Mat4 orthographic(float right, float left, float top, float bottom, float zNear, float zFar) {
		Mat4 m = identity();
		m.matrix[0][0] = 2.0F / (right - left);
		m.matrix[0][3] = -(right + left) / (right - left);
		m.matrix[1][1] = 2.0F / (top - bottom);
		m.matrix[1][3] = -(top + bottom) / (top - bottom);
		m.matrix[2][2] = -2F / (zFar - zNear);
		m.matrix[2][3] = -(zFar + zNear) / (zFar - zNear);
		return m;
	}

	public Mat4 perspective(float fov, float aspect, float zNear, float zFar) {
		Mat4 result = identity();
		float FOV = (float) Math.tan(Math.toRadians(fov / 2.0F));
		float r = zNear - zFar;
		result.matrix[0][0] = 1.0F / (FOV * aspect);
		result.matrix[1][1] = 1.0F / FOV;
		result.matrix[2][2] = (-zNear - zFar) / r;
		result.matrix[2][3] = (2.0F * zFar * zNear) / r;
		result.matrix[3][2] = 1.0F;
		result.matrix[3][3] = 0.0F;
		return result;
	}

	public static Mat4 rotate(float x, float y, float z) {
		Mat4 result = identity();
		Mat4 rx = identity();
		Mat4 ry = identity();
		Mat4 rz = identity();
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		rx.matrix[1][1] = (float) Math.cos(x);
		rx.matrix[1][2] = -(float) Math.sin(x);
		rx.matrix[2][1] = (float) Math.sin(x);
		rx.matrix[2][2] = (float) Math.cos(x);
		ry.matrix[0][0] = (float) Math.cos(y);
		ry.matrix[0][2] = -(float) Math.sin(y);
		ry.matrix[2][0] = (float) Math.sin(y);
		ry.matrix[2][2] = (float) Math.cos(y);
		rz.matrix[0][0] = (float) Math.cos(z);
		rz.matrix[0][1] = -(float) Math.sin(z);
		rz.matrix[1][0] = (float) Math.sin(z);
		rz.matrix[1][1] = (float) Math.cos(z);
		result = rz.mul(ry.mul(rx));
		return result;
	}

	public static Mat4 rotate(Vec3 forward, Vec3 up, Vec3 right) {
		Mat4 result = identity();
		Vec3 f = (new Vec3(forward)).normalize();
		Vec3 r = (new Vec3(right)).normalize();
		Vec3 u = (new Vec3(up)).normalize();
		result.matrix[0][0] = r.x;
		result.matrix[0][1] = r.y;
		result.matrix[0][2] = r.z;
		result.matrix[1][0] = u.x;
		result.matrix[1][1] = u.y;
		result.matrix[1][2] = u.z;
		result.matrix[2][0] = f.x;
		result.matrix[2][1] = f.y;
		result.matrix[2][2] = f.z;
		return result;
	}

	public static Mat4 rotate(Vec3 forward, Vec3 up) {
		Mat4 m = identity();

		Vec3 f = new Vec3(forward).normalize();
		Vec3 r = new Vec3(up).normalize();
		r = r.cross(f);
		Vec3 u = f.cross(r);

		m.matrix[0][0] = r.x;
		m.matrix[0][1] = r.y;
		m.matrix[0][2] = r.z;

		m.matrix[1][0] = u.x;
		m.matrix[1][1] = u.y;
		m.matrix[1][2] = u.z;

		m.matrix[2][0] = f.x;
		m.matrix[2][1] = f.y;
		m.matrix[2][2] = f.z;

		return m;
	}

	public static Mat4 scale(float x, float y, float z) {
		Mat4 result = identity();
		result.matrix[0][0] = x;
		result.matrix[1][1] = y;
		result.matrix[2][2] = z;
		return result;
	}

	public static Vec3 transform(Mat4 m, Vec3 v) {
		return new Vec3(m.matrix[0][0] * v.x + m.matrix[0][1] * v.y + m.matrix[0][2] * v.z + m.matrix[0][3],
				m.matrix[1][0] * v.x + m.matrix[1][1] * v.y + m.matrix[1][2] * v.z + m.matrix[1][3],
				m.matrix[2][0] * v.x + m.matrix[2][1] * v.y + m.matrix[2][2] * v.z + m.matrix[2][3]);
	}

	public static Vec4 transform(Mat4 left, Vec4 right, Vec4 dest) {
		if (dest == null)
			dest = new Vec4();
		float x = left.matrix[0][0] * right.x + left.matrix[1][0] * right.y + left.matrix[2][0] * right.z
				+ left.matrix[3][0] * right.w;
		float y = left.matrix[0][1] * right.x + left.matrix[1][1] * right.y + left.matrix[2][1] * right.z
				+ left.matrix[3][1] * right.w;
		float z = left.matrix[0][2] * right.x + left.matrix[1][2] * right.y + left.matrix[2][2] * right.z
				+ left.matrix[3][2] * right.w;
		float w = left.matrix[0][3] * right.x + left.matrix[1][3] * right.y + left.matrix[2][3] * right.z
				+ left.matrix[3][3] * right.w;
		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;
		return dest;
	}

	public static Mat4 translate(float x, float y, float z) {
		Mat4 result = identity();
		result.matrix[0][3] = x;
		result.matrix[1][3] = y;
		result.matrix[2][3] = z;
		return result;
	}

	public Mat4 translate(Vec3 pos) {
		Mat4 result = identity();
		result.matrix[0][3] = pos.x;
		result.matrix[1][3] = pos.y;
		result.matrix[2][3] = pos.z;
		return result;
	}

	public float matrix[][];

	public Mat4() {
		matrix = new float[4][4];
	}

	public float determinant() {
		float f = matrix[0][0]
				* ((matrix[1][1] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][1]
						+ matrix[1][3] * matrix[2][1] * matrix[3][2]) - matrix[1][3] * matrix[2][2] * matrix[3][1]
						- matrix[1][1] * matrix[2][3] * matrix[3][2] - matrix[1][2] * matrix[2][1] * matrix[3][3]);
		f -= matrix[0][1] * ((matrix[1][0] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][0]
				+ matrix[1][3] * matrix[2][0] * matrix[3][2]) - matrix[1][3] * matrix[2][2] * matrix[3][0]
				- matrix[1][0] * matrix[2][3] * matrix[3][2] - matrix[1][2] * matrix[2][0] * matrix[3][3]);
		f += matrix[0][2] * ((matrix[1][0] * matrix[2][1] * matrix[3][3] + matrix[1][1] * matrix[2][3] * matrix[3][0]
				+ matrix[1][3] * matrix[2][0] * matrix[3][1]) - matrix[1][3] * matrix[2][1] * matrix[3][0]
				- matrix[1][0] * matrix[2][3] * matrix[3][1] - matrix[1][1] * matrix[2][0] * matrix[3][3]);
		f -= matrix[0][3] * ((matrix[1][0] * matrix[2][1] * matrix[3][2] + matrix[1][1] * matrix[2][2] * matrix[3][0]
				+ matrix[1][2] * matrix[2][0] * matrix[3][1]) - matrix[1][2] * matrix[2][1] * matrix[3][0]
				- matrix[1][0] * matrix[2][2] * matrix[3][1] - matrix[1][1] * matrix[2][0] * matrix[3][2]);
		return f;
	}

	public Mat4 mul(Mat4 m) {
		Mat4 result = identity();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++)
				result.matrix[x][y] = matrix[x][0] * m.matrix[0][y] + matrix[x][1] * m.matrix[1][y]
						+ matrix[x][2] * m.matrix[2][y] + matrix[x][3] * m.matrix[3][y];

		}

		return result;
	}

	public float getM(int x, int y) {
		return matrix[x][y];
	}
}
