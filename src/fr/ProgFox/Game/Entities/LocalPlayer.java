package fr.ProgFox.Game.Entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFW;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Chunk;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Game.World.Blocks.LeafBlock;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Inputs.Mouse;
import fr.ProgFox.Inputs.MouseButton;
import fr.ProgFox.Math.Mathf;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.Shader.ColorShader;
import fr.ProgFox.Renderer.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;

public class LocalPlayer extends Entity {
	public boolean gravity = true;
	public World world;
	public Raycast raycast;
	private boolean updateVBO = true;
	private CubeLine select;
	private CubeLine perso;
	int vbo;
	private Camera cam;
	private Shader shader;
	public NetworkClient net;

	public LocalPlayer(World world, Camera cam, int id, String name, Vec3 position, Vec3 rotation) {

		super(position, rotation);
		this.world = world;
		this.cam = cam;
		this.id = id;
		this.name = name;
		this.shader = new ColorShader();
		Var.selectedPosition = new Vec3(0, 0, 0);
		raycast = new Raycast(this);
		select = new CubeLine(new Vec3(1, 1, 1));
		perso = new CubeLine(new Vec3(0, 0, 0));

		init();
	}

	public void connect(Game game, String address, int port) {
	}

	public NetworkClient getNetwork() {
		return net;
	}

	public void init() {
		int x2 = (int) 0;
		int y2 = (int) 0;
		int z2 = (int) 0;
		select.add(x2, y2, z2, 1, 1, 1, false);
		perso.add(position.x, position.y, position.z, 0.5f, 2, 0.5f, true);
	}

