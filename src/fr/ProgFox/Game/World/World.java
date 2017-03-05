package fr.ProgFox.Game.World;

import java.util.*;

import fr.ProgFox.Game.Entities.*;
import fr.ProgFox.Game.Variables.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Renderer.*;

public class World {
	public Noise noise;
	public Noise noiseColor;
	public Chunk[][] chunks;
	public Random random;
	float percentage;

	public Random[][] rand;

	public World(long seed, float x, float z) {
		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 15);
		noiseColor = new Noise(random.nextLong(), 1, 1);
		chunks = new Chunk[1000][1000];
		rand = new Random[1000][1000];

		for (int xx = 0; xx < 100; xx++) {
			for (int zz = 0; zz < 100; zz++) {
				rand[xx][zz] = new Random(seed);
			}
		}

	}

	int renderSize = 5;

	public void update(LocalPlayer player) {
		float xP = (float) (player.position.x / 16);
		float zP = (float) (player.position.z / 16);

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
					chunks[x + i][z + j] = new Chunk(x + i, 0, z + j, noise, noiseColor, rand[x + i][z + j], this);
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
					chunks[x - i][z - j] = new Chunk(x - i, 0, z - j, noise, noiseColor, rand[x - i][z - j], this);
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
					chunks[x + i][z - j] = new Chunk(x + i, 0, z - j, noise, noiseColor, rand[x + i][z - j], this);
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
					chunks[x - i][z + j] = new Chunk(x - i, 0, z + j, noise, noiseColor, rand[x - i][z + j], this);
					chunks[x - i][z + j].createChunk();
				}
			}
		}
	}

	public void render(LocalPlayer player) {
		float xP = (float) (player.position.x / 16);
		float zP = (float) (player.position.z / 16);
		for (int x = 0; x < 100; x++) {
			for (int z = 0; z < 100; z++) {

				if (chunks[x][z] != null && chunks[x][z].createRequest) {
					chunks[x][z].createBuffer();
				}

				if (chunks[x][z] != null && chunks[x][z].updateRequest) {
					chunks[x][z].updateVBO();
				}
				if (chunks[x][z] != null && chunks[x][z].canRender) {
					if (xP < x + renderSize && xP > x - renderSize && zP < z + renderSize && zP > z - renderSize) {
						chunks[x][z].render(player);
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
		if (chunks[(int) xx][(int) zz] == null) {
			return null;
		}

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
		Chunk chunk = chunks[(int) x][(int) z];
		return chunk;
	}

	public Chunk getChunkAtBlok(float x, float z) {
		int xx = (int) (x / Chunk.SIZE);
		int zz = (int) (z / Chunk.SIZE);

		if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
			return null;

		Chunk chunk = chunks[(int) xx][(int) zz];
		if (chunk == null)
			return null;

		return chunk;

	}

	// TESTE
}