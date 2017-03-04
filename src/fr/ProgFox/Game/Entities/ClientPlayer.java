package fr.ProgFox.Game.Entities;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Main;
import fr.ProgFox.Game.Game;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Camera;
import fr.ProgFox.Renderer.VertexBuffer.CubeLine;

public class ClientPlayer extends Entity {
	public CubeLine perso;

	public ClientPlayer(int id, String name, Vec3 pos, Vec3 rot) {

		super(pos, rot);

		this.id = id;
		this.name = name;
		perso = new CubeLine(new Vec3());
		position = new Vec3(pos.x, pos.y, pos.z);
		rotation = new Vec3(rot.x, rot.y, rot.z);
		perso.add(0, 0, 0, 0, 0, 0, true);
	}

	public void connect(Game game, String address, int port) {
	}

	public void init() {
	}

	public void update() {
		perso.update(position.x, position.y, position.z, 0.5f, 1.25f, 0.5f, true);
	}

	public void render() {
		Camera cam = Main.getMain().getGame().getCamera();
		if (cam.getPlayer().position.equals(position))
			return;

		perso.render(GL_LINES, 2, cam.getProjectionMatrix(), cam.getTransform(cam.position, cam.rotation), Main.getMain().getShader());

	}

	public void input() {

	}

	public void removeAndAddBlockGestion() {

	}

	public void gravity() {

	}

	public void actionTimeGestion() {

	}

}
