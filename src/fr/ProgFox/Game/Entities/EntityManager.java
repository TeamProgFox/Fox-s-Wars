package fr.ProgFox.Game.Entities;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
	private List<Entity> e;
	private List<ClientPlayer> cp;
	private List<String> name;

	public EntityManager() {
		e = new ArrayList<Entity>();
		cp = new ArrayList<ClientPlayer>();
		name = new ArrayList<String>();
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

	public Entity getEntity(String name) {
		for (Entity a : e) {
			if (a.name.equals(name))
				return a;
		}
		return null;
	}

	public void add(ClientPlayer cp) {
		this.cp.add(cp);
		this.name.add(cp.name);

	}

	public void updateClientPlayer() {
		for (ClientPlayer a : cp) {
			a.update();
		}
	}

	public void renderClientPlayer() {
		for (ClientPlayer a : cp) {
			a.render();
		}
	}
	public boolean get(String cP){
		if(name.contains(cP))
			return true;
		return false;
	}
	
	public ClientPlayer getPlayer(String name) {
		for (ClientPlayer a : cp) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}
	
}