	public void update() {
		input();
		raycast.update();
		if (Var.isInThirdPerson)
			perso.update(position.x, position.y, position.z, 0.5f, 1.25f, 0.5f, true);
		if (raycast.getBlock(world) != null) {

			Var.selectedBlock = world.getBlock(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			Var.selectedPosition = new Vec3(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);

		}

	}

	public void render() {
		int x2 = (int) Var.selectedPosition.x;
		int y2 = (int) Var.selectedPosition.y;
		int z2 = (int) Var.selectedPosition.z;
		if (updateVBO) {
			select.update(x2, y2, z2, 1, 1, 1, false);

			updateVBO = false;
		}
		if (Var.isInThirdPerson)
			perso.render(GL_LINES, 2, cam.getPerspectiveProjection(), cam.position, shader);
		select.render(GL_LINES, 2, cam.getPerspectiveProjection(), cam.position, shader);

	}

	float speed = 0.1f;
	float sensibilite = 3;

	public void input() {
		if (Var.isInMenu)
			return;

		float xDir = 0, yDir = 0, zDir = 0;
		rotation.addX(Mouse.getDY() / sensibilite);
		rotation.addY(-Mouse.getDX() / sensibilite);
		if (rotation.getX() > 90)
			rotation.setX(90);
		if (rotation.getX() < -90)
			rotation.setX(-90);

		if (Input.getKey(GLFW.GLFW_KEY_Q)) {
			speed = 0.8f;
		} else {
			speed = 0.1f;
		}

		if (Input.getKey(GLFW.GLFW_KEY_W)) {

			zDir = speed;
		}
		if (Input.getKey(GLFW.GLFW_KEY_A)) {

			xDir = -speed;
		}
		if (Input.getKey(GLFW.GLFW_KEY_S)) {

			zDir = -speed;
		}
		if (Input.getKey(GLFW.GLFW_KEY_D)) {

			xDir = speed;
		}
		if (Input.getKey(GLFW.GLFW_KEY_SPACE) && Var.grounded && !Var.flyMode) {

			Var.isJumping = true;

		}
		if (Input.getKey(GLFW.GLFW_KEY_SPACE) && Var.flyMode) {

			yDir = speed;

		}
		if (Input.getKey(GLFW.GLFW_KEY_LEFT_SHIFT) && Var.flyMode) {
			yDir = -speed;
		}

		float xa = xDir * Mathf.cos(Mathf.toRadians(rotation.y)) - zDir * Mathf.sin(Mathf.toRadians(rotation.y));
		float za = zDir * Mathf.cos(Mathf.toRadians(rotation.y)) + xDir * Mathf.sin(Mathf.toRadians(rotation.y));

		move(xa, yDir, za);

		isGrounded(xDir, yDir, zDir);

		xa *= 0.9f;
		za *= 0.9f;
		removeAndAddBlockGestion();
		actionTimeGestion();
		if (!Var.flyMode)
			gravity();

	}

	Block lastBlock;
	Vec3 lastPos = new Vec3();
	Vec3 nowPos;

	public void removeAndAddBlockGestion() {
		if (Chunk.canBreakBlock) {
			if (Input.getMouseDown(0)) {

				world.removeBlock(Var.selectedPosition.x, Var.selectedPosition.y, Var.selectedPosition.z, true);
				lastBlock = null;
				Chunk.canBreakBlock = false;
			}
			if (Input.getMouseDown(1)) {
				int x22 = (int) Var.selectedPosition.x;
				int y22 = (int) Var.selectedPosition.y;
				int z22 = (int) Var.selectedPosition.z;

				float x33 = Var.selectedPosition.x;
				float y33 = Var.selectedPosition.y;
				float z33 = Var.selectedPosition.z;

				float vx = x33 - x22;
				float vy = y33 - y22;
				float vz = z33 - z22;

				Vec3 check = new Vec3(vx, vy, vz).check();

				int xp = (int) check.x;
				int yp = (int) check.y;
				int zp = (int) check.z;

				int rx = x22 + xp;
				int ry = y22 + yp;
				int rz = z22 + zp;
				int posX = (int) Math.abs(position.x);
				int posY = (int) Math.abs(position.y);
				int posZ = (int) Math.abs(position.z);
				if (rx == posX && ry == posY - 1 && rz == posZ)
					return;
				if (ry == posY && rx == posX && rz == posZ)
					return;
				world.addBlock(rx, ry, rz, Block.TESTE, true);
				Chunk.canBreakBlock = false;
			}
		}
	}

	public void gravity() {

		if (!Var.flyMode)
			Var.gravityFactor += 0.01f;

		if (Var.isJumping) {
			Var.jumpFactor += 0.02f;

			move(0, Var.jumpFactor, 0);

		}
		if (Var.jumpFactor > 0.27f) {
			Var.jumpFactor = 0;
			Var.isJumping = false;
		}
		if (gravity) {
			if (Var.grounded) {
				Var.gravityFactor = 0;
			}
			if (!Var.grounded) {

			}

			move(0, -Var.gravityFactor, 0);

		}
	}

	public void actionTimeGestion() {

		Var.InfoSpeedFactor += 0.1f;
		if (Var.InfoSpeedFactor > 1.5f) {
			Var.InfoSpeedFactor = 0;
			updateVBO = true;
		}
		if (Chunk.canBreakBlock == false) {
			Var.breakSpeedFactor += 0.1f;
			if (Var.breakSpeedFactor > 1.5F) {
				Chunk.canBreakBlock = true;
				Var.breakSpeedFactor = 0;
			}
		}
	}

	public void isGrounded(float xDir, float yDir, float zDir) {
		if (!isColliding(0, yDir - 1, 0)) {
			Var.grounded = false;
		} else {
			Var.grounded = true;
		}
	}

	public Vec3 getForward() {
		Vec3 r = new Vec3();
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY() + 90));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY() + 90));
		float cosP = (float) Math.cos(Math.toRadians(rotation.getX()));
		float sinP = (float) Math.sin(Math.toRadians(rotation.getX()));

		r.setX(cosY * cosP);
		r.setY(sinP);
		r.setZ(sinY * cosP);
		return r;
	}

	public Vec3 getRight() {
		Vec3 r = new Vec3();
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY()));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY()));

		r.setX(cosY);
		r.setZ(sinY);

		return r;
	}

}
