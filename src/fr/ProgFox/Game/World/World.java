package fr.ProgFox.Game.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Logs.Logs;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.DisplayManager;

public class World {
	public static int sizeX = 4;
	public static int sizeZ = 4;
	public static int moreSizeX = 0;
	public static int moreSizeZ = 0;
	public Noise noise;
	public Chunk[][] chunks;
	public Chunk[][] newChunks;
	private Random random;

	float percentage;

	public World(long seed) {

		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 15);
		chunks = new Chunk[100][100];
		newChunks = new Chunk[100][100];
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
			Display.setTitle("Fox's Wars - WorldVegetationGeneration: " + (int) percentage + "%");
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

	public void update(Camera cam) {

		float xP = (float) (cam.position.x / 16);
		float zP = (float) (cam.position.z / 16);

		if (xP < 0)
			xP = 0;
		if (zP < 0)
			zP = 0;

		if (chunks[(int) xP][(int) zP] == null) {
			new Logs().Info("debut de la g�n�ration");
			chunks[(int) xP][(int) zP] = new Chunk(xP, 0, zP, noise, random, this);
			new Logs().Info("debut de la vegetation");
			chunks[(int) xP][(int) zP].generateVegetation();
			new Logs().Info("debut de la cr�ation");
			chunks[(int) xP][(int) zP].createChunk();

		}

		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				if (getChunk(x, z) != null) {
					chunks[x][z].update();
				}
			}
		}
	}

	public void render(Camera cam) {
		float xP = (float) (cam.position.x / 16);
		float zP = (float) (cam.position.z / 16);
		// int renderDistance = 2;
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				if (getChunk(x, z) != null && getChunk(x, z).canRender) {
					chunks[x][z].render(cam);
				}
			}
		}
	}

	public void newRender(Camera cam) {

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
		chunk.addBlock(x3, y3, z3, block);
	}

	public void removeBlock(float x, float y, float z) {
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

		chunk.removeBlock(x3, y3, z3);

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
			if (getChunk(xx, zz - -1) != null) {
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