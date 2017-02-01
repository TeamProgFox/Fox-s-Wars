package fr.ProgFox.World;

import java.util.Random;

import fr.ProgFox.Math.Mathf;
import fr.ProgFox.World.Blocks.Block;

public class Tree {
	private Chunk chunk;
	private Random random;

	public Tree(Chunk chunk, Random random) {

		this.chunk = chunk;
		this.random = random;
	}

	public void addTRee(Block[][][] blocks, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= Chunk.SIZE || y >= Chunk.HEIGHT || z >= Chunk.SIZE)
			return;
		if (random.nextFloat() > 0.2f) {

			SimpleTree(blocks, x, y, z);
		} else {

			SapinTree(blocks, x, y, z);
		}
	}

	public void SimpleTree(Block[][][] blocks, int x, int y, int z) {
		if (x > Chunk.SIZE - 6 || z > Chunk.SIZE - 6)
			return;
		if (x < 6 || z < 6)
			return;

		for (int i = 0; i < 6; i++) {
			blocks[x][y + i][z] = Block.WOOD;
		}
		blocks[x + 1][y + 4][z + 1] = Block.LEAF;
		blocks[x + 2][y + 4][z + 1] = Block.LEAF;
		blocks[x - 1][y + 4][z + 1] = Block.LEAF;
		blocks[x - 2][y + 4][z + 1] = Block.LEAF;
		blocks[x][y + 4][z + 1] = Block.LEAF;

		blocks[x][y + 4][z + 2] = Block.LEAF;
		blocks[x + 1][y + 4][z + 2] = Block.LEAF;
		blocks[x - 1][y + 4][z + 2] = Block.LEAF;

		blocks[x][y + 4][z - 1] = Block.LEAF;
		blocks[x - 1][y + 4][z - 1] = Block.LEAF;
		blocks[x - 2][y + 4][z - 1] = Block.LEAF;
		blocks[x + 1][y + 4][z - 1] = Block.LEAF;
		blocks[x + 2][y + 4][z - 1] = Block.LEAF;

		blocks[x][y + 4][z - 2] = Block.LEAF;
		blocks[x - 1][y + 4][z - 2] = Block.LEAF;
		blocks[x + 1][y + 4][z - 2] = Block.LEAF;

		blocks[x + 1][y + 4][z] = Block.LEAF;
		blocks[x - 1][y + 4][z] = Block.LEAF;
		blocks[x + 2][y + 4][z] = Block.LEAF;
		blocks[x - 2][y + 4][z] = Block.LEAF;

		for (int x2 = 0; x2 < 3; x2++) {
			for (int z2 = 0; z2 < 3; z2++) {
				blocks[x + x2][y + 5][z + z2] = Block.LEAF;
				blocks[x - x2][y + 5][z - z2] = Block.LEAF;
				blocks[x + x2][y + 5][z - z2] = Block.LEAF;
				blocks[x - x2][y + 5][z + z2] = Block.LEAF;

			}
		}

		blocks[x + 1][y + 6][z + 1] = Block.LEAF;
		blocks[x + 2][y + 6][z + 1] = Block.LEAF;
		blocks[x - 1][y + 6][z + 1] = Block.LEAF;
		blocks[x - 2][y + 6][z + 1] = Block.LEAF;
		blocks[x][y + 6][z + 1] = Block.LEAF;

		blocks[x][y + 6][z + 2] = Block.LEAF;
		blocks[x + 1][y + 6][z + 2] = Block.LEAF;
		blocks[x - 1][y + 6][z + 2] = Block.LEAF;

		blocks[x][y + 6][z - 1] = Block.LEAF;
		blocks[x - 1][y + 6][z - 1] = Block.LEAF;
		blocks[x - 2][y + 6][z - 1] = Block.LEAF;
		blocks[x + 1][y + 6][z - 1] = Block.LEAF;
		blocks[x + 2][y + 6][z - 1] = Block.LEAF;

		blocks[x][y + 6][z - 2] = Block.LEAF;
		blocks[x - 1][y + 6][z - 2] = Block.LEAF;
		blocks[x + 1][y + 6][z - 2] = Block.LEAF;

		blocks[x + 1][y + 6][z] = Block.LEAF;
		blocks[x - 1][y + 6][z] = Block.LEAF;
		blocks[x + 2][y + 6][z] = Block.LEAF;
		blocks[x - 2][y + 6][z] = Block.LEAF;

		blocks[x][y + 7][z] = Block.LEAF;
		blocks[x][y + 7][z + 1] = Block.LEAF;
		blocks[x][y + 7][z - 1] = Block.LEAF;
		blocks[x + 1][y + 7][z] = Block.LEAF;
		blocks[x - 1][y + 7][z] = Block.LEAF;

		blocks[x + 1][y + 7][z + 1] = Block.LEAF;
		blocks[x - 1][y + 7][z - 1] = Block.LEAF;
		blocks[x + 1][y + 7][z - 1] = Block.LEAF;
		blocks[x - 1][y + 7][z + 1] = Block.LEAF;

		blocks[x][y + 7][z + 2] = Block.LEAF;
		blocks[x][y + 7][z - 2] = Block.LEAF;
		blocks[x + 2][y + 7][z] = Block.LEAF;
		blocks[x - 2][y + 7][z] = Block.LEAF;
	}

	public void SapinTree(Block[][][] blocks, int x, int y, int z) {
		if (x > Chunk.SIZE - 6 || z > Chunk.SIZE - 6)
			return;
		if (x < 6 || z < 6)
			return;
		for (int i = 0; i < 10; i++) {
			blocks[x][y + i][z] = Block.WOOD;
		}
		for (int a = 0; a < 7; a++) {
			for (int b = 0; b < 8; b++) {
				for (int c = 0; c < 7; c++) {
					float a2 = a - 3f;
					float b2 = b - 3f;
					float c2 = c - 3f;
					float size = 1;
					float l = Mathf.sqrt(a2 * a2 + c2 * c2);

					size -= (float) b / 8f;
					if (l < 3.5f * size) {
						blocks[(int) (x + a2)][(int) (y + b2 + 7)][(int) (z + c2)] = Block.SAPINLEAF;
					}
				}
			}
		}
	}
}
