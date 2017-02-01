package fr.ProgFox.World;

import java.util.Random;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.World.Blocks.Block;

public class World {
	public static final int SIZE = 10;
	public Noise noise;
	public Chunk[][] chunks;
	private Random random;
	public World(long seed) {
		
		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 10);
		chunks = new Chunk[100][100];
		for (int x = 0; x < SIZE; x++) {
			for (int z = 0; z < SIZE; z++) {
				chunks[x][z] = new Chunk(x, 0, z, noise, random, this);
			}
		}
		for (int x = 0; x < SIZE; x++) {
			for (int z = 0; z < SIZE; z++) {
				chunks[x][z].createChunk();
			}
		}
	}

	public void update() {
		for (int x = 0; x < SIZE; x++) {
			for (int z = 0; z < SIZE; z++) {
				chunks[x][z].update();
			}
		}
	}

	public void render(Player player) {
		float xP1 = (float) Math.abs(player.position.x / 16);
		float zP1 = (float) Math.abs(player.position.z / 16);
		float xP2 = (float) Math.abs(player.position.x / 16 - 3);
		float zP2 = (float) Math.abs(player.position.z / 16 - 3);

//		if (getChunk(xP2, 0) == null) {
//			chunks[(int) xP2][(int) 0] = new Chunk(xP2, 0, 0, noise, seed, this);
//			chunks[(int) xP2][(int) 0].createChunk();
//
//		} else {
//			chunks[(int) xP2][(int) 0].render(player);
//		}
		int renderDistance = 10;
		for (int x = 0; x < SIZE; x++) {
			for (int z = 0; z < SIZE; z++) {

				if (xP1 > x - renderDistance && xP1 < x + renderDistance) {
					if (zP1 > z - renderDistance && zP1 < z + renderDistance) {

						chunks[x][z].render(player);
					}
				}

			}
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

		if (xx < 0 || xx >= SIZE || zz < 0 || zz >= SIZE)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.addBlock(x3, y3, z3, block);
	}

	public void addSelectBlock(float x, float y, float z, Block block) {
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;

		if (xx < 0 || xx >= SIZE || zz < 0 || zz >= SIZE)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.changeToSelectBlock(x3, y3, z3, block);
	}

	public void removeBlock(float x, float y, float z) {
		float xx = x / Chunk.SIZE;
		float zz = z / Chunk.SIZE;

		if (xx < 0 || xx >= SIZE || zz < 0 || zz >= SIZE)
			return;
		Chunk chunk = chunks[(int) xx][(int) zz];
		float x3 = x % Chunk.SIZE;
		float y3 = y % Chunk.HEIGHT;
		float z3 = z % Chunk.SIZE;

		chunk.removeBlock(x3, y3, z3);
	}

	public Chunk getChunk(float x, float z) {
		if (x < 0 || z < 0 || x >= 100 || z >= 100)
			return null;
		Chunk c = chunks[(int) x][(int) z];
		return c;
	}

	// TESTE
}