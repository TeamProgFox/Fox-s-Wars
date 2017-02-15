package fr.ProgFox.Game.Entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Variables.Var;

public class SavePlayersConfiguration {
	public Game game;
	public SavePlayersConfiguration(Game game){
		this.game = game;
	}
	
	public void save(){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("saves/Player/Player.tpf"));
			bw.write(Float.toString(game.cam.player.position.x));
			bw.newLine();
			bw.write(Float.toString(game.cam.player.position.y));
			bw.newLine();
			bw.write(Float.toString(game.cam.player.position.z));
			bw.newLine();
			bw.write(Float.toString(game.cam.player.rotation.x));
			bw.newLine();
			bw.write(Float.toString(game.cam.player.rotation.y));
			bw.newLine();
			bw.write(Boolean.toString(Var.flyMode));
			bw.newLine();
			bw.write(Boolean.toString(Var.debugMode));
			bw.newLine();
			bw.write(Boolean.toString(Var.isInFirstPerson));
			bw.newLine();
			bw.write(Boolean.toString(Var.isInThirdPerson));
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
