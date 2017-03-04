package fr.ProgFox.Game;

import fr.ProgFox.Game.World.Chunk;
import fr.ProgFox.Game.World.GestionBlock;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;

public class MultiPlayer {

	public static void place(NetworkClient net, Game game) {

		if (game.addBlock) {

			for (int i = 0; i < game.addPos.size(); i++) {
				if (i > game.add.size() - 1)
					return;
				
				int xx = (int) (game.addPos.get(i).x / Chunk.SIZE);
				int zz = (int) (game.addPos.get(i).z / Chunk.SIZE);

				if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
					return;
				if (game.getWorld().getChunk(xx, zz) == null) {
					game.getWorld().chunks[xx][zz] = new Chunk(xx, 0, zz, game.getWorld().noise,
							game.getWorld().noiseColor, game.getWorld().random, game.getWorld());
					game.getWorld().chunks[xx][zz].createChunk();
				}
				game.getWorld().addBlock = new Vec3(game.addPos.get(i).x, game.addPos.get(i).y, game.addPos.get(i).z);
				game.getWorld().block = game.add.get(i);
				game.getWorld().addBlockRequest = true;
			}
			game.addPos.clear();
			game.add.clear();
			game.addBlock = false;
		}

		if (game.removeBlock) {
			for (int i = 0; i < game.removePos.size(); i++) {
				int xx = (int) (game.removePos.get(i).x / Chunk.SIZE);
				int zz = (int) (game.removePos.get(i).z / Chunk.SIZE);
				if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
					return;
				if (game.getWorld().getChunk(xx, zz) == null) {
					game.getWorld().chunks[xx][zz] = new Chunk(xx, 0, zz, game.getWorld().noise,
							game.getWorld().noiseColor, game.getWorld().random, game.getWorld());
					game.getWorld().chunks[xx][zz].createChunk();
				}
				game.getWorld().removeBlock = new Vec3(game.removePos.get(i).x, game.removePos.get(i).y,
						game.removePos.get(i).z);
				game.getWorld().removeBlockRequest = true;
			}
			game.removePos.clear();
			game.removeBlock = false;
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sendPos(NetworkClient net, Game game) {
		net.send("controle;" + game.pseudo + ";" + game.getCamera().getPlayer().position.x + ";"
				+ game.getCamera().getPlayer().position.y + ";" + game.getCamera().getPlayer().position.z);

	}

	public static void rePlace(Game game) {
		if ((game.addBlockRequest || game.removeBlockRequest) && !game.isConnected) {
			for (GestionBlock a : game.gestionBlock) {
				int xx = (int) (a.getX() / Chunk.SIZE);
				int zz = (int) (a.getZ() / Chunk.SIZE);
				if (xx < 0 || xx >= 100 || zz < 0 || zz >= 100)
					return;

				if (game.getWorld().getChunk(xx, zz) == null) {
					game.getWorld().chunks[xx][zz] = new Chunk(xx, 0, zz, game.getWorld().noise,
							game.getWorld().noiseColor, game.getWorld().random, game.getWorld());
					game.getWorld().chunks[xx][zz].createChunk();
				}
				if (a.getAction()) {
					game.getWorld().removeBlock(a.getX(), a.getY(), a.getZ(), true);
				} else {
					game.getWorld().addBlock(a.getX(), a.getY(), a.getZ(), Block.getBlock(a.getBlock()), true);
				}

			}
			game.gestionBlock.clear();

			game.addBlockRequest = false;
			game.removeBlockRequest = false;
			game.isConnected = true;
		}
	}

}
