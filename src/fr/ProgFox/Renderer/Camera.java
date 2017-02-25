package fr.ProgFox.Renderer;

import fr.ProgFox.Game.Entities.LocalPlayer;
import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Game.World.World;
import fr.ProgFox.Inputs.Input;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.ColorShader;
import fr.ProgFox.Renderer.Shader.Shader;
import fr.ProgFox.Utils.UniqueID;

public class Camera {

	public LocalPlayer player;
	World world;
	public Vec3 position, rotation;
	private float fov, zNear, zFar;
	public Shader shader;

	public Camera(Vec3 position, Vec3 rotation, World world, String pseudo) {
		player = new LocalPlayer(world, this, UniqueID.getUniqueID(), pseudo, new Vec3(), new Vec3());
		player.position = position;
		player.rotation = rotation;
		this.shader = new ColorShader();
		this.position = position;
		this.rotation = rotation;
		this.world = world;
	}

	public void update() {
		if (Var.isInFirstPerson) {
			position.x = player.position.x;
			position.y = player.position.y;
			position.z = player.position.z;

		} else {
			float x = (float) (player.position.x + player.getForward().x);
			float y = (float) (player.position.y - player.getForward().y);
			float z = (float) (player.position.z + player.getForward().z);

			float xx = player.getForward().x * 4f;
			float zz = player.getForward().z * 4f;
			x -= xx;
			y += 2;
			z -= zz;

			position = new Vec3(x, y, z);

		}
		rotation = player.rotation;
	}

	public void setPosition(Vec3 position) {

		player.position = position;
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

	public void setPerspectiveProjection(float fov, float zNear, float zFar) {
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;
	}

	public Mat4 getPerspectiveProjection() {
		Transform t = new Transform();

		Transform t2 = new Transform();

		t.rotate(new Vec3(1, 0, 0), -rotation.getX());
		t2.rotate(new Vec3(0, 1, 0), rotation.getY());

		Mat4 p = new Mat4().perspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		return p.mul(t.toMatrix().mul(t2.toMatrix()));

	}

}
