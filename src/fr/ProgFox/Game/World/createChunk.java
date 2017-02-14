package fr.ProgFox.Game.World;

import fr.ProgFox.Game.Variables.Var;

public class createChunk {
	public World world;

	public createChunk(World world) {
		this.world = world;
	}
	
	public void update(){
		System.out.println(Var.generateChunk);
	}

}
