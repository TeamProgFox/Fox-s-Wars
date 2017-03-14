package fr.ProgFox.Renderer;

import fr.ProgFox.*;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;

public class Camera {

	private float fov, zNear, zFar;

	public Camera() {
	}

	public void update() {
		// if (Var.isInFirstPerson) {
		// position.x = player.position.x;
		// position.y = player.position.y;
		// position.z = player.position.z;
		//
		// } else {
		// float x = (float) (player.position.x + player.getForward().x);
		// float y = (float) (player.position.y - player.getForward().y);
		// float z = (float) (player.position.z + player.getForward().z);
		//
		// float xx = player.getForward().x * 4f;
		// float zz = player.getForward().z * 4f;
		// x -= xx;
		// y += 2;
		// z -= zz;
		//
		// position = new Vec3(x, y, z);
		//
		// }
		// rotation = player.rotation;
	}

	public void setPosition(Vec3 position) {

		// player.position = position;
	}

	public Vec3 getForward() {
		Vec3 r = new Vec3();
		float cosY = (float) Math.cos(Math.toRadians(Main.getMain().getGame().getPlayer().rotation.getY() + 90));
		float sinY = (float) Math.sin(Math.toRadians(Main.getMain().getGame().getPlayer().rotation.getY() + 90));
		float cosP = (float) Math.cos(Math.toRadians(Main.getMain().getGame().getPlayer().rotation.getX()));
		float sinP = (float) Math.sin(Math.toRadians(Main.getMain().getGame().getPlayer().rotation.getX()));

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

	public Mat4 getProjectionMatrix() {
		Transform t = new Transform();

		Transform t2 = new Transform();

		t.rotate(new Vec3(1, 0, 0), -Main.getMain().getGame().getPlayer().rotation.getX());
		t2.rotate(new Vec3(0, 1, 0), Main.getMain().getGame().getPlayer().rotation.getY());

		Mat4 p = new Mat4().perspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		return p;

	}

	public Mat4 getModelViewMatrix(Vec3 pos, Vec3 rot) {
		Transform translate = new Transform();
		Transform t = new Transform();
		Transform t2 = new Transform();
		translate.setLocalPosition(new Vec3(-pos.x, -pos.y, -pos.z));
		t.rotate(new Vec3(1, 0, 0), -rot.getX());
		t2.rotate(new Vec3(0, 1, 0), rot.getY());

		return t.toMatrix().mul(t2.toMatrix().mul(translate.toMatrix()));

	}

	public Mat4 getTransform2(Vec3 pos, Vec3 rot) {
		Transform translate = new Transform();
		Transform t = new Transform();
		Transform t2 = new Transform();
		translate.setLocalPosition(new Vec3(pos.x, pos.y, pos.z));
		t.rotate(new Vec3(1, 0, 0), -rot.getX());
		t2.rotate(new Vec3(0, 1, 0), rot.getY());

		return t.toMatrix().mul(t2.toMatrix().mul(translate.toMatrix()));

	}

	// public LocalPlayer getPlayer() {
	// return player;
	// }

}
