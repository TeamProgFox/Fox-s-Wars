package fr.ProgFox.Game.Entities;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Math.Vec3;

public class ClientPlayer extends Entity {
	
	public ClientPlayer(int id, String name, Vec3 pos, Vec3 rot) {
		
		super(pos, rot);
		
		this.id = id;
		this.name = name;
		position = new Vec3(pos.x, pos.y, pos.z);
		rotation = new Vec3(rot.x, rot.y, rot.z);
	}

	public void connect(Game game, String address, int port) {
	}

	public void init() {
	}

	public void update() {

	}

	public void render() {

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
