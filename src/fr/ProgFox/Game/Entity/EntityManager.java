package fr.ProgFox.Game.Entity;

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
	public void add(Entity e){
		this.e.add(e);
	}
}
