package fr.ProgFox.renderer;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Transform;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.World;
import fr.ProgFox.newMath.Vector3f;

public class Camera {

	private float fov, zNear, zFar;
	public Player player;
	World world;

	public Camera(Vector3f position, World world) {
		player = new Player(world);
		player.position = position;
		player.rotation = new Vector3f(0, 0, 0);
		this.world = world;
		
		
		//TESTE D'ENVOI DE FICHIER !
		
	}

	public void update() {
		player.update();
	}

	public void input() {
		player.input();
	}

	public Vector3f getPosition() {
		return player.position;
	}

	public void gravity() {

	}

	public void setPosition(Vector3f position) {

		player.position = position;
	}

	public Vector3f getDirection() {
		Vector3f r = new Vector3f();
		float cosY = (float) Math.cos(Math.toRadians(player.rotation.getY() + 90));
		float sinY = (float) Math.sin(Math.toRadians(player.rotation.getY() + 90));
		float cosP = (float) Math.cos(Math.toRadians(player.rotation.getX()));
		float sinP = (float) Math.sin(Math.toRadians(player.rotation.getX()));
		r.setX(cosY * cosP);
		r.setY(sinP);
		r.setZ(sinY * cosP);
		return r;
	}
}
