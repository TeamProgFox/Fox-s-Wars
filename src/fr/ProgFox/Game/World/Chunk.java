package fr.ProgFox.Game.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.GrassBlock;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Math.Vec4;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Shader.ColorShader;
import fr.ProgFox.Renderer.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;

public class Chunk {
	private int vbo;
	public static final int SIZE = 16;
	public static final int HEIGHT = 64;

	private int bufferSize;
	private static FloatBuffer buffer;
	private Noise noise;
	private float x, y, z;
	public Block[][][] blocks;
	public static boolean canBreakBlock = true;
	public static boolean grounded;
	private Shader shader;
	private World world;
	private Tree tree;
	private Random random;
	public boolean canRender = false;
	private CubeLine cl;
	public boolean canRealyRender = false;

	public Chunk(float x, float y, float z, Noise noise, Random seed, World world) {
		this.noise = noise;
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.random = seed;
		this.cl = new CubeLine(new Vec3(1, 1, 1));
		int x2 = (int) ((int) this.x * SIZE);
		int y2 = (int) ((int) this.y * HEIGHT);
		int z2 = (int) ((int) this.z * SIZE);
		cl.add(x2, y2, z2, SIZE, HEIGHT, SIZE, false);
		tree = new Tree(random);
		blocks = new Block[SIZE][HEIGHT][SIZE];
		shader = new ColorShader();
		generate();
	}

