package fr.ProgFox.Game.Entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import fr.ProgFox.Main;
import fr.ProgFox.Game.Variables.Var;

public class SavePlayersConfiguration {
	private LocalPlayer player;

	public SavePlayersConfiguration() {
		player = Main.getMain().getGame().getPlayer();
	}

	public void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("saves/Player/Player.tpf"));
			bw.write(Float.toString(player.position.x));
			bw.newLine();
			bw.write(Float.toString(player.position.y));
			bw.newLine();
			bw.write(Float.toString(player.position.z));
			bw.newLine();
			bw.write(Float.toString(player.rotation.x));
			bw.newLine();
			bw.write(Float.toString(player.rotation.y));
			bw.newLine();
			bw.write(Boolean.toString(Var.flyMode));
			bw.newLine();
			bw.write(Boolean.toString(Var.debugMode));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
