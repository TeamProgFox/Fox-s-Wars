package fr.ProgFox.Game.Entities;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends Entity {
	private List<Entity> e;

	public EntityManager() {
		e = new ArrayList<Entity>();
	}

	public void update() {
		for (Entity a : e) {
			a.update();
		}
	}

	public void render() {
		for (Entity a : e) {
			a.render();
		}
	}

	public void add(Entity e) {
		this.e.add((Entity) e);
	}

	public void add(ClientPlayer e) {
		this.e.add((ClientPlayer) e);
	}

	public Entity getEntity(int id) {
		for (Entity a : e) {
			if (a.id == id)
				return a;
		}
		return null;
	}

	public Entity getEntity(String name) {
		for (Entity a : e) {
			if (a.name.equals(name))
				return a;
		}
		return null;
	}

	public ClientPlayer getPlayer(String name) {
		for (Entity a : e) {
			if (a.name.equals(name))
				return (ClientPlayer) a;
		}

		return null;

	}

}
