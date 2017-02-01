package fr.ProgFox.Utils;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import fr.ProgFox.Math.Mat4;

public class Buffer {
	public static FloatBuffer createBuffer(Mat4 v) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

		for (int x = 0; x < 4; x++) {
			for (int z = 0; z < 4; z++) {
				buffer.put(v.getM(x, z));
			}
		}
		buffer.flip();

		return buffer;
	}
}
