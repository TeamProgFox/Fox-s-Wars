package fr.ProgFox.Game.Entities;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.*;
import fr.ProgFox.Game.*;
import fr.ProgFox.Game.PlayersInventory.*;
import fr.ProgFox.Game.Variables.*;
import fr.ProgFox.Game.World.*;
import fr.ProgFox.Game.World.Blocks.*;
import fr.ProgFox.Inputs.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.GUI.*;
import fr.ProgFox.Renderer.VertexBuffer.*;

public class LocalPlayer extends Entity {

	private boolean gravity = true;
	private boolean updateVBO = true;

	private World world;
	private Raycast raycast;
	private CubeLine select;
	private Inventory playersInventory;
	SimpleText sT;

	public LocalPlayer(World world, String name, Vec3 position, Vec3 rotation) {

		super(position, rotation);
		this.world = world;
		this.name = name;
		Var.selectedPosition = new Vec3(0, 0, 0);
		raycast = new Raycast(this);
		select = new CubeLine(new Vec3(1, 1, 1));
		playersInventory = new Inventory();
		init();
		sT = new SimpleText("", 0, 0, new Vec3(1, 1, 1));
	}

	public void init() {
		int x2 = (int) 0;
		int y2 = (int) 0;
		int z2 = (int) 0;
		select.add(x2, y2, z2, 1, 1, 1, false);

	}

	public void update() {
		input();
		raycast.update();
		if (raycast.getBlock(world) != null) {

			Var.selectedBlock = world.getBlock(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);
			
			Var.selectedPosition = new Vec3(raycast.getBlock(world).x, raycast.getBlock(world).y,
					raycast.getBlock(world).z);

		}
		if (Main.getMain().getInput().getMouse().getDWheel() != 0) {
			if (Main.getMain().getInput().getMouse().getDWheel() > 0) {
				playersInventory.addScrollBar((int) Main.getMain().getInput().getMouse().getDWheel() + 1);
			} else if (Main.getMain().getInput().getMouse().getDWheel() < 0) {
				playersInventory.addScrollBar((int) Main.getMain().getInput().getMouse().getDWheel() - 1);
			}
			sT = new SimpleText(("" + playersInventory.getScrollBar()),100, 200, new Vec3(1, 1, 1));
		}

		playersInventory.update();
	}

	public void render() {
		int x2 = (int) Var.selectedPosition.x;
		int y2 = (int) Var.selectedPosition.y;
		int z2 = (int) Var.selectedPosition.z;
		if (updateVBO) {
			select.update(x2, y2, z2, 1, 1, 1, false);

			updateVBO = false;
		}
		select.render(GL_LINES, 2,
				Main.getMain().getCamera().getProjectionMatrix(), Main.getMain().getCamera()
						.getTransform(Main.getMain().getPlayer().position, Main.getMain().getPlayer().rotation),
				Main.getMain().getShader());

		sT.render();
		playersInventory.render();
	}

	float speed = 0.1f;
	float sensibilite = 3;

	public void input() {
		if (Var.isInMenu)
			return;

		float xDir = 0, yDir = 0, zDir = 0;
		rotation.addX(Main.getMain().getInput().getMouse().getDY() / sensibilite);
		rotation.addY(-Main.getMain().getInput().getMouse().getDX() / sensibilite);
		if (rotation.getX() > 90)
			rotation.setX(90);
		if (rotation.getX() < -90)
			rotation.setX(-90);

		if (Main.getMain().getInput().getKey(Input.KEY_A)) {
			speed = 0.8f;
		} else {
			speed = 0.1f;
		}

		if (Main.getMain().getInput().getKey(Input.KEY_Z)) {

			zDir = speed;
		}
		if (Main.getMain().getInput().getKey(Input.KEY_Q)) {

			xDir = -speed;
		}
		if (Main.getMain().getInput().getKey(Input.KEY_S)) {

			zDir = -speed;
		}
		if (Main.getMain().getInput().getKey(Input.KEY_D)) {

			xDir = speed;
		}
		if (Main.getMain().getInput().getKey(Input.KEY_SPACE) && Var.grounded && !Var.flyMode) {

			Var.isJumping = true;

		}
		if (Main.getMain().getInput().getKey(Input.KEY_SPACE) && Var.flyMode) {

			yDir = speed;

		}
		if (Main.getMain().getInput().getKey(Input.KEY_LEFT_SHIFT) && Var.flyMode) {
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
		if (Main.getMain().getInput().getMouse().getButtonDown(0)) {

			world.removeBlock(Var.selectedPosition.x, Var.selectedPosition.y, Var.selectedPosition.z, true);
			
			if(Var.selectedPosition.equals(new Vec3()))
				Var.selectedBlock = null;
			
			if (Var.selectedBlock != null) {
				playersInventory.addBlockAt(playersInventory.getScrollBar(),
						new Block(Var.selectedBlock.getColor(), Var.selectedBlock.getName()));
			}
			lastBlock = null;
		}
		if (Main.getMain().getInput().getMouse().getButtonDown(1)) {

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
			world.addBlock(rx, ry, rz, playersInventory.getBlockAt(playersInventory.getScrollBar()), true);
			Chunk.canBreakBlock = false;

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

	public Inventory getInventory() {
		return playersInventory;
	}

}
