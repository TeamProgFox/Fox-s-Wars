package fr.ProgFox.Game.Entity;

import org.lwjgl.input.Keyboard; 
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.Chunk;
import fr.ProgFox.World.World;
import fr.ProgFox.World.Blocks.Block;
import fr.ProgFox.newMath.Vector3f;

public class Player extends Entity {
	public Vector3f position;
	public Vector3f rotation;
	public boolean gravity = true;
	public boolean grounded = false;
	public float gravityFactor = 0;
	public float jumpFactor = 0;
	public boolean isJumping = false;
	public float timeToJump = 0;
	public World world;
	public boolean flyMode = false;
	public float breakSpeedFactor = 0;
	public float InfoSpeedFactor = 0;
	public float breakSpeedFactor2 = 0;
	public Raycast raycast;
	private Block selectedBlock;
	public Vector3f selectedPosition;
	private boolean teste = true;
	int vbo;

	public Player(World world) {
		this.world = world;
		selectedPosition = new Vector3f(0, 0, 0);
		raycast = new Raycast(this);
	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			flyMode = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
			flyMode = false;
		}
		raycast.update();
	}

	float speed = 0.3f;
	float sensibilite = 3;

	public void input() {
		update();
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

			xDir = getForward().mul(new Vector3f(-speed, 0, -speed)).getX();
			zDir = getForward().mul(new Vector3f(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {

			xDir = getForward().mul(new Vector3f(speed, 0, speed)).getX();
			zDir = getForward().mul(new Vector3f(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {

			xDir = getRight().mul(new Vector3f(speed, 0, speed)).getX();
			zDir = getRight().mul(new Vector3f(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {

			xDir = getRight().mul(new Vector3f(-speed, 0, -speed)).getX();
			zDir = getRight().mul(new Vector3f(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && grounded && timeToJump > 1 && !flyMode) {

			isJumping = true;
			timeToJump = 0;

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && flyMode) {

			yDir = -speed;
			move(0, yDir, 0);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && flyMode) {
			yDir = speed;
			move(0, yDir, 0);
		}

		if (raycast.getBlock(world) != null) {

			selectedBlock = world.getBlock(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			selectedPosition = new Vector3f(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			// System.out.println("BLOCK = " + selectedBlock);

		}
		teste(xDir, yDir, zDir);
		removeAndAddBlockGestion();
		actionTimeGestion();
		if (!flyMode)
			gravity();
	}

	Block lastBlock;
	Vector3f lastPos = new Vector3f();
	Vector3f nowPos;

	public void removeAndAddBlockGestion() {
		nowPos = selectedPosition;

		if (teste) {
			if (world.getBlock(nowPos.x, nowPos.y, nowPos.z) != Block.INFO) {
				world.addSelectBlock(nowPos.x, nowPos.y, nowPos.z, Block.INFO);
				// System.out.println("LAST BLOCK = " + lastBlock);
				// System.out.println("LAST POS = " + (int) lastPos.x + "/" +
				// (int) lastPos.y + "/" + (int) lastPos.z);
				if (selectedBlock != Block.INFO) {
					world.addSelectBlock(lastPos.x, lastPos.y, lastPos.z, lastBlock);
				}
			}
			if (selectedBlock != Block.INFO) {
				lastBlock = selectedBlock;
				lastPos = nowPos;
			}
			teste = false;
		}
		// System.out.println(selectedPosition.y + " / " + (int)
		// selectedPosition.y);

		// System.out.println(selectedPosition.y);
		// System.out.println((int)selectedPosition.y + 0.3f);*

		if (Chunk.canBreakBlock) {
			if (Mouse.isButtonDown(0)) {

				world.removeBlock(selectedPosition.x, selectedPosition.y, selectedPosition.z);
				lastBlock = null;
				Chunk.canBreakBlock = false;
			}
			if (Mouse.isButtonDown(1)) {
				int x22 = (int) selectedPosition.x;
				int y22 = (int) selectedPosition.y;
				int z22 = (int) selectedPosition.z;

				float x33 = selectedPosition.x;
				float y33 = selectedPosition.y;
				float z33 = selectedPosition.z;

				float vx = x33 - x22;
				float vy = y33 - y22;
				float vz = z33 - z22;

				Vector3f check = new Vector3f(vx, vy, vz).check();

				int xp = (int) check.x;
				int yp = (int) check.y;
				int zp = (int) check.z;

				int rx = x22 + xp;
				int ry = y22 + yp;
				int rz = z22 + zp;

				world.addBlock(rx, ry, rz, Block.LEAF);
				Chunk.canBreakBlock = false;
			}
		}
	}

	public void gravity() {
		gravityFactor += 0.01f;
		if (isJumping) {
			jumpFactor += 0.02f;

			move(0, -jumpFactor, 0);

		}
		if (jumpFactor > 0.27f) {
			jumpFactor = 0;
			isJumping = false;
		}
		if (gravity) {
			if (grounded) {
				gravityFactor = 0;
			}
			if (!grounded) {

			}

			move(0, gravityFactor, 0);

		}
		if (grounded) {
			timeToJump += 0.1f;
		}
	}

	public void actionTimeGestion() {

		breakSpeedFactor += 0.1f;
		if (breakSpeedFactor > 2) {
			breakSpeedFactor = 0;
		}

		InfoSpeedFactor += 0.1f;
		if (InfoSpeedFactor > 0.75f) {
			InfoSpeedFactor = 0;
			teste = true;
		}
		if (Chunk.canBreakBlock == false) {
			breakSpeedFactor2 += 0.1f;
			if (breakSpeedFactor2 > 1.5F) {
				Chunk.canBreakBlock = true;
				breakSpeedFactor2 = 0;
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
		if (flyMode) {
			position.addX(xDir);
			position.addY(yDir);
			position.addZ(zDir);
		}
	}

	public void teste(float xDir, float yDir, float zDir) {
		if (!isColliding(0, yDir + 1, 0)) {
			grounded = false;
		} else {
			grounded = true;
		}
	}

	public boolean isColliding(float xDir, float yDir, float zDir) {

		float rayon = 0.3f;

		float x0 = Math.abs(position.getX() + xDir) - rayon;
		float x1 = Math.abs(position.getX() + xDir) + rayon;

		float y0 = Math.abs(position.getY() + yDir) - rayon - 0.40f;
		float y1 = Math.abs(position.getY() + yDir) + rayon;

		float z0 = Math.abs(position.getZ() + zDir) - rayon;
		float z1 = Math.abs(position.getZ() + zDir) + rayon;

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

	public Vector3f getForward() {
		Vector3f r = new Vector3f();
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY() + 90));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY() + 90));
		float cosP = (float) Math.cos(Math.toRadians(rotation.getX()));
		float sinP = (float) Math.sin(Math.toRadians(rotation.getX()));

		r.setX(cosY * cosP);
		r.setY(sinP);
		r.setZ(sinY * cosP);
		return r;
	}

	public Vector3f getRight() {
		Vector3f r = new Vector3f();
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY()));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY()));

		r.setX(cosY);
		r.setZ(sinY);

		return r;
	}

	public Vector3f getDirection() {

		Vector3f r = new Vector3f();
		float cosY = (float) Math.cos(Math.toRadians(rotation.getY() - 90));
		float sinY = (float) Math.sin(Math.toRadians(rotation.getY() - 90));
		float cosP = (float) Math.cos(Math.toRadians(-rotation.getX()));
		float sinP = (float) Math.sin(Math.toRadians(-rotation.getX()));

		r.setX(cosY * cosP);
		r.setY(sinP);
		r.setZ(sinY * cosP);
		r.normalize();
		return r;

	}

	public Mat4 getPerspectiveProjection() {
		Transform t = new Transform();

		Transform t2 = new Transform();

		t.rotate(new Vec3(1, 0, 0), -rotation.getX());

		t2.rotate(new Vec3(0, 1, 0), rotation.getY());

		Mat4 p = new Mat4().perspective(70.0F, Display.getWidth() / Display.getHeight(), 0.1F, 1000.0F);
		return p.mul(t.toMatrix().mul(t2.toMatrix()));
	}

}
