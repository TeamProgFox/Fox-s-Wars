package fr.ProgFox.Game.World;

import java.util.Random;

import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Math.Mathf;
import fr.ProgFox.Math.Vec4;

public class Tree {
	private Random random;

	public Tree(Random random) {
		this.random = random;
	}

	public void addTree(Block[][][] blocks, int x, int y, int z, World world) {
		if (random.nextFloat() > 0.5f) {
			SimpleTree(blocks, x, y, z, world);
		} else {
			SapinTree(blocks, x, y, z, world);
		}
	}

	public void SimpleTree(Block[][][] blocks, int x, int y, int z, World world) {
		Block block = Block.LEAF;
		for (int i = 0; i < 6; i++) {
			world.addBlock(x, y + i, z, Block.WOOD);
		}
		world.addBlock(x + 1, y + 4, z + 1, block);
		world.addBlock(x + 2, y + 4, z + 1, block);
		world.addBlock(x - 1, y + 4, z + 1, block);
		world.addBlock(x - 2, y + 4, z + 1, block);
		world.addBlock(x, y + 4, z + 1, block);

		world.addBlock(x, y + 4, z + 2, block);
		world.addBlock(x + 1, y + 4, z + 2, block);
		world.addBlock(x - 1, y + 4, z + 2, block);

		world.addBlock(x, y + 4, z - 1, block);
		world.addBlock(x - 1, y + 4, z - 1, block);
		world.addBlock(x - 2, y + 4, z - 1, block);
		world.addBlock(x + 1, y + 4, z - 1, block);
		world.addBlock(x + 2, y + 4, z - 1, block);

		world.addBlock(x, y + 4, z - 2, block);
		world.addBlock(x - 1, y + 4, z - 2, block);
		world.addBlock(x + 1, y + 4, z - 2, block);

		world.addBlock(x + 1, y + 4, z, block);
		world.addBlock(x - 1, y + 4, z, block);
		world.addBlock(x + 2, y + 4, z, block);
		world.addBlock(x - 2, y + 4, z, block);
		for (int x2 = 0; x2 < 3; x2++) {
			for (int z2 = 0; z2 < 3; z2++) {
				world.addBlock(x + x2, y + 5, z + z2, block);
				world.addBlock(x - x2, y + 5, z - z2, block);
				world.addBlock(x + x2, y + 5, z - z2, block);
				world.addBlock(x - x2, y + 5, z + z2, block);
			}
		}
		world.addBlock(x + 1, y + 6, z + 1, block);
		world.addBlock(x + 2, y + 6, z + 1, block);
		world.addBlock(x - 1, y + 6, z + 1, block);
		world.addBlock(x - 2, y + 6, z + 1, block);
		world.addBlock(x, y + 6, z + 1, block);

		world.addBlock(x, y + 6, z + 2, block);
		world.addBlock(x + 1, y + 6, z + 2, block);
		world.addBlock(x - 1, y + 6, z + 2, block);

		world.addBlock(x, y + 6, z - 1, block);
		world.addBlock(x - 1, y + 6, z - 1, block);
		world.addBlock(x - 2, y + 6, z - 1, block);
		world.addBlock(x + 1, y + 6, z - 1, block);
		world.addBlock(x + 2, y + 6, z - 1, block);

		world.addBlock(x, y + 6, z - 2, block);
		world.addBlock(x - 1, y + 6, z - 2, block);
		world.addBlock(x + 1, y + 6, z - 2, block);

		world.addBlock(x + 1, y + 6, z, block);
		world.addBlock(x - 1, y + 6, z, block);
		world.addBlock(x + 2, y + 6, z, block);
		world.addBlock(x - 2, y + 6, z, block);

		world.addBlock(x, y + 7, z, block);
		world.addBlock(x, y + 7, z + 1, block);
		world.addBlock(x, y + 7, z - 1, block);
		world.addBlock(x + 1, y + 7, z, block);
		world.addBlock(x - 1, y + 7, z, block);

		world.addBlock(x + 1, y + 7, z + 1, block);
		world.addBlock(x - 1, y + 7, z - 1, block);
		world.addBlock(x + 1, y + 7, z - 1, block);
		world.addBlock(x - 1, y + 7, z + 1, block);

		world.addBlock(x, y + 7, z + 2, block);
		world.addBlock(x, y + 7, z - 2, block);
		world.addBlock(x + 2, y + 7, z, block);
		world.addBlock(x - 2, y + 7, z, block);
	}

	public void SapinTree(Block[][][] blocks, int x, int y, int z, World world) {
		Block block = Block.SAPINLEAF;
		block.setColor(new Vec4(0, 0.55f, 0, 1));
		for (int i = 0; i < 10; i++) {
			world.addBlock(x, y + i, z, Block.WOOD);
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
						world.addBlock((int) (x + a2), (int) (y + b2 + 7), (int) (z + c2), block);
					}
				}
			}
		}
	}
}
