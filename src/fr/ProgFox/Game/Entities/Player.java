package fr.ProgFox.Game.Entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.Chunk;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Utils.VertexBuffer.CubeLine;

public class Player extends Entity {
	public Vec3 position;
	public Vec3 rotation;
	public boolean gravity = true;
	public World world;
	public Raycast raycast;
	private boolean updateVBO = true;
	private CubeLine select;
	private CubeLine perso;
	int vbo;
	private Camera cam;

	public Player(World world, Camera cam) {
		this.world = world;
		this.cam = cam;
		position = new Vec3();
		Var.selectedPosition = new Vec3(0, 0, 0);
		raycast = new Raycast(this);
		select = new CubeLine(new Vec3(1, 1, 1));
		perso = new CubeLine(new Vec3(0, 0, 0));
		init();
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
			perso.render(GL_LINES, cam, 2);
		select.render(GL_LINES, cam, 2);

	}

	float speed = 0.1f;
	float sensibilite = 3;

	public void input() {
		float xDir = 0, yDir = 0, zDir = 0;
		rotation.addX(-Mouse.getDY() / sensibilite);
		rotation.addY(-Mouse.getDX() / sensibilite);
		if (rotation.getX() > 90)
			rotation.setX(90);
		if (rotation.getX() < -90)
			rotation.setX(-90);

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			speed = 0.8f;
		} else {
			speed = 0.1f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {

			xDir = getForward().mul(new Vec3(speed, 0, speed)).getX();
			zDir = getForward().mul(new Vec3(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {

			xDir = getForward().mul(new Vec3(-speed, 0, -speed)).getX();
			zDir = getForward().mul(new Vec3(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {

			xDir = getRight().mul(new Vec3(-speed, 0, -speed)).getX();
			zDir = getRight().mul(new Vec3(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {

			xDir = getRight().mul(new Vec3(speed, 0, speed)).getX();
			zDir = getRight().mul(new Vec3(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Var.grounded && !Var.flyMode) {

			Var.isJumping = true;

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Var.flyMode) {

			yDir = speed;
			move(0, yDir, 0);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Var.flyMode) {
			yDir = -speed;
			move(0, yDir, 0);
		}

		if (raycast.getBlock(world) != null) {

			Var.selectedBlock = world.getBlock(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			Var.selectedPosition = new Vec3(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			// System.out.println("BLOCK = " + selectedBlock);

		}
		isGrounded(xDir, yDir, zDir);
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
			if (Mouse.isButtonDown(0)) {

				world.removeBlock(Var.selectedPosition.x, Var.selectedPosition.y, Var.selectedPosition.z);
				lastBlock = null;
				Chunk.canBreakBlock = false;
			}
			if (Mouse.isButtonDown(1)) {
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
				world.addBlock(rx, ry, rz, Block.LEAF);
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

	public void move(float xDir, float yDir, float zDir) {
		if (!isColliding(xDir, 0, 0)) {
			position.addX(xDir);
		}
		if (!isColliding(0, yDir, 0)) {
			position.addY(yDir);
		}
		if (!isColliding(0, 0, zDir)) {
			position.addZ(zDir);
		}
		if (Var.flyMode) {
			position.addX(xDir);
			position.addY(yDir);
			position.addZ(zDir);
		}
	}

	public void isGrounded(float xDir, float yDir, float zDir) {
		if (!isColliding(0, yDir - 1, 0)) {
			Var.grounded = false;
		} else {
			Var.grounded = true;
		}
	}

	public boolean isColliding(float xDir, float yDir, float zDir) {

		float rayon = 0.3f;

		float x0 = (position.getX() + xDir) - rayon;
		float x1 = (position.getX() + xDir) + rayon;

		float y0 = (position.getY() + yDir) - rayon - 0.40f;
		float y1 = (position.getY() + yDir) + rayon;

		float z0 = (position.getZ() + zDir) - rayon;
		float z1 = (position.getZ() + zDir) + rayon;

		if (world.getBlock(x0, y0, z0) != null) {
			// System.out.println("1");
			return true;
		}
		if (world.getBlock(x1, y0, z0) != null) {
			// System.out.println("2");
			return true;
		}
		if (world.getBlock(x1, y1, z0) != null) {
			// System.out.println("3");
			return true;
		}
		if (world.getBlock(x0, y1, z0) != null) {
			// System.out.println("4");
			return true;
		}

		if (world.getBlock(x0, y0, z1) != null) {
			// System.out.println("5");
			return true;
		}
		if (world.getBlock(x1, y0, z1) != null) {
			// System.out.println("6");
			return true;
		}
		if (world.getBlock(x1, y1, z1) != null) {
			// System.out.println("7");
			return true;
		}
		if (world.getBlock(x0, y1, z1) != null) {
			// System.out.println("8");
			return true;
		}

		return false;
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
