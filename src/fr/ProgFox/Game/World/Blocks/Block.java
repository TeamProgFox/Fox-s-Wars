package fr.ProgFox.Game.World.Blocks;

import java.util.ArrayList;
import java.util.List;

import fr.ProgFox.Math.Color4f;

public class Block {

	public static List<Block> blocks = new ArrayList<>();

	public static final Block TESTE = new Block(new Color4f(1f, 1f, 1f, 0f), "TESTE");
	private String name;
	public Color4f color;
	public static float sizeX = 1, sizeY = 1, sizeZ = 1;

	public Block(Color4f color, String name) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setColor(Color4f newColor) {
		this.color = newColor;
	}

	public Color4f getColor() {
		return this.color;
	}

	public static void add(Block block) {
		blocks.add(block);
	}

	public static Block getBlock(String name) {
		for (Block a : blocks) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}

	public float[] BlockDataFront(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
		return new float[] {

				x + 1, y, z, r * 0.8f * color2.r, g * 0.8f * color2.r, b * 0.8f * color2.r, a, ///
				x, y, z, r * 0.8f * color2.g, g * 0.8f * color2.g, b * 0.8f * color2.g, a, //////
				x, y + 1, z, r * 0.8f * color2.b, g * 0.8f * color2.b, b * 0.8f * color2.b,a, /////
				x + 1, y + 1, z, r * 0.8f * color2.a, g * 0.8f * color2.a, b * 0.8f * color2.a, a, // 5

		};

	}

	public float[] BlockDataBack(float x, float y, float z, Color4f color2) {
		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x, y, z + 1, r * 0.8f * color2.r, g * 0.8f * color2.r, b * 0.8f * color2.r, a, ///
				x + 1, y, z + 1, r * 0.8f * color2.g, g * 0.8f * color2.g, b * 0.8f * color2.g, a, //////
				x + 1, y + 1, z + 1, r * 0.8f * color2.b, g * 0.8f * color2.b, b * 0.8f * color2.b, a, /////
				x, y + 1, z + 1, r * 0.8f * color2.a, g * 0.8f * color2.a, b * 0.8f * color2.a, a, // 5

		};

	}

	public float[] BlockDataRight(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x + 1, y, z, r * 0.75f * color2.r, g * 0.75f * color2.r, b * 0.75f * color2.r, a , ///
				x + 1, y + 1, z, r * 0.75f * color2.g, g * 0.75f * color2.g, b * 0.75f * color2.g, a, //////
				x + 1, y + 1, z + 1, r * 0.75f * color2.b, g * 0.75f * color2.b, b * 0.75f * color2.b, a, /////
				x + 1, y, z + 1, r * 0.75f * color2.a, g * 0.75f * color2.a, b * 0.75f * color2.a, a, // 5

		};

	}

	public float[] BlockDataLeft(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x, y + 1, z, r * 0.75f * color2.r, g * 0.75f * color2.r, b * 0.75f * color2.r, a, ///
				x, y, z, r * 0.75f * color2.g, g * 0.75f * color2.g, b * 0.75f * color2.g, a, //////
				x, y, z + 1, r * 0.75f * color2.b, g * 0.75f * color2.b, b * 0.75f * color2.b, a, /////
				x, y + 1, z + 1, r * 0.75f * color2.a, g * 0.75f * color2.a, b * 0.75f * color2.a, a, // 5

		};

	}

	public float[] BlockDataUp(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x + 1, y + 1, z, r * 0.7f * color2.r, g * 0.7f * color2.r, b * 0.7f * color2.r, a, ///
				x, y + 1, z, r * 0.7f * color2.g, g * 0.7f * color2.g, b * 0.7f * color2.g, a, //////
				x, y + 1, z + 1, r * 0.7f * color2.b, g * 0.7f * color2.b, b * 0.7f * color2.b, a, /////
				x + 1, y + 1, z + 1, r * 0.7f * color2.a, g * 0.7f * color2.a, b * 0.7f * color2.a, a, // 5

		};

	}

	public float[] BlockDataDown(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x, y, z, r * 0.7f * color2.r, g * 0.7f * color2.r, b * 0.7f * color2.r, a, ///
				x + 1, y, z, r * 0.7f * color2.g, g * 0.7f * color2.g, b * 0.7f * color2.g, a, //////
				x + 1, y, z + 1, r * 0.7f * color2.b, g * 0.7f * color2.b, b * 0.7f * color2.b, a, /////
				x, y, z + 1, r * 0.7f * color2.a, g * 0.7f * color2.a, b * 0.7f * color2.a, a, // 5

		};

	}

}