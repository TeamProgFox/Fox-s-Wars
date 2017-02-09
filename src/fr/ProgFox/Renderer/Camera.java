package fr.ProgFox.Renderer;

import org.lwjgl.opengl.Display;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.World;

public class Camera {

	public Player player;
	World world;
	public Vec3 position, rotation;
	private float fov, zNear, zFar;

	public Camera(Vec3 position, World world) {
		player = new Player(world, this);
		player.position = position;
		player.rotation = new Vec3(0, 0, 0);
		
		this.position = new Vec3();
		this.rotation = new Vec3();
		this.world = world;
	}

	public void update() {
		position.x = -player.position.x;
		position.y = -player.position.y;
		position.z = -player.position.z;
		
		rotation = player.rotation;
	}

	public Vec3 getPosition() {
		return position;
	}
	public Vec3 getRotation() {
		return rotation;
	}

	public void setPosition(Vec3 position) {

		player.position = position;
	}

	public void setPerspectiveProjection(float fov, float zNear, float zFar) {
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;
	}

	public Mat4 getPerspectiveProjection() {
		Transform t = new Transform();

		Transform t2 = new Transform();

		t.rotate(new Vec3(1, 0, 0), -player.rotation.getX());

		t2.rotate(new Vec3(0, 1, 0), player.rotation.getY());

		Mat4 p = new Mat4().perspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		return p.mul(t.toMatrix().mul(t2.toMatrix()));

	}

}
