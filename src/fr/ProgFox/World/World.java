package fr.ProgFox.World;

import java.util.Random;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Logs.Logs;
import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.World.Blocks.Block;
import fr.ProgFox.World.Buffer.VBO;
import fr.ProgFox.newMath.Vector3f;

public class World {
	public static final int SIZE = 5;
	public Noise noise;
	public Chunk[][] chunks;
	private Random random;

	public World(long seed) {

		random = new Random(seed);
		noise = new Noise(random.nextLong(), 15, 15);
		chunks = new Chunk[100][100];
		for (int x = 0; x < SIZE; x++) {
			for (int z = 0; z < SIZE; z++) {
				chunks[x][z] = new Chunk(x, 0, z, noise, random, this);
			}
		}
		new Logs().Info("FIN DE LA GENERATION DES CHUNKS");

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
		player.render();
		int renderDistance = 2;
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

		if ((int) x3 == 15) {
			getChunk(xx + 1, zz).updateChunk();
		}
		if ((int) x3 == 0 && (int) xx > 0) {
			getChunk(xx - 1, zz).updateChunk();
		}
		if ((int) z3 == 15) {
			getChunk(xx, zz + 1).updateChunk();
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

	// TESTE
}