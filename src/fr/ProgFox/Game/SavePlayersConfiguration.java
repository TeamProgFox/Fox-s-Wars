package fr.ProgFox.Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SavePlayersConfiguration {
	public Game game;
	public SavePlayersConfiguration(Game game){
		this.game = game;
	}
	
	public void save(){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("saves/Player/Player.tpf"));
			bw.write(Float.toString(game.cam.position.x));
			bw.newLine();
			bw.write(Float.toString(game.cam.position.y));
			bw.newLine();
			bw.write(Float.toString(game.cam.position.z));
			bw.newLine();
			bw.write(Float.toString(game.cam.rotation.x));
			bw.newLine();
			bw.write(Float.toString(game.cam.rotation.y));
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
