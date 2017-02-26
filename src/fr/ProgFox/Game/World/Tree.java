package fr.ProgFox.Game.World;

import java.util.Random;

import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.LeafBlock;
import fr.ProgFox.Game.World.Blocks.WoodBlock;
import fr.ProgFox.Math.Color4f;
import fr.ProgFox.Math.Mathf;

public class Tree {
	private Random random;

	public Tree(Random random) {
		this.random = random;
	}

	public void addTree(Block[][][] blocks, int x, int y, int z, Chunk world) {
		if (random.nextFloat() > 0.5f) {
			SimpleTree(blocks, x, y, z, world);
		} else {
			SapinTree(blocks, x, y, z, world);
		}
	}

	public void SimpleTree(Block[][][] blocks, int x, int y, int z, Chunk world) {
		if(x + 4 >= 16 || x - 4 < 0 || z + 4 >= 16 | z - 4 < 0)
			return;
		
		Block block = new LeafBlock();

		world.addBlock(x + 1, y + 4, z + 1, block, false);
		world.addBlock(x + 2, y + 4, z + 1, block, false);
		world.addBlock(x - 1, y + 4, z + 1, block, false);
		world.addBlock(x - 2, y + 4, z + 1, block, false);
		world.addBlock(x, y + 4, z + 1, block, false);

		world.addBlock(x, y + 4, z + 2, block, false);
		world.addBlock(x + 1, y + 4, z + 2, block, false);
		world.addBlock(x - 1, y + 4, z + 2, block, false);

		world.addBlock(x, y + 4, z - 1, block, false);
		world.addBlock(x - 1, y + 4, z - 1, block, false);
		world.addBlock(x - 2, y + 4, z - 1, block, false);
		world.addBlock(x + 1, y + 4, z - 1, block, false);
		world.addBlock(x + 2, y + 4, z - 1, block, false);

		world.addBlock(x, y + 4, z - 2, block, false);
		world.addBlock(x - 1, y + 4, z - 2, block, false);
		world.addBlock(x + 1, y + 4, z - 2, block, false);

		world.addBlock(x + 1, y + 4, z, block, false);
		world.addBlock(x - 1, y + 4, z, block, false);
		world.addBlock(x + 2, y + 4, z, block, false);
		world.addBlock(x - 2, y + 4, z, block, false);
		for (int x2 = 0; x2 < 3; x2++) {
			for (int z2 = 0; z2 < 3; z2++) {
				world.addBlock(x + x2, y + 5, z + z2, block, false);
				world.addBlock(x - x2, y + 5, z - z2, block, false);
				world.addBlock(x + x2, y + 5, z - z2, block, false);
				world.addBlock(x - x2, y + 5, z + z2, block, false);
			}
		}
		world.addBlock(x + 1, y + 6, z + 1, block, false);
		world.addBlock(x + 2, y + 6, z + 1, block, false);
		world.addBlock(x - 1, y + 6, z + 1, block, false);
		world.addBlock(x - 2, y + 6, z + 1, block, false);
		world.addBlock(x, y + 6, z + 1, block, false);

		world.addBlock(x, y + 6, z + 2, block, false);
		world.addBlock(x + 1, y + 6, z + 2, block, false);
		world.addBlock(x - 1, y + 6, z + 2, block, false);

		world.addBlock(x, y + 6, z - 1, block, false);
		world.addBlock(x - 1, y + 6, z - 1, block, false);
		world.addBlock(x - 2, y + 6, z - 1, block, false);
		world.addBlock(x + 1, y + 6, z - 1, block, false);
		world.addBlock(x + 2, y + 6, z - 1, block, false);

		world.addBlock(x, y + 6, z - 2, block, false);
		world.addBlock(x - 1, y + 6, z - 2, block, false);
		world.addBlock(x + 1, y + 6, z - 2, block, false);

		world.addBlock(x + 1, y + 6, z, block, false);
		world.addBlock(x - 1, y + 6, z, block, false);
		world.addBlock(x + 2, y + 6, z, block, false);
		world.addBlock(x - 2, y + 6, z, block, false);

		world.addBlock(x, y + 7, z, block, false);
		world.addBlock(x, y + 7, z + 1, block, false);
		world.addBlock(x, y + 7, z - 1, block, false);
		world.addBlock(x + 1, y + 7, z, block, false);
		world.addBlock(x - 1, y + 7, z, block, false);

		world.addBlock(x + 1, y + 7, z + 1, block, false);
		world.addBlock(x - 1, y + 7, z - 1, block, false);
		world.addBlock(x + 1, y + 7, z - 1, block, false);
		world.addBlock(x - 1, y + 7, z + 1, block, false);

		world.addBlock(x, y + 7, z + 2, block, false);
		world.addBlock(x, y + 7, z - 2, block, false);
		world.addBlock(x + 2, y + 7, z, block, false);
		world.addBlock(x - 2, y + 7, z, block, false);
		for (int i = 0; i < 6; i++) {
			world.addBlock(x, y + i, z, new WoodBlock(), false);
		}
	}

	public void SapinTree(Block[][][] blocks, int x, int y, int z, Chunk world) {
		if(x + 4 >= 16 || x - 4 < 0 || z + 4 >= 16 | z - 4 < 0)
			return;
		
		Block block = new LeafBlock();
		block.setColor(new Color4f(0f, 0.55f, 0f, 1f));

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
						
						world.addBlock((int) (x + a2), (int) (y + b2 + 7), (int) (z + c2), block, false);
					}
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			world.addBlock(x, y + i, z, new WoodBlock(), false);
		}
	}
}