	public void generate() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					if (noise.getNoise(x2, z2) > y2) {
						grounded = noise.getNoise(x2, z2) > y2 && noise.getNoise(x2, z2) < y2 + 1;
						if (random.nextFloat() < 0.5f) {
							blocks[x][y][z] = new GrassBlock();
						} else {
							blocks[x][y][z] = new GrassBlock();
							blocks[x][y][z].setColor(new Vec4(0, 0.97f, 0, 1));
						}

					}
				}
			}
		}
	}

	float ao = 0.8f;

	public void createChunk() {
		buffer = BufferUtils.createFloatBuffer(SIZE * HEIGHT * SIZE * 6 * 4 * (3 + 4));
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					boolean up = world.getBlock(x2, y2 + 1, z2) == null;
					boolean down = world.getBlock(x2, y2 - 1, z2) == null;
					boolean back = world.getBlock(x2, y2, z2 + 1) == null;
					boolean front = world.getBlock(x2, y2, z2 - 1) == null;
					boolean right = world.getBlock(x2 + 1, y2, z2) == null;
					boolean left = world.getBlock(x2 - 1, y2, z2) == null;
					if (!up && !down && !left && !right && !front && !back)
						continue;
					if (blocks[x][y][z] == null)
						continue;
					Block block = blocks[x][y][z];
					int size = 0;

					if (up) {
						if (world.getBlock(x2 + 1, y2 + 1, z2) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, ao, ao, 1)));

						} else if (world.getBlock(x2 - 1, y2 + 1, z2) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(ao, 1, 1, ao)));

						} else if (world.getBlock(x2, y2 + 1, z2 + 1) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, 1, ao, ao)));

						} else if (world.getBlock(x2, y2 + 1, z2 - 1) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(ao, ao, 1, 1)));

						} else {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, 1, 1, 1)));

						}
						size++;
					}
					if (down) {
						if (world.getBlock(x2 - 1, y2 - 1, z2) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(ao, 1, 1, ao)));
						} else if (world.getBlock(x2 + 1, y2 - 1, z2) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, ao, ao, 1)));

						} else if (world.getBlock(x2, y2 - 1, z2 + 1) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, 1, ao, ao)));

						} else if (world.getBlock(x2, y2 - 1, z2 - 1) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(ao, ao, 1, 1)));

						} else {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, 1, 1, 1)));

						}
						size++;
					}
					if (back) {
						buffer.put(block.BlockDataBack(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (front) {
						buffer.put(block.BlockDataFront(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (right) {
						buffer.put(block.BlockDataRight(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (left) {
						buffer.put(block.BlockDataLeft(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					bufferSize += size * 4;
				}
			}
		}
		buffer.flip();
		createBuffer();
		canRender = true;
	}

	public void generateVegetation() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					grounded = noise.getNoise(x2, z2) > y2 && noise.getNoise(x2, z2) < y2 + 1;
					if (random.nextFloat() > 0.997f && grounded) {
						tree.addTree(blocks, x2, y2, z2, world);

					}
				}
			}
		}
	}

	public void updateChunk() {
		bufferSize = 0;
		buffer.clear();
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					boolean up = world.getBlock(x2, y2 + 1, z2) == null;
					boolean down = world.getBlock(x2, y2 - 1, z2) == null;
					boolean back = world.getBlock(x2, y2, z2 + 1) == null;
					boolean front = world.getBlock(x2, y2, z2 - 1) == null;
					boolean right = world.getBlock(x2 + 1, y2, z2) == null;
					boolean left = world.getBlock(x2 - 1, y2, z2) == null;
					if (!up && !down && !left && !right && !front && !back)
						continue;
					if (blocks[x][y][z] == null)
						continue;
					Block block = blocks[x][y][z];
					int size = 0;
					if (up) {
						if (world.getBlock(x2 + 1, y2 + 1, z2) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, ao, ao, 1)));

						} else if (world.getBlock(x2 - 1, y2 + 1, z2) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(ao, 1, 1, ao)));

						} else if (world.getBlock(x2, y2 + 1, z2 + 1) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, 1, ao, ao)));

						} else if (world.getBlock(x2, y2 + 1, z2 - 1) != null) {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(ao, ao, 1, 1)));

						} else {
							buffer.put(block.BlockDataUp(x2, y2, z2, new Vec4(1, 1, 1, 1)));

						}
						size++;
					}
					if (down) {
						if (world.getBlock(x2 - 1, y2 - 1, z2) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(ao, 1, 1, ao)));
						} else if (world.getBlock(x2 + 1, y2 - 1, z2) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, ao, ao, 1)));

						} else if (world.getBlock(x2, y2 - 1, z2 + 1) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, 1, ao, ao)));

						} else if (world.getBlock(x2, y2 - 1, z2 - 1) != null) {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(ao, ao, 1, 1)));

						} else {
							buffer.put(block.BlockDataDown(x2, y2, z2, new Vec4(1, 1, 1, 1)));

						}
						size++;
					}
					if (back) {
						buffer.put(block.BlockDataBack(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (front) {
						buffer.put(block.BlockDataFront(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (right) {
						buffer.put(block.BlockDataRight(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					if (left) {
						buffer.put(block.BlockDataLeft(x2, y2, z2, new Vec4(1, 1, 1, 1)));
						size++;
					}
					bufferSize += size * 4;
				}
			}
		}
		buffer.flip();
		updateVBO();
	}

	public void updateVBO() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	private void createBuffer() {
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void update() {

	}

	public void render(Camera cam) {
		shader.bind();
		shader.setUniform("perspective", cam.getPerspectiveProjection());
		shader.setUniform("perspectivePosition", cam.position);
		shader.setUniform("light", Var.light);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * 4, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, true, 7 * 4, 12);
		glDrawArrays(GL_QUADS, 0, bufferSize);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		if (Var.debugMode) {
			cl.render(GL_LINES, 2, cam.getPerspectiveProjection(), cam.position, shader);
		}
	}

	public Block getBlock(float x, float y, float z) {
		if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
			return null;
		return blocks[(int) x][(int) y][(int) z];
	}

	public void addBlock(float x, float y, float z, Block block) {
		if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
			return;

		if (blocks[(int) x][(int) y][(int) z] != block) {
			blocks[(int) x][(int) y][(int) z] = block;
			if (buffer != null) {
				updateChunk();
			}
		}
	}

	public void removeBlock(float x, float y, float z) {
		if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
			return;
		if (blocks[(int) x][(int) y][(int) z] != null) {
			blocks[(int) x][(int) y][(int) z] = null;
			if (buffer != null) {
				updateChunk();
			}
		}
	}

}
