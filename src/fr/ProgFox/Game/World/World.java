package fr.ProgFox.Game.World;

import java.util.Random;
import fr.ProgFox.Game.Logs.Logs;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Display;
import fr.ProgFox.Renderer.Shader.ColorShader;

public class World {
	public static int sizeX = 0;
	public static int sizeZ = 0;
	public static int moreSizeX = 0;
	public static int moreSizeZ = 0;
	public Noise noise;
	public Noise noiseColor;
	public Chunk[][] chunks;
	private Random random;
	int i = 0;
	float percentage;

	public World(long seed) {

		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 15);
		noiseColor = new Noise(random.nextLong(), 30, 5);
		chunks = new Chunk[1000][1000];
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z] = new Chunk(x, 0, z, noise, noiseColor, random, this);
				percentage = ((float) (x + 1) / sizeX) * 33;
				Display.update();
			}
			System.out.println(percentage);
		}
		new Logs().Info("FIN DE LA GENERATION DES CHUNKS");
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z].generateVegetation();
				percentage = (((float) (x + 1) / sizeX) * 33) + 33;
				Display.update();
			}
			System.out.println(percentage);
		}
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z].createChunk();
				percentage = (((float) (x + 1) / sizeX) * 33) + 66;
				Display.update();
			}
			System.out.println(percentage);
		}
	}

	int renderSize = 4;

	public void update(Camera cam) {
		float xP = (float) (cam.position.x / 16);
		float zP = (float) (cam.position.z / 16);

		int x = (int) xP;
		int z = (int) zP;
		if (x < 0)
			x = 0;
		if (z < 0)
			z = 0;

		for (int i = 0; i < renderSize; i++) {
			for (int j = 0; j < renderSize; j++) {
				if (x + i > 100 || z + j > 100)
					return;
				if (x + i < 0 || z + j < 0)
					return;

				if (chunks[x + i][z + j] == null) {
					chunks[x + i][z + j] = new Chunk(x + i, 0, z + j, noise, noiseColor, random, this);
					chunks[x + i][z + j].generateVegetation();
					chunks[x + i][z + j].createChunk();
				}
			}
		}

		for (int i = 0; i < renderSize; i++) {
			for (int j = 0; j < renderSize; j++) {
				if (x - i > 100 || z - j > 100)
					return;
				if (x - i < 0 || z - j < 0)
					return;
				if (chunks[x - i][z - j] == null) {
					chunks[x - i][z - j] = new Chunk(x - i, 0, z - j, noise, noiseColor, random, this);
					chunks[x - i][z - j].generateVegetation();
					chunks[x - i][z - j].createChunk();
				}
			}
		}

		for (int i = 0; i < renderSize; i++) {
			for (int j = 0; j < renderSize; j++) {
				if (x + i > 100 || z - j > 100)
					return;
				if (x + i < 0 || z - j < 0)
					return;
				if (chunks[x + i][z - j] == null) {
					chunks[x + i][z - j] = new Chunk(x + i, 0, z - j, noise, noiseColor, random, this);
					chunks[x + i][z - j].generateVegetation();
					chunks[x + i][z - j].createChunk();
				}
			}
		}

		for (int i = 0; i < renderSize; i++) {
			for (int j = 0; j < renderSize; j++) {
				if (x - i > 100 || z + j > 100)
					return;
				if (x - i < 0 || z + j < 0)
					return;
				if (chunks[x - i][z + j] == null) {
					chunks[x - i][z + j] = new Chunk(x - i, 0, z + j, noise, noiseColor, random, this);
					chunks[x - i][z + j].generateVegetation();
					chunks[x - i][z + j].createChunk();
				}
			}
		}
	}

	public void render(Camera cam) {
		float xP = (float) (cam.position.x / 16);
		float zP = (float) (cam.position.z / 16);
		for (int x = 0; x < 100; x++) {
			for (int z = 0; z < 100; z++) {

				if (chunks[x][z] != null && chunks[x][z].hasShader == false) {
					chunks[x][z].setShader(new ColorShader());
				}

				if (chunks[x][z] != null && chunks[x][z].createRequest) {
					chunks[x][z].createBuffer();
				}

				if (chunks[x][z] != null && chunks[x][z].updateRequest) {
					chunks[x][z].updateVBO();
				}
				if (chunks[x][z] != null && chunks[x][z].canRender && chunks[x][z].hasShader) {
					if (xP < x + renderSize && xP > x - renderSize && zP < z + renderSize && zP > z - renderSize) {
						chunks[x][z].render(cam);
					}
				}
			}
		}
	}

	public static void cycleToDay() {
		if (Var.isInDay) {
			Var.light += Var.speedTime;
			if (Var.light >= 1)
				Var.isInDay = false;
		}
		if (!Var.isInDay) {
			Var.light -= Var.speedTime;
			if (Var.light <= 0.2f)
				Var.isInDay = true;
		}

	}

	public Block getBlock(float x, float y, float z) {
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;
		if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
			return null;
		if (chunks[(int) xx][(int) zz] == null)
			return null;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;
		return chunk.getBlock(x3, y3, z3);
	}

	public void addBlock(float x, float y, float z, Block block, boolean update) {

		int xx = (int) (x / Chunk.SIZE);
		int zz = (int) (z / Chunk.SIZE);

		if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
			return;

		Chunk chunk = chunks[(int) xx][(int) zz];
		if (chunk == null)
			return;

		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.addBlock(x3, y3, z3, block, update);

	}

	public void removeBlock(float x, float y, float z, boolean update) {
		if ((int) y == 0)
			return;
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;

		if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		if (chunk == null)
			return;

		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.removeBlock(x3, y3, z3, update);

		if ((int) x3 == 15) {
			if (getChunk(xx + 1, zz) != null) {
				getChunk(xx + 1, zz).updateChunk();
			}
		}
		if ((int) x3 == 0 && (int) xx > 0) {
			if (getChunk(xx - 1, zz) != null) {
				getChunk(xx - 1, zz).updateChunk();
			}
		}
		if ((int) z3 == 15) {
			if (getChunk(xx, zz + 1) != null) {
				getChunk(xx, zz + 1).updateChunk();
			}
		}
		if ((int) z3 == 0 && (int) zz > 0) {
			if (getChunk(xx, zz - 1) != null) {
				getChunk(xx, zz - 1).updateChunk();
			}
		}
	}

	public Chunk getChunk(float x, float z) {
		if (x < 0 || z < 0 || x >= 100 || z >= 100)
			return null;
		Chunk c = chunks[(int) x][(int) z];
		return c;
	}

	// TESTE
}