package fr.ProgFox.Game.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Logs.Logs;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.GrassBlock;
import fr.ProgFox.Math.Color4f;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;

public class Chunk {
	private int vbo;
	public static final int SIZE = 16;
	public static final int HEIGHT = 64;

	private int bufferSize;
	private float x, y, z;
	private boolean isUpdating = false;
	private boolean isCreating = false;

	public FloatBuffer buffer;
	private Noise noise;
	private Shader shader;
	private World world;
	private Tree tree;
	private Random random;
	private CubeLine cl;
	private int oneTimes = 0;

	public Block[][][] blocks = new Block[SIZE][HEIGHT][SIZE];;
	public Noise colorNoise;

	public static boolean canBreakBlock = true;
	public static boolean grounded;

	public boolean canRender = false;
	public boolean hasShader = false;
	public boolean createRequest = false;
	public boolean isReady = false;
	public boolean updateRequest = false;

	public Chunk(float x, float y, float z, Noise noise, Noise colorNoise, Random seed, World world) {
		this.noise = noise;
		this.x = x;
		this.y = y;
		this.z = z;
		this.colorNoise = colorNoise;
		this.world = world;
		this.random = seed;

		tree = new Tree(random);
		generate();
	}

	public void generate() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					if (noise.getNoise(x2, z2) > y2 - 4) {
						float v = colorNoise.getNoise(x2, z2);

						Color4f fC = Color4f.lerp(new Color4f(0f, 0.9f, 0f), new Color4f(0f, 1f, 0f), v);
						if (blocks[x][y][z] == null) {
							blocks[x][y][z] = new GrassBlock();
							blocks[x][y][z].setColor(new Color4f(fC.r, fC.g, fC.b, fC.a));
						}
					}
				}
			}
		}
	}

	public void generateVegetation() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				for (int z = 0; z < SIZE; z++) {
					int x2 = (int) this.x * SIZE + x;
					int y2 = (int) this.y * HEIGHT + y;
					int z2 = (int) this.z * SIZE + z;
					grounded = noise.getNoise(x2, z2) > y2 - 4 && noise.getNoise(x2, z2) < y2 - 3;
					if (random.nextFloat() > 0.99f && grounded) {
						tree.addTree(blocks, x, y, z, this);
					}
				}
			}
		}
	}

	float ao = 0.8f;

	public void createChunk() {
		isCreating = true;
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
						Color4f shadow;
						if (world.getBlock(x2 + 1, y2 + 1, z2) != null) {
							shadow = new Color4f(ao, 1f, 1f, ao);
						} else if (world.getBlock(x2 - 1, y2 + 1, z2) != null) {
							shadow = new Color4f(1f, ao, ao, 1f);
						} else if (world.getBlock(x2, y2 + 1, z2 + 1) != null) {
							shadow = new Color4f(1f, 1f, ao, ao);
						} else if (world.getBlock(x2, y2 + 1, z2 - 1) != null) {
							shadow = new Color4f(ao, ao, 1f, 1f);
						} else {
							shadow = new Color4f(1f, 1f, 1f, 1f);
						}
						buffer.put(block.BlockDataUp(x2, y2, z2, shadow));
						size++;
					}
					if (down) {
						Color4f shadow;
						if (world.getBlock(x2 - 1, y2 - 1, z2) != null) {
							shadow = new Color4f(ao, 1f, 1f, ao);
						} else if (world.getBlock(x2 + 1, y2 - 1, z2) != null) {
							shadow = new Color4f(1f, ao, ao, 1f);
						} else if (world.getBlock(x2, y2 - 1, z2 + 1) != null) {
							shadow = new Color4f(1f, 1f, ao, ao);
						} else if (world.getBlock(x2, y2 - 1, z2 - 1) != null) {
							shadow = new Color4f(ao, ao, 1f, 1f);
						} else {
							shadow = new Color4f(1f, 1f, 1f, 1f);
						}
						buffer.put(block.BlockDataDown(x2, y2, z2, shadow));
						size++;
					}
					if (back) {
						buffer.put(block.BlockDataBack(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (front) {
						buffer.put(block.BlockDataFront(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (right) {
						buffer.put(block.BlockDataRight(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (left) {
						buffer.put(block.BlockDataLeft(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					bufferSize += size * 4;
				}
			}
		}
		buffer.flip();
		createRequest = true;
		isCreating = false;

	}

	public void updateChunk() {
		isUpdating = true;
		buffer = BufferUtils.createFloatBuffer(SIZE * HEIGHT * SIZE * 6 * 4 * (3 + 4));
		bufferSize = 0;
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
						Color4f shadow;
						if (world.getBlock(x2 + 1, y2 + 1, z2) != null) {
							shadow = new Color4f(ao, 1f, 1f, ao);
						} else if (world.getBlock(x2 - 1, y2 + 1, z2) != null) {
							shadow = new Color4f(1f, ao, ao, 1f);
						} else if (world.getBlock(x2, y2 + 1, z2 + 1) != null) {
							shadow = new Color4f(1f, 1f, ao, ao);
						} else if (world.getBlock(x2, y2 + 1, z2 - 1) != null) {
							shadow = new Color4f(ao, ao, 1f, 1f);
						} else {
							shadow = new Color4f(1f, 1f, 1f, 1f);
						}
						buffer.put(block.BlockDataUp(x2, y2, z2, shadow));
						size++;
					}
					if (down) {
						Color4f shadow;
						if (world.getBlock(x2 - 1, y2 - 1, z2) != null) {
							shadow = new Color4f(ao, 1f, 1f, ao);
						} else if (world.getBlock(x2 + 1, y2 - 1, z2) != null) {
							shadow = new Color4f(1f, ao, ao, 1f);
						} else if (world.getBlock(x2, y2 - 1, z2 + 1) != null) {
							shadow = new Color4f(1f, 1f, ao, ao);
						} else if (world.getBlock(x2, y2 - 1, z2 - 1) != null) {
							shadow = new Color4f(ao, ao, 1f, 1f);
						} else {
							shadow = new Color4f(1f, 1f, 1f, 1f);
						}
						buffer.put(block.BlockDataDown(x2, y2, z2, shadow));
						size++;
					}
					if (back) {
						buffer.put(block.BlockDataBack(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (front) {
						buffer.put(block.BlockDataFront(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (right) {
						buffer.put(block.BlockDataRight(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					if (left) {
						buffer.put(block.BlockDataLeft(x2, y2, z2, new Color4f(1f, 1f, 1f, 1f)));
						size++;
					}
					bufferSize += size * 4;
				}
			}
		}

		buffer.flip();
		updateRequest = true;
		isUpdating = false;
	}

	public void updateVBO() {
		if (isUpdating || isCreating)
			return;
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		if (buffer != null) {
			glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		}
		buffer = null;
	}

	public void createBuffer() {
		if (isCreating || isUpdating)
			return;
		int x2 = (int) ((int) this.x * SIZE);
		int y2 = (int) ((int) this.y * HEIGHT);
		int z2 = (int) ((int) this.z * SIZE);
		this.cl = new CubeLine(new Vec3(1, 1, 1));
		cl.add(x2, y2, z2, SIZE, HEIGHT, SIZE, false);
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		createRequest = false;
		canRender = true;
		buffer = null;

	}

	public void setShader(Shader shader) {
		this.shader = shader;
		hasShader = true;
	}

	public void render(Camera cam) {
		isReady = true;

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

	public void addBlock(float x, float y, float z, Block block, boolean update) {
		if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
			return;

		if (blocks[(int) x][(int) y][(int) z] != block) {
			blocks[(int) x][(int) y][(int) z] = block;
			if (update) {
				updateChunk();
			}
		}
	}

	public void removeBlock(float x, float y, float z, boolean update) {
		if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= HEIGHT || z >= SIZE)
			return;
		if (blocks[(int) x][(int) y][(int) z] != null) {
			blocks[(int) x][(int) y][(int) z] = null;

			if (update) {
				updateChunk();
			}
		}
	}

	public String getName() {
		return (x + "][" + z).toString();
	}

}
