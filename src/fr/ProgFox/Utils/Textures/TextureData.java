package fr.ProgFox.Utils.Textures;

import java.nio.IntBuffer;

public class TextureData {

	private int id;
	private int width, height;
	private IntBuffer buffer;

	public TextureData(int id, int width, int height, IntBuffer buffer) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}

	public int getID() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public IntBuffer getBuffer() {
		return buffer;
	}
}