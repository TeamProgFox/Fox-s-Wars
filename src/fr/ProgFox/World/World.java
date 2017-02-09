package fr.ProgFox.World;

import java.util.Random;

import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Logs.Logs;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.DisplayManager;
import fr.ProgFox.World.Blocks.Block;

public class World {
	public static int sizeX = 10;
	public static int sizeZ = 10;
	public static int moreSizeX = 0;
	public static int moreSizeZ = 0;
	public Noise noise;
	public Chunk[][] chunks;
	private Random random;
	float percentage;

	public World(long seed) {

		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 15);
		chunks = new Chunk[100][100];
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z] = new Chunk(x, 0, z, noise, random, this);
				percentage = ((float) (x + 1) / sizeX) * 33;
				DisplayManager.update();
			}
			Display.setTitle("Fox's Wars - WorldGenerate : " + (int) percentage + "%");
			System.out.println(percentage);
		}
		new Logs().Info("FIN DE LA GENERATION DES CHUNKS");
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z].generateVegetation();
				percentage = (((float) (x + 1) / sizeX) * 33) + 33;
				DisplayManager.update();
			}
			Display.setTitle("Fox's Wars - WorldCreationVegetation : " + (int) percentage + "%");
			System.out.println(percentage);
		}
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				chunks[x][z].createChunk();
				percentage = (((float) (x + 1) / sizeX) * 33) + 66;
				DisplayManager.update();
			}
			Display.setTitle("Fox's Wars - WorldCreation : " + (int) percentage + "%");
			System.out.println(percentage);
		}
	}

	public void update() {
		cycleToDay();
		for (int x = moreSizeX; x < sizeX + moreSizeX; x++) {
			for (int z = moreSizeZ; z < sizeZ + moreSizeZ; z++) {
				if (getChunk(x, z) != null) {
					chunks[x][z].update();
				}
			}
		}
	}

	public void render(Camera cam) {
		float xP1 = (float) (cam.position.x / 16);
		float zP1 = (float) (cam.position.z / 16);
		int renderDistance = 2;
		for (int x = moreSizeX; x < sizeX + moreSizeX; x++) {
			for (int z = moreSizeZ; z < sizeZ + moreSizeZ; z++) {
				if (getChunk(x, z) != null) {
					chunks[x][z].render(cam);
				}
				if (xP1 > x - renderDistance && xP1 < x + renderDistance + 1) {
					if (zP1 > z - renderDistance && zP1 < z + renderDistance + 1) {
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

	public void addBlock(float x, float y, float z, Block block) {
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;

		if (xx < 0 || xx >= sizeX || zz < 0 || zz >= sizeZ)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.addBlock(x3, y3, z3, block);
	}

	public void removeBlock(float x, float y, float z) {
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;

		if (xx < 0 || xx >= sizeX || zz < 0 || zz >= sizeZ)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.removeBlock(x3, y3, z3);

		if ((int) x3 == 15) {
			if (getChunk(xx + 1, zz) != null) {
				getChunk(xx + 1, zz).updateChunk();
			}
		}
		if ((int) x3 == 0 && (int) xx > 0) {
			getChunk(xx - 1, zz).updateChunk();
		}
		if ((int) z3 == 15) {
			if (getChunk(xx, zz + 1) != null) {
				getChunk(xx, zz + 1).updateChunk();
			}
		}
		if ((int) z3 == 0 && (int) zz > 0) {
			getChunk(xx, zz - 1).updateChunk();
		}

	}

	public Chunk getChunk(float x, float z) {
		if (x < 0 || z < 0 || x >= 100 || z >= 100)
			return null;
		Chunk c = chunks[(int) x][(int) z];
		return c;
	}

	public void lol() {
		// if (getChunk(xP3, zP3 - 3) == null) {
		// if (xP3 > 0 && zP3 - 3 > 0) {
		// chunks[(int) xP3][(int) zP3 - 3] = new Chunk(xP3, 0, zP3 - 3, noise,
		// random, this);
		// chunks[(int) xP3][(int) zP3 - 3].createChunk();
		//
		// if (getChunk(xP3, zP3 - 2) == null) {
		// chunks[(int) xP3][(int) zP3 - 2] = new Chunk(xP3, 0, zP3 - 2, noise,
		// random, this);
		// chunks[(int) xP3][(int) zP3 - 2].createChunk();
		// }
		// if (getChunk(xP3, zP3 - 1) == null) {
		// chunks[(int) xP3][(int) zP3 - 1] = new Chunk(xP3, 0, zP3 - 1, noise,
		// random, this);
		// chunks[(int) xP3][(int) zP3 - 1].createChunk();
		// }
		//
		// if (getChunk(xP3, zP3 + 2) == null) {
		// chunks[(int) xP3][(int) zP3 + 2] = new Chunk(xP3, 0, zP3 + 2, noise,
		// random, this);
		// chunks[(int) xP3][(int) zP3 + 2].createChunk();
		// }
		// if (getChunk(xP3, zP3 + 1) == null) {
		// chunks[(int) xP3][(int) zP3 + 1] = new Chunk(xP3, 0, zP3 + 1, noise,
		// random, this);
		// chunks[(int) xP3][(int) zP3 + 1].createChunk();
		// }
		//
		//
		// }
		// }
		// if (getChunk(xP3 - 3, zP3) == null || getChunk(xP3 - 2, zP3) == null
		// || getChunk(xP3 - 1, zP3) == null
		// || getChunk(xP3, zP3) == null) {
		// if (xP3 - 3 > 0 && zP3 > 0) {
		// chunks[(int) xP3 - 3][(int) zP3] = new Chunk(xP3 - 3, 0, zP3, noise,
		// random, this);
		// chunks[(int) xP3 - 3][(int) zP3].createChunk();
		// if (getChunk(xP3 - 2, zP3) == null) {
		// chunks[(int) xP3 - 2][(int) zP3] = new Chunk(xP3 - 2, 0, zP3, noise,
		// random, this);
		// chunks[(int) xP3 - 2][(int) zP3].createChunk();
		// }
		// if (getChunk(xP3 - 1, zP3) == null) {
		//
		// chunks[(int) xP3 - 1][(int) zP3] = new Chunk(xP3 - 1, 0, zP3, noise,
		// random, this);
		// chunks[(int) xP3 - 1][(int) zP3].createChunk();
		// }
		//
		// if (getChunk(xP3 + 2, zP3) == null) {
		// chunks[(int) xP3 + 2][(int) zP3] = new Chunk(xP3 + 2, 0, zP3, noise,
		// random, this);
		// chunks[(int) xP3 + 2][(int) zP3].createChunk();
		// }
		// if (getChunk(xP3 + 1, zP3) == null) {
		//
		// chunks[(int) xP3 + 1][(int) zP3] = new Chunk(xP3 + 1, 0, zP3, noise,
		// random, this);
		// chunks[(int) xP3 + 1][(int) zP3].createChunk();
		// }
		//
		// }
		//
		// }
		// if (getChunk(xP3, zP3) == null) {
		// if (xP3 > 0 && zP3 > 0) {
		// chunks[(int) xP3][(int) zP3] = new Chunk(xP3, 0, zP3, noise, random,
		// this);
		// chunks[(int) xP3][(int) zP3].createChunk();
		// }
		// }
	}
	// TESTE
}