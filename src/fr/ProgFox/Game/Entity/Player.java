package fr.ProgFox.Game.Entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import fr.ProgFox.Game.Raycast;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.VBO;
import fr.ProgFox.World.Chunk;
import fr.ProgFox.World.World;
import fr.ProgFox.World.Blocks.Block;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity {
	public Vec3 position;
	public Vec3 rotation;
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
	public Vec3 selectedPosition;
	private boolean teste = true;
	private VBO select;
	private Shader shader;
	int vbo;

	public Player(World world) {
		this.world = world;
		selectedPosition = new Vec3(0, 0, 0);
		raycast = new Raycast(this);
		select = new VBO();
		shader = new ColorShader();
		init();
	}

	public void init() {
		int x2 = (int) 0;
		int y2 = (int) 0;
		int z2 = (int) 0;
		select.init(24, shader);
		select.addVertex(x2, y2, z2, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2, z2, new Vec3(1, 1, 1));

		select.addVertex(x2, y2 + 1, z2, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));

		select.addVertex(x2, y2, z2 + 1, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2, y2, z2, new Vec3(1, 1, 1));
		select.addVertex(x2, y2, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2, y2 + 1, z2, new Vec3(1, 1, 1));
		select.addVertex(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2 + 1, y2, z2, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2, y2, z2, new Vec3(1, 1, 1));
		select.addVertex(x2, y2 + 1, z2, new Vec3(1, 1, 1));

		select.addVertex(x2 + 1, y2, z2, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));

		select.addVertex(x2, y2, z2 + 1, new Vec3(1, 1, 1));
		select.addVertex(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

		select.addVertex(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));
		select.addVertex(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

		glLineWidth(2);
		select.end();

	}

	public void update() {
		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				flyMode = !flyMode;
			}
		}
		raycast.update();
	}

	public void render() {
		int x2 = (int) selectedPosition.x;
		int y2 = (int) selectedPosition.y;
		int z2 = (int) selectedPosition.z;
		if (teste) {

			
			select.clearBuffer();
			select.update(x2, y2, z2, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2, z2, new Vec3(1, 1, 1));

			select.update(x2, y2 + 1, z2, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));

			select.update(x2, y2, z2 + 1, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2, y2, z2, new Vec3(1, 1, 1));
			select.update(x2, y2, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2, y2 + 1, z2, new Vec3(1, 1, 1));
			select.update(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2 + 1, y2, z2, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2, y2, z2, new Vec3(1, 1, 1));
			select.update(x2, y2 + 1, z2, new Vec3(1, 1, 1));

			select.update(x2 + 1, y2, z2, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2 + 1, z2, new Vec3(1, 1, 1));

			select.update(x2, y2, z2 + 1, new Vec3(1, 1, 1));
			select.update(x2, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

			select.update(x2 + 1, y2, z2 + 1, new Vec3(1, 1, 1));
			select.update(x2 + 1, y2 + 1, z2 + 1, new Vec3(1, 1, 1));

			select.updateEnd();
			teste = false;
		}

		select.render(this, GL_LINES);

	}

	float speed = 0.1f;
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

			xDir = getForward().mul(new Vec3(-speed, 0, -speed)).getX();
			zDir = getForward().mul(new Vec3(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {

			xDir = getForward().mul(new Vec3(speed, 0, speed)).getX();
			zDir = getForward().mul(new Vec3(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {

			xDir = getRight().mul(new Vec3(speed, 0, speed)).getX();
			zDir = getRight().mul(new Vec3(speed, 0, speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {

			xDir = getRight().mul(new Vec3(-speed, 0, -speed)).getX();
			zDir = getRight().mul(new Vec3(-speed, 0, -speed)).getZ();
			move(xDir, yDir, zDir);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && grounded && !flyMode) {

			isJumping = true;

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
			selectedPosition = new Vec3(raycast.getBlock(world).x, raycast.getBlock(world).y,
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
	Vec3 lastPos = new Vec3();
	Vec3 nowPos;

	public void removeAndAddBlockGestion() {
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

		if (!flyMode)
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

		breakSpeedFactor += 0.2f;
		if (breakSpeedFactor > 2) {
			breakSpeedFactor = 0;
		}

		InfoSpeedFactor += 0.1f;
		if (InfoSpeedFactor > 1.5f) {
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

		float x0 = -(position.getX() + xDir) - rayon;
		float x1 = -(position.getX() + xDir) + rayon;

		float y0 = -(position.getY() + yDir) - rayon - 0.40f;
		float y1 = -(position.getY() + yDir) + rayon;

		float z0 = -(position.getZ() + zDir) - rayon;
		float z1 = -(position.getZ() + zDir) + rayon;

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

	public Vec3 getDirection() {

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

	public Mat4 getPerspectiveProjection() {
		Transform t = new Transform();

		Transform t2 = new Transform();

		t.rotate(new Vec3(1, 0, 0), -rotation.getX());

		t2.rotate(new Vec3(0, 1, 0), rotation.getY());

		Mat4 p = new Mat4().perspective(70.0F, (float) Display.getWidth() / (float) Display.getHeight(), 0.1F, 1000000.0F);
		return p.mul(t.toMatrix().mul(t2.toMatrix()));

	}

}
