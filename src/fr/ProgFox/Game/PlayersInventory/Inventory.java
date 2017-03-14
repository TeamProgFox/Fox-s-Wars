package fr.ProgFox.Game.PlayersInventory;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.*;
import fr.ProgFox.Game.Entities.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.GUI.*;
import fr.ProgFox.Renderer.VertexBuffer2D.*;

public class Inventory {

	VBO2D[] slots;
	VBO2D[] blocksSlots;
	Block[] blocks;
	Vec3[] blocksPos;
	SimpleText[] blocksCount;
	VBO2D position;
	Vec2 pos, lastPos;

	int[] stackBlock;

	int scrollBar;

	LocalPlayer player;

	public Inventory(LocalPlayer player) {
		this.player = player;
		slots = new VBO2D[10];
		position = new VBO2D();
		pos = new Vec2();
		blocks = new Block[10];
		stackBlock = new int[64];
		blocksSlots = new VBO2D[10];
		blocksCount = new SimpleText[10];
		blocksPos = new Vec3[10];
		lastPos = new Vec2();
		for (int i = 0; i < 8; i++) {
			slots[i] = new VBO2D();
			slots[i].init(8);
			slots[i].addVertex(0, i * Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(Display.getHeight() / 11, i * Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(Display.getHeight() / 11, i * Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(Display.getHeight() / 11, Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(Display.getHeight() / 11, Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(0, Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(0, Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(0, i * Display.getHeight() / 11, new Vec3());

			slots[i].end();
		}
	}

	public void updateGUIWhenResized() {
		for (int i = 0; i < 8; i++) {
			slots[i] = new VBO2D();
			slots[i].init(8);
			slots[i].addVertex(0, i * Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(Display.getHeight() / 11, i * Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(Display.getHeight() / 11, i * Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(Display.getHeight() / 11, Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(Display.getHeight() / 11, Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(0, Display.getHeight() / 11, new Vec3());

			slots[i].addVertex(0, Display.getHeight() / 11, new Vec3());
			slots[i].addVertex(0, i * Display.getHeight() / 11, new Vec3());

			slots[i].end();
		}

		for (int index = 0; index < 6; index++) {
			if (blocksSlots[index] != null || blocks[index] != null) {
				blocksSlots[index] = new VBO2D();
				blocksSlots[index].init(4);
				blocksSlots[index].addVertex(Display.getWidth() - Display.getHeight() / 11,
						index * Display.getHeight() / 11,
						new Vec3(blocks[index].getColor().r, blocks[index].getColor().g, blocks[index].getColor().b));
				blocksSlots[index].addVertex(Display.getWidth() - Display.getHeight() / 11 + Display.getHeight() / 11,
						index * Display.getHeight() / 11,
						new Vec3(blocks[index].getColor().r, blocks[index].getColor().g, blocks[index].getColor().b));
				blocksSlots[index].addVertex(Display.getWidth() - Display.getHeight() / 11 + Display.getHeight() / 11,
						index * Display.getHeight() / 11 + Display.getHeight() / 11,
						new Vec3(blocks[index].getColor().r, blocks[index].getColor().g, blocks[index].getColor().b));
				blocksSlots[index].addVertex(Display.getWidth() - Display.getHeight() / 11,
						index * Display.getHeight() / 11 + Display.getHeight() / 11,
						new Vec3(blocks[index].getColor().r, blocks[index].getColor().g, blocks[index].getColor().b));
				blocksSlots[index].end();

			}
		}
	}

	public void update() {
		if (scrollBar < 0)
			scrollBar = 0;
		if (scrollBar > 6)
			scrollBar = 6;

		pos = new Vec2(Display.getWidth() - Display.getHeight() / 11, scrollBar * Display.getHeight() / 11);

		if (!lastPos.equals(pos)) {
			lastPos = pos;
			position.init(4);
			position.addVertex(pos.x, pos.y, new Vec3(1, 1, 1));
			position.addVertex(pos.x + Display.getHeight() / 11, pos.y, new Vec3(1, 1, 1));
			position.addVertex(pos.x + Display.getHeight() / 11, pos.y + Display.getHeight() / 11, new Vec3(1, 1, 1));
			position.addVertex(pos.x, pos.y + Display.getHeight() / 11, new Vec3(1, 1, 1));
			position.end();
		}
	}

	public void render() {
		for (VBO2D s : slots) {
			if (s != null) {
				s.render(GL_LINES, Main.getMain().getShader(),
						Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
						Main.getMain().getCamera().getModelViewMatrix(
								new Vec3(-Display.getWidth() + Display.getHeight() / 11, -100, 0), new Vec3()));

			}
		}

		glDisable(GL_DEPTH_TEST);

		position.render(GL_QUADS, Main.getMain().getShader(),
				Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
				Main.getMain().getCamera().getModelViewMatrix(new Vec3(0, -100, 0), new Vec3()));

		glEnable(GL_DEPTH_TEST);
		for (SimpleText s : blocksCount) {
			if (s != null) {
				s.render();
			}
		}

		for (VBO2D s : blocksSlots) {
			if (s != null) {
				s.render(GL_QUADS, Main.getMain().getShader(),
						Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
						Main.getMain().getCamera().getModelViewMatrix(new Vec3(0, -100, 0), new Vec3()));

			}
		}

	}

	public void addScrollBar(int value) {
		scrollBar -= value;
	}

	public void addBlockAt(int index, Block block) {
		if (index < 0 || index >= 10)
			return;
		if (blocks[index] == null) {
			blocks[index] = block;
			blocksSlots[index] = new VBO2D();
			blocksPos[index] = new Vec3(pos.x, pos.y, 0);
			blocksSlots[index].init(4);
			blocksSlots[index].addVertex(pos.x, pos.y,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			blocksSlots[index].addVertex(pos.x + Display.getHeight() / 11, pos.y,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			blocksSlots[index].addVertex(pos.x + Display.getHeight() / 11, pos.y + Display.getHeight() / 11,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			blocksSlots[index].addVertex(pos.x, pos.y + Display.getHeight() / 11,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			blocksSlots[index].end();

		}

		if (blocks[index].getName().equals(block.getName())) {
			stackBlock[index]++;
			blocksCount[index] = new SimpleText("" + stackBlock[index], (int) pos.x,
					(int) pos.y + 100 + Display.getHeight() / 12, new Vec3(0, 0, 0));
		}

	}

	public void removeBlockAt(int index) {
		if (index < 0 || index >= 10)
			return;
		if (blocks[index] != null) {
			stackBlock[index]--;
			blocksCount[index] = new SimpleText("" + stackBlock[index], (int) pos.x,
					(int) pos.y + 100 + Display.getHeight() / 12, new Vec3(0, 0, 0));
		}
		if (stackBlock[index] <= 0) {
			blocks[index] = null;
			blocksSlots[index] = null;
			blocksCount[index] = null;
		}

	}

	public Block getBlockAt(int index) {
		if (index < 0 || index >= 10)
			return null;
		return blocks[index];
	}

	public int getScrollBar() {
		return scrollBar;
	}

}
