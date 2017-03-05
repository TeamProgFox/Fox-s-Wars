package fr.ProgFox.Game.PlayersInventory;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import fr.ProgFox.*;
import fr.ProgFox.Game.Variables.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Inputs.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.VertexBuffer2D.*;

public class Inventory {
	private Block[] blocksInBar;
	private int[] numberBlocksInBar;
	private VBO2D[] invBar;
	private int scrollBar = 0;
	int vbo = 0;
	FloatBuffer buffer;

	public Inventory() {
		blocksInBar = new Block[10];
		numberBlocksInBar = new int[10];
		invBar = new VBO2D[10];
		for (int i = 0; i < invBar.length; i++) {
			invBar[i] = new VBO2D();
		}

	}

	public void update() {
		if (scrollBar > 9)
			scrollBar = 9;

		if (scrollBar < 0)
			scrollBar = 0;

	}

	public void render() {
		for (int i = 0; i < invBar.length; i++) {
			invBar[i].render(GL_QUADS, Main.getMain().getShader(),
					Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
					Main.getMain().getCamera().getTransform(new Vec3(), new Vec3()));

		}

		if (vbo == 0)
			return;

		Main.getMain().getShader().bind();
		Main.getMain().getShader().setUniform("projectionMatrix", Main.getMain().getCamera().getProjectionMatrix());
		Main.getMain().getShader().setUniform("modelViewMatrix",
				Main.getMain().getCamera().getTransform(new Vec3(), new Vec3(0, 0, 0)));
		Main.getMain().getShader().setUniform("light", Var.light);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * 4, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, true, 7 * 4, 12);
		glDrawArrays(GL_QUADS, 0, 24);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);

	}

	public Block getBlockAt(int index) {
		if (index < 0 || index >= 10)
			return null;

		return blocksInBar[index];
	}

	float x = 1f, y = -1f, z = 0.75f;

	public void addScrollBar(int value) {
		scrollBar += value;
		if (buffer != null) {
			buffer.clear();
		}

		Block block = getBlockAt(scrollBar);

		if (block == null) {
			vbo = 0;
			return;
		}

		buffer = BufferUtils.createFloatBuffer(6 * 4 * 7);

		buffer.put(block.BlockDataBack(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataFront(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataUp(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataDown(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataRight(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataLeft(x, y, z, new Color4f(255, 255, 255)));

		buffer.flip();
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		buffer = null;
	}

	public int getScrollBar() {
		return scrollBar;
	}

	public void addBlockAt(int index, Block block) {
		if (index < 0 || index >= 10)
			return;

		if (block == null) {
			vbo = 0;
			return;
		}
		
		if(blocksInBar[index] != null && !block.getName().equals(blocksInBar[index].getName()))
			return;
		
		
		blocksInBar[index] = block;

		numberBlocksInBar[index]++;

		if (buffer != null) {
			buffer.clear();
		}

		invBar[index].init(4);
		invBar[index].addVertex(index * 100, Display.getHeight() - 100,
				new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
		invBar[index].addVertex(index * 100 + 100, Display.getHeight() - 100,
				new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
		invBar[index].addVertex(index * 100 + 100, Display.getHeight() - 100 + 200,
				new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
		invBar[index].addVertex(index * 100, Display.getHeight() - 100 + 200,
				new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
		invBar[index].end();

		buffer = BufferUtils.createFloatBuffer(6 * 4 * 7);

		buffer.put(block.BlockDataBack(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataFront(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataUp(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataDown(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataRight(x, y, z, new Color4f(255, 255, 255)));
		buffer.put(block.BlockDataLeft(x, y, z, new Color4f(255, 255, 255)));

		buffer.flip();
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		buffer = null;
	}

	public void updateWhenResized() {
		for (int i = 0; i < blocksInBar.length; i++) {
			Block block = blocksInBar[i];
			if (block == null) {
				return;
			}
			invBar[i].init(4);
			invBar[i].addVertex(i * 100, Display.getHeight() - 100,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			invBar[i].addVertex(i * 100 + 100, Display.getHeight() - 100,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			invBar[i].addVertex(i * 100 + 100, Display.getHeight() - 100 + 200,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			invBar[i].addVertex(i * 100, Display.getHeight() - 100 + 200,
					new Vec3(block.getColor().r, block.getColor().g, block.getColor().b));
			invBar[i].end();
		}
	}

	public void removeBlockAt(int index) {
		if (index < 0 || index >= 10)
			return;
		numberBlocksInBar[index]--;

		if (numberBlocksInBar[index] <= 0) {
			blocksInBar[index] = null;
			vbo = 0;
		}
	}

}
